<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="header.jsp"%>
		<%@include file="search.jsp"%>
		<table style="margin: 0 auto;">
			<tr>
				<c:forEach var="coffee" items="${coffeeList}" end="2">
					<td style="/*padding: 10px;*/">
						<div class="box-content-1">
							<table>
								<tr>
									<td colspan="2">
										<a href="coffee/post?num=${coffee.num}"><img src="${pageContext.request.contextPath}/resources/image/${coffee.filename}" width="190px" height="185px"/></a>
									</td>
								</tr>
								<tr class="box-content-1-title">
									<td style="width: 90%;">
										<a href="coffee/posts?manufacturer=${coffee.manufacturer}" style="font-size: 80%; color: #777777;">${coffee.manufacturer}</a>
										<br>
										<a href="coffee/post?num=${coffee.num}">${coffee.name}</a>
									</td>
									<td>♡</td>
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">										
										<a href="coffee/post?num=${coffee.num}" class=".text-ellipsis" style="font-size: 80%;">${coffee.review}</a>
									</td>
								</tr>
							</table>
						</div>			
					</td>
				</c:forEach>
			</tr>
		</table>
	</div>
	<!-- 하단 -->
	<div id="wrap-content-bottom">
		<table class="list-content-1" style="margin: 0 auto; width: 63%;">
			<tr>
				<td class="list-content-item-1">제목</td>
				<td class="list-content-item-2">카테고리</td>
				<td class="list-content-item-3">작성자</td>
				<td class="list-content-item-4">작성일</td>
			</tr>
			<c:forEach var="coffee" items="${coffeeList}" end="4">
			<tr onClick="location.href='/coffee/post?num=${coffee.num}'">
				<td class="list-content-item-1">${coffee.name}</td>
				<td class="list-content-item-2">
					<c:if test="${coffee.category eq 'cb'}">원두</c:if>
				</td>
				<td class="list-content-item-3">${coffee.registrant}</td>
				<td class="list-content-item-4">${coffee.regdate}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<%@include file="footer.jsp"%>
</div>
</body>
</html>