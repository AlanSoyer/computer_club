package dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import domain.ComputerStatus;
import exception.DAOException;

public class ComputerStatusDbDAO implements RepositoryDAO<ComputerStatus> {
    
    private ConnectionBuilder builder = new DbConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private static final String SELECT_ALL = "SELECT id, status_name FROM computer_status ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, status_name FROM computer_status WHERE id = ?";
    private static final String INSERT = "INSERT INTO computer_status (status_name) VALUES (?)";
    private static final String UPDATE = "UPDATE computer_status SET status_name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM computer_status WHERE id = ?";

    @Override
    public Long insert(ComputerStatus status) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            pst.setString(1, status.getStatusName());
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
    public void update(ComputerStatus status) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setString(1, status.getStatusName());
            pst.setLong(2, status.getId());
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
    public ComputerStatus findById(Long id) throws DAOException {
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
    public List<ComputerStatus> findAll() throws DAOException {
        List<ComputerStatus> list = new LinkedList<>();
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

    private ComputerStatus fill(ResultSet rs) throws SQLException {
        ComputerStatus status = new ComputerStatus();
        status.setId(rs.getLong("id"));
        status.setStatusName(rs.getString("status_name"));
        return status;
    }
}