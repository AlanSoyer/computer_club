<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Visit, domain.Visitor, domain.Computer, java.time.LocalDate" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Редактирование посещения</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Редактирование посещения</h2>

            <%
                List<Visit> visits = (List<Visit>) request.getAttribute("visits");
                List<Visitor> visitors = (List<Visitor>) request.getAttribute("visitors");
                List<Computer> computers = (List<Computer>) request.getAttribute("computers");
                Visit editVisit = (Visit) request.getAttribute("editVisit");
            %>

            <div class="row">
                <div class="col-md-7">
                    <h4>Журнал посещений</h4>
                    <table class="table table-bordered">
                        <thead>
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
                            <% if (visits != null) {
                                for (Visit v : visits) { %>
                            <tr>
                                <td><%= v.getId() %></td>
                                <td><%= v.getVisitor().getFullName() %></td>
                                <td><%= v.getComputer().getComputerName() %></td>
                                <td><%= v.getVisitDate() %></td>
                                <td><%= v.getFormattedDuration() %></td>
                                <td><%= v.getPayment() %></td>
                            </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-5">
                    <% if (editVisit != null) { %>
                    <h4>Редактирование</h4>
                    <form method="POST" action="/computer_club/editvisit" class="border p-4 bg-light rounded">
                        <input type="hidden" name="id" value="<%= editVisit.getId() %>">
                        
                        <div class="mb-2">
                            <label>Посетитель</label>
                            <select name="visitorId" class="form-control" required>
                                <% if (visitors != null) {
                                    for (Visitor v : visitors) { 
                                        String selected = (v.getId().equals(editVisit.getVisitorId())) ? "selected" : "";
                                %>
                                <option value="<%= v.getId() %>" <%= selected %>><%= v.getFullName() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="mb-2">
                            <label>Компьютер</label>
                            <select name="computerId" class="form-control" required>
                                <% if (computers != null) {
                                    for (Computer c : computers) { 
                                        String selected = (c.getId().equals(editVisit.getComputerId())) ? "selected" : "";
                                %>
                                <option value="<%= c.getId() %>" <%= selected %>><%= c.getComputerName() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="mb-2">
                            <label>Дата</label>
                            <input type="date" name="visitDate" class="form-control" 
                                   value="<%= editVisit.getVisitDate() %>" required>
                        </div>
                        <div class="mb-2">
                            <label>Длительность (мин)</label>
                            <input type="number" name="duration" class="form-control" 
                                   value="<%= editVisit.getDuration() %>" required>
                        </div>
                        <div class="mb-2">
                            <label>Оплата</label>
                            <input type="text" name="payment" class="form-control" 
                                   value="<%= editVisit.getPayment() %>" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a href="/computer_club/visits" class="btn btn-secondary">Отмена</a>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>