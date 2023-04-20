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

p {
	font-size: 1.2em;
	text-align: center;
}

.back {
	text-align: center;
}
</style>
<meta charset="UTF-8">
<title>占い結果</title>
</head>
<body>
	<!-- 結果取得 -->
	<%
	Omikuji omikuji = (Omikuji) session.getAttribute("omikuji");
	String result = "🔯" + "　" + omikuji.disp() + "🔯" + "　" + "," + omikuji.getNegaigoto() + "," + omikuji.getAkinai()
			+ "," + omikuji.getGakumon();
	%>

	<!-- 改行出力 -->
	<%!public String convertBR(String s) {
		String newStr = s.replaceAll(",", "<BR />");
		return newStr;
	}%>

	<p><%=convertBR(result)%></p>

	<form class="back">
		<input type="button" name="submit" onClick="history.back()" value="戻る">
	</form>

</body>
</html>