<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>
	</div>
	<div>
		<div class="post-content-1">
			<table class="member-info-content" style="border-collapse: collapse;">
				<tr>
					<td colspan="2">
						<h2>회원 정보</h2>
					</td>
				</tr>		
				<tr>
					<td class="input-label-2">이름</td>
					<td>${member.name}</td>
				</tr>
				<tr>
					<td class="input-label-2">아이디</td>
					<td>${member.id}</td>
				</tr>
				<tr>
					<td class="input-label-2">이메일</td>
					<td>${member.email}</td>
				</tr>
				<tr>
					<td class="input-label-2">생일</td>
					<td>${member.birth}</td>
				</tr>
			</table>
			<a href="edit-member">회원정보수정</a>
			<a href="withdrawal-member">회원탈퇴</a>
			<a href="my-post">내 게시물</a>
		</div>		
	</div>
	<%@include file="../footer.jsp"%>
</div>	
</body>
</html>