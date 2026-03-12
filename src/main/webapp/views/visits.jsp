<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Visit, domain.Visitor, domain.Computer" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Посещения</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Журнал посещений</h2>

            <%
                List<Visit> visits = (List<Visit>) request.getAttribute("visits");
            %>

            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Посетитель</th>
                        <th>Компьютер</th>
                        <th>Дата</th>
                        <th>Длительность</th>
                        <th>Оплата</th>
                        <th>Редакт.</th>
                        <th>Удалить</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (visits != null && !visits.isEmpty()) {
                            for (Visit v : visits) {
                    %>
                    <tr>
                        <td><%= v.getId() %></td>
                        <td><%= v.getVisitor().getFullName() %></td>
                        <td><%= v.getComputer().getComputerName() %></td>
                        <td><%= v.getVisitDate() %></td>
                        <td><%= v.getFormattedDuration() %></td>
                        <td><%= v.getPayment() %></td>
                        <td>
                            <a href="/computer_club/editvisit?id=<%= v.getId() %>" 
                               class="btn btn-sm btn-outline-primary">✏️</a>
                        </td>
                        <td>
                            <a href="/computer_club/deletevisit?id=<%= v.getId() %>" 
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Удалить посещение с кодом <%= v.getId() %>?')">🗑️</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">Нет данных</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>