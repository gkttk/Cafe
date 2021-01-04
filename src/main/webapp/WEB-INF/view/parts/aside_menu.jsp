<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="side_menu">

  <%--    <h2><fmt:message key="aside.our.menu"/></h2>--%>
      <c:set var="userRole" value="${sessionScope.authUser.role}"/>
    <ul>
      <li>
          <form method="post" action="${pageContext.request.contextPath}/controller">
          <input type="hidden" name="command" value="MENU"/>
          <button type="submit" class="btn"><fmt:message key="aside.menu"/></button>
      </form>
      </li>
      <li>
          <form method="post" action="${pageContext.request.contextPath}/controller">
          <input type="hidden" name="command" value="MY_ORDERS"/>
          <button class="btn"><fmt:message key="aside.my.orders"/></button>
          </form>
      </li>
   <%--   <li>
          <form method="post" action="${pageContext.request.contextPath}/controller">
              <input type="hidden" name="command" value="COMMENTS"/>
          <button class="btn"><fmt:message key="aside.comments"/></button>
          </form>
      </li>--%>

      <!-- if user == ADMIN -->
        <c:if test="${userRole.name() eq 'ADMIN'}">
      <li>
          <form method="post" action="${pageContext.request.contextPath}/controller">
              <input type="hidden" name="command" value="USERS"/>
              <button type="submit" class="btn">
                  <fmt:message key="aside.users"/>
              </button>
          </form>
        </li>
            </c:if>

    </ul>
  </div>