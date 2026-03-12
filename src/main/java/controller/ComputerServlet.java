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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ComputerDbDAO computerDao = new ComputerDbDAO();
            ComputerStatusDbDAO statusDao = new ComputerStatusDbDAO();
            
            List<Computer> computers = computerDao.findAll();
            List<ComputerStatus> statuses = statusDao.findAll();
            
            request.setAttribute("computers", computers);
            request.setAttribute("statuses", statuses);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/computers.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String computerName = request.getParameter("computerName");
        String description = request.getParameter("description");
        Long statusId = Long.parseLong(request.getParameter("statusId"));

        Computer computer = new Computer();
        computer.setComputerName(computerName);
        computer.setDescription(description);
        computer.setStatusId(statusId);

        try {
            ComputerDbDAO dao = new ComputerDbDAO();
            Long id = dao.insert(computer);
            System.out.println("Добавлен компьютер с id: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/computers");
    }
}