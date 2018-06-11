<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p align="center" style="color:#ff0099; font-size:30px">СПРАВОЧНИК ПО ВИДАМ ЦВЕТОВ</p> <br><br>

<p align="center">Необходима регистрация</p> <br><br>

<form name="vk" method="get" action="/controller">
    <input type="hidden" name="command" value="vk_authorization"/>
    <input type="submit" value="Войти через VKontakte"/>
</form> <br>

<form name="gh" method="get" action="/controller">
    <input type="hidden" name="command" value="gh_authorization"/>
    <input type="submit" value="Войти через Github"/>
</form> <br>

<form name="yd" method="get" action="/controller">
    <input type="hidden" name="command" value="yd_authorization"/>
    <input type="submit" value="Войти через Yandex.ru"/>
</form> <br>

</body>
</html>
