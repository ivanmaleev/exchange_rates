<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<form action="<c:url value='/index'/>" method='GET'>
    <div class="container">
        <div class="row">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/statistics'/>">Статистика</a>
                </li>
            </ul>
        </div>
        <div class="row">
            <p>Выберите валюты для конвертации:</p>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Исходная валюты</th>
                    <th scope="col">Количество</th>
                    <th scope="col">Целевая валюта</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <select name="valuteFrom.id">
                            <c:forEach var="valuteFrom" items="${valutes}">
                                <c:choose>
                                    <c:when test="${valuteFrom.id == valuteFromId}">
                                        <option value="${valuteFrom.id}" selected>${valuteFrom.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${valuteFrom.id}">${valuteFrom.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type='number' name='countFrom' value="${countFrom}">
                    </td>
                    <td>
                        <select name="valuteTo.id">
                            <c:forEach var="valuteTo" items="${valutes}">
                                <c:choose>
                                    <c:when test="${valuteTo.id == valuteToId}">
                                        <option value="${valuteTo.id}" selected>${valuteTo.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${valuteTo.id}">${valuteTo.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Рассчитать"/></td>
        </tr>
        <div class="row">
            <p>Результат:</p>
            <td><input type='number' name='result' value="${result}" disabled></td>
        </div>
    </div>
</form>
</body>
</html>