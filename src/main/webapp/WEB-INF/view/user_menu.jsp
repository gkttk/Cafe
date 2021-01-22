<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="dt" uri="WEB-INF/tlds/dishtypestag" %>
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

                <div class="meal_types">
                    <form method="get" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_DISHES"/>
                        <button class="btn"><fmt:message key="user.menu.all.dishes"/></button>
                    </form>
                    <form method="get" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_DISHES"/>
                        <input type="hidden" name="dishType" value="SOUP"/>
                        <button type="submit" class="btn"><fmt:message key="user.menu.soups.button"/></button>
                    </form>
                    <form method="get" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_DISHES"/>
                        <input type="hidden" name="dishType" value="SALAD"/>
                        <button class="btn"><fmt:message key="user.menu.salads.button"/></button>
                    </form>
                    <form method="get" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_DISHES"/>
                        <input type="hidden" name="dishType" value="BEVERAGE"/>
                        <button class="btn"><fmt:message key="user.menu.beverages.button"/></button>
                    </form>
                </div>
                <c:if test="${not empty requestScope.message}">
                    <h4><fmt:message key="${requestScope.message}"/></h4>
                </c:if>
                <c:if test="${not empty sessionScope.message}">
                    <h4><fmt:message key="${sessionScope.message}"/></h4>
                </c:if>

                <div class="menu_content">
                    <table id="customers">
                        <tr>
                            <th><fmt:message key="user.menu.dish.image"/></th>
                            <th><fmt:message key="user.menu.dish.title"/></th>
                            <th><fmt:message key="user.menu.dish.price"/></th>
                            <th colspan="2">
                                <c:choose>
                                    <c:when test="${userRole.name() eq 'USER'}">
                                        <form id="form_content" method="POST"
                                              action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="FORM_ORDER"/>
                                            <input form="form_content" type="submit"
                                                   value="<fmt:message key="user.menu.make.order.button"/>"/>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="littleButton" onclick="openAddDishForm()">
                                            <fmt:message key="user.menu.add.dish.button"/>
                                        </button>
                                        <form id="add_dish_form" method="POST"
                                              action="${pageContext.request.contextPath}/upload"
                                              enctype="multipart/form-data">
                                            <input type="hidden" name="command" value="ADD_DISH"/>
                                            <input type="file" name="file" accept="image/*"/>

                                            <label for="dishName">
                                                <fmt:message key="user.menu.add.dish.button.name"/>
                                            </label>
                                            <input id="dishName" type="text" name="name" required
                                                   pattern="(?=\D)(?=\S)[\S\s]{3,25}"/>

                                            <div class="add_dish_form_div">
                                                <label for="dishCost">
                                                    <fmt:message key="user.menu.add.dish.button.cost"/>
                                                </label>
                                                <input id="dishCost" type="number" name="cost" min="0.1" max="25"
                                                       step="0.1" required/>
                                            </div>
                                            <dt:dishTypesTag/>
                                            <c:forEach items="${dishTypes}" var="typeValue">
                                                <div class="add_dish_form_div">
                                                    <label for="${typeValue}">
                                                        <c:choose>
                                                            <c:when test="${typeValue.name() eq 'SOUP'}">
                                                                <fmt:message key="user.menu.add.dish.button.soup"/>
                                                            </c:when>
                                                            <c:when test="${typeValue.name() eq 'SALAD'}">
                                                                <fmt:message key="user.menu.add.dish.button.salad"/>
                                                            </c:when>
                                                            <c:when test="${typeValue.name() eq 'BEVERAGE'}">
                                                                <fmt:message key="user.menu.add.dish.button.beverage"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <fmt:message
                                                                        key="user.menu.add.dish.button.unknown.type"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                    <input type="radio" id="${typeValue}"
                                                           name="type" value="${typeValue}" checked>
                                                </div>
                                            </c:forEach>
                                            <input class="littleButton" type="submit"
                                                   value="<fmt:message key="user.menu.add.dish.button"/>"/>
                                        </form>
                                    </c:otherwise>
                                </c:choose>

                            </th>
                        </tr>

                        <c:forEach var="dish" items="${sessionScope.dishes}">
                            <tr>
                                <td>
                                    <img src="data:image/jpeg;base64,${dish.imgBase64}" alt="dish"/>
                                </td>
                                <td>${dish.name}</td>
                                <td>${dish.cost}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${userRole.name() eq 'USER'}">
                                            <form method="POST" action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="TO_BASKET"/>
                                                <input type="hidden" name="dishId" value="${dish.id}"/>
                                                <button class="littleButton" type="submit">
                                                    <fmt:message key="user.menu.to.bucket"/></button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="littleButton" type="submit" onclick="deleteDish(${dish.id})">
                                                <fmt:message key="user.menu.delete.dish"/>
                                            </button>
                                            <form id="deleteForm${dish.id}" style="display: none" method="POST"
                                                  action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="REMOVE_DISH"/>
                                                <input type="hidden" name="dishId" value="${dish.id}"/>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="get" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="DISH_COMMENTS"/>
                                        <input type="hidden" name="dishId" value="${dish.id}"/>
                                        <button class="littleButton" type="submit" class="btn">
                                            <fmt:message key="user.menu.comments.button"/></button>
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
    <script>var ctx = "${pageContext.request.contextPath}"</script>
    </body>
</fmt:bundle>
</html>
