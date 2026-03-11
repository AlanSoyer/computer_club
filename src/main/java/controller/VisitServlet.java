package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import dao.VisitDbDAO;
import dao.VisitorDbDAO;
import dao.ComputerDbDAO;
import domain.Visit;
import domain.Visitor;
import domain.Computer;
import exception.DAOException;

@WebServlet("/visits")
public class VisitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisitServlet() {
        super();
        System.out.println("✅ VisitServlet создан");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("➡️ VisitServlet doGet вызван");
        
        try {
            System.out.println("➡️ Создаём DAO...");
            VisitDbDAO visitDao = new VisitDbDAO();
            VisitorDbDAO visitorDao = new VisitorDbDAO();
            ComputerDbDAO computerDao = new ComputerDbDAO();
            
            System.out.println("➡️ Загружаем посещения...");
            List<Visit> visits = visitDao.findAll();
            System.out.println("✅ Загружено посещений: " + visits.size());
            
            System.out.println("➡️ Загружаем посетителей...");
            List<Visitor> visitors = visitorDao.findAll();
            System.out.println("✅ Загружено посетителей: " + visitors.size());
            
            System.out.println("➡️ Загружаем компьютеры...");
            List<Computer> computers = computerDao.findAll();
            System.out.println("✅ Загружено компьютеров: " + computers.size());
            
            request.setAttribute("visits", visits);
            request.setAttribute("visitors", visitors);
            request.setAttribute("computers", computers);
            
        } catch (DAOException e) {
            System.out.println("❌ Ошибка DAO: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Другая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/visits.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("📥 VisitServlet doPost вызван");
        
        try {
            Long visitorId = Long.parseLong(request.getParameter("visitorId"));
            Long computerId = Long.parseLong(request.getParameter("computerId"));
            LocalDate visitDate = LocalDate.parse(request.getParameter("visitDate"));
            Integer duration = Integer.parseInt(request.getParameter("duration"));
            Double payment = Double.parseDouble(request.getParameter("payment"));
            
            System.out.println("Получены данные:");
            System.out.println("  visitorId = " + visitorId);
            System.out.println("  computerId = " + computerId);
            System.out.println("  visitDate = " + visitDate);
            System.out.println("  duration = " + duration);
            System.out.println("  payment = " + payment);
            
            Visit visit = new Visit();
            visit.setVisitorId(visitorId);
            visit.setComputerId(computerId);
            visit.setVisitDate(visitDate);
            visit.setDuration(duration);
            visit.setPayment(payment);
            
            VisitDbDAO dao = new VisitDbDAO();
            Long id = dao.insert(visit);
            System.out.println("✅ Добавлено посещение с id = " + id);
            
        } catch (Exception e) {
            System.out.println("❌ Ошибка в doPost: " + e.getMessage());
            e.printStackTrace();
        }
        
        doGet(request, response);
    }
}