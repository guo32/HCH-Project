<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 정보수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/coffeeEditValidation.js"></script>
<style>
	.post-content-1 tr:not(:last-child) { border-bottom: 1px solid #777777; }
</style>
</head>
<script type="text/javascript">
	var flag = false;
	function showImage() {
		var imageOpenButtonId = document.getElementById('openImage');
		var previousImageId = document.getElementById('previousImage');
		
		if(!flag) {
			previousImageId.style = 'display:block';
			imageOpenButtonId.innerText = '이전 이미지 닫기';
			flag = true;
		} else {
			previousImageId.style = 'display:none';
			imageOpenButtonId.innerText = '이전 이미지 확인하기';
			flag = false;
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
	<div>
		<form action="edit-coffee-completion?num=${coffee.num}" enctype="multipart/form-data" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="registrant" id="registrant" value="${member.id}"/> <!-- hidden으로 변경할 것 -->
			<input type="hidden" name="category" id="category" value="${coffee.category}"/>
			<table class="post-content-1">
				<tr>
					<td colspan="2">
						<h2>원두 정보 수정</h2>
					</td>
				</tr>			
				<tr>
					<td class="input-label-2"><label for="name">원두명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${coffee.name}" placeholder="원두명"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="manufacturer">제조사</label></td>
					<td><input type="text" name="manufacturer" id="manufacturer" class="input-box-3" value="${coffee.manufacturer}" placeholder="제조사"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="price">가격</label></td>
					<td><input type="text" name="price" id="price" class="input-box-3" value="${coffee.price}" placeholder="가격"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="roastlevel">로스팅</label></td>
					<td>
						<c:set var="roastlevel" value="${coffee.roastlevel}"/>
						<input type="radio" name="roastlevel" value="Green Bean" <c:if test="${roastlevel.equals('Green Bean')}"><c:out value="checked"/></c:if>/>Green Bean
						<input type="radio" name="roastlevel" value="Very Light" <c:if test="${roastlevel.equals('Very Light')}"><c:out value="checked"/></c:if>/>Very Light
						<input type="radio" name="roastlevel" value="Light" <c:if test="${roastlevel.equals('Light')}"><c:out value="checked"/></c:if>/>Light
						<input type="radio" name="roastlevel" value="Cinnamon" <c:if test="${roastlevel.equals('Cinnamon')}"><c:out value="checked"/></c:if>/>Cinnamon
						<input type="radio" name="roastlevel" value="Medium" <c:if test="${roastlevel.equals('Medium')}"><c:out value="checked"/></c:if>/>Medium
						<input type="radio" name="roastlevel" value="High" <c:if test="${roastlevel.equals('High')}"><c:out value="checked"/></c:if>/>High
						<input type="radio" name="roastlevel" value="City" <c:if test="${roastlevel.equals('City')}"><c:out value="checked"/></c:if>/>City
						<input type="radio" name="roastlevel" value="Full City" <c:if test="${roastlevel.equals('Full City')}"><c:out value="checked"/></c:if>/>Full City
						<input type="radio" name="roastlevel" value="French" <c:if test="${roastlevel.equals('French')}"><c:out value="checked"/></c:if>/>French
						<input type="radio" name="roastlevel" value="Italian" <c:if test="${roastlevel.equals('Italian')}"><c:out value="checked"/></c:if>/>Italian
						<input type="radio" name="roastlevel" value="No Data" <c:if test="${roastlevel.equals('No Data')}"><c:out value="checked"/></c:if>/>정보 없음
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="taste">맛</label></td>
					<td><input type="text" name="taste" id="taste" class="input-box-3" value="${coffee.taste}" placeholder="맛"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="volume">용량</label></td>
					<td><input type="text" name="volume" id="volume" class="input-box-3" value="${coffee.volume}" placeholder="용량"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="rating">평점</label></td>
					<td>
						<c:set var="rating" value="${coffee.rating}"/>
						<select name="rating" id="rating" disabled>
							<option value="1.0" <c:if test="${rating >= 1 && rating < 2}"><c:out value="selected"/></c:if>>★☆☆☆☆</option>
							<option value="2.0" <c:if test="${rating >= 2 && rating < 3}"><c:out value="selected"/></c:if>>★★☆☆☆</option>
							<option value="3.0" <c:if test="${rating >= 3 && rating < 4}"><c:out value="selected"/></c:if>>★★★☆☆</option>
							<option value="4.0" <c:if test="${rating >= 4 && rating < 5}"><c:out value="selected"/></c:if>>★★★★☆</option>
							<option value="5.0" <c:if test="${rating == 5}"><c:out value="selected"/></c:if>>★★★★★</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="review">후기</label></td>
					<td><textarea rows="10" cols="50" name="review" id="review" class="input-box-3">${coffee.review}</textarea></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="filename">이미지</label></td>
					<td>
						<div>
							<input type="file" name="imagefile" id="filename" value="${coffee.filename}"/>
							<button type="button" onClick="showImage()" class="short-button-1" id="openImage" style="width: 130px; margin: 1% 0;">이전 이미지 확인하기</button>
						</div>
						<div id="previousImage" style="display: none;">
							<img src="${pageContext.request.contextPath}/resources/image/${coffee.filename}" width="195px" height="250px" class="post-content-1-main-image" style="margin-top: 2%;"/>
						</div>
					</td>
				</tr>
				<tr>					
					<td colspan="2"><input type="submit" value="수정" class="long-button-1"/></td>		
				</tr>
			</table>
		</form>
	</div>
	<%@include file="../footer.jsp"%>
</div>	
</body>
</html>