<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.Visit, domain.Visitor, domain.Computer, domain.ComputerStatus, java.time.LocalDate" %>

<%
    ComputerStatus s1 = new ComputerStatus(1L, "Работает");
    Computer c1 = new Computer(1L, "PC-01", "Intel i5, 16GB RAM", 1L, s1);
    Visitor v1 = new Visitor(1L, "Иван", "Иванов", "Иванович",
                              "паспорт 1234", "ул. Ленина, 1", "+7-911-111-11-11");

    Visit visit1 = new Visit(1L, v1, c1, LocalDate.now(), 120, 300.0);
    Visit[] visits = new Visit[]{visit1};
%>

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
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Код</th>
                        <th>Посетитель</th>
                        <th>Компьютер</th>
                        <th>Дата</th>
                        <th>Длительность</th>
                        <th>Оплата</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Visit visit : visits) { %>
                    <tr>
                        <td><%= visit.getId() %></td>
                        <td><%= visit.getVisitor().getFullName() %></td>
                        <td><%= visit.getComputer().getComputerName() %></td>
                        <td><%= visit.getVisitDate() %></td>
                        <td><%= visit.getFormattedDuration() %></td>
                        <td><%= visit.getPayment() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <jsp:include page="/views/footer.jsp" />
    </div>
    <script src="/computer_club/js/jquery-3.6.4.js"></script>
    <script src="/computer_club/js/bootstrap.bundle.min.js"></script>
</body>
</html>