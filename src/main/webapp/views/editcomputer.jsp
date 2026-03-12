<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.Computer, domain.ComputerStatus" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Редактирование компьютера</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Редактирование компьютера</h2>

            <%
                List<Computer> computers = (List<Computer>) request.getAttribute("computers");
                List<ComputerStatus> statuses = (List<ComputerStatus>) request.getAttribute("statuses");
                Computer editComputer = (Computer) request.getAttribute("editComputer");
            %>

            <div class="row">
                <div class="col-md-7">
                    <h4>Список компьютеров</h4>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Название</th>
                                <th>Описание</th>
                                <th>Статус</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (computers != null) {
                                for (Computer c : computers) { %>
                            <tr>
                                <td><%= c.getId() %></td>
                                <td><%= c.getComputerName() %></td>
                                <td><%= c.getDescription() %></td>
                                <td><%= c.getStatusName() %></td>
                            </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-5">
                    <% if (editComputer != null) { %>
                    <h4>Редактирование</h4>
                    <form method="POST" action="/computer_club/editcomputer" class="border p-4 bg-light rounded">
                        <input type="hidden" name="id" value="<%= editComputer.getId() %>">
                        
                        <div class="mb-2">
                            <label>Название</label>
                            <input type="text" name="computerName" class="form-control" 
                                   value="<%= editComputer.getComputerName() %>" required>
                        </div>
                        <div class="mb-2">
                            <label>Описание</label>
                            <input type="text" name="description" class="form-control" 
                                   value="<%= editComputer.getDescription() %>">
                        </div>
                        <div class="mb-2">
                            <label>Статус</label>
                            <select name="statusId" class="form-control" required>
                                <% if (statuses != null) {
                                    for (ComputerStatus s : statuses) { 
                                        String selected = (s.getId().equals(editComputer.getStatusId())) ? "selected" : "";
                                %>
                                <option value="<%= s.getId() %>" <%= selected %>><%= s.getStatusName() %></option>
                                <% } } %>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a href="/computer_club/computers" class="btn btn-secondary">Отмена</a>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>