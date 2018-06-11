package logic;

import dao.FamilyDAO;
import entity.FlowersFamily;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyLogic {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<FlowersFamily> findAll() {
        List<FlowersFamily> familyList = new ArrayList<>();
        FamilyDAO familyDAO = new FamilyDAO();

        try {
            familyList = familyDAO.findAll();
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
        return familyList;
    }

    public void delete(int familyId) {
        FamilyDAO familyDAO = new FamilyDAO();

        try {
            familyDAO.delete(familyId);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }

    public void add(String name, String floweringTime) {
        FamilyDAO familyDAO = new FamilyDAO();

        try {
            familyDAO.add(name, floweringTime);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }

    public void change(int familyId, String name, String floweringTime) {
        FamilyDAO familyDAO = new FamilyDAO();

        try {
            familyDAO.change(familyId, name, floweringTime);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }
}
