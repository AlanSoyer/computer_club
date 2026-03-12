<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Visitor" %>

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

            <%
                List<Visitor> visitors = (List<Visitor>) request.getAttribute("visitors");
            %>

            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Фамилия</th>
                        <th>Имя</th>
                        <th>Отчество</th>
                        <th>Документ</th>
                        <th>Адрес</th>
                        <th>Телефон</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (visitors != null && !visitors.isEmpty()) {
                            for (Visitor v : visitors) {
                    %>
                    <tr>
                        <td><%= v.getId() %></td>
                        <td><%= v.getLastName() %></td>
                        <td><%= v.getFirstName() %></td>
                        <td><%= v.getPatronymic() %></td>
                        <td><%= v.getIdentityDocument() %></td>
                        <td><%= v.getAddress() %></td>
                        <td><%= v.getPhone() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">Нет данных</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h3 class="mb-3">Новый посетитель</h3>
            <form method="POST" action="/computer_club/visitors" class="border p-4 bg-light rounded">
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Фамилия</label>
                        <input type="text" name="lastName" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Имя</label>
                        <input type="text" name="firstName" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Отчество</label>
                        <input type="text" name="patronymic" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Документ</label>
                        <input type="text" name="identityDocument" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Адрес</label>
                        <input type="text" name="address" class="form-control">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Телефон</label>
                        <input type="text" name="phone" class="form-control">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
    <script src="/computer_club/js/bootstrap.bundle.min.js"></script>
</body>
</html>