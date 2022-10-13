<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 제품등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>		
	</div>
	<div id="wrap-content-bottom">
		<table class="result-box-1">
			<tr><td><h3>정상적으로 등록되었습니다.</h3></td></tr>
			<tr>
				<td>
					<button type="button" onClick="location.href='/machine/posts'" class="short-button-1">목록으로</button>
				</td>
			</tr>
		</table>
	</div>	
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>