<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<div id="top-menu">
		<table style="float: right;">
			<tr>
				<c:if test="${admin!=null}">
					<td class="top-menu-item"><a href="${pageContext.request.contextPath}/admin/logout">로그아웃</a></td>
				</c:if>
			</tr>
		</table>
	</div>
	<div id="menu">
		<table style="margin: 0 auto; width: 63%;">
			<tr>
				<td id="logo"><a href="${pageContext.request.contextPath}/index">Home Cafe<br>Helper</a></td>
				<td class="menu-item"><a href="${pageContext.request.contextPath}/admin/manage-coffee-item">원두</a></td>
				<td class="menu-item"><a href="${pageContext.request.contextPath}/admin/manage-machine-item">가전</a></td>
				<td class="menu-item"><a href="${pageContext.request.contextPath}/admin/manage-etc-item">기타</a></td>
				<c:if test="${admin==null}">
					<td class="menu-item"><a href="#" onclick="alert('로그인이 필요합니다.')">+제품 등록하기</a></td>
				</c:if>
				<c:if test="${admin!=null}">
					<td class="menu-item"><a href="${pageContext.request.contextPath}/select-category">+제품 등록하기</a></td>
				</c:if>
			</tr>
		</table>
	</div>
</header>