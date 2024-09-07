<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Информация</title>
</head>
<body>
<%@include file="logout.jsp" %>
<form action="/information" method="post">
    <label for="firstName">Имя:
        <input type="text" name="firstName" id="firstName">
    </label><br/>
    <label for="secondName">Отчество:
        <input type="text" name="secondName" id="secondName">
    </label><br/>
    <label for="lastName">Фамилия:
        <input type="text" name="lastName" id="lastName">
    </label><br/>
    <label for="address">Адрес:
        <input type="text" name="address" id="address">
    </label><br/>
    <label for="phone">Телефон:
        <input type="text" name="phone" id="phone">
    </label><br/>
    <input type="submit" value="SEND">
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