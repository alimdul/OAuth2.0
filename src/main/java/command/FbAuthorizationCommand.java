package command;

import controller.Router;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FbAuthorizationCommand implements Command {

    private static final String CLIENT_ID = "244295399649408";
    //private static final String REDIRECT_URI = "http://localhost:8080/controller";
    private static final String REDIRECT_URI = "https://7eef33a5.ngrok.io/controller";
    private static final String CLIENT_SECRET = "acfb496c3a5cf1793f1799552cfd92cd";

    private static final String PATH_AFTER_AUTHORIZATION_PAGE = "/jsp/afterAuthorization.jsp";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Router router = new Router();

        String userName;
        String accessToken;

        if(request.getParameter("code") == null || "".equals(request.getParameter("code"))) {
            System.out.println("1 " + request.getParameter("code"));
            request.getSession().setAttribute("gotFbCode", "true");
            //request.getSession().setAttribute("endProcess", "true");
            authorize(response);
            System.out.println("2 " + request.getParameter("code"));
        } else {
            System.out.println("3 " + request.getParameter("code"));
            accessToken = getAccessToken(request.getParameter("code"));
            if(accessToken != null) {
                System.out.println("4 " + request.getParameter("code"));
                userName = getUserName(accessToken);
                request.getSession().setAttribute("userName", userName);
            }
        }

        router.setPagePath(PATH_AFTER_AUTHORIZATION_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }

    public void authorize(HttpServletResponse response) throws IOException {
        String url= String.format("https://www.facebook.com/dialog/oauth" +
                "?client_id="+ CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI);

        response.sendRedirect(url);
    }

    public String getAccessToken(String code) throws IOException {
        String fbGraphUrl = "https://graph.facebook.com/oauth/access_token" +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&client_secret=" + CLIENT_SECRET +
                "&code=" + code;

        URL fbGraphURL = new URL(fbGraphUrl);

        URLConnection fbConnection = fbGraphURL.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine + "\n");
        }
        reader.close();

        String accessToken = buffer.toString();

        return accessToken;
    }

    public String getUserName(String token) throws IOException {
        String url = "https://graph.facebook.com/me?" + token;
        URL obj = new URL(url);
        URLConnection connection = obj.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String inputLine;
        String graph = null;
        while ((inputLine = reader.readLine()) != null) {
            //buffer.append(inputLine + "\n");
            graph += inputLine;
        }
        reader.close();
        //String graph = buffer.toString();

        JSONObject json = new JSONObject(graph);
        String userName = json.getString("last_name") + " " + json.getString("first_name");

        return userName;
    }
}
