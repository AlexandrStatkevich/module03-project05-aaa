<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<%@include file="logout.jsp" %>
<form action="/registration" method="post">
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label><br/>
    <label for="pwd">Password:
        <input type="password" name="pwd" id="pwd">
    </label><br/>

    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}"> ${role}</option>
            <br/>
        </c:forEach>
    </select>
    <br/>

    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" VALUE="${gender}"> ${gender}
        <br/>
    </c:forEach>

    <input type="submit" value="SEND">
    <a href="${pageContext.request.contextPath}/login">
        <button type="button">Login</button>
    </a>
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
