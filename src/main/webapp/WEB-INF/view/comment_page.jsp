<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="WEB-INF/tlds/datetimeformatter" prefix="f" %>
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
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_COMMENTS"/>
                        <input type="hidden" name="pageNumber" value="${sessionScope.currentPagePagination}"/>
                        <input type="hidden" name="sortType" value="DATE"/>
                        <button class="btn">Новые</button>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="SORT_COMMENTS"/>
                        <input type="hidden" name="pageNumber" value="${sessionScope.currentPagePagination}"/>
                        <input type="hidden" name="sortType" value="RATING"/>
                        <button class="btn">По рейтингу</button>
                    </form>

                </div>
                <div class="menu_content">

                    <div>
                        <button onclick="openCommentForm()">Оставить комментарий</button>
                        <div id="commentDiv">
                            <form action="#" method="POST">
                                <input type="hidden" name="command" value="ADD_COMMENT"/>
                                <textarea name="commentText"></textarea>
                                <button class="littleButton" type="submit">Отправить</button>
                            </form>

                        </div>
                    </div>


                    <ul>
                        <c:forEach var="page" begin="1" end="${sessionScope.pageCount}">
                            <li>
                                <form method="post" action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="DISH_COMMENTS"/>
                                    <input type="hidden" name="pageNumber" value="${page}">
                                    <button class="littleButton" type="submit">${page}</button>
                                </form>
                            </li>
                        </c:forEach>
                    </ul>


                    <c:set var="estimatesMap" value="${sessionScope.estimates}"/>
                    <c:forEach var="dishComment" items="${sessionScope.dishComments}">
                        <div class="comment_item">

                            <div class="user_info">
                                <h3>${dishComment.userLogin}</h3>
                                <img src="data:image/jpeg;base64,${dishComment.userAvatarBase64}" alt="avatar"/>
                            </div>

                            <div class="comment_info">

                                <div class="comment_text">
                                    <div>${dishComment.text}</div>
                                    <div class="date">
                                        <span>
                                                ${f:formatLocalDateTimeDefault(dishComment.creationDate)}

                                        </span>

                                        <div>
                                            <c:if test="${(dishComment.userLogin.equals(sessionScope.authUser.login))}">
                                                <button class="littleButton"
                                                        onclick="showChangeCommentForm(${dishComment.id})">CHANGE
                                                    COMMENT
                                                </button>
                                                <form id="changeCommentForm${dishComment.id}"
                                                      action="${pageContext.request.contextPath}/controller"
                                                      method="POST">
                                                    <input type="hidden" name="command" value="UPDATE_COMMENT"/>
                                                    <input type="hidden" name="commentId" value="${dishComment.id}">
                                                    <textarea name="commentText"></textarea>
                                                    <button class="littleButton" type="submit">UPDATE COMMENT</button>
                                                </form>

                                            </c:if>
                                        </div>


                                        <c:if test="${(dishComment.userLogin.equals(sessionScope.authUser.login)) || sessionScope.authUser.role == 'ADMIN'}">
                                            <form action="${pageContext.request.contextPath}/controller" method="POST">
                                                <input type="hidden" name="command" value="DELETE_COMMENT"/>
                                                <input type="hidden" name="commentId" value="${dishComment.id}">
                                                <button class="littleButton" type="submit">DELETE COMMENT</button>
                                            </form>
                                        </c:if>


                                    </div>
                                </div>

                                <div class="comment_rating">
                                    <c:set var="estimate" value="${estimatesMap.get(dishComment.id)}"/>
                                    <div>
                                        <c:if test="${(empty estimate) || (estimate == false)}">
                                            <form action="${pageContext.request.contextPath}/controller" method="POST">
                                                <input type="hidden" name="command" value="RATE_COMMENT"/>
                                                <input type="hidden" name="commentId" value="${dishComment.id}"/>
                                                <input type="hidden" name="estimate" value="true"/>
                                                <button class="littleButton" type="submit">+</button>
                                            </form>
                                        </c:if>
                                    </div>
                                    <div>
                                        <span>${dishComment.rating}</span>
                                    </div>
                                    <div>
                                        <c:if test="${(empty estimate) || (estimate == true)}">
                                            <form action="${pageContext.request.contextPath}/controller" method="POST">
                                                <input type="hidden" name="command" value="RATE_COMMENT"/>
                                                <input type="hidden" name="commentId" value="${dishComment.id}"/>
                                                <input type="hidden" name="estimate" value="false"/>
                                                <button class="littleButton" type="submit">-</button>
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
