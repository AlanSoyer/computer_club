<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.Computer, domain.ComputerStatus" %>

<%
    ComputerStatus s1 = new ComputerStatus(1L, "Работает");
    Computer c1 = new Computer(1L, "PC-01", "Intel i5, 16GB RAM", 1L, s1);
    Computer c2 = new Computer(2L, "PC-02", "Intel i7, 32GB RAM", 1L, s1);
    Computer[] computers = new Computer[]{c1, c2};
%>

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
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Код</th>
                        <th>Название</th>
                        <th>Описание</th>
                        <th>Статус</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Computer c : computers) { %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td><%= c.getComputerName() %></td>
                        <td><%= c.getDescription() %></td>
                        <td><%= c.getStatusName() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый компьютер</h3>
            <form method="POST" action="#" class="border p-4 bg-light rounded">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Название</label>
                        <input type="text" name="computerName" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Описание</label>
                        <input type="text" name="description" class="form-control">
                    </div>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
    <script src="/computer_club/js/jquery-3.6.4.js"></script>
    <script src="/computer_club/js/bootstrap.bundle.min.js"></script>
</body>
</html>