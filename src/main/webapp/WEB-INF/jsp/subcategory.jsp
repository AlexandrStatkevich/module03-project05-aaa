<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${requestScope.category.category()}</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.category.category()}:</h1>
<ul>
    <c:if test="${not empty requestScope.subcategories}">
        <c:forEach var="subcategory" items="${requestScope.subcategories}">
            <li><a href="${pageContext.request.contextPath}/productGroup?subcategoryId=${subcategory.id()}">
                    ${subcategory.subcategory()}</a></li>
        </c:forEach>
    </c:if>
</ul>
<%@include file="loginButton.jsp" %>
</body>
</html>
