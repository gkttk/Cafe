<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">
    <html>
    <head>
        <title><fmt:message key="registration.page.title"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
    </head>
    <body>
    <div class="wrapper">

        <%@ include file="parts/header.jsp" %>
        <p>
            <c:if test="${not empty requestScope.message}">
                <fmt:message key="${requestScope.message}"/>
            </c:if>
        </p>
        <main>
            <div id="registrationContent">
                <div class="menu_content">
                    <h2><fmt:message key="registration.registration"/></h2>
                    <form id="form_content" method="POST" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="REGISTRATION"/>
                        <label for="login_reg"><fmt:message key="registration.login"/></label>
                        <input id="login_reg" type="text" name="login" pattern="\D([a-zA-Z1-9]{3,9})"
                               title="Must contain first letter, and amount 4-10 symbols" required/>
                        <label for="password_reg"><fmt:message key="registration.password"/></label>
                        <input id="password_reg" type="password" name="password"
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{5,10}"
                               title="Must contain at least one number and one uppercase and lowercase letter, and 5-10 symbols"
                               required/>
                        <button type="submit"><fmt:message key="registration.sign.up"/></button>
                    </form>
                </div>
            </div>
        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
