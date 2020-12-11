<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">
    <html>
    <head>
        <title><fmt:message key="make.order.title"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
    </head>
    <body>
    <div class="wrapper">

        <%@ include file="parts/header.jsp" %>
        <main>
            <%@ include file="parts/aside_menu.jsp" %>
            <div class="content">
                <h2><fmt:message key="make.order.total.amount"/> ${sessionScope.orderCost}</h2>
                <div class="menu_content">


                        <input type="hidden" name="command" value="SAVE_ORDER"/>

                        <label for="date_input"><fmt:message key="make.order.date"/></label>
                        <input form="form_content" id="date_input" type="text" name="date" pattern="\d{2}-\d{2}-\d{4}\s\d{2}:\d{2}"
                               required><br/>

                        <table id="customers">
                            <tr>
                                <th><fmt:message key="user.menu.dish.image"/></th>
                                <th><fmt:message key="user.menu.dish.title"/></th>
                                <th><fmt:message key="user.menu.dish.price"/></th>
                                <th></th>
                                <th>
                                    <form id="form_content" method="POST" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="SAVE_ORDER"/>
                                    <button type="submit"><fmt:message key="user.menu.make.order.button"/></button>
                                    </form>
                                </th>
                            </tr>


                            <c:forEach var="orderDish" items="${sessionScope.basket}">
                                <tr>
                                    <td><img src="${orderDish.imgUrl}" alt="dish"></td>
                                    <td>${orderDish.name}</td>
                                    <td>${orderDish.cost}</td>
                                    <td></td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="CANCEL_DISH"/>
                                            <input type="hidden" name="dishId" value="${orderDish.id}"/>
                                            <button type="submit"><fmt:message key="make.order.cancel"/></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                </div>

            </div>


        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
