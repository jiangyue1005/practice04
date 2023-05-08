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
<title>🔯占い結果🔯</title>
</head>
<body>
	<!-- 結果取得 -->
	<%
	Omikuji omikuji = (Omikuji) session.getAttribute("omikuji");
	%>

	<!-- 結果表示 -->
	<p>
		<%
		//運勢
		out.print("🔯" + " " + omikuji.disp() + " " + "🔯");
		%>
	</p>
	<p>
		<%
		//願い事
		out.print(omikuji.getNegaigoto());
		%>
	</p>
	<p>
		<%
		//商い
		out.print(omikuji.getAkinai());
		%>
	</p>
	<p>
		<%
		//学問
		out.print(omikuji.getGakumon());
		%>
	</p>


	<form class="back">
		<input type="button" name="submit" onClick="history.back()" value="戻る">
	</form>

</body>
</html>