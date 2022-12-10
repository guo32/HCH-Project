<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 관리자 로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<script>
	function checkForm() {
		if(!document.loginForm.id.value) {
			alert("아이디를 입력하세요.");
			return false;
		}
		if(!document.loginForm.password.value) {
			alert("비밀번호를 입력하세요.");
			return false;
		}
	}
</script>
<body>
<div id="wrap">
	<div id="wrap-content-top">
		<%@include file="header.jsp"%>
		<%@include file="search.jsp"%>
		<div>
			<form name="loginForm" action="${pageContext.request.contextPath}/admin/login-success" method="post" onsubmit="return checkForm()">
				<table style="margin: 0 auto;">
					<tr><td class="form-title-1">관리자 로그인</td></tr>
					<tr>
						<!-- <td><label for="id" class="input-label-1">아이디</label></td> -->
						<td><input type="text" name="id" id="id" class="input-box-1" placeholder="아이디"/></td>
					</tr>
					<tr>
						<!-- <td><label for="password" class="input-label-1">비밀번호</label></td> -->
						<td><input type="password" name="password" id="password" class="input-box-1" placeholder="비밀번호"/></td>
					</tr>
					<tr><td><input type="submit" value="로그인" class="long-button-1"/></td></tr>
				</table>
			</form>
		</div>
	</div>	
	<%@include file="../footer.jsp"%>
</div>	
</body>
</html>