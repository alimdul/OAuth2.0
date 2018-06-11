<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <br>
    <jsp:useBean id="calendar" class="java.util.GregorianCalendar"/>
    <%=calendar.getTime()%>

    ${emptyList}

    <table border="3" align="center">
        <tr>
            <th>Номер</th>
            <th>Название</th>
            <th>Время цветения</th>
            <th>Удалить</th>
            <th>Редактировать</th>
        </tr>
        <c:forEach var="family" items="${familyList}">
            <tr>
                <td>${family.id}</td>
                <td>
                    <form name="showFlowerList" method="post" action="/controller">
                        <input type="hidden" name="command" value="show_flower_list"/>
                        <input type="hidden" name="family" value="${family.name}"/>
                        <input type="hidden" name="familyId" value="${family.id}"/>
                        <input type="submit" value="${family.name}"/>
                    </form>
                </td>
                <td>${family.floweringTime}</td>
                <td>
                    <form name="deleteFamily" method="post" action="/controller">
                        <input type="hidden" name="command" value="delete_family"/>
                        <input type="hidden" name="familyId" value="${family.id}"/>
                        <input type="submit" value="Удалить"/>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/jsp/changeFamily.jsp">
                        <c:set var="familyIdForChange" value="${family.id}" />
                        ${pageContext.request.session.setAttribute("familyIdForChange", familyIdForChange)}
                        <input type="submit" value="Редактировать"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br><br>

    <form action="${pageContext.request.contextPath}/jsp/addFamily.jsp">
        <input type="submit" value="Добавить семейство цветов"/>
    </form>
</body>
</html>
