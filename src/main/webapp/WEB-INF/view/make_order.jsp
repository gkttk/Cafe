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
            <jsp:include page="parts/aside_menu.jsp"/>
            <div class="content">
                <h2><fmt:message key="make.order.your.order"/></h2>
                <div class="menu_content">
                    <form id="form_content" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="saveOrder"/>

                        <input form="form_content" type="submit"
                               value="<fmt:message key="user.menu.make.order.button"/>"/>

                        <c:forEach var="orderDish" items="${sessionScope.orderDishes}">
                        <div class="menu_item">
                            <img src="${orderDish.imgUrl}" alt="dish">
                            <span>${orderDish.name}</span>
                            <span>${orderDish.cost}</span>
                        </div>
                        </c:forEach>
                        <h2>Общая сумма заказа: ${sessionScope.orderCost}</h2>
                        <label for="date_input"><fmt:message key="make.order.date"/></label>
                        <input id="date_input" type="text" name="date" pattern="\d{2}-\d{2}-\d{4}\s\d{2}:\d{2}" required><br/>


                    </form>
                </div>

            </div>


        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
