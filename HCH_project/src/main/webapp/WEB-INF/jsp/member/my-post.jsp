<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 내 게시물</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	.list-content-category {
	background-color: #eee;
    color: #555555;
    font-style: italic;
	padding: 1% 0;
	font-size: 110%;
	}
	.list-content-head {
		background-color: #8C6642;
		color: #f2d0a7;
	}
</style>
</head>
<body>
<div id="wrap">
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>
	</div>
	<div>
		<div>
			<table class="list-content-1" style="margin: 0 auto; width: 62%;">
				<tr>
					<td colspan="4">
						<h2>내 게시물</h2>
					</td>
				</tr>
				<tr class="list-content-head">
					<td class="list-content-item-1">제품명</td>
					<td class="list-content-item-2">제조사</td>
					<td class="list-content-item-3">작성자</td>
					<td class="list-content-item-4">관심 수</td>
				</tr>
				<!-- 원두 카테고리 중 내 게시물 -->
				<c:if test="${fn:length(coffeePosting)>0}">
				<tr class="list-content-category"><td colspan="5">원두</td></tr>				
					<c:forEach var="coffee" items="${coffeePosting}">
					<tr onClick="location.href='/coffee/post?num=${coffee.num}'">
						<td class="list-content-item-1">${coffee.name}</td>
						<td class="list-content-item-2">${coffee.manufacturer}</td>
						<td class="list-content-item-3">${coffee.registrant}</td>
						<td class="list-content-item-4">${coffee.favorite}</td>
					</tr>
					</c:forEach>
				</c:if>
				<!-- 가전 카테고리 중 내 게시물 -->
				<c:if test="${fn:length(machinePosting)>0}">
				<tr class="list-content-category"><td colspan="5">가전</td></tr>
					<c:forEach var="machine" items="${machinePosting}" end="3">
					<tr onClick="location.href='/machine/post?id=${machine.id}'">
						<td class="list-content-item-1">${machine.name}</td>
						<td class="list-content-item-2">${machine.brand}</td>
						<td class="list-content-item-3">${machine.registrant}</td>
						<td class="list-content-item-4">${machine.favorite}</td>
					</tr>
					</c:forEach>
				</c:if>
				<!-- 기타 카테고리 중 내 게시물 -->
				<c:if test="${fn:length(etcPosting)>0}">
				<tr class="list-content-category"><td colspan="5">기타</td></tr>				
					<c:forEach var="etc" items="${etcPosting}" end="3">
					<tr onClick="location.href='/etc/post?id=${etc.id}'">
						<td class="list-content-item-1">${etc.name}</td>
						<td class="list-content-item-2">${etc.brand}</td>						
						<td class="list-content-item-3">${etc.registrant}</td>
						<td class="list-content-item-4">${etc.favorite}</td>
					</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</div>
</div>
</body>
</html>