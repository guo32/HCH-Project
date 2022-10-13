<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>HCH : ${coffee.name}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?after">
<link href="${pageContext.request.contextPath}/resources/image/coffee-bean.png" rel="shortcut icon" type="image/x-icon">
</head>
<body>
<div id="wrap">
	<!-- 상단 -->
	<div id="wrap-content-top">
		<%@include file="../header.jsp"%>
		<%@include file="../search.jsp"%>		
	</div>
	<div>
		<table class="post-content-1">
			<c:if test="${member.id==machine.registrant}">
				<tr>
					<td colspan="2">
						<a href="edit-machine?id=${machine.id}">수정</a>
						<a href="delete-machine?id=${machine.id}">삭제</a>						
					</td>
				</tr>
			</c:if>
			<tr>
				<td rowspan="2" width="195px"><img src="${pageContext.request.contextPath}/resources/image/${machine.filename}" width="195px" height="250px" class="post-content-1-main-image"/></td>
				<td>
					<table>
						<tr>
							<td class="post-content-1-title">${machine.name}</td>
							<td>
								<c:if test="${member==null}">
									<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="33" height="33" style="margin: 0 10px;" onclick="alert('로그인이 필요합니다.')"/>
								</c:if>
								<c:if test="${member!=null}">
									<c:choose>
										<c:when test="${favorite==true}">
											<form action="release-favorite?id=${machine.id}" method="post">
												<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-1.svg" width="33" height="33" style="margin: 0 10px;"/>
											</form>
										</c:when>
										<c:otherwise>
											<form action="add-favorite?id=${machine.id}" method="post">
												<input type="image" src="${pageContext.request.contextPath}/resources/image/iconmonstr-favorite-2.svg" width="33" height="33" style="margin: 0 10px;"/>
											</form>
										</c:otherwise>									
									</c:choose>
								</c:if>							
							</td>
							<td>${machine.favorite}</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table border="1" class="post-content-1-detail">
						<tr>
							<td>브랜드</td>
							<td>${machine.brand}</td>
						</tr>
						<tr>
							<td>가격</td>
							<td>${machine.price}원</td>
						</tr>
						<tr>
							<td>로스팅</td>
							<td>${coffee.roastlevel}</td>
						</tr>
						<tr>
							<td>노트</td>
							<td>${coffee.taste}</td>
						</tr>
						<tr>
							<td>용량</td>
							<td>${coffee.volume}g</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b style="font-size: 120%; color: #555555;">평점 평균
						<c:set var="halfRating" value="${ratingAvg / 0.5}"/>
						<c:forEach var="i" begin="1" end="${ratingAvg}">
							<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-filled.svg" width="17" height="17">								
						</c:forEach>
						<c:if test="${halfRating % 2 != 0}">
							<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-half-lined.svg" width="17" height="17">
						</c:if>
						<c:forEach var="j" begin="1" end="${5-ratingAvg}">
							<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-lined.svg" width="17" height="17">
						</c:forEach>
					(${ratingAvg})</b>
					<br>
					<p class="comment-content">${coffee.review}</p>
					<br>
					<p class="comment-regdate">${coffee.registrant} | ${coffee.regdate}에 게시함.</p>
				</td>
			</tr>
		</table>		
	</div>
	<!-- 댓글 -->
	<div>
		<form action="post-comment?num=${coffee.num}" method="post">
			<input type="hidden" name="registrant" id="registrant" value="${member.id}"/>
			<input type="hidden" name="posting" id="posting" value="${coffee.num}"/>
			<input type="hidden" name="category" id="category" value="cb"/>
 			<table class="post-content-1">
				<tr>
					<td colspan="2"><h3>추가 의견</h3></td>
				</tr>
				<tr>
					<td colspan="2">
						평점
						<select name="rating" id="rating">
							<% for(double i = 1.0; i <= 5.0; i+=0.5) { %>
							<option value="<%=i %>"><%=i %></option>
							<% } %>
						</select>
					</td>
				</tr>
				<c:if test="${member==null}">
					<tr>
						<td><textarea rows="6" cols="65" name="content" class="comment-box-1" id="content" readonly>로그인한 회원만 이용 가능합니다.</textarea></td>
						<td><input type="button" class="box-button-1" value="등록" onclick="alert('로그인이 필요합니다.')"/></td>
					</tr>
				</c:if>
				<c:if test="${member!=null}">
					<tr>
						<td><textarea rows="6" cols="65" name="content" class="comment-box-1" id="content">소중한 의견을 남겨주세요.</textarea></td>
						<td><input type="submit" class="box-button-1" value="등록"/></td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div style="margin-top: 2%;">
		<!-- 
		<c:if test="${comments==null}">
			<p>첫 번째 의견을 남겨주세요.</p>
		</c:if>
		 -->
		<c:if test="${comments!=null}">
			<!-- 등록된 댓글 표시 공간 -->
			<c:forEach var="comment" items="${comments}">
				<table class="post-content-1">				
					<tr style="border-top: solid 1px #000000;">
						<td style="padding: 2% 0; width: 77%;"><b style="font-size: 110%; color: #555555;">${comment.registrant}</b></td>
						<td>
							<c:set var="commentRating" value="${comment.rating}"/>
							<c:set var="halfRating2" value="${commentRating / 0.5}"/>
							<c:forEach var="i" begin="1" end="${commentRating}">
								<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-filled.svg" width="17" height="17">								
							</c:forEach>
							<c:if test="${halfRating2 % 2 != 0}">
								<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-half-lined.svg" width="17" height="17">
							</c:if>
							<c:forEach var="j" begin="1" end="${5-commentRating}">
								<img src="${pageContext.request.contextPath}/resources/image/iconmonstr-star-lined.svg" width="17" height="17">
							</c:forEach>
						</td>						
					</tr>
					<tr><td colspan="2"><p>${comment.content}</p></td></tr>
					<tr><td colspan="2"><p style="font-size: 90%; color: #999999;">${comment.regdate}에 작성함.</p></td></tr>				
				</table>
			</c:forEach>
		</c:if>
	</div>
	<%@include file="../footer.jsp"%>
</div>
</body>
</html>