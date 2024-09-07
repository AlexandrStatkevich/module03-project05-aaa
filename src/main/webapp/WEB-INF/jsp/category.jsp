<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Категории товаров</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>Категории товаров:</h1>
<ul>
    <c:if test="${not empty requestScope.categories}">
        <c:forEach var="category" items="${requestScope.categories}">
            <li><a href="${pageContext.request.contextPath}/subcategory?categoryId=${category.id()}">
                    ${category.category()}</a></li>
        </c:forEach>
    </c:if>
</ul>
<%@include file="loginButton.jsp" %>
</body>
</html>
