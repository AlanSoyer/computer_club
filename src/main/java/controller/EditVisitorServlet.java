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

@WebServlet("/editvisitor")
public class EditVisitorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditVisitorServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            VisitorDbDAO dao = new VisitorDbDAO();
            List<Visitor> visitors = dao.findAll();
            request.setAttribute("visitors", visitors);
            
            String strId = request.getParameter("id");
            if (strId != null) {
                Long id = Long.parseLong(strId);
                Visitor editVisitor = dao.findById(id);
                request.setAttribute("editVisitor", editVisitor);
            }
            
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/views/editvisitor.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String strId = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String patronymic = request.getParameter("patronymic");
            String identityDocument = request.getParameter("identityDocument");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            
            if (strId != null) {
                Long id = Long.parseLong(strId);
                Visitor visitor = new Visitor(id, firstName, lastName, patronymic,
                                               identityDocument, address, phone);
                
                VisitorDbDAO dao = new VisitorDbDAO();
                dao.update(visitor);
                System.out.println("✅ Посетитель обновлён: id=" + id);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/visitors");
    }
}