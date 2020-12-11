<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<footer>
    <div class="footer_logo"><h2><fmt:message key="footer.logo"/></h2></div>


    <c:if test="${not empty sessionScope.basket}">
        <div class="bucketDiv">
            <span><fmt:message key="footer.yourBucket"/></span>
            <ul>
                <c:forEach var="basketItem" items="${sessionScope.basket}">
                    <li>${basketItem.name} - ${basketItem.cost}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

</footer>
