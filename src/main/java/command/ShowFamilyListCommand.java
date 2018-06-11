package command;

import controller.Router;
import entity.FlowersFamily;
import logic.FamilyLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowFamilyListCommand implements Command {

    private static final String PATH_FAMILY_LIST_PAGE = "/jsp/familyList.jsp";

    private FamilyLogic logic;

    public ShowFamilyListCommand(FamilyLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();

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
