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
                <c:if test="${not empty requestScope.noMoneyErrorMessage}">
                    <fmt:message key="${requestScope.noMoneyErrorMessage}"/>
                </c:if>
                <div class="menu_content">
                    <table id="customers">
                        <tr>
                            <th>Идентификатор</th>
                            <th>Стоимость</th>
                            <th>Время</th>
                            <th>Активность</th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="order" items="${sessionScope.orders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.cost}</td>
                                <td>${order.date}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.status == 'ACTIVE'}">
                                            Активен
                                        </c:when>
                                        <c:when test="${order.status == 'RETRIEVED'}">
                                            Выдан
                                        </c:when>
                                        <c:when test="${order.status == 'CANCELLED'}">
                                            Отменен
                                        </c:when>
                                        <c:otherwise>
                                            Закрыт
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'ACTIVE'}">
                                    <form method="POST" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="TAKE_ORDER"/>
                                       <input type="hidden" name="orderId" value="${order.id}"/>
                                        <button type="submit">
                                            Забрать заказ
                                        </button>
                                    </form>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'ACTIVE'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="CANCEL_ORDER"/>
                                            <input type="hidden" name="orderId" value="${order.id}"/>
                                        <button type="submit">Отмена</button>
                                    </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
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
