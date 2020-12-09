<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">
    <html>
    <head>
        <title><fmt:message key="user.menu.title"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
    </head>
    <body>
<%--    <c:set var="currentPage" value="MENU" scope="session"/>--%>
    <div class="wrapper">
        <%@ include file="parts/header.jsp" %>
        <main>
            <%@ include file="parts/aside_menu.jsp" %>
            <div class="content">
                    ${sessionScope.orderMessage}
                <div class="meal_types">
                    <button class="btn">ВСЕ</button>
                    <button class="btn"><fmt:message key="user.menu.main.dishes.button"/></button>
                    <button class="btn"><fmt:message key="user.menu.salads.button"/></button>
                    <button class="btn"><fmt:message key="user.menu.beverages.button"/></button>
                </div>
                        <p style="text-align: center">${requestScope.errorMessage}</p>
                <div class="menu_content">
                    <form id="form_content" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="FORM_ORDER"/>

                        <table id="customers">
                            <tr>
                                <th>Изображение</th>
                                <th>Название</th>
                                <th>Цена</th>
                                <th colspan="2">
                                    <input form="form_content" type="submit"
                                           value="<fmt:message key="user.menu.make.order.button"/>"/>
                                </th>
                            </tr>

                        <c:forEach var="dish" items="${sessionScope.dishes}">
                            <tr>
                                <td><img src="${dish.imgUrl}" alt="dish"></td>
                                <td>${dish.name}</td>
                                <td>${dish.cost}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sessionScope.authUser.role == 'ADMIN'}">
                                            <button class="btn"><fmt:message key="user.menu.update.dish.button"/></button>
                                        </c:when>
                                        <c:otherwise>
                                            <form id="dish_comments_form" method="POST" action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="DISH_COMMENTS"/>
                                                <input type="hidden" name="dishId" value="${dish.id}"/>
                                                <button form="dish_comments_form" type="submit" class="btn"><fmt:message key="user.menu.comments.button"/></button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                   </td>
                                <td><input type="checkbox" name="orderDishes" value="${dish.id}"/></td>
                            </tr>
                           </c:forEach>
                        </table>
                    </form>
                </div>
            </div>
        </main>
        <%@ include file="parts/footer.jsp" %>

    </div>
    </body>
</fmt:bundle>
</html>
