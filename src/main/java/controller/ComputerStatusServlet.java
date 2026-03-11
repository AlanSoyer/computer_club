package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ComputerStatusDbDAO;
import domain.ComputerStatus;
import exception.DAOException;

@WebServlet("/statuses")
public class ComputerStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ComputerStatusServlet() {
        super();
        System.out.println("✅ ComputerStatusServlet создан");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("➡️ ComputerStatusServlet doGet вызван");
        
        try {
            System.out.println("➡️ Создаём DAO...");
            ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
            
            System.out.println("➡️ Загружаем статусы...");
            List<ComputerStatus> statuses = dao.findAll();
            System.out.println("✅ Загружено статусов: " + statuses.size());
            
            request.setAttribute("statuses", statuses);
            
        } catch (DAOException e) {
            System.out.println("❌ Ошибка DAO: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Другая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/computerStatuses.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("📥 ComputerStatusServlet doPost вызван");
        
        try {
            String statusName = request.getParameter("statusName");
            System.out.println("Получен статус: " + statusName);
            
            ComputerStatus status = new ComputerStatus();
            status.setStatusName(statusName);
            
            ComputerStatusDbDAO dao = new ComputerStatusDbDAO();
            Long id = dao.insert(status);
            System.out.println("✅ Добавлен статус с id = " + id);
            
        } catch (Exception e) {
            System.out.println("❌ Ошибка в doPost: " + e.getMessage());
            e.printStackTrace();
        }
        
        doGet(request, response);
    }
}