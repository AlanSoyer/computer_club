<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.ComputerStatus" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/computer_club/css/bootstrap.min.css">
    <title>Редактирование статуса</title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="/views/header.jsp" />

        <div class="container mt-4">
            <h2 class="mb-4 text-center">Редактирование статуса</h2>

            <%
                List<ComputerStatus> statuses = (List<ComputerStatus>) request.getAttribute("statuses");
                ComputerStatus editStatus = (ComputerStatus) request.getAttribute("editStatus");
            %>

            <div class="row">
                <div class="col-md-6">
                    <h4>Список статусов</h4>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Статус</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (statuses != null) {
                                for (ComputerStatus s : statuses) { %>
                            <tr>
                                <td><%= s.getId() %></td>
                                <td><%= s.getStatusName() %></td>
                            </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-6">
                    <% if (editStatus != null) { %>
                    <h4>Редактирование</h4>
                    <form method="POST" action="/computer_club/editstatus" class="border p-4 bg-light rounded">
                        <input type="hidden" name="id" value="<%= editStatus.getId() %>">
                        
                        <div class="mb-3">
                            <label class="form-label">Код статуса</label>
                            <input type="text" class="form-control" value="<%= editStatus.getId() %>" readonly>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Наименование статуса</label>
                            <input type="text" name="statusName" class="form-control" 
                                   value="<%= editStatus.getStatusName() %>" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a href="/computer_club/statuses" class="btn btn-secondary">Отмена</a>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
</body>
</html>