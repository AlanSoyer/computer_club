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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VisitDbDAO visitDao = new VisitDbDAO();
            VisitorDbDAO visitorDao = new VisitorDbDAO();
            ComputerDbDAO computerDao = new ComputerDbDAO();
            
            List<Visit> visits = visitDao.findAll();
            List<Visitor> visitors = visitorDao.findAll();
            List<Computer> computers = computerDao.findAll();
            
            request.setAttribute("visits", visits);
            request.setAttribute("visitors", visitors);
            request.setAttribute("computers", computers);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/visits.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Long visitorId = Long.parseLong(request.getParameter("visitorId"));
        Long computerId = Long.parseLong(request.getParameter("computerId"));
        LocalDate visitDate = LocalDate.parse(request.getParameter("visitDate"));
        Integer duration = Integer.parseInt(request.getParameter("duration"));
        Double payment = Double.parseDouble(request.getParameter("payment"));

        Visit visit = new Visit();
        visit.setVisitorId(visitorId);
        visit.setComputerId(computerId);
        visit.setVisitDate(visitDate);
        visit.setDuration(duration);
        visit.setPayment(payment);

        try {
            VisitDbDAO dao = new VisitDbDAO();
            Long id = dao.insert(visit);
            System.out.println("Добавлено посещение с id: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/visits");
    }
}