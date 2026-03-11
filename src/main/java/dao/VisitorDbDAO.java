package dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import domain.Visitor;
import exception.DAOException;

public class VisitorDbDAO implements RepositoryDAO<Visitor> {
    
    private ConnectionBuilder builder = new DbConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private static final String SELECT_ALL = "SELECT id, first_name, last_name, patronymic, identity_document, address, phone FROM visitors ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, first_name, last_name, patronymic, identity_document, address, phone FROM visitors WHERE id = ?";
    private static final String INSERT = "INSERT INTO visitors (first_name, last_name, patronymic, identity_document, address, phone) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE visitors SET first_name = ?, last_name = ?, patronymic = ?, identity_document = ?, address = ?, phone = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM visitors WHERE id = ?";

    @Override
    public Long insert(Visitor visitor) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            pst.setString(1, visitor.getFirstName());
            pst.setString(2, visitor.getLastName());
            pst.setString(3, visitor.getPatronymic());
            pst.setString(4, visitor.getIdentityDocument());
            pst.setString(5, visitor.getAddress());
            pst.setString(6, visitor.getPhone());
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
    public void update(Visitor visitor) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setString(1, visitor.getFirstName());
            pst.setString(2, visitor.getLastName());
            pst.setString(3, visitor.getPatronymic());
            pst.setString(4, visitor.getIdentityDocument());
            pst.setString(5, visitor.getAddress());
            pst.setString(6, visitor.getPhone());
            pst.setLong(7, visitor.getId());
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
    public Visitor findById(Long id) throws DAOException {
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
    public List<Visitor> findAll() throws DAOException {
        List<Visitor> list = new LinkedList<>();
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

    private Visitor fill(ResultSet rs) throws SQLException {
        Visitor visitor = new Visitor();
        visitor.setId(rs.getLong("id"));
        visitor.setFirstName(rs.getString("first_name"));
        visitor.setLastName(rs.getString("last_name"));
        visitor.setPatronymic(rs.getString("patronymic"));
        visitor.setIdentityDocument(rs.getString("identity_document"));
        visitor.setAddress(rs.getString("address"));
        visitor.setPhone(rs.getString("phone"));
        return visitor;
    }
}