<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    
    <title>Компьютерный клуб - Учет посетителей</title>
</head>
<body>
    <div class="container-fluid">
        <!-- Подключаем шапку -->
        <jsp:include page="/views/header.jsp" />

        <!-- Основной контент -->
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-12 text-center">
                    <h1>Добро пожаловать в систему учета</h1>
                    <p class="lead">Выберите раздел для работы</p>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body text-center">
                            <h5 class="card-title">Посетители</h5>
                            <p class="card-text">Управление данными посетителей</p>
                            <a href="/computer_club/visitors" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body text-center">
                            <h5 class="card-title">Компьютеры</h5>
                            <p class="card-text">Учет компьютеров и их статусов</p>
                            <a href="/computer_club/computers" class="btn btn-success">Перейти</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body text-center">
                            <h5 class="card-title">Посещения</h5>
                            <p class="card-text">Журнал посещений клуба</p>
                            <a href="/computer_club/visits" class="btn btn-warning">Перейти</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body text-center">
                            <h5 class="card-title">Статусы</h5>
                            <p class="card-text">Статусы компьютеров</p>
                            <a href="/computer_club/statuses" class="btn btn-info">Перейти</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Подключаем подвал -->
        <jsp:include page="/views/footer.jsp" />
    </div>

    <!-- jQuery и Bootstrap JS -->
    <script src="js/jquery-3.6.4.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>
