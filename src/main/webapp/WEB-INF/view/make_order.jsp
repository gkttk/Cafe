<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="i18n/message">
    <fmt:setLocale value="en"/>
    <html>
    <head>
        <title><fmt:message key="make.order.title"/></title>
        <link rel="stylesheet" type="text/css" href="../../static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="../../static/css/common.css"/>
        <script type="text/javascript" src="../../static/js/script.js"></script>
    </head>
    <body>
    <div class="wrapper">

        <%@ include file="parts/header.jsp" %>
        <main>
            <jsp:include page="parts/aside_menu.jsp"/>
            <div class="content">
                <h2><fmt:message key="make.order.your.order"/></h2>
                <div class="menu_content">
                    <form id="form_content" method="POST" action="#">
                        <div class="menu_item">
                            <img src="../../static/images/dishes.jpg" alt="dish">
                            <span>${orderDish.getName()}</span>
                            <span>${orderDish.getCost()}</span>
                        </div>

                        <label for="date_input"><fmt:message key="make.order.date"/></label>
                        <input id="date_input" type="text" name="date" required><br/>

                        <input form="form_content" type="submit"
                               value="<fmt:message key="user.menu.make.order.button"/>"/>
                    </form>
                </div>

            </div>


        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
