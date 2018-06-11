package command;

import controller.Router;
import entity.Flower;
import logic.FlowerLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteFlowerCommand implements Command {

    private static final String PATH_FLOWER_LIST_PAGE = "/jsp/flowersList.jsp";
    private static final String PARAMETER_FLOWER_ID = "flowerId";

    private FlowerLogic logic;

    public DeleteFlowerCommand(FlowerLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String flowerId = request.getParameter(PARAMETER_FLOWER_ID);

        Router router = new Router();

        logic.delete(Integer.parseInt(flowerId));

        String family = String.valueOf(request.getSession().getAttribute("currentFamily"));
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
