<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/login" method="get">
            <button type="submit">Login</button>
        </form>
    </c:if>
</div>
