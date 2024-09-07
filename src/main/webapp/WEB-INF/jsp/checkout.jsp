<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${requestScope.information.lastName()} ${requestScope.information.firstName().charAt(0)}.
        ${requestScope.information.secondName().charAt(0)}. покупки</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.information.lastName()} ${requestScope.information.firstName()}
    ${requestScope.information.secondName()}:</h1>
<h2>Оформленные покупки:</h2>
<ul>
    <c:if test="${not empty requestScope.checkouts}">
        <c:forEach var="checkout" items="${requestScope.checkouts}">
            <li>
                <a href="${pageContext.request.contextPath}/order?usersId=${requestScope.information.usersId()}&checkoutId=${checkout.id()}">
                        ${checkout.checkoutTime().toLocalDate().toString()} в
                        ${checkout.checkoutTime().toLocalTime().toString()}
                    Стоимость: ${requestScope.orderService.findAllByCheckoutId(checkout.id()).stream().map(orderDto ->
                        orderDto.productAmount().multiply(requestScope.productService.findById(orderDto.productId())
                                .productCost())).reduce((x, y) -> x + y).orElse(0).toString()} руб.</a></li>
        </c:forEach>
    </c:if>
</ul>
<h2>Общая стоимость покупок: ${requestScope.totalCheckoutsCost} руб.</h2>
</body>
</html>
