<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${requestScope.information.lastName()} ${requestScope.information.firstName().charAt(0)}.
        ${requestScope.information.secondName().charAt(0)}. информация</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.information.lastName()} ${requestScope.information.firstName()}
    ${requestScope.information.secondName()}:</h1>
<ul>
    <h2>e-mail: ${requestScope.user.email()}</h2>
    <h2>password: ${requestScope.user.password()}</h2>
    <h2>Адрес: ${requestScope.information.address()}</h2>
    <h2>Телефон: ${requestScope.information.phone()}</h2>
    <h2>Роль: ${requestScope.roleService.findById(requestScope.user.roleId()).role()}</h2>
    <h2>Пол: ${requestScope.genderService.findById(requestScope.user.genderId()).gender()}</h2>

    <li>
        <a href="${pageContext.request.contextPath}/checkout?usersId=${requestScope.user.id()}">
            Дата и время оформленных покупок</a>
    </li>
</ul>
</body>
</html>
