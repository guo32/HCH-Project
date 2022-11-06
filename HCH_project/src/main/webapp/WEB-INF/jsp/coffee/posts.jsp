<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 원두</title>
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
		} else {
			datailSearchId.style = 'display:none';
			flag = false;
			detailSearchButtonId.innerText = '상세검색 열기';
		}			
	}
	
	/* 맛(노트) 기타 입력란 */
	function showTasteOtherText() {
		var tasteOtherId = document.getElementById('tasteOther');
		var tasteOtherTextId = document.getElementById('tasteOtherText');
		
		// 기타 체크 여부 확인
		if(tasteOtherId.checked == true) {
			tasteOtherTextId.style = 'display:inline-block';
		} else {
			tasteOtherTextId.style = 'display:none';
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
			<form method="get" id="detailSearch" class="detail-search-form" action="${pageContext.request.contextPath}/coffee/posts-datail-search" style="display: none;">
				<table style="border-collapse: collapse;">
					<tr>
						<td>제조사</td>
						<td>
							<c:if test="${manufacturerSearch != null}">
								<input type="text" name="manufacturer" id="manufacturer" value="${manufacturerSearch}"/>
							</c:if>
							<c:if test="${manufacturerSearch == null}">
								<input type="text" name="manufacturer" id="manufacturer" value="${coffeeSearchDetailRequest.manufacturer}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>가격</td>
						<td>
							<table>
								<tr><td><input type="radio" name="price" value="null" checked/>미선택</td></tr>
								<% for(int i = 5, j = 0; i <= 25; i+=5, j++) { if(j % 3 == 0) { %>
								<tr>
								<% } %>
								<td style="width: 33%;"><input type="radio" name="price" value="<%=i * 1000 %>"/>
									<% if(i * 1000 < 10000) { %> <%= i * 1000 %>원 미만 <% } else { %>
									<%= (i - 5) * 1000 %>원 ~ <%= i * 1000 %>원 <% } %>
								</td>								
								<% } %>
								<td style="width: 33%;"><input type="radio" name="price" value="25001"/>25000원 이상</td>
							</table>						
						</td>
					</tr>
					<tr>
						<td>로스팅</td>
						<td>
							<input type="radio" name="roastlevel" value="null" checked/>미선택
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
							<input type="radio" name="taste" value="null" checked/>미선택
							<input type="radio" name="taste" value="견과류"/>견과류
							<input type="radio" name="taste" value="초콜릿"/>초콜릿
							<input type="radio" name="taste" value="과일향"/>과일향
							<input type="radio" name="taste" value="꽃향기"/>꽃향기
							<input type="radio" name="taste" id="tasteOther" value="other" onclick="showTasteOtherText()"/>기타
							<!-- 기타 입력란 -->
							<input type="text" name="tasteOther" id="tasteOtherText" style="display: none;"/>
						</td>
					</tr>
					<tr>
						<td>용량</td>
						<td>
							<table>
								<tr><td><input type="radio" name="volume" value="null" checked/>미선택</td></tr>
								<% for(int i = 100, j = 0; i <= 700; i+=200, j++) { if(j % 3 == 0) { %>
								<tr>
								<% } %>
								<td style="width: 33%;"><input type="radio" name="volume" value="<%=i%>"/>
									<% if(i - 200 < 0) { %> <%= i %>g 미만 <% } else { %>
									<%= i - 200 %>g ~ <%= i %>g <% } %>
								</td>								
								<% } %>
								<td style="width: 33%;"><input type="radio" name="volume" value="1000"/>700g ~ 1kg</td>								
								<td style="width: 33%;"><input type="radio" name="volume" value="1001"/>1kg 이상</td>
							</table>
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
					<c:when test="${manufacturerSearch != null}">${manufacturerSearch}</c:when>
					<c:otherwise>전체</c:otherwise>
				</c:choose>				
				&quot;&nbsp;:&nbsp;검색 결과
			</div>
		</div>		
		<table style="margin: 0 auto;">	
			<c:forEach var="coffee" items="${coffeeList}" varStatus="i">
				<c:if test="${i.index % 3 == '0'}"><tr></c:if>
					<td style="/*padding: 10px;*/">
						<div class="box-content-1">
							<table>
								<tr>
									<td colspan="2">
										<a href="post?num=${coffee.num}"><img src="${pageContext.request.contextPath}/resources/image/${coffee.filename}" width="190px" height="185px"/></a>
									</td>
								</tr>
								<tr class="box-content-1-title">
									<td style="width: 90%;">
										<a href="${pageContext.request.contextPath}/coffee/posts?manufacturer=${coffee.manufacturer}" style="font-size: 80%; color: #777777;">${coffee.manufacturer}</a>
										<br>
										<a href="post?num=${coffee.num}">${coffee.name}</a>
									</td>
									<td>
										<c:if test="${member==null}">
											<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;" onclick="alert('로그인이 필요합니다.')"/>
										</c:if>
										<c:if test="${member!=null}">
											<c:choose>
												<c:when test="${favorite.contains(coffee.num)}">
													<form action="release-favorite?num=${coffee.num}&posts=true" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-1.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:when>
												<c:otherwise>
													<form action="add-favorite?num=${coffee.num}&posts=true" method="post">
														<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="17" height="17" style="margin: 0 5px;"/>
													</form>
												</c:otherwise>									
											</c:choose>
										</c:if>	
									</td>
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">
										<a href="post?num=${coffee.num}" class="text-ellipsis" style="font-size: 80%;">${coffee.review}</a>
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