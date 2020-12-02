<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="side_menu">
    <h2><fmt:message key="aside.our.menu"/></h2>
    <c:set var="userRole" value="${sessionScope.authUser.role}"/>
    <ul>
      <li><button class="btn"><fmt:message key="aside.menu"/></button></li>
      <li><button class="btn"><fmt:message key="aside.my.orders"/></button></li>
      <li><button class="btn"><fmt:message key="aside.comments"/></button></li>

      <!-- if user == ADMIN -->
        <c:if test="${userRole.name() eq 'ADMIN'}">
      <li><button class="btn"><fmt:message key="aside.users"/></button></li>
        </c:if>
    </ul>
  </div>