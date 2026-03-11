<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.ComputerStatus" %>

<%
    ComputerStatus s1 = new ComputerStatus(1L, "Работает");
    ComputerStatus s2 = new ComputerStatus(2L, "В ремонте");
    ComputerStatus s3 = new ComputerStatus(3L, "Свободен");
    ComputerStatus[] statuses = new ComputerStatus[]{s1, s2, s3};
%>

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
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Код</th>
                        <th>Статус</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ComputerStatus s : statuses) { %>
                    <tr>
                        <td><%= s.getId() %></td>
                        <td><%= s.getStatusName() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый статус</h3>
            <form method="POST" action="#" class="border p-4 bg-light rounded w-50 mx-auto">
                <div class="mb-3">
                    <label class="form-label">Наименование статуса</label>
                    <input type="text" name="statusName" class="form-control">
                </div>
                <button type="submit" class="btn btn-info text-white">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
    <script src="/computer_club/js/jquery-3.6.4.js"></script>
    <script src="/computer_club/js/bootstrap.bundle.min.js"></script>
</body>
</html>