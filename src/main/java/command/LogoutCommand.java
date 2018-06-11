package command;

import controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    private static final String PATH_START_PAGE = "/jsp/start.jsp";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();

        request.getSession().invalidate();

        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(PATH_START_PAGE);
        return router;
    }
}
