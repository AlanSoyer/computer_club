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

@WebServlet("/editcomputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditComputerServlet() {
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
            
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                Computer editComputer = computerDao.findById(id);
                request.setAttribute("editComputer", editComputer);
            }
            
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/editcomputer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            String computerName = request.getParameter("computerName");
            String description = request.getParameter("description");
            Long statusId = Long.parseLong(request.getParameter("statusId"));
            
            if (strId != null) {
                Long id = Long.parseLong(strId);
                Computer computer = new Computer();
                computer.setId(id);
                computer.setComputerName(computerName);
                computer.setDescription(description);
                computer.setStatusId(statusId);
                
                ComputerDbDAO dao = new ComputerDbDAO();
                dao.update(computer);
                System.out.println("✅ Компьютер обновлён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/computers");
    }
}