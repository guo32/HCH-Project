<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 기타</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	#detailSearch table tr:not(:last-child) { border-bottom: 1px solid #777777; }
	#detailSearch table tr td:first-child { width: 15%; }
	#detailSearch table tr td { padding: 2% 0; }
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
			detailSearchButtonId.innerText = '상세검색 닫기';
		}			
		else {
			datailSearchId.style = 'display:none';
			flag = false;
			detailSearchButtonId.innerText = '상세검색 열기';
		}			
	}
</script>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="search.jsp"%>
	</div>
	<!-- 하단 -->
	<div id="wrap-content-bottom">		
		<!-- 세부 검색 -->
		<div class="post-content-1">
			<div id="detailSearchButton" onclick="openAndCloseDetailSearch()" class="detail-search-button">상세검색 열기</div>
			<form method="get" id="detailSearch" class="detail-search-form" action="${pageContext.request.contextPath}/etc/posts-datail-search" style="display: none;">
				<table style="border-collapse: collapse;">
					<tr>
						<td>브랜드</td>
						<td><input type="text" name="brand"/></td>
					</tr>
					<tr>
						<td>가격</td>
						<td>
							<table>
								<tr><td><input type="radio" name="price" value="null" checked/>미선택</td></tr>
								<% for(int i = 1, j = 0; i <= 15; i+=2, j++) { if(j % 3 == 0) { %>
								<tr>
								<% } %>
								<td style="width: 33%;"><input type="radio" name="price" value="<%=i * 10000 %>"/>
									<% if(i - 2 < 0) { %> <%= i %>만원 미만 <% } else { %>
									<%= (i - 2) %>만원 ~ <%= i %>만원 <% } %>
								</td>								
								<% } %>
								<td style="width: 33%;"><input type="radio" name="price" value="150001"/>15만원 이상</td>
							</table>						
						</td>
					</tr>
					<tr>
						<td>타입</td>
						<td>
							<input type="radio" name="type" value="null" checked/>미선택
							<input type="radio" name="type" value="드리퍼"/>드리퍼
							<input type="radio" name="type" value="템퍼"/>템퍼
							<input type="radio" name="type" value="밀크저그"/>밀크저그
							<input type="radio" name="type" value="원두 거름망"/>원두 거름망
							<input type="radio" name="type" value="포터필터"/>포터필터
							<input type="radio" name="type" value="other"/>그 외
						</td>
					</tr>
					<tr>
						<td>평점 평균</td>
						<td>
							<input type="radio" name="rating" value="4.5" checked/>4.5 이상
							<% for(double i = 4.0; i >= 1; i -= 0.5) { %>
								<input type="radio" name="rating" value="<%=i %>"/><%=i %> 이상
							<% } %>
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="검색" class="short-button-1"/></td>
					</tr>
				</table>				
			</form>
			<div style="color: #777777; margin: 2% 0;">
				&quot;
				<c:choose>
					<c:when test="${search != null}">${search}</c:when>
					<c:when test="${brand != null}">${brandSearch}</c:when>
					<c:otherwise>전체</c:otherwise>
				</c:choose>				
				&quot;&nbsp;:&nbsp;검색 결과
			</div>
		</div>
		<table style="margin: 0 auto;">			
			<c:forEach var="etc" items="${etcList}" varStatus="i">
				<c:if test="${i.index % 3 == '0'}"><tr></c:if>
					<td style="/*padding: 10px;*/">
						<div class="box-content-1">
							<table>
								<tr>
									<td colspan="2">
										<a href="post?id=${etc.id}"><img src="${pageContext.request.contextPath}/resources/image/${etc.filename}" width="190px" height="185px"/></a>
									</td>
								</tr>
								<tr class="box-content-1-title">
									<td style="width: 90%;">
										<a href="${pageContext.request.contextPath}/etc/posts?brand=${etc.brand}" style="font-size: 80%; color: #777777;">${etc.brand}</a>
										<br>
										<a href="post?id=${etc.id}">${etc.name}</a>
									</td>
									<td>
										<c:if test="${member==null}">
											<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;" onclick="alert('로그인이 필요합니다.')"/>
										</c:if>
										<c:if test="${member!=null}">
											<c:choose>
												<c:when test="${favorite.contains(etc.id)}">
													<form action="release-favorite?id=${etc.id}&posts=true" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-1.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:when>
												<c:otherwise>
													<form action="add-favorite?id=${etc.id}&posts=true" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:otherwise>									
											</c:choose>
										</c:if>	
									</td>
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">
										<a href="post?id=${etc.id}" class="text-ellipsis" style="font-size: 80%;">${etc.review}</a>
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