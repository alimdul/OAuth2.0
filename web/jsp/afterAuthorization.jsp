<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div style="float: right">
    <jsp:include page="/jsp/logoutForm.jsp"/>
</div>

${pageContext.session.getAttribute("userName")}, добро пожаловать=) <br><br>

<form name="showFamilyList" method="post" action="/controller">
    <input type="hidden" name="command" value="show_family_list"/>
    <input type="submit" value="Просмотреть все семейства"/>
</form> <br>

</body>
</html>
