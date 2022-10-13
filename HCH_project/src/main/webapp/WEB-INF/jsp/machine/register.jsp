<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 가전등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	.post-content-1 tr:not(:last-child) { border-bottom: 1px solid #777777; }
</style>
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>		
	</div>
	<div>
		<form action="register-completion" enctype="multipart/form-data" method="post">
			<input type="hidden" name="registrant" id="registrant" value="${member.id}"/> <!-- hidden으로 변경할 것 -->
			<input type="hidden" name="category" id="category" value="cm"/>
			<table class="post-content-1">
				<tr>
					<td class="input-label-2"><label for="name">가전명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${machineRegisterRequest.name}" placeholder="가전명"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="brand">브랜드</label></td>
					<td><input type="text" name="brand" id="brand" class="input-box-3" placeholder="브랜드"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="price">가격</label></td>
					<td><input type="text" name="price" id="price" class="input-box-3" placeholder="가격"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="type">타입</label></td>
					<td>
						<input type="radio" name="type" value="캡슐"/>캡슐
						<input type="radio" name="type" value="반자동"/>반자동
						<input type="radio" name="type" value="자동"/>자동
						<input type="radio" name="type" value="수동"/>수동
						<input type="radio" name="type" value="커피메이커"/>커피메이커
						<input type="radio" name="type" value="No Data"/>정보 없음
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="color">색상</label></td>
					<td>
						<input type="radio" name="color" value="white"/>white
						<input type="radio" name="color" value="black"/>black
						<input type="radio" name="color" value="silver"/>silver
						<input type="radio" name="color" value="red"/>red
						<input type="radio" name="color" value="green"/>green
						<input type="radio" name="color" value="beige"/>beige
						<input type="radio" name="color" value="No Data"/>정보 없음
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="rating">평점</label></td>
					<td>
						<select name="rating" id="rating">
							<% for(double i = 1.0; i <= 5.0; i+=0.5) { %>
							<option value="<%=i %>"><%=i %></option>
							<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="review">후기</label></td>
					<td><textarea rows="10" cols="50" name="review" class="input-box-3" id="review"></textarea></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="filename">이미지</label></td>
					<td><input type="file" name="imagefile" id="filename"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" class="long-button-1" value="등록" width="95%"/></td>				
				</tr>
			</table>
		</form>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>