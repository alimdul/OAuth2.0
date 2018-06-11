package dao;

import connection.ConnectionPool;
import entity.FlowersFamily;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyDAO extends AbstractDAO<FlowersFamily> {

    private static final String SQL_SELECT_ALL_FAMILIES =
            "SELECT id, name, floweringTime FROM flowersFamily";

    private static final String SQL_DELETE_FAMILY =
            "DELETE FROM flowersFamily WHERE id=?";

    private static final String SQL_ADD_FAMILY =
            "INSERT INTO flowersFamily (name, floweringTime) VALUES (?, ?)";

    private static final String SQL_UPDATE_FAMILY =
            "UPDATE flowersFamily SET name=?, floweringTime=? WHERE id =?";

    public FamilyDAO() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public List<FlowersFamily> findAll() throws SQLException {
        List<FlowersFamily> familyList = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_ALL_FAMILIES);
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                FlowersFamily family = new FlowersFamily();
                family.setId(resultSet.getInt("id"));
                family.setName(resultSet.getString("name"));
                family.setFloweringTime(resultSet.getString("floweringTime"));

                familyList.add(family);
            }
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
        return familyList;
    }

    public void delete(int familyId) throws SQLException {
        FlowerDAO flowerDAO = new FlowerDAO();
        flowerDAO.deleteByFamilyId(familyId);

        PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_FAMILY);
        try {
            statement.setInt(1, familyId);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }

    public void add(String name, String floweringTime) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_ADD_FAMILY);
        try {
            statement.setString(1, name);
            statement.setString(2, floweringTime);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }

    public void change(int familyId, String name, String floweringTime) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_FAMILY);
        try {
            statement.setString(1, name);
            statement.setString(2, floweringTime);
            statement.setInt(3, familyId);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }
}
