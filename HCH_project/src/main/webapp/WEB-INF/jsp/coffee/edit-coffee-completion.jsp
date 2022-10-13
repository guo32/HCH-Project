<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : ${coffee.name}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>		
	</div>
	<div>
		<table class="post-content-1">
			<c:if test="${member.id==coffee.registrant}">
				<tr>
					<td colspan="2">
						<a href="edit-coffee?num=${coffee.num}">수정</a>
						<a href="delete-coffee?num=${coffee.num}">삭제</a>						
					</td>
				</tr>
			</c:if>
			<tr>
				<td rowspan="2" width="195px"><img src="${pageContext.request.contextPath}/resources/image/${coffeeRegisterRequest.filename}" width="195px" height="250px"/></td>
				<td>
					<table>
						<tr>
							<td class="post-content-1-title">${coffeeRegisterRequest.name}</td>
							<td class="post-content-1-heart">♡</td>
							<td>${coffee.like}</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table border="1" class="post-content-1-detail">
						<tr>
							<td>제조사</td>
							<td>${coffeeRegisterRequest.manufacturer}</td>
						</tr>
						<tr>
							<td>가격</td>
							<td>${coffeeRegisterRequest.price}원</td>
						</tr>
						<tr>
							<td>로스팅</td>
							<td>${coffeeRegisterRequest.roastlevel}</td>
						</tr>
						<tr>
							<td>노트</td>
							<td>${coffeeRegisterRequest.taste}</td>
						</tr>
						<tr>
							<td>용량</td>
							<td>${coffeeRegisterRequest.volume}g</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b style="font-size: 120%; color: #555555;">평가(${coffee.rating})</b>
					<br>
					<p style="color: #777777;">${coffeeRegisterRequest.review}</p>
					<br>
					<p style="font-size: 90%; color: #999999;">${coffee.registrant} | ${coffee.regdate}에 게시함.</p>
				</td>
			</tr>
		</table>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>