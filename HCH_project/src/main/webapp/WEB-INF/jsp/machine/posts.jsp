<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 가전</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	.post-content-1 tr:not(:last-child) { border-bottom: 1px solid #777777; }
	.post-content-1 tr td:first-child { width: 15%; }
	.post-content-1 tr td { padding: 2% 0; }
</style>
</head>
<script type="text/javascript">
	var flag = false;

	function openAndCloseDetailSearch() {
		var datailSearchId = document.getElementById('detailSearch');
		var detailSearchButtonId = document.getElementById('detailSearchButton');
		if(!flag) {
			datailSearchId.style = 'display:block';
			flag = true;
			detailSearchButtonId.innerText = '상세검색 -';
		}			
		else {
			datailSearchId.style = 'display:none';
			flag = false;
			detailSearchButtonId.innerText = '상세검색 +';
		}			
	}
</script>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>
	</div>
	<!-- 하단 -->
	<div id="wrap-content-bottom">		
		<!-- 세부 검색 -->
		<div class="post-content-1">
			<div id="detailSearchButton" onclick="openAndCloseDetailSearch()" style="color: #333333;">상세검색 +</div>
			<form id="detailSearch" style="display: none;">
				<table>
					<tr>
						<td>제조사</td>
						<td><input type="text" name="manufaturer"/></td>
					</tr>
					<tr>
						<td>가격</td>
						<td>
							<input type="radio" name="price" value="5000"/>5000원 미만
							<input type="radio" name="price" value="10000"/>10000원 미만 
							<input type="radio" name="price" value="15000"/>15000원 미만
							<input type="radio" name="price" value="20000"/>20000원 미만
							<input type="radio" name="price" value="20001"/>20000원 이상
						</td>
					</tr>
					<tr>
						<td>로스팅</td>
						<td>
							<input type="radio" name="roastlevel" value="Green Bean"/>Green Bean
							<input type="radio" name="roastlevel" value="Very Light"/>Very Light
							<input type="radio" name="roastlevel" value="Light"/>Light
							<input type="radio" name="roastlevel" value="Cinnamon"/>Cinnamon
							<input type="radio" name="roastlevel" value="Medium"/>Medium
							<input type="radio" name="roastlevel" value="High"/>High
							<input type="radio" name="roastlevel" value="City"/>City
							<input type="radio" name="roastlevel" value="Full City"/>Full City
							<input type="radio" name="roastlevel" value="French"/>French
							<input type="radio" name="roastlevel" value="Itanlian"/>Italian
							<input type="radio" name="roastlevel" value="No Data"/>정보 없음
						</td>
					</tr>
					<tr>
						<td>맛(노트)</td>
						<td>
							<input type="checkbox" name="taste" value="견과류"/>견과류
							<input type="checkbox" name="taste" value="초콜렛"/>초콜렛
							<input type="checkbox" name="taste" value="과일향"/>과일향
							<input type="checkbox" name="taste" value="꽃향기"/>꽃향기
							기타 <input type="text" name="taste"/>
						</td>
					</tr>
					<tr>
						<td>용량</td>
						<td>
							<input type="radio" name="volume" value="100"/>100g 미만
							<input type="radio" name="volume" value="300"/>300g 미만
							<input type="radio" name="volume" value="500"/>500g 미만
							<input type="radio" name="volume" value="700"/>700g 미만
							<input type="radio" name="volume" value="1000"/>1kg 미만
							<input type="radio" name="volume" value="1001"/>1kg 이상
						</td>
					</tr>
					<tr>
						<td>평점 평균</td>
						<td>
							<input type="radio" name="rating" value="4.5"/>4.5 이상
							<input type="radio" name="rating" value="4.0"/>4.0 이상
							<input type="radio" name="rating" value="3.5"/>3.5 이상
							<input type="radio" name="rating" value="3.0"/>3.0 이상
							<input type="radio" name="rating" value="2.5"/>2.5 이상
							<input type="radio" name="rating" value="2.0"/>2.0 이상
							<input type="radio" name="rating" value="1.5"/>1.5 이상
							<input type="radio" name="rating" value="1.0"/>1.0 이상
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="검색" class="short-button-1"/></td>
					</tr>
				</table>
			</form>
		</div>
		<table style="margin: 0 auto;">			
			<c:forEach var="machine" items="${machineList}" varStatus="i">
				<c:if test="${i.index % 3 == '0'}"><tr></c:if>
					<td style="/*padding: 10px;*/">
						<div class="box-content-1">
							<table>
								<tr>
									<td colspan="2">
										<a href="post?id=${machine.id}"><img src="${pageContext.request.contextPath}/resources/image/${machine.filename}" width="190px" height="185px"/></a>
									</td>
								</tr>
								<tr class="box-content-1-title">
									<td style="width: 90%;">
										<a href="${pageContext.request.contextPath}/machine/posts?brand=${machine.brand}" style="font-size: 80%; color: #777777;">${machine.brand}</a>
										<br>
										<a href="post?id=${machine.id}">${machine.name}</a>
									</td>
									<td>♡</td>
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">
										<a href="post?id=${machine.id}" class="text-ellipsis" style="font-size: 80%;">${machine.review}</a>
									</td>
								</tr>
							</table>
						</div>					
					</td>
			</c:forEach>
		</table>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>