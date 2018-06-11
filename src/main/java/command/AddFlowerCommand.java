package command;

import controller.Router;
import entity.Flower;
import entity.FlowersFamily;
import logic.FamilyLogic;
import logic.FlowerLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddFlowerCommand implements Command {

    private static final String PATH_FLOWER_LIST_PAGE = "/jsp/flowersList.jsp";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_STEM = "stem";
    private static final String PARAMETER_LEAF = "leaf";
    private static final String PARAMETER_FAMILY_ID = "currentFamilyId";
    private static final String PARAMETER_FAMILY = "currentFamily";

    private FlowerLogic logic;

    public AddFlowerCommand(FlowerLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(PARAMETER_NAME);
        String stem = request.getParameter(PARAMETER_STEM);
        String leaf = request.getParameter(PARAMETER_LEAF);
        String family = String.valueOf(request.getSession().getAttribute(PARAMETER_FAMILY));
        String familyId = String.valueOf(request.getSession().getAttribute(PARAMETER_FAMILY_ID));

        Router router = new Router();

        logic.add(name, stem, leaf, Integer.parseInt(familyId));

        List<Flower> flowerList = logic.findFlowersInFamily(family);
        if(flowerList.isEmpty()) {
            request.setAttribute("emptyList", "Список цветов пуст");
        } else {
            request.setAttribute("flowerList", flowerList);
        }
        router.setPagePath(PATH_FLOWER_LIST_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
