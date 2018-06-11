package command;

import controller.Router;
import entity.FlowersFamily;
import logic.FamilyLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeFamilyCommand implements Command {

    private static final String PATH_FAMILY_LIST_PAGE = "/jsp/familyList.jsp";
    private static final String PARAMETER_FAMILY_ID = "familyIdForChange";
    private static final String PARAMETER_NEW_NAME = "name";
    private static final String PARAMETER_NEW_FLOWERING_TIME = "floweringTime";

    private FamilyLogic logic;

    public ChangeFamilyCommand(FamilyLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String familyId = String.valueOf(request.getSession().getAttribute(PARAMETER_FAMILY_ID));
        String name = request.getParameter(PARAMETER_NEW_NAME);
        String floweringTime = request.getParameter(PARAMETER_NEW_FLOWERING_TIME);

        Router router = new Router();

        logic.change(Integer.parseInt(familyId), name, floweringTime);

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
