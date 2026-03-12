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
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (visits != null && !visits.isEmpty()) {
                            for (Visit v : visits) {
                    %>
                    <tr>
                        <td><%= v.getId() %></td>
                        <td>
                            <% if (v.getVisitor() != null) { %>
                                <%= v.getVisitor().getFullName() %>
                            <% } else { %>
                                <span class="text-muted">—</span>
                            <% } %>
                        </td>
                        <td>
                            <% if (v.getComputer() != null) { %>
                                <%= v.getComputer().getComputerName() %>
                            <% } else { %>
                                <span class="text-muted">—</span>
                            <% } %>
                        </td>
                        <td><%= v.getVisitDate() %></td>
                        <td><%= v.getFormattedDuration() %></td>
                        <td><%= v.getPayment() %></td>
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
            <h3 class="mb-3">Новое посещение</h3>
            <form method="POST" action="/computer_club/visits" class="border p-4 bg-light rounded">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <select name="visitorId" class="form-control" required>
                            <option value="">Выберите посетителя</option>
                            <%
                                List<Visitor> visitors = (List<Visitor>) request.getAttribute("visitors");
                                if (visitors != null) {
                                    for (Visitor v : visitors) {
                            %>
                            <option value="<%= v.getId() %>"><%= v.getFullName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <select name="computerId" class="form-control" required>
                            <option value="">Выберите компьютер</option>
                            <%
                                List<Computer> computers = (List<Computer>) request.getAttribute("computers");
                                if (computers != null) {
                                    for (Computer c : computers) {
                            %>
                            <option value="<%= c.getId() %>"><%= c.getComputerName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <input type="date" name="visitDate" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <input type="number" name="duration" class="form-control" placeholder="Длительность (мин)" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <input type="text" name="payment" class="form-control" placeholder="Сумма" required>
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