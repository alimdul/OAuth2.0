package logic;

import dao.FamilyDAO;
import dao.FlowerDAO;
import entity.Flower;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerLogic {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<Flower> findFlowersInFamily(String family) {
        List<Flower> flowerList = new ArrayList<>();
        FlowerDAO flowerDAO = new FlowerDAO();

        try {
            flowerList = flowerDAO.findFlowersInFamily(family);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
        return flowerList;
    }

    public void delete(int flowerId) {
        FlowerDAO flowerDAO = new FlowerDAO();

        try {
            flowerDAO.delete(flowerId);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }

    public void add(String name, String stem, String leaf, int familyId) {
        FlowerDAO flowerDAO = new FlowerDAO();

        try {
            flowerDAO.add(name, stem, leaf, familyId);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }

    public void change(int flowerId, String name, String stem, String leaf) {
        FlowerDAO flowerDAO = new FlowerDAO();

        try {
            flowerDAO.change(flowerId, name, stem, leaf);
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- SQLException");
        }
    }
}
