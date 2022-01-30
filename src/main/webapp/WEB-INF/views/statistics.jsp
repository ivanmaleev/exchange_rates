<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/index'/>">Главная</a>
            </li>
        </ul>
    </div>
    <div class="row">
        <p>Выберите валюты для конвертации:</p>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Исходная валюты</th>
                <th scope="col">Целевая валюта</th>
                <th scope="col">Количество конвертаций</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="counter" items="${counters}">
                <tr>
                    <td><c:out value="${counter.valuteFrom.name}"/></td>
                    <td><c:out value="${counter.valuteTo.name}"/></td>
                    <td><c:out value="${counter.count}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>