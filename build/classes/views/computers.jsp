<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Computer, domain.ComputerStatus" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Компьютеры</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Список компьютеров</h2>

            <%
                List<Computer> computers = (List<Computer>) request.getAttribute("computers");
            %>

            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Описание</th>
                        <th>Статус</th>
                        <th>Редакт.</th>
                        <th>Удалить</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (computers != null && !computers.isEmpty()) {
                            for (Computer c : computers) {
                    %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td><%= c.getComputerName() %></td>
                        <td><%= c.getDescription() %></td>
                        <td><%= c.getStatusName() %></td>
                        <td>
                            <a href="/computer_club/editcomputer?id=<%= c.getId() %>" 
                               class="btn btn-sm btn-outline-primary">✏️</a>
                        </td>
                        <td>
                            <a href="/computer_club/deletecomputer?id=<%= c.getId() %>" 
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Удалить компьютер с кодом <%= c.getId() %>?')">🗑️</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">Нет данных</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый компьютер</h3>
            <form method="POST" action="/computer_club/computers" class="border p-4 bg-light rounded">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <input type="text" name="computerName" class="form-control" placeholder="Название" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <input type="text" name="description" class="form-control" placeholder="Описание">
                    </div>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>