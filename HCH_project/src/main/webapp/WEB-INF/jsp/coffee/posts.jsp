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
<script type="text/javascript">
	function changeMajor(v) {
		var value = v;		
		var middle = document.querySelector('.middle-select');
		var test = "";
		// 옵션 초기화
		middle.options.length = 0;
		
		var option = document.createElement("option");
		option.innerText = "중분류";
		option.value = "-1";
		middle.append(option);
		
		<c:forEach var="note" items="${noteList}">
			if("${note.major}" == value) {
				if(test != "${note.middle}") { // 중복 제거
					test = "${note.middle}";
					// 옵션 추가
					var option = document.createElement("option");
					option.innerText = "${note.middle}";
					option.value = "${note.middle}";
					middle.append(option);
				}
			}
		</c:forEach>
	}
	
	function changeMiddle(v) {
		var value = v;
		var minor = document.querySelector('.minor-select');
		// 옵션 초기화
		minor.options.length = 0;
		
		var option = document.createElement("option");
		option.innerText = "소분류";
		option.value = "-1";
		minor.append(option);
		
		<c:forEach var="note" items="${noteList}">
		 	if("${note.middle}" == value) {
		 		var option = document.createElement("option");
		 		option.innerText = "${note.minor}";
		 		option.value = "${note.noteid}";
		 		minor.append(option);
		 	}
		</c:forEach>
	}
</script>
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
						<td>원산지</td>
						<td>
							<select name="nation" id="nation">
								<option value="null">원산지 선택</option>
								<optgroup label="아라비카">
									<c:forEach var="nation" items="${nationList}">
										<c:if test="${nation.group eq '아라비카'}">
											<option value="${nation.nationid}">${nation.country}</option>
										</c:if>
									</c:forEach>
								</optgroup>
								<optgroup label="로부스타">
									<c:forEach var="nation" items="${nationList}">
										<c:if test="${nation.group eq '로부스타'}">
											<option value="${nation.nationid}">${nation.country}</option>
										</c:if>
									</c:forEach>
								</optgroup>
							</select>
						</td>
					</tr>
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
							<table>
								<tr>
									<td style="width:30%"><input type="radio" name="roastlevel" value="null" checked/>미선택</td>
									<td><input type="radio" name="roastlevel" value="Green Bean"/><img src="${pageContext.request.contextPath}/resources/image/bean1.svg" width="25" height="25"/>Green Bean</td>
									<td><input type="radio" name="roastlevel" value="Very Light"/><img src="${pageContext.request.contextPath}/resources/image/bean2.svg" width="25" height="25"/>Very Light</td>									
								</tr>
								<tr>
									<td><input type="radio" name="roastlevel" value="Light"/><img src="${pageContext.request.contextPath}/resources/image/bean3.svg" width="25" height="25"/>Light</td>
									<td><input type="radio" name="roastlevel" value="Cinnamon"/><img src="${pageContext.request.contextPath}/resources/image/bean4.svg" width="25" height="25"/>Cinnamon</td>
									<td><input type="radio" name="roastlevel" value="Medium"/><img src="${pageContext.request.contextPath}/resources/image/bean5.svg" width="25" height="25"/>Medium</td>									
								</tr>
								<tr>
									<td><input type="radio" name="roastlevel" value="High"/><img src="${pageContext.request.contextPath}/resources/image/bean6.svg" width="25" height="25"/>High</td>
									<td><input type="radio" name="roastlevel" value="City"/><img src="${pageContext.request.contextPath}/resources/image/bean7.svg" width="25" height="25"/>City</td>
									<td><input type="radio" name="roastlevel" value="Full City"/><img src="${pageContext.request.contextPath}/resources/image/bean8.svg" width="25" height="25"/>Full City</td>									
								</tr>
								<tr>
									<td><input type="radio" name="roastlevel" value="French"/><img src="${pageContext.request.contextPath}/resources/image/bean9.svg" width="25" height="25"/>French</td>
									<td><input type="radio" name="roastlevel" value="Itanlian"/><img src="${pageContext.request.contextPath}/resources/image/bean10.svg" width="25" height="25"/>Italian</td>
									<td><input type="radio" name="roastlevel" value="No Data"/>정보 없음</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>맛(노트)</td>
						<td>
							<select onchange="changeMajor(this.value)">
								<option value="null">대분류</option>
								<c:forEach var="major" items="${majorList}">
									<option value="${major}">${major}</option>
								</c:forEach>
							</select>
							<select class="middle-select" onchange="changeMiddle(this.value)">
								<option value="-1">중분류</option>
							</select>
							<select name="taste" id="taste" class="minor-select">
								<option value="-1">소분류</option>
							</select>
						</td>
						<!-- 
						<td>
							<input type="radio" name="taste" value="null" checked/>미선택
							<input type="radio" name="taste" value="견과류"/>견과류
							<input type="radio" name="taste" value="초콜릿"/>초콜릿
							<input type="radio" name="taste" value="과일향"/>과일향
							<input type="radio" name="taste" value="꽃향기"/>꽃향기
							<input type="radio" name="taste" id="tasteOther" value="other" onclick="showTasteOtherText()"/>기타
							기타입력란 부분
							<input type="text" name="tasteOther" id="tasteOtherText" style="display: none;"/>
						</td>
						 -->
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