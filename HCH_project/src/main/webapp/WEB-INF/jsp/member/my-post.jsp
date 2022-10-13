<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>내 게시물</title>
</head>
<body>
	<p>내 게시물</p>
	<table border="1">
		<c:forEach var="coffee" items="${coffeePosting}">
			<tr>
				<td><a href="../coffee/post?num=${coffee.num}">상세</a></td>
				<td>${coffee.filename}</td>
				<td>${coffee.num}</td>
				<td>${coffee.name}</td>
				<td>${coffee.manufacturer}</td>
				<td>${coffee.registrant}</td>
				<td>${coffee.favorite}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>