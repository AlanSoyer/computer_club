package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.VisitorDbDAO;
import domain.Visitor;
import exception.DAOException;

@WebServlet("/visitors")
public class VisitorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisitorServlet() {
        super();
        System.out.println("✅ VisitorServlet создан");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("➡️ doGet вызван");
        
        try {
            System.out.println("➡️ Создаём DAO...");
            VisitorDbDAO dao = new VisitorDbDAO();
            
            System.out.println("➡️ Вызываем dao.findAll()...");
            List<Visitor> visitors = dao.findAll();
            
            System.out.println("✅ Найдено записей: " + visitors.size());
            request.setAttribute("visitors", visitors);
            
        } catch (DAOException e) {
            System.out.println("❌ Ошибка DAO: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Другая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/visitors.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("📥 doPost вызван");
        
        // Получаем данные из формы
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymic = request.getParameter("patronymic");
        String identityDocument = request.getParameter("identityDocument");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        
        System.out.println("Получены данные:");
        System.out.println("  firstName = " + firstName);
        System.out.println("  lastName = " + lastName);
        System.out.println("  patronymic = " + patronymic);
        System.out.println("  identityDocument = " + identityDocument);
        System.out.println("  address = " + address);
        System.out.println("  phone = " + phone);
        
        // Создаём объект Visitor
        Visitor visitor = new Visitor(firstName, lastName, patronymic,
                                       identityDocument, address, phone);
        
        try {
            // Сохраняем в БД
            VisitorDbDAO dao = new VisitorDbDAO();
            Long id = dao.insert(visitor);
            System.out.println("✅ Запись добавлена с id = " + id);
            
        } catch (DAOException e) {
            System.out.println("❌ Ошибка при добавлении: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Возвращаемся к списку
        doGet(request, response);
    }
}