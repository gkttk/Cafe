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
        <header>
            <div class="header_logo">
                <h1><fmt:message key="header.logo"/></h1>
            </div>

            <div class="header_buttons">
                <div class="header_nav">
                    <div class="about">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="HOME"/>
                            <button type="submit">
                                <fmt:message key="header.home.button"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </header>

        <main>
            <div class="content">
                <img id="errorImg" src="static/images/error.jpg" alt="error"/>
            </div>

        </main>
        <%@ include file="WEB-INF/view/parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
