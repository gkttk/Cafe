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
                    <script>

                        function openCommentForm(){
                            if (document.getElementById("commentDiv").style.display == "none") {
                                document.getElementById("commentDiv").style.display = "block";
                            }else{
                                document.getElementById("commentDiv").style.display = "none";
                            }
                        }
                    </script>


                    <div>
                        <button onclick="openCommentForm()">Оставить комментарий</button>
                        <div id="commentDiv">
                            <form action="#" method="POST">
                                <input type="hidden" name="command" value="ADD_COMMENT"/>
                                <textarea name="commentText"></textarea>
                                <button type="submit">Отправить</button>
                            </form>

                        </div>
                    </div>



        <ul>
            <c:forEach var="page" begin="1" end="${sessionScope.pageCount}">
                <li>
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="DISH_COMMENTS"/>
                    <input type="hidden" name="currentPage" value="${page}">
                    <button type="submit" >${page}</button>
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
                                    <div class="date">${dishComment.creationDate}</div>
                                </div>

                                <div class="comment_rating">
                                    <c:set var = "estimate" value="${estimatesMap.get(dishComment.id)}"/>
                                    <div>
                                    <c:if test="${(empty estimate) || (estimate == false)}">
                                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                                            <input type="hidden" name="command" value="RATE_COMMENT"/>
                                            <input type="hidden" name="commentId" value="${dishComment.id}"/>
                                            <input type="hidden" name="rating" value="${dishComment.rating}"/>
                                            <input type="hidden" name="estimate" value="true"/>
                                            <button type="submit">+</button>
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
                                            <input type="hidden" name="rating" value="${dishComment.rating}"/>
                                            <input type="hidden" name="estimate" value="false"/>
                                    <button type="submit">-</button>
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
