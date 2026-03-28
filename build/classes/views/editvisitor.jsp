<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Visitor" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Редактирование посетителя</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Редактирование посетителя</h2>

            <%
                List<Visitor> visitors = (List<Visitor>) request.getAttribute("visitors");
                Visitor editVisitor = (Visitor) request.getAttribute("editVisitor");
            %>

            <div class="row">
                <div class="col-md-7">
                    <h4>Список посетителей</h4>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Фамилия</th>
                                <th>Имя</th>
                                <th>Отчество</th>
                                <th>Телефон</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (visitors != null) {
                                for (Visitor v : visitors) { %>
                            <tr>
                                <td><%= v.getId() %></td>
                                <td><%= v.getLastName() %></td>
                                <td><%= v.getFirstName() %></td>
                                <td><%= v.getPatronymic() %></td>
                                <td><%= v.getPhone() %></td>
                            </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-5">
                    <% if (editVisitor != null) { %>
                    <h4>Редактирование</h4>
                    <form method="POST" action="/computer_club/editvisitor" class="border p-4 bg-light rounded">
                        <input type="hidden" name="id" value="<%= editVisitor.getId() %>">
                        
                        <div class="mb-2">
                            <label>Фамилия</label>
                            <input type="text" name="lastName" class="form-control" 
                                   value="<%= editVisitor.getLastName() %>" required>
                        </div>
                        <div class="mb-2">
                            <label>Имя</label>
                            <input type="text" name="firstName" class="form-control" 
                                   value="<%= editVisitor.getFirstName() %>" required>
                        </div>
                        <div class="mb-2">
                            <label>Отчество</label>
                            <input type="text" name="patronymic" class="form-control" 
                                   value="<%= editVisitor.getPatronymic() %>">
                        </div>
                        <div class="mb-2">
                            <label>Документ</label>
                            <input type="text" name="identityDocument" class="form-control" 
                                   value="<%= editVisitor.getIdentityDocument() %>">
                        </div>
                        <div class="mb-2">
                            <label>Адрес</label>
                            <input type="text" name="address" class="form-control" 
                                   value="<%= editVisitor.getAddress() %>">
                        </div>
                        <div class="mb-2">
                            <label>Телефон</label>
                            <input type="text" name="phone" class="form-control" 
                                   value="<%= editVisitor.getPhone() %>" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a href="/computer_club/visitors" class="btn btn-secondary">Отмена</a>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>