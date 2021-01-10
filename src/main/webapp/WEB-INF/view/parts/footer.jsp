<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<footer>

    <c:if test="${not empty sessionScope.basket}">
        <div class="bucketDiv">
            <span><fmt:message key="footer.yourBucket"/></span>
            <h4>${sessionScope.basket.size()}</h4>
          <%--  <ul>
                <c:forEach var="basketItem" items="${sessionScope.basket}">
                    <li>${basketItem.name} - ${basketItem.cost}</li>
                </c:forEach>
            </ul>--%>
        </div>
    </c:if>

</footer>
