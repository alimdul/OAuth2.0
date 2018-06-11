package controller;

public class Router {

    public enum RouteType {
        FORWARD, REDIRECT
    }

    private RouteType route = RouteType.FORWARD;
    private String pagePath;

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        if(route == null) {
            this.route = RouteType.FORWARD;
        } else {
            this.route = route;
        }
    }
}
