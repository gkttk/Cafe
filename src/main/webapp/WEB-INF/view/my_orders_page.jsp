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
                <h2>MY_ORDER_PAGE</h2>
                <div class="menu_content">
                    <div class="users">
                        <c:forEach var="order" items="${sessionScope.orders}">
                            <div class="user_item">
                                <div>${order.id}</div>
                                <div>${order.cost}</div>
                                <div>${order.time}</div>
                                <div>${order.active}</div>
                                <div class="info_div">
                                        <button class="accordion" onclick="openPanel()">Информация</button>
                                    <div class="panel">
                                        <div>
                                            Выпадающий блок
                                        </div>
                                    </div>
                                </div>

                                <div>
                                    <form method="post" action="#">
                                        <button type="submit">Отмена</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <script>
                function openPanel() {
                    var acc = document.getElementsByClassName("accordion");
                    var i;

                    for (i = 0; i < acc.length; i++) {
                        acc[i].addEventListener("click", function () {
                            this.classList.toggle("active");
                            var panel = this.nextElementSibling;
                            if (panel.style.maxHeight) {
                                panel.style.maxHeight = null;
                            } else {
                                panel.style.maxHeight = panel.scrollHeight + "px";
                            }
                        });
                    }
                }
            </script>
        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
