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
                <div class="meal_types">
                    <button class="btn">По рейтингу</button>
                    <button class="btn">Новые</button>
                </div>
                <div class="menu_content">

                    <c:set var="estimatesMap" value="${sessionScope.estimates}"/>
                    <c:forEach var="dishComment" items="${sessionScope.dishComments}">
                        <div class="comment_item">

                            <div class="user_info">
                                <h3>Имя</h3>
                                <img src="${pageContext.request.contextPath}/static/images/not_found.png" alt="avatar"/>
                            </div>

                            <div class="comment_info">

                                <div class="comment_text">
                                    <div>${dishComment.text}</div>
                                    <div class="date">${dishComment.creationDate}</div>
                                </div>

                                <div class="comment_rating">
                                    <c:set var = "estimate" value="${estimatesMap.get(dishComment.id)}"/>
                                    <div>
                                    <c:if test="${(empty estimate) || (estimate == 'DISLIKE')}">
                                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                                            <input type="hidden" name="command" value="RATE_COMMENT"/>
                                            <input type="hidden" name="commentId" value="${dishComment.id}"/>
                                            <input type="hidden" name="rating" value="${dishComment.rating}"/>
                                            <input type="hidden" name="estimate" value="LIKE"/>
                                            <button type="submit">+</button>
                                        </form>
                                    </c:if>
                                    </div>
                                    <div>
                                    <span>${dishComment.rating}</span>
                                        </div>
                                    <div>
                                    <c:if test="${(empty estimate) || (estimate == 'LIKE')}">
                                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                                            <input type="hidden" name="command" value="RATE_COMMENT"/>
                                            <input type="hidden" name="commentId" value="${dishComment.id}"/>
                                            <input type="hidden" name="rating" value="${dishComment.rating}"/>
                                            <input type="hidden" name="estimate" value="DISLIKE"/>
                                    <button>-</button>
                                        </form>
                                    </c:if>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </c:forEach>


                </div>
            </div>
        </main>
        <%@ include file="parts/footer.jsp" %>
    </div>
    </body>
</fmt:bundle>
</html>
