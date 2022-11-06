<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
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
									<td>
										<c:if test="${member==null}">
											<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;" onclick="alert('로그인이 필요합니다.')"/>
										</c:if>
										<c:if test="${member!=null}">
											<c:choose>
												<c:when test="${favorite.contains(coffee.num)}">
													<form action="/coffee/release-favorite?num=${coffee.num}" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-1.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:when>
												<c:otherwise>
													<form action="/coffee/add-favorite?num=${coffee.num}" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:otherwise>									
											</c:choose>
										</c:if>	
									</td>
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">										
										<a href="coffee/post?num=${coffee.num}" class="text-ellipsis" style="font-size: 80%;">${coffee.review}</a>
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
		<div id="postListSlider">			
			<ul id="slides">
				<li>
					<table class="list-content-1" style="margin: 0 auto; width: 68%;">
						<tr>
							<td class="list-content-item-1">제목</td>
							<td class="list-content-item-2">카테고리</td>
							<td class="list-content-item-3">작성자</td>
							<td class="list-content-item-4">작성일</td>
						</tr>
					</table>
				</li>
				<li class="slide">
					<table class="list-content-1" style="margin: 0 auto; width: 68%;">
						<c:forEach var="coffee" items="${coffeeList}" end="3">
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
				</li>
				<li class="slide" style="display: none;">
					<table class="list-content-1" style="margin: 0 auto; width: 68%;">
						<c:forEach var="machine" items="${machineList}" end="3">
						<tr onClick="location.href='/machine/post?id=${machine.id}'">
							<td class="list-content-item-1">${machine.name}</td>
							<td class="list-content-item-2">
								<c:if test="${machine.category eq 'cm'}">가전</c:if>
							</td>
							<td class="list-content-item-3">${machine.registrant}</td>
							<td class="list-content-item-4">${machine.regdate}</td>
						</tr>
						</c:forEach>
					</table>
				</li>
				<li class="slide" style="display: none;">
					<table class="list-content-1" style="margin: 0 auto; width: 68%;">
						<c:forEach var="etc" items="${etcList}" end="3">
						<tr onClick="location.href='/etc/post?id=${etc.id}'">
							<td class="list-content-item-1">${etc.name}</td>
							<td class="list-content-item-2">
								<c:if test="${etc.category eq 'ce'}">기타</c:if>
							</td>
							<td class="list-content-item-3">${etc.registrant}</td>
							<td class="list-content-item-4">${etc.regdate}</td>
						</tr>
						</c:forEach>
					</table>
				</li>
				<li>
					<table style="margin: 0 auto; width: 68%;">
						<tr>
							<td><button id="prev" onclick="prevClick()" style="margin: 0 0 0 42%;">&lang; 이전</button>
							<button id="next" onclick="nextClick()">다음 &rang;</button></td>
						</tr>  
					</table>
				</li>
			</ul>			
		</div>
		<script type="text/javascript">
			let slideItem = document.getElementsByClassName("slide");
			var index = 0;
			
			function nextClick() {
				slideItem[index].style = "display:none";
				index += 1;
				if(index == 3) index = 0;
				slideItem[index].style = "display:block";
			}
			function prevClick() {
				slideItem[index].style = "display:none";
				index -= 1;
				if(index == -1) index = 2;
				slideItem[index].style = "display:block";
			}
		</script>
	</div>
	<%@include file="footer.jsp"%>
</div>
</body>
</html>