<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Пользователи</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>Пользователи:</h1>
<ul>
    <c:if test="${not empty requestScope.users}">
        <c:forEach var="user" items="${requestScope.users}">
            <li><a href="${pageContext.request.contextPath}/user?usersId=${user.id()}">
                    ${requestScope.informationService.findById(user.id()).lastName()}
                    ${requestScope.informationService.findById(user.id()).firstName()}
                    ${requestScope.informationService.findById(user.id()).secondName()}</a></li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
