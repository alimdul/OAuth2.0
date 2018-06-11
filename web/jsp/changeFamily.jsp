<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form name="changeFamily" method="post" action="/controller">
        <input type="hidden" name="command" value="change_family"/>
        Название <br>
        <input type="text" name="name" value=""/> <br>
        Время цветения<br>
        <input type="text" name="floweringTime" value=""/> <br><br>

        <input type="submit" value="Редактировать"/>
    </form> <br><br>

    <form name="showFamilyList" method="post" action="/controller">
        <input type="hidden" name="command" value="show_family_list"/>
        <input type="submit" value="На главную"/>
    </form> <br>
</body>
</html>
