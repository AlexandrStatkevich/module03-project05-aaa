<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${requestScope.checkout.checkoutTime().toLocalDate().toString()}
        в ${requestScope.checkout.checkoutTime().toLocalTime().toString()}</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.information.lastName()} ${requestScope.information.firstName()}
    ${requestScope.information.secondName()}:</h1>
<h2>Телефон: ${requestScope.information.phone()}</h2>
<h2>Оформлено: ${requestScope.checkout.checkoutTime().toLocalDate().toString()}
    в ${requestScope.checkout.checkoutTime().toLocalTime().toString()}</h2>
<h2>Доставка по адресу ${requestScope.delivery.deliveryAddress()}</h2>
<h2>Дата и время доставки: ${requestScope.checkout.checkoutTime().toLocalDate().toString()}
    к ${requestScope.delivery.deliveryDateTime().toLocalTime().toString()}</h2>
<ul>
    <c:if test="${not empty requestScope.orders}">
        <c:forEach var="order" items="${requestScope.orders}">
            <li><a>${requestScope.productService.findById(order.productId()).productName()}
                Цена: ${requestScope.productService.findById(order.productId()).productCost().toString()} руб.
                Количество: ${order.productAmount().toString()} шт.
                Стоимость: ${requestScope.productService.findById(order.productId()).productCost()
                    .multiply(order.productAmount()).toString()} руб.</a></li>
        </c:forEach>
    </c:if>
</ul>
<h2>Общая стоимость покупки: ${requestScope.checkoutCost} руб.</h2>
</body>
</html>
