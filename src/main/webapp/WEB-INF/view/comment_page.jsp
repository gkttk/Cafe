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
                <h2>COMMENT_PAGE</h2>
                <div class="menu_content">

                </div>
            </div>
        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
