package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionBuilder implements ConnectionBuilder {
    
    public DbConnectionBuilder() {
        try {
            // Загружаем драйвер PostgreSQL напрямую (без config.properties)
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Драйвер PostgreSQL загружен");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Ошибка загрузки драйвера");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // Параметры подключения прописаны прямо в коде
        String url = "jdbc:postgresql://localhost:5432/computer_club_db";
        String login = "postgres";
        String password = "admin"; // замени на свой пароль, если другой
        
        System.out.println("🔌 Подключаемся к БД...");
        Connection conn = DriverManager.getConnection(url, login, password);
        System.out.println("✅ Подключение успешно");
        return conn;
    }
}