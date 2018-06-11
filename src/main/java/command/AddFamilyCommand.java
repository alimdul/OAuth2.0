package command;

import controller.Router;
import entity.FlowersFamily;
import logic.FamilyLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddFamilyCommand implements Command {

    private static final String PATH_FAMILY_LIST_PAGE = "/jsp/familyList.jsp";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_FLOWERING_TIME = "floweringTime";

    private FamilyLogic logic;

    public AddFamilyCommand(FamilyLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(PARAMETER_NAME);
        String floweringTime = request.getParameter(PARAMETER_FLOWERING_TIME);

        Router router = new Router();

        logic.add(name, floweringTime);

        List<FlowersFamily> familyList = logic.findAll();
        if(familyList.isEmpty()) {
            request.setAttribute("emptyList", "Список семейств пуст");
        } else {
            request.setAttribute("familyList", familyList);
        }
        router.setPagePath(PATH_FAMILY_LIST_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
