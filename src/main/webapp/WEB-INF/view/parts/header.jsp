<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="i18n/message">
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

            <div class="dropdown">

                <button class="dropbtn"><fmt:message key="header.language.button"/></button>
                <div class="dropdown-content">
                    <form action="${pageContext.request.contextPath}/controller" method="POST">
                        <input type="hidden" name="command" value="LOCALE">
                        <input type="hidden" name="lang" value="ru">
                        <button type="submit"><fmt:message key="header.ru.button"/></button>
                    </form>
                    <form action="${pageContext.request.contextPath}/controller" method="POST">
                        <input type="hidden" name="command" value="LOCALE">
                        <input type="hidden" name="lang" value="en">
                        <button type="submit"><fmt:message key="header.en.button"/></button>
                    </form>
                    <form action="${pageContext.request.contextPath}/controller" method="POST">
                        <input type="hidden" name="command" value="LOCALE">
                        <input type="hidden" name="lang" value="by">
                        <button type="submit"><fmt:message key="header.by.button"/></button>
                    </form>
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
                <input type="hidden" name="command" value="LOGIN">
                <label for="login"><fmt:message key="header.login.label"/></label>
                <input id="login" type="text" name="login" required>
                <label for="psw"><fmt:message key="header.password.label"/></label>
                <input id="psw" type="password" name="password" required>
                <div class="buttons">
                    <button type="submit" class="btn"><fmt:message key="header.sign.in.button"/></button>
                    <button form="registration_form" type="submit" class="btn">
                        <fmt:message key="header.sign.up.button"/>
                    </button>
                    <button type="submit" class="btn cancel" onclick="closeForm()">
                        <fmt:message key="header.close.form.button"/></button>
                </div>
            </form>

            <form id="registration_form" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="REGISTRATION_PAGE">
            </form>
        </div>
        </c:when>
        <c:otherwise>
        <div class="header_greeting">
            <div class="user_greeting"><h4><fmt:message
                    key="header.user.greetings"/> ${sessionScope.authUser.login}!</h4>
            </div>
            <div class="user_points">
                <fmt:message key="header.user.points"/> ${sessionScope.authUser.points}
            </div>
            <div class="user_points">
                Деньги на счете: ${sessionScope.authUser.money}
            </div>
            <div class="log_out">
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="LOGOUT">
                    <button class="logout_btn" type="submit"><fmt:message key="header.logout.button"/></button>
                </form>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
        </fmt:bundle>
</header>