<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${emptyList}

    <table border="3" align="center">
        <tr>
            <th>Номер</th>
            <th>Название</th>
            <th>Описание стебля</th>
            <th>Описание листка</th>
            <th>Удалить</th>
            <th>Редактировать</th>
        </tr>
        <c:forEach var="flower" items="${flowerList}">
        <tr>
            <td>${flower.id}</td>
            <td>${flower.name}</td>
            <td>${flower.stem}</td>
            <td>${flower.leaf}</td>
            <td>
                <form name="deleteFlower" method="post" action="/controller">
                    <input type="hidden" name="command" value="delete_flower"/>
                    <input type="hidden" name="flowerId" value="${flower.id}"/>
                    <input type="submit" value="Удалить"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/jsp/changeFlower.jsp">
                    <c:set var="flowerIdForChange" value="${flower.id}" />
                        ${pageContext.request.session.setAttribute("flowerIdForChange", flowerIdForChange)}
                    <input type="submit" value="Редактировать"/>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>

    <br><br>

    <form action="${pageContext.request.contextPath}/jsp/addFlower.jsp">
        <input type="submit" value="Добавить цветок"/>
    </form> <br>
    <form name="showFamilyList" method="post" action="/controller">
        <input type="hidden" name="command" value="show_family_list"/>
        <input type="submit" value="На главную"/>
    </form> <br>
</body>
</html>
