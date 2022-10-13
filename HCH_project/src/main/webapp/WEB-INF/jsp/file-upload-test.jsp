<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>파일업로드</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data" action="fileUploadTest">
		<input type="file" name="imagefile">
		<input type="submit">
	</form>
</body>
</html>