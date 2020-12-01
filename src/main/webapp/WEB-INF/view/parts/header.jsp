<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<header>
    <div class="header_logo">
        <h1><fmt:message key="header.logo"/></h1>
    </div>

    <div class="header_buttons">
        <div class="header_nav">
            <div class="about">
                <a href=""><fmt:message key="header.home.button"/></a>
            </div>

            <div class="dropdown">

                <button class="dropbtn"><fmt:message key="header.language.button"/></button>
                <div class="dropdown-content">

                    <a href="#"><fmt:message key="header.ru.button"/></a>
                    <a href="#"><fmt:message key="header.en.button"/></a>
                    <a href="#"><fmt:message key="header.by.button"/></a>

                </div>
            </div>
        </div>

        <c:choose>
        <c:when test="${sessionScope.authUser == null}">
        <div class="header_button">
            <ul>
                <li><a class="sign_up" onclick="openForm()"><fmt:message
                        key="header.sign.in.or.sign.up.button"/></a></li>
            </ul>
        </div>

        <div class="form-popup" id="myForm">
            <form class="form-container" action="${pageContext.request.contextPath}/controller" method="POST">
                <label for="login"><fmt:message key="header.login.label"/></label>
                <input id="login" type="text" name="login" required>
                <label for="psw"><fmt:message key="header.password.label"/></label>
                <input id="psw" type="password" name="psw" required>
                <div class="buttons">
                    <button type="submit" class="btn"><fmt:message key="header.sign.in.button"/></button>
                    <button type="submit" class="btn"><fmt:message key="header.sign.up.button"/></button>
                    <button type="submit" class="btn cancel" onclick="closeForm()">
                        <fmt:message key="header.close.form.button"/></button>
                </div>
            </form>
        </div>
        </c:when>
        <c:otherwise>
        <div class="header_greeting">
            <div class="user_greeting"><h4><fmt:message
                    key="header.user.greetings"/> ${sessionScope.authUser.getLogin()}!</h4></div>
            <div class="user_points"><fmt:message key="header.user.points"/> ${sessionScope.authUser.getPoints()}</div>
            <div class="log_out">
                <a href="#" class="Log out"><fmt:message key="header.logout.button"/></a>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
</header>