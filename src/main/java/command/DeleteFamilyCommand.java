package command;

import controller.Router;
import entity.FlowersFamily;
import logic.FamilyLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteFamilyCommand implements Command{

    private static final String PATH_FAMILY_LIST_PAGE = "/jsp/familyList.jsp";
    private static final String PARAMETER_FAMILY_ID = "familyId";

    private FamilyLogic logic;

    public DeleteFamilyCommand(FamilyLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String familyId = request.getParameter(PARAMETER_FAMILY_ID);

        Router router = new Router();

        logic.delete(Integer.parseInt(familyId));

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
