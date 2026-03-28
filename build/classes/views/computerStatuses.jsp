<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.ComputerStatus" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Статусы компьютеров</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Список статусов</h2>

            <%
                List<ComputerStatus> statuses = (List<ComputerStatus>) request.getAttribute("statuses");
            %>

            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Статус</th>
                        <th>Редактировать</th>
                        <th>Удалить</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (statuses != null && !statuses.isEmpty()) {
                            for (ComputerStatus s : statuses) {
                    %>
                    <tr>
                        <td><%= s.getId() %></td>
                        <td><%= s.getStatusName() %></td>
                        <td>
                            <a href="/computer_club/editstatus?id=<%= s.getId() %>" 
                               class="btn btn-sm btn-outline-primary">✏️ Редактировать</a>
                        </td>
                        <td>
                            <a href="/computer_club/deletestatus?id=<%= s.getId() %>" 
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Удалить статус с кодом <%= s.getId() %>?')">🗑️ Удалить</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Нет данных</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый статус</h3>
            <form method="POST" action="/computer_club/statuses" class="border p-4 bg-light rounded w-50 mx-auto">
                <div class="mb-3">
                    <input type="text" name="statusName" class="form-control" placeholder="Название статуса" required>
                </div>
                <button type="submit" class="btn btn-info text-white">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>