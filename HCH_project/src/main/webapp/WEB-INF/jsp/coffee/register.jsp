<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 원두등록</title>
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
			<input type="hidden" name="category" id="category" value="cb"/>
			<table class="post-content-1">
				<tr>
					<td colspan="2">
						<h2>원두 등록</h2>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="name">원두명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${coffeeRegisterRequest.name}" placeholder="원두명"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="manufacturer">제조사</label></td>
					<td><input type="text" name="manufacturer" id="manufacturer" class="input-box-3" placeholder="제조사"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="price">가격</label></td>
					<td><input type="text" name="price" id="price" class="input-box-3" placeholder="가격"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="roastlevel">로스팅</label></td>
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
					<td class="input-label-2"><label for="taste">맛</label></td>
					<td><input type="text" name="taste" id="taste" class="input-box-3" placeholder="맛"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="volume">용량</label></td>
					<td><input type="text" name="volume" id="volume" class="input-box-3" placeholder="용량"/></td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="rating">평점</label></td>
					<td>
						<select name="rating" id="rating">
							<option value="1.0">★☆☆☆☆</option>
							<option value="2.0">★★☆☆☆</option>
							<option value="3.0">★★★☆☆</option>
							<option value="4.0">★★★★☆</option>
							<option value="5.0">★★★★★</option>
						</select>
					</td>
					<!-- <td><input type="text" name="rating" id="rating" class="input-box-3" placeholder="평점"/></td>  -->
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