<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 제품삭제</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
	</div>
	<div id="wrap-content-bottom">
		<table class="result-box-1">
			<tr><td><h3>정상적으로 삭제되었습니다.</h3></td></tr>
			<tr>
				<td>
					<c:if test="${admin!=null}">
						<button type="button" onClick="location.href='/admin/manage-etc-item'" class="short-button-1">목록으로</button>
					</c:if>
					<c:if test="${admin==null}">
						<button type="button" onClick="location.href='/etc/posts'" class="short-button-1">목록으로</button>
					</c:if>
				</td>
			</tr>
		</table>
	</div>	
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>