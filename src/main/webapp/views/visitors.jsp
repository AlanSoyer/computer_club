<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.Visitor" %>

<%
    Visitor v1 = new Visitor(1L, "Иван", "Иванов", "Иванович",
                              "паспорт 1234", "ул. Ленина, 1", "+7-911-111-11-11");
    Visitor v2 = new Visitor(2L, "Петр", "Петров", "Петрович",
                              "паспорт 5678", "ул. Пушкина, 5", "+7-922-222-22-22");
    Visitor[] visitors = new Visitor[]{v1, v2};
%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Посетители</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Список посетителей</h2>
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Код</th>
                        <th>ФИО</th>
                        <th>Телефон</th>
                        <th>Документ</th>
                        <th>Адрес</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Visitor v : visitors) { %>
                    <tr>
                        <td><%= v.getId() %></td>
                        <td><%= v.getFullName() %></td>
                        <td><%= v.getPhone() %></td>
                        <td><%= v.getIdentityDocument() %></td>
                        <td><%= v.getAddress() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый посетитель</h3>
            <form method="POST" action="#" class="border p-4 bg-light rounded">
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Фамилия</label>
                        <input type="text" name="lastName" class="form-control">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Имя</label>
                        <input type="text" name="firstName" class="form-control">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Отчество</label>
                        <input type="text" name="patronymic" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Телефон</label>
                        <input type="text" name="phone" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Документ</label>
                        <input type="text" name="identityDocument" class="form-control">
                    </div>
                    <div class="col-12 mb-3">
                        <label class="form-label">Адрес</label>
                        <input type="text" name="address" class="form-control">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
    <script src="/computer_club/js/jquery-3.6.4.js"></script>
    <script src="/computer_club/js/bootstrap.bundle.min.js"></script>
</body>
</html>