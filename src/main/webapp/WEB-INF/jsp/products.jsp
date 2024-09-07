<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${requestScope.productGroup.productGroup()}</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.productGroup.productGroup()}:</h1>
<ul>
    <c:if test="${not empty requestScope.products}">
        <c:forEach var="product" items="${requestScope.products}">
            <li>
                <a href="${pageContext.request.contextPath}/product?productId=${product.id()}">
                        ${product.productName()} - Цена: ${product.productCost()} руб. - В
                    наличии: ${product.productAmount()} ${requestScope.unitService.findById(product.productUnitId()).unit()}.</a>
            </li>
        </c:forEach>
    </c:if>
</ul>
<%@include file="loginButton.jsp" %>
</body>
</html>
