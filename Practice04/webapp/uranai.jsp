<%@ page language="java" import="kadai4.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body {
	background-color: #E6E6F2;
	margin-bottom: 1px;
}

form {
	font-size: 1.2em;
	text-align: center;
}

.errMessage {
	font-size: 1em;
	text-align: center;
	color: red;
}
</style>
<meta charset="UTF-8">
<title>🔯占い🔯</title>
</head>
<body>
	<%
	ErrMessage errMessage = (ErrMessage) session.getAttribute("errMessage");
	%>
	<form action="uranai">
		誕生日を入力してください(yyyymmdd): <br> <input type="text" name="birthday">
		<input type="submit">
	</form>

	<!-- エラーメッセージあれば取得 -->
	<div class="errMessage">
		<br />
		<%
		if (errMessage != null && errMessage.getErrMessage() != null) {
			out.println(errMessage.getErrMessage());
			errMessage.setErrMessage(null);
		}
		%>
	</div>
</body>
</html>