<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>News</title>
</head>


<body>
    <h1>News</h1>
    <table>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>contents</td>
            <td>category</td>
        </tr>
        <c:forEach items="${news}" var="news_item">
        <tr>
            <td>${news_item.getId()}</td>
            <td>${news_item.getName()}</td>
            <td>${news_item.getContents()}</td>
            <td>${news_item.getCategory().getName()}</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>