<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 기타등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/etcValidation.js"></script>
<style>
	.post-content-1 tr:not(:last-child) { border-bottom: 1px solid #777777; }
</style>
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="search.jsp"%>		
	</div>
	<div>
		<form action="register-completion" enctype="multipart/form-data" method="post" onsubmit="return checkForm()">
			<c:if test="${admin!=null}">
				<input type="hidden" name="registrant" id="registrant" value="관리자"/>
			</c:if>
			<c:if test="${admin==null}">
				<input type="hidden" name="registrant" id="registrant" value="${member.id}"/> <!-- hidden으로 변경할 것 -->
			</c:if>
			<input type="hidden" name="category" id="category" value="ce"/>
			<table class="post-content-1">
				<tr>
					<td colspan="2">
						<h2>기타 등록</h2>
					</td>
				</tr>
				<tr>
					<td class="input-label-2"><label for="name">제품명</label></td>
					<td><input type="text" name="name" id="name" class="input-box-3" value="${etcRegisterRequest.name}" placeholder="제품명"/></td>
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
						<input type="radio" name="type" value="드리퍼"/>드리퍼
						<input type="radio" name="type" value="템퍼"/>템퍼
						<input type="radio" name="type" value="밀크저그"/>밀크저그
						<input type="radio" name="type" value="원두 거름망"/>원두 거름망
						<input type="radio" name="type" value="포터필터"/>포터필터
						<input type="radio" name="type" value="디스트리뷰터"/>디스트리뷰터
						<input type="radio" name="type" value="other" checked/>그 외
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