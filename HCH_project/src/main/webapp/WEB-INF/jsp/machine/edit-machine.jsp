<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 정보수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/machineEditValidation.js"></script>
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
		<form action="edit-machine-completion?id=${machine.id}" enctype="multipart/form-data" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="registrant" id="registrant" value="${member.id}"/> <!-- hidden으로 변경할 것 -->
			<input type="hidden" name="category" id="category" value="${machine.category}"/>
			<table class="post-content-1">
				<tr>
					<td colspan="2">
						<h2>가전 정보 수정</h2>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="name">가전명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${machine.name}" placeholder="가전명"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="brand">브랜드</label></td>
					<td><input type="text" name="brand" id="brand" class="input-box-3" value="${machine.brand}" placeholder="브랜드"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="price">가격</label></td>
					<td><input type="text" name="price" id="price" class="input-box-3" value="${machine.price}" placeholder="가격"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="type">타입</label></td>
					<td>
						<c:set var="type" value="${machine.type}"/>
						<input type="radio" name="type" value="캡슐" <c:if test="${type.equals('캡슐')}"><c:out value="checked"/></c:if>/>캡슐
						<input type="radio" name="type" value="반자동" <c:if test="${type.equals('반자동')}"><c:out value="checked"/></c:if>/>반자동
						<input type="radio" name="type" value="자동" <c:if test="${type.equals('자동')}"><c:out value="checked"/></c:if>/>자동
						<input type="radio" name="type" value="수동" <c:if test="${type.equals('수동')}"><c:out value="checked"/></c:if>/>수동
						<input type="radio" name="type" value="커피메이커" <c:if test="${type.equals('커피메이커')}"><c:out value="checked"/></c:if>/>커피메이커
						<input type="radio" name="type" value="에스프레소 머신" <c:if test="${type.equals('에스프레소 머신')}"><c:out value="checked"/></c:if>/>에스프레소 머신
						<input type="radio" name="type" value="자동 그라인더" <c:if test="${type.equals('자동 그라인더')}"><c:out value="checked"/></c:if>/>자동 그라인더
						<input type="radio" name="type" value="other" <c:if test="${type.equals('other')}"><c:out value="checked"/></c:if>/>그 외
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="color">색상</label></td>
					<td>
						<c:set var="color" value="${machine.color}"/>
						<input type="radio" name="color" value="white" <c:if test="${color.equals('white')}"><c:out value="checked"/></c:if>/>white
						<input type="radio" name="color" value="black" <c:if test="${color.equals('black')}"><c:out value="checked"/></c:if>/>black
						<input type="radio" name="color" value="silver" <c:if test="${color.equals('silver')}"><c:out value="checked"/></c:if>/>silver
						<input type="radio" name="color" value="red" <c:if test="${color.equals('red')}"><c:out value="checked"/></c:if>/>red
						<input type="radio" name="color" value="green" <c:if test="${color.equals('green')}"><c:out value="checked"/></c:if>/>green
						<input type="radio" name="color" value="beige" <c:if test="${color.equals('beige')}"><c:out value="checked"/></c:if>/>beige
						<input type="radio" name="color" value="No Data" <c:if test="${color.equals('No Data')}"><c:out value="checked"/></c:if>/>정보 없음
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="rating">평점</label></td>
					<td>
						<select name="rating" id="rating" disabled>
							<option value="${machine.rating}">${machine.rating}</option>						
						</select>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="review">후기</label></td>
					<td><textarea rows="10" cols="50" name="review" class="input-box-3" id="review">${machine.review}</textarea></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="filename">이미지</label></td>
					<td>
						<div>
							<input type="file" name="imagefile" id="filename" value="${machine.filename}"/>
							<button type="button" onClick="showImage()" class="short-button-1" id="openImage" style="width: 130px; margin: 1% 0;">이전 이미지 확인하기</button>
						</div>
						<div id="previousImage" style="display: none;">
							<img src="${pageContext.request.contextPath}/resources/image/${machine.filename}" width="195px" height="250px" class="post-content-1-main-image" style="margin-top: 2%;"/>
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