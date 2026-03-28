package controller;

import com.google.gson.Gson;
import dao.ComputerDbDAO;
import domain.Computer;
import exception.DAOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/computerapi")
public class ComputerApiServlet extends HttpServlet {
    
    private final Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            ComputerDbDAO dao = new ComputerDbDAO();
            List<Computer> computers = dao.findAll();
            String json = gson.toJson(computers);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            BufferedReader reader = request.getReader();
            Computer computer = gson.fromJson(reader, Computer.class);
            
            ComputerDbDAO dao = new ComputerDbDAO();
            Long id = dao.insert(computer);
            computer.setId(id);
            
            String json = gson.toJson(computer);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            Long id = null;
            if (pathInfo != null && pathInfo.length() > 1) {
                id = Long.parseLong(pathInfo.substring(1));
            }
            
            BufferedReader reader = request.getReader();
            Computer computer = gson.fromJson(reader, Computer.class);
            computer.setId(id);
            
            ComputerDbDAO dao = new ComputerDbDAO();
            dao.update(computer);
            
            String json = gson.toJson(computer);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Invalid ID\"}");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            Long id = null;
            if (pathInfo != null && pathInfo.length() > 1) {
                id = Long.parseLong(pathInfo.substring(1));
            }
            
            if (id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"ID required\"}");
                return;
            }
            
            ComputerDbDAO dao = new ComputerDbDAO();
            dao.delete(id);
            
            response.getWriter().print("{\"success\": true}");
        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}