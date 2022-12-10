<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 원두</title>
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
	.list-content-item-rating { width: 12%; }
	.list-content-head {
		background-color: #8C6642;
		color: #f2d0a7;
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
		<div class="post-content-1">
			<div style="color: #777777; margin: 2% 0;">
				&quot;
				<c:choose>
					<c:when test="${search != null}">${search}</c:when>
					<c:otherwise>전체</c:otherwise>
				</c:choose>				
				&quot;&nbsp;:&nbsp;검색 결과
			</div>
		</div>
		<table class="list-content-1" style="margin: 0 auto; width: 62%;">
			<tr class="list-content-head">
				<td class="list-content-item-1">제품명</td>
				<td class="list-content-item-2">제조사</td>
				<td class="list-content-item-rating">평점</td>
				<td class="list-content-item-3">작성자</td>
				<td class="list-content-item-4">작성일</td>
			</tr>
			<!-- 원두 결과 -->
			<tr class="list-content-category"><td colspan="5">원두</td></tr>
			<c:if test="${fn:length(coffeeList)>0}">
				<c:forEach var="coffee" items="${coffeeList}" end="3">
				<tr onClick="location.href='/coffee/post?num=${coffee.num}'">
					<td class="list-content-item-1">${coffee.name}</td>
					<td class="list-content-item-2">${coffee.manufacturer}</td>
					<td class="list-content-item-rating"><fmt:formatNumber value="${coffee.ratingsum / coffee.comment}" pattern=".0"/></td>
					<td class="list-content-item-3">${coffee.registrant}</td>
					<td class="list-content-item-4">${coffee.regdate}</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(coffeeList)==0}">
				<tr>
					<td colspan="5">해당 조건의 게시물이 없습니다.</td>
				</tr>
			</c:if>
			<!-- 가전 결과 -->
			<tr class="list-content-category"><td colspan="5">가전</td></tr>
			<c:if test="${fn:length(machineList)>0}">
				<c:forEach var="machine" items="${machineList}" end="3">
				<tr onClick="location.href='/machine/post?id=${machine.id}'">
					<td class="list-content-item-1">${machine.name}</td>
					<td class="list-content-item-2">${machine.brand}</td>
					<td class="list-content-item-rating"><fmt:formatNumber value="${machine.ratingsum / machine.comment}" pattern=".0"/></td>
					<td class="list-content-item-3">${machine.registrant}</td>
					<td class="list-content-item-4">${machine.regdate}</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(machineList)==0}">
				<tr>
					<td colspan="5">해당 조건의 게시물이 없습니다.</td>
				</tr>
			</c:if>
			<!-- 기타 결과 -->
			<tr class="list-content-category"><td colspan="5">기타</td></tr>
			<c:if test="${fn:length(etcList)>0}">
				<c:forEach var="etc" items="${etcList}" end="3">
				<tr onClick="location.href='/etc/post?id=${etc.id}'">
					<td class="list-content-item-1">${etc.name}</td>
					<td class="list-content-item-2">${etc.brand}</td>
					<td class="list-content-item-rating"><fmt:formatNumber value="${etc.ratingsum / etc.comment}" pattern=".0"/></td>
					<td class="list-content-item-3">${etc.registrant}</td>
					<td class="list-content-item-4">${etc.regdate}</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(etcList)==0}">
				<tr>
					<td colspan="5">해당 조건의 게시물이 없습니다.</td>
				</tr>
			</c:if>
		</table>
	</div>
	<%@include file="footer.jsp"%>
</div>
</body>
</html>