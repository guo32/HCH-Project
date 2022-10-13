<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 카테고리 선택</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	.category-box {
		background-color: gray;
		width: 80px;
		padding: 50px 0;
		margin: 10px 5px;
		border-radius: 15px;
		text-align: center;
	}
</style>
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="header.jsp"%>
		<%@include file="search.jsp"%>
	</div>
	<!-- 하단 -->
	<div id="wrap-content-bottom">			
		<table style="margin: 0 auto;">			
			<tr>
				<td><div class="category-box" onclick="location.href='${pageContext.request.contextPath}/coffee/register'">원두<br>(coffee)</div></td>
				<td><div class="category-box" onclick="location.href='${pageContext.request.contextPath}/machine/register'">가전<br>(machine)</div></td>
				<td><div class="category-box">기타<br>(etc)</div></td>
			</tr>
		</table>
	</div>
	<%@include file="footer.jsp"%>
</div>
</body>
</html>