<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">

    <html>
    <head>
        <title><fmt:message key="index.title"/></title>
        <link rel="stylesheet" type="text/css" href="static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="static/js/script.js"></script>
    </head>
    <body>
    <c:if test="${sessionScope.authUser != null}">
        <jsp:forward page="WEB-INF/view/user_menu.jsp"/>
    </c:if>
    <div class="wrapper">
        <%@ include file="WEB-INF/view/parts/header.jsp" %>

        <p>
            <c:if test="${not empty requestScope.message}">
                <fmt:message key="${requestScope.message}"/>
            </c:if>
            <c:if test="${not empty sessionScope.message}">
                <fmt:message key="${sessionScope.message}"/>
            </c:if>
        </p>

        <main>
        </main>
        <%@ include file="WEB-INF/view/parts/footer.jsp" %>
    </div>
    </body>

</fmt:bundle>


</html>
