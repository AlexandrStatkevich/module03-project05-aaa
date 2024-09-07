<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${requestScope.subcategory.subcategory()}</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.subcategory.subcategory()}:</h1>
<ul>
    <c:if test="${not empty requestScope.productGroups}">
        <c:forEach var="productGroup" items="${requestScope.productGroups}">
            <li>
                <a href="${pageContext.request.contextPath}/products?productGroupId=${productGroup.id()}">
                        ${productGroup.productGroup()}</a>
            </li>
        </c:forEach>
    </c:if>
</ul>
<%@include file="loginButton.jsp" %>
</body>
</html>
