package command;

import controller.Router;
import entity.Flower;
import entity.FlowersFamily;
import logic.FlowerLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeFlowerCommand implements Command {

    private static final String PATH_FAMILY_LIST_PAGE = "/jsp/flowersList.jsp";
    private static final String PARAMETER_FLOWER_ID = "flowerIdForChange";
    private static final String PARAMETER_NEW_NAME = "name";
    private static final String PARAMETER_NEW_STEM = "stem";
    private static final String PARAMETER_NEW_LEAF = "leaf";

    private FlowerLogic logic;

    public ChangeFlowerCommand(FlowerLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String flowerId = String.valueOf(request.getSession().getAttribute(PARAMETER_FLOWER_ID));
        String name = request.getParameter(PARAMETER_NEW_NAME);
        String stem = request.getParameter(PARAMETER_NEW_STEM);
        String leaf = request.getParameter(PARAMETER_NEW_LEAF);

        Router router = new Router();

        logic.change(Integer.parseInt(flowerId), name, stem, leaf);

        String family = String.valueOf(request.getSession().getAttribute("currentFamily"));
        List<Flower> flowerList = logic.findFlowersInFamily(family);
        if(flowerList.isEmpty()) {
            request.setAttribute("emptyList", "Список цыетов пуст");
        } else {
            request.setAttribute("flowerList", flowerList);
        }
        router.setPagePath(PATH_FAMILY_LIST_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
