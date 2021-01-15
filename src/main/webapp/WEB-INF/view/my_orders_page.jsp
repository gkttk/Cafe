<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="WEB-INF/tlds/datetimeformatter" prefix="f" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">
    <html>
    <head>
        <title><fmt:message key="my.orders.page.title"/></title>
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
                <c:if test="${not empty requestScope.message}">
                    <fmt:message key="${requestScope.message}"/>
                </c:if>
                <div class="order_conditions">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="MY_ORDERS"/>
                        <input type="hidden" name="sortType" value="ACTIVE"/>
                        <button type="submit">
                            <fmt:message key="my.orders.active"/>
                        </button>
                    </form>
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="MY_ORDERS"/>
                        <input type="hidden" name="sortType" value="EXPIRED"/>
                        <button type="submit">
                            <fmt:message key="my.orders.history"/>
                        </button>
                    </form>
                </div>
                <div class="menu_content">
                    <table id="customers">
                        <tr>
                            <th><fmt:message key="my.orders.id"/></th>
                            <th><fmt:message key="my.orders.cost"/></th>
                            <th><fmt:message key="my.orders.date"/></th>
                            <th><fmt:message key="my.orders.status"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="order" items="${sessionScope.orders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.cost}</td>
                                <td>
                                        ${f:formatLocalDateTimeDefault(order.date)}
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.status == 'ACTIVE'}">
                                            <fmt:message key="my.orders.active.status"/>
                                        </c:when>
                                        <c:when test="${order.status == 'RETRIEVED'}">
                                            <fmt:message key="my.orders.retrieved.status"/>
                                        </c:when>
                                        <c:when test="${order.status == 'CANCELLED'}">
                                            <fmt:message key="my.orders.cancelled.status"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="my.orders.blocked.status"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'ACTIVE'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="TAKE_ORDER"/>
                                            <input type="hidden" name="orderId" value="${order.id}"/>
                                            <button type="submit">
                                                <fmt:message key="my.orders.take"/>
                                            </button>
                                        </form>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'ACTIVE'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="CANCEL_ORDER"/>
                                            <input type="hidden" name="orderId" value="${order.id}"/>
                                            <button type="submit">
                                                <fmt:message key="my.orders.cancel"/>
                                            </button>
                                        </form>
                                    </c:if>
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
