package dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import domain.Computer;
import domain.ComputerStatus;
import exception.DAOException;

public class ComputerDbDAO implements RepositoryDAO<Computer> {
    
    private ConnectionBuilder builder = new DbConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private static final String SELECT_ALL = 
        "SELECT c.id, c.computer_name, c.description, c.status_id, cs.status_name " +
        "FROM computers c LEFT JOIN computer_status cs ON c.status_id = cs.id ORDER BY c.id";
    
    private static final String SELECT_BY_ID = 
        "SELECT c.id, c.computer_name, c.description, c.status_id, cs.status_name " +
        "FROM computers c LEFT JOIN computer_status cs ON c.status_id = cs.id WHERE c.id = ?";
    
    private static final String INSERT = 
        "INSERT INTO computers (computer_name, description, status_id) VALUES (?, ?, ?)";
    
    private static final String UPDATE = 
        "UPDATE computers SET computer_name = ?, description = ?, status_id = ? WHERE id = ?";
    
    private static final String DELETE = "DELETE FROM computers WHERE id = ?";

    @Override
    public Long insert(Computer computer) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            pst.setString(1, computer.getComputerName());
            pst.setString(2, computer.getDescription());
            pst.setLong(3, computer.getStatusId());
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
    public void update(Computer computer) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setString(1, computer.getComputerName());
            pst.setString(2, computer.getDescription());
            pst.setLong(3, computer.getStatusId());
            pst.setLong(4, computer.getId());
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
    public Computer findById(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_BY_ID)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return fill(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> findAll() throws DAOException {
        List<Computer> list = new LinkedList<>();
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

    private Computer fill(ResultSet rs) throws SQLException {
        Computer computer = new Computer();
        computer.setId(rs.getLong("id"));
        computer.setComputerName(rs.getString("computer_name"));
        computer.setDescription(rs.getString("description"));
        
        Long statusId = rs.getLong("status_id");
        if (!rs.wasNull()) {
            computer.setStatusId(statusId);
            
            ComputerStatus status = new ComputerStatus();
            status.setId(statusId);
            status.setStatusName(rs.getString("status_name"));
            computer.setStatus(status);
        }
        
        return computer;
    }
}