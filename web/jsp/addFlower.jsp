<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form name="addFlower" method="post" action="/controller">
        <input type="hidden" name="command" value="add_flower"/>
        Название <br>
        <input type="text" name="name" value=""/> <br>
        Описание стебля<br>
        <input type="text" name="stem" value=""/> <br>
        Описание листка<br>
        <input type="text" name="leaf" value=""/> <br><br>

        <input type="submit" value="Добавить"/>
    </form> <br><br>

    <form name="showFamilyList" method="post" action="/controller">
        <input type="hidden" name="command" value="show_family_list"/>
        <input type="submit" value="На главную"/>
    </form> <br>
</body>
</html>
