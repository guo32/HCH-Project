<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach var="nation" items="${nationList}">
		<div>국가명 : ${nation.country} | 그룹 : ${nation.group}</div>
	</c:forEach>
	<select>
		<option value="">선택하세요</option>
		<optgroup label="아라비카">
			<c:forEach var="nation" items="${nationList}">
				<c:if test="${nation.group eq '아라비카'}">
					<option value="${nation.nationid}">${nation.country}</option>
				</c:if>
			</c:forEach>
		</optgroup>
		<optgroup label="로부스타">
			<c:forEach var="nation" items="${nationList}">
				<c:if test="${nation.group eq '로부스타'}">
					<option value="${nation.nationid}">${nation.country}</option>
				</c:if>
			</c:forEach>
		</optgroup>
	</select>
	<c:forEach var="note" items="${noteList}">
		<div>note name : ${note.minor} | middle : ${note.middle} | major : ${note.major}</div>
	</c:forEach>
	<script type="text/javascript">
		function changeMajor(v) {
			//var major = document.getElementById("majorValue");
			
			var value = v;
			alert(value); // 값 확인용
			var arr = new Array();
			var test = "";
			
			var middle = document.querySelector('.middleSelect');
			middle.options.length = 0;
			var option = document.createElement("option");
			option.innerText = "중분류";
			option.value = "null";
			middle.append(option);
			<c:forEach var="note" items="${noteList}">
				if("${note.major}" == value) {
					if(test != "${note.middle}") {
						test = "${note.middle}";
						var option = document.createElement("option");
						option.innerText = "${note.middle}";
						option.value = "${note.middle}";
						middle.append(option);
					}
					
					
					//arr.push("${note.middle}");
					//test += "${note.middle}" + "\n";
				}
			</c:forEach>
			
			//alert(test);
		}
		
		function changeMiddle(v) {
			var value = v;
			alert(value);
			var minor = document.querySelector('.minorSelect');
			minor.options.length = 0;
			var option = document.createElement("option");
			option.innerText = "소분류";
			option.value = "null";
			minor.append(option);
			<c:forEach var="note" items="${noteList}">
			 	if("${note.middle}" == value) {
			 		var option = document.createElement("option");
			 		option.innerText = "${note.minor}";
			 		option.value = "${note.noteid}";
			 		minor.append(option);
			 	}
			</c:forEach>
		}
	</script>
	<select onchange="changeMajor(this.value)">
		<option value="null">대분류</option>
		<c:forEach var="major" items="${majorList}">
			<option value="${major}">${major}</option>
		</c:forEach>
	</select>
	<select class="middleSelect" onchange="changeMiddle(this.value)">
		<option value="null">중분류</option>
	</select>
	<select class="minorSelect">
		<option value="null">소분류</option>
	</select>
</body>
</html>