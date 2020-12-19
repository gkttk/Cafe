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
            <%@ include file="parts/aside_menu.jsp" %>

            <div class="content">
                <div class="user_conditions">
                    <div>
                        <form method="post" action="#">
                            <button type="submit">ВСЕ</button>
                        </form>
                    </div>
                    <div>
                        <form method="post" action="#">
                            <button type="submit"><fmt:message key="users.page.active"/></button>
                        </form>
                    </div>
                    <div>
                        <form method="post" action="#">
                            <button type="submit"><fmt:message key="users.page.blocked"/></button>
                        </form>
                    </div>
                </div>

                <div class="users">
                    <table id="customers">
                        <tr>
                            <th>Логин</th>
                            <th>Баллы</th>
                            <th>Роль</th>
                            <th>Активность</th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="user" items="${sessionScope.users}">
                            <tr>
                                <td>${user.login}</td>
                                <td>${user.points}</td>
                                <td>${user.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.blocked}">
                                            Заблокирован
                                        </c:when>
                                        <c:otherwise>
                                            Активен
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="blocked" value="true">
                                        <input type="hidden" name="command" value="CHANGE_STATUS">
                                        <button type="submit"><fmt:message key="users.page.block"/></button>
                                    </form>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="blocked" value="false">
                                        <input type="hidden" name="command" value="CHANGE_STATUS">
                                        <button type="submit"><fmt:message key="users.page.unblock"/></button>
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
