<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/memberValidation.js"></script>
</head>
<body>
<c:if test="${usedId != null}">
	<script type="text/javascript">
		alert("존재하는 아이디입니다.");
	</script>
</c:if>
<div id="wrap">
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>
		<div>
			<form name="registerForm" action="register-completion" method="post" onsubmit="return checkForm()">
				<table style="margin: 0 auto;">
					<tr><td class="form-title-1" colspan="2">회원가입</td></tr>
					<tr>
						<td><label for="id" class="input-label-1">아이디</label></td>
						<td><input type="text" name="id" id="id" class="input-box-2" value="${registerRequest.id}" placeholder="아이디"/></td>
					</tr>
					<tr>
						<td><label for="password" class="input-label-1">비밀번호</label></td>
						<td><input type="password" name="password" id="password" class="input-box-2" placeholder="비밀번호"/></td>
					</tr>
					<tr>
						<td><label for="confirmPassword" class="input-label-1">비밀번호 확인</label></td>
						<td><input type="password" name="confirmPassword" id="confirmPassword" class="input-box-2" placeholder="비밀번호 확인"/></td>
					</tr>
					<tr>
						<td><label for="email" class="input-label-1">이메일</label></td>
						<td><input type="text" name="email" id="email" class="input-box-2" value="${registerRequest.email}" placeholder="이메일"/></td>
					</tr>
					<tr>
						<td><label for="name" class="input-label-1">이름</label></td>
						<td><input type="text" name="name" id="name" class="input-box-2" placeholder="이름"/></td>
					</tr>
					<tr>
						<td><label for="birth" class="input-label-1">생년월일</label></td>
						<td><input type="text" name="birth" id="birth" class="input-box-2" placeholder="1999-01-01"/></td>				
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="회원가입" class="long-button-1"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<%@include file="../footer.jsp"%>
</div>	
</body>
</html>