<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 회원정보수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/memberEditValidation.js"></script>
</head>
<body>
<div id="wrap">
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>
		<div>
			<form name="editForm" action="edit-member-completion" method="post" onsubmit="return checkForm()">
				<table style="margin: 0 auto;">
					<tr><td class="form-title-1" colspan="2">회원정보 수정</td></tr>
					<tr>
						<td><label for="id" class="input-label-1">아이디</label></td>
						<td><input type="text" name="id" id="id" class="input-box-2" value="${member.id}" placeholder="아이디" readonly/></td>
					</tr>
					<tr>
						<td><label for="email" class="input-label-1">이메일</label></td>
						<td><input type="text" name="email" id="email" class="input-box-2" value="${member.email}" placeholder="이메일"/></td>
					</tr>
					<tr>
						<td><label for="name" class="input-label-1">이름</label></td>
						<td><input type="text" name="name" id="name" class="input-box-2" value="${member.name}" placeholder="이름"/></td>
					</tr>
					<tr>
						<td><label for="birth" class="input-label-1">생년월일</label></td>
						<td><input type="text" name="birth" id="birth" class="input-box-2" value="${member.birth}" placeholder="생년월일"/></td>				
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="수정" class="long-button-1"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<%@include file="../footer.jsp"%>
</div>	
</body>
</html>