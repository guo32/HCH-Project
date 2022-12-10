<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 정보수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/etcEditValidation.js"></script>
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
		<form action="edit-etc-completion?id=${etc.id}" enctype="multipart/form-data" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="registrant" id="registrant" value="${member.id}"/> <!-- hidden으로 변경할 것 -->
			<input type="hidden" name="category" id="category" value="${etc.category}"/>
			<table class="post-content-1">
				<tr>
					<td colspan="2">
						<h2>가전 정보 수정</h2>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="name">제품명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${etc.name}" placeholder="제품명"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="brand">브랜드</label></td>
					<td><input type="text" name="brand" id="brand" class="input-box-3" value="${etc.brand}" placeholder="브랜드"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="price">가격</label></td>
					<td><input type="text" name="price" id="price" class="input-box-3" value="${etc.price}" placeholder="가격"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="type">타입</label></td>
					<td>
						<c:set var="type" value="${etc.type}"/>
						<input type="radio" name="type" value="드리퍼" <c:if test="${type.equals('드리퍼')}"><c:out value="checked"/></c:if>/>드리퍼
						<input type="radio" name="type" value="템퍼" <c:if test="${type.equals('템퍼')}"><c:out value="checked"/></c:if>/>템퍼
						<input type="radio" name="type" value="밀크저그" <c:if test="${type.equals('밀크저그')}"><c:out value="checked"/></c:if>/>밀크저그
						<input type="radio" name="type" value="원두 거름망" <c:if test="${type.equals('원두 거름망')}"><c:out value="checked"/></c:if>/>원두 거름망
						<input type="radio" name="type" value="포터필터" <c:if test="${type.equals('포터필터')}"><c:out value="checked"/></c:if>/>포터필터
						<input type="radio" name="type" value="디스트리뷰터" <c:if test="${type.equals('디스트리뷰터')}"><c:out value="checked"/></c:if>/>디스트리뷰터
						<input type="radio" name="type" value="other" <c:if test="${type.equals('other')}"><c:out value="checked"/></c:if>/>그 외
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="rating">평점</label></td>
					<td>
						<select name="rating" id="rating" disabled>
							<option value="${etc.rating}">${etc.rating}</option>						
						</select>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="review">후기</label></td>
					<td><textarea rows="10" cols="50" name="review" class="input-box-3" id="review">${etc.review}</textarea></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="filename">이미지</label></td>
					<td>
						<div>
							<input type="file" name="imagefile" id="filename" value="${etc.filename}"/>
							<button type="button" onClick="showImage()" class="short-button-1" id="openImage" style="width: 130px; margin: 1% 0;">이전 이미지 확인하기</button>
						</div>
						<div id="previousImage" style="display: none;">
							<img src="${pageContext.request.contextPath}/resources/image/${etc.filename}" width="195px" height="250px" class="post-content-1-main-image" style="margin-top: 2%;"/>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" class="long-button-1" value="수정" width="95%"/></td>				
				</tr>
			</table>
		</form>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>