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
                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="command" value="SORT_USERS"/>
                            <button type="submit"><fmt:message key="users.page.all"/></button>
                        </form>
                    </div>
                    <div>
                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="command" value="SORT_USERS"/>
                            <input type="hidden" name="userStatus" value="ACTIVE"/>
                            <button type="submit"><fmt:message key="users.page.active"/></button>
                        </form>
                    </div>
                    <div>
                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="command" value="SORT_USERS"/>
                            <input type="hidden" name="userStatus" value="BLOCKED"/>
                            <button type="submit"><fmt:message key="users.page.blocked"/></button>
                        </form>
                    </div>
                </div>

                <div class="users">
                    <table id="customers">
                        <tr>
                            <th><fmt:message key="users.page.login"/></th>
                            <th><fmt:message key="users.page.points"/></th>
                            <th><fmt:message key="users.page.role"/></th>
                            <th><fmt:message key="users.page.status"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:set var="idForChangePointsDiv" value="1"/>
                        <c:forEach var="user" items="${sessionScope.users}">
                            <tr>
                                <td>${user.login}</td>
                                <td>${user.points}</td>
                                <td>${user.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.blocked}">
                                            <fmt:message key="users.page.blocked"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="users.page.active"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a class="littleButton" onclick="showChangePointsDiv(${idForChangePointsDiv})">
                                        <fmt:message key="users.page.change.points"/>
                                    </a>
                                    <div id="changePointsDiv${idForChangePointsDiv}" style="display: none">
                                        <form method="POST" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="CHANGE_POINTS"/>
                                            <input type="hidden" name="userId" value="${user.id}"/>
                                            <button class="littleButton" type="submit" name="isAdd" value="false">
                                                -
                                            </button>
                                            <input type="number" name="points" value="15" min="0" max="1000" step="5"/>
                                            <button class="littleButton" type="submit" name="isAdd" value="true">
                                                +
                                            </button>
                                        </form>
                                    </div>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.blocked}">
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="userId" value="${user.id}">
                                                <input type="hidden" name="blocked" value="false">
                                                <input type="hidden" name="command" value="CHANGE_STATUS">
                                                <button class="littleButton" type="submit">
                                                    <fmt:message key="users.page.unblock"/>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="userId" value="${user.id}">
                                                <input type="hidden" name="blocked" value="true">
                                                <input type="hidden" name="command" value="CHANGE_STATUS">
                                                <button class="littleButton" type="submit">
                                                    <fmt:message key="users.page.block"/>
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:set var="idForChangePointsDiv" value="${idForChangePointsDiv + 1}"/>
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
