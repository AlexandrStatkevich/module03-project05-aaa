<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${requestScope.product.productName()}</title>
</head>
<body>
<%@include file="logout.jsp" %>
<h1>${requestScope.product.productName()}:</h1>
<ul>
    <h2>${requestScope.product.productDescription()}</h2>
    <h2>Цена: ${requestScope.product.productCost()} руб.</h2>
    <h2>Остаток ${requestScope.product.productAmount().toString()} ${requestScope.unitService
            .findById(requestScope.product.productUnitId()).unit()}.</h2>
    <h2>Товар поступил: ${requestScope.product.productArrivalDate().toLocalDate().toString()}
        в ${requestScope.product.productArrivalDate().toLocalTime().toString()}</h2>
    <h2>Производитель: ${requestScope.producerService
            .findById(requestScope.product.productProducerId()).producerName()}</h2>
    <h2>Адрес производства: ${requestScope.producerService
            .findById(requestScope.product.productProducerId()).producerAddress()}</h2>
    <h2>Страна производства: ${requestScope.countryService
            .findById(requestScope.product.countryOriginId()).country()}</h2>
</ul>
<%@include file="loginButton.jsp" %>
</body>
</html>
