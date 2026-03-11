package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ComputerDbDAO;
import dao.ComputerStatusDbDAO;
import domain.Computer;
import domain.ComputerStatus;
import exception.DAOException;

@WebServlet("/computers")
public class ComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ComputerServlet() {
        super();
        System.out.println("✅ ComputerServlet создан");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("➡️ ComputerServlet doGet вызван");
        
        try {
            System.out.println("➡️ Создаём DAO для компьютеров...");
            ComputerDbDAO computerDao = new ComputerDbDAO();
            
            System.out.println("➡️ Создаём DAO для статусов...");
            ComputerStatusDbDAO statusDao = new ComputerStatusDbDAO();
            
            System.out.println("➡️ Загружаем список компьютеров...");
            List<Computer> computers = computerDao.findAll();
            System.out.println("✅ Загружено компьютеров: " + computers.size());
            
            System.out.println("➡️ Загружаем список статусов...");
            List<ComputerStatus> statuses = statusDao.findAll();
            System.out.println("✅ Загружено статусов: " + statuses.size());
            
            request.setAttribute("computers", computers);
            request.setAttribute("statuses", statuses);
            
        } catch (DAOException e) {
            System.out.println("❌ Ошибка DAO: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Другая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/computers.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("📥 ComputerServlet doPost вызван");
        doGet(request, response);
    }
}