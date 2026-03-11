package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import domain.Visit;
import domain.Visitor;
import domain.Computer;
import exception.DAOException;

public class VisitDbDAO implements RepositoryDAO<Visit> {
    
    private ConnectionBuilder builder = new DbConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private VisitorDbDAO visitorDAO = new VisitorDbDAO();
    private ComputerDbDAO computerDAO = new ComputerDbDAO();

    private static final String SELECT_ALL = 
        "SELECT id, visitor_id, computer_id, visit_date, duration, payment FROM visits ORDER BY visit_date DESC";
    
    private static final String SELECT_BY_ID = 
        "SELECT id, visitor_id, computer_id, visit_date, duration, payment FROM visits WHERE id = ?";
    
    private static final String INSERT = 
        "INSERT INTO visits (visitor_id, computer_id, visit_date, duration, payment) VALUES (?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
        "UPDATE visits SET visitor_id = ?, computer_id = ?, visit_date = ?, duration = ?, payment = ? WHERE id = ?";
    
    private static final String DELETE = "DELETE FROM visits WHERE id = ?";

    @Override
    public Long insert(Visit visit) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            pst.setLong(1, visit.getVisitorId());
            pst.setLong(2, visit.getComputerId());
            pst.setDate(3, Date.valueOf(visit.getVisitDate()));
            pst.setInt(4, visit.getDuration());
            pst.setDouble(5, visit.getPayment());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Visit visit) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(1, visit.getVisitorId());
            pst.setLong(2, visit.getComputerId());
            pst.setDate(3, Date.valueOf(visit.getVisitDate()));
            pst.setInt(4, visit.getDuration());
            pst.setDouble(5, visit.getPayment());
            pst.setLong(6, visit.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Visit findById(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_BY_ID)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return fillFull(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Visit> findAll() throws DAOException {
        List<Visit> list = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fill(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    private Visit fill(ResultSet rs) throws SQLException, DAOException {
        Visit visit = new Visit();
        visit.setId(rs.getLong("id"));
        visit.setVisitorId(rs.getLong("visitor_id"));
        visit.setComputerId(rs.getLong("computer_id"));
        visit.setVisitDate(rs.getDate("visit_date").toLocalDate());
        visit.setDuration(rs.getInt("duration"));
        visit.setPayment(rs.getDouble("payment"));
        return visit;
    }

    private Visit fillFull(ResultSet rs) throws SQLException, DAOException {
        Visit visit = fill(rs);
        
        Visitor visitor = visitorDAO.findById(visit.getVisitorId());
        visit.setVisitor(visitor);
        
        Computer computer = computerDAO.findById(visit.getComputerId());
        visit.setComputer(computer);
        
        return visit;
    }
}