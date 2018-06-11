package command;

import controller.Router;
import entity.Flower;
import logic.FlowerLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowFlowerListCommand implements Command {

    private static final String PATH_FLOWERS_LIST_PAGE = "/jsp/flowersList.jsp";
    private static final String PARAMETER_FAMILY = "family";
    private static final String PARAMETER_FAMILY_ID = "familyId";

    private FlowerLogic logic;

    public ShowFlowerListCommand(FlowerLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String family = request.getParameter(PARAMETER_FAMILY);
        String familyId = request.getParameter(PARAMETER_FAMILY_ID);
        request.getSession().setAttribute("currentFamily", family);
        request.getSession().setAttribute("currentFamilyId", familyId);

        Router router = new Router();

        List<Flower> flowerList = logic.findFlowersInFamily(family);
        if(flowerList.isEmpty()) {
            request.setAttribute("emptyList", "Список цветов пуст");
        } else {
            request.setAttribute("flowerList", flowerList);
        }
        router.setPagePath(PATH_FLOWERS_LIST_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
