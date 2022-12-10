<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div>
	<form method="get" action="${pageContext.request.contextPath}/etc/posts">
		<table style="margin: 0 auto;">
			<tr>
				<td><input type="search" name="q" id="search" placeholder="기타(etc)에서 검색하기"/></td>
				<td><input type="image" class="search-button" src="${pageContext.request.contextPath}/resources/image/search-icon.svg" width="34" height="34"/></td>
			</tr>
		</table>
	</form>
</div>