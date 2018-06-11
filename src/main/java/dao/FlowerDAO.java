package dao;

import connection.ConnectionPool;
import entity.Flower;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerDAO extends AbstractDAO<Flower> {

    private static final String SQL_SELECT_FLOWERS_IN_FAMILY=
            "SELECT id, name, stem, leaf FROM flower WHERE family = " +
                    "(SELECT id FROM flowersFamily WHERE name=?)";

    private static final String SQL_DELETE_FLOWERS_BY_FAMILY_ID =
            "DELETE FROM flower WHERE family = (SELECT id FROM flowersFamily WHERE id=?)";

    private static final String SQL_DELETE_FLOWER =
            "DELETE FROM flower WHERE id=?";

    private static final String SQL_ADD_FLOWER =
            "INSERT INTO flower (name, stem, leaf, family) VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE_FLOWER =
            "UPDATE flower SET name=?, stem=?, leaf=? WHERE id =?";

    public FlowerDAO() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public List<Flower> findFlowersInFamily(String family) throws SQLException {
        List<Flower> flowerList = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_FLOWERS_IN_FAMILY);
        try {
            statement.setString(1, family);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("id"));
                flower.setName(resultSet.getString("name"));
                flower.setStem(resultSet.getString("stem"));
                flower.setLeaf(resultSet.getString("leaf"));

                flowerList.add(flower);
            }
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
        return flowerList;
    }

    public void deleteByFamilyId(int familyId) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_FLOWERS_BY_FAMILY_ID);
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

    public void delete(int flowerId) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_FLOWER);
        try {
            statement.setInt(1, flowerId);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }

    public void add(String name, String stem, String leaf, int familyId) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_ADD_FLOWER);
        try {
            statement.setString(1, name);
            statement.setString(2, stem);
            statement.setString(3, leaf);
            statement.setInt(4, familyId);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }

    public void change(int flowerId, String name, String stem, String leaf) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_FLOWER);
        try {
            statement.setString(1, name);
            statement.setString(2, stem);
            statement.setString(3, leaf);
            statement.setInt(4, flowerId);
            statement.executeUpdate();
        }  finally {
            if (statement != null) {
                statement.close();
            }
            close();
        }
    }
}
