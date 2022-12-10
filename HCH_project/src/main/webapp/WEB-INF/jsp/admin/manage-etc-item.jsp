<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : 관리자</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/etc-bean.png" rel="shortcut icon" type="image/x-icon">
<style>
	#detailSearch table tr:not(:last-child) { border-bottom: 1px solid #777777; }
	#detailSearch table tr td:first-child { width: 15%; }
	#detailSearch table tr td { padding: 2% 0; }
</style>
</head>
<script type="text/javascript">
	var flag = false;

	function openAndCloseDetailSearch() {
		var datailSearchId = document.getElementById('detailSearch');
		var detailSearchButtonId = document.getElementById('detailSearchButton');
		if(!flag) {
			datailSearchId.style = 'display:block';
			flag = true;
			detailSearchButtonId.innerText = '상세검색 닫기';
		} else {
			datailSearchId.style = 'display:none';
			flag = false;
			detailSearchButtonId.innerText = '상세검색 열기';
		}			
	}
	
	/* 맛(노트) 기타 입력란 */
	function showTasteOtherText() {
		var tasteOtherId = document.getElementById('tasteOther');
		var tasteOtherTextId = document.getElementById('tasteOtherText');
		
		// 기타 체크 여부 확인
		if(tasteOtherId.checked == true) {
			tasteOtherTextId.style = 'display:inline-block';
		} else {
			tasteOtherTextId.style = 'display:none';
		}
	}
</script>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="header.jsp"%>
		<%@include file="search.jsp"%>
	</div>
	<!-- 하단 -->
	<div id="wrap-content-bottom">			
		<table style="margin: 0 auto;" id="etcTable">	
			<c:forEach var="etc" items="${etcList}" varStatus="i">
				<c:if test="${i.index % 3 == '0'}"><tr></c:if>
					<td style="/*padding: 10px;*/">
						<div class="box-content-1">
							<table>
								<tr>
									<td colspan="2">
										<a href="${pageContext.request.contextPath}/etc/post?id=${etc.id}"><img src="${pageContext.request.contextPath}/resources/image/${etc.filename}" width="190px" height="185px"/></a>
									</td>
								</tr>
								<tr class="box-content-1-title">
									<td style="width: 90%;">
										<a href="${pageContext.request.contextPath}/etc/posts?brand=${etc.brand}" style="font-size: 80%; color: #777777;">${etc.brand}</a>
										<br>
										<a href="${pageContext.request.contextPath}/etc/post?id=${etc.id}" class="text-ellipsis-title">${etc.name}</a>
									</td>									
								</tr>
								<tr class="box-content-1-content">
									<td colspan="2">
										<a href="${pageContext.request.contextPath}/etc/post?id=${etc.id}" class="text-ellipsis" style="font-size: 80%;">게시자 | ${etc.registrant} <br>게시일 | ${etc.regdate}</a>
									</td>
								</tr>
								<tr>
									<td>
										<button type="button" onClick="location.href='/etc/edit-etc?id=${etc.id}'" class="short-button-edit" style="margin-top: 5%;">수정</button>
										<button type="button" onClick="location.href='/etc/delete-etc?id=${etc.id}'" class="short-button-delete" style="margin-top: 5%;">삭제</button>
									</td>
								</tr>
							</table>
						</div>					
					</td>
			</c:forEach>
		</table>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>