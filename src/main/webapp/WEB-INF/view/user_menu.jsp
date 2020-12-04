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
    <div class="wrapper">
        <%@ include file="parts/header.jsp" %>
        <main>
            <c:set var="currentPage" value="MENU" scope="session"/>
            <%@ include file="parts/aside_menu.jsp" %>
            <div class="content">
                <div class="meal_types">
                    <button class="btn"><fmt:message key="user.menu.main.dishes.button"/></button>
                    <button class="btn"><fmt:message key="user.menu.salads.button"/></button>
                    <button class="btn"><fmt:message key="user.menu.beverages.button"/></button>
                </div>

                <div class="menu_content">
                    <form id="form_content" method="POST" action="#">

                        <c:forEach var="dish" items="${sessionScope.dishes}">
                            <div class="menu_item">
                                <img src="${dish.imgUrl}" alt="dish"/>
                                <span>${dish.name}</span>
                                <span>${dish.cost}</span>
                                <!-- if user == USER -->
                                <c:choose>
                                    <c:when test="${sessionScope.authUser.role == 'ADMIN'}">
                                        <button class="btn"><fmt:message key="user.menu.update.dish.button"/></button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn"><fmt:message key="user.menu.comments.button"/></button>
                                    </c:otherwise>
                                </c:choose>
                                <input type="checkbox" name="orderDishes" value="${dish.id}"/>
                            </div>
                        </c:forEach>
                        <input form="form_content" type="submit"
                               value="<fmt:message key="user.menu.make.order.button"/>"/>
                    </form>
                </div>
            </div>
        </main>
        <%@ include file="parts/footer.jsp" %>

    </div>
    </body>
</fmt:bundle>
</html>
