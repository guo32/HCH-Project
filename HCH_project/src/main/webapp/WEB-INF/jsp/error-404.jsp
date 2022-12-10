<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 오류</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="header.jsp"%>
	</div>
	<div id="wrap-content-bottom">
		<table class="result-box-1">
			<tr><td><h3>존재하지 않는 페이지입니다.</h3></td></tr>
			<tr>
				<td>
					<button type="button" onClick="location.href='/index'" class="short-button-1">메인으로</button>
				</td>
			</tr>
		</table>
	</div>	
	<%@include file="footer.jsp"%>
</div>
</body>
</html>