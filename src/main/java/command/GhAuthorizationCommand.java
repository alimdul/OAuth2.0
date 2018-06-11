package command;

import controller.Router;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GhAuthorizationCommand implements Command {

    private static final String CLIENT_ID = "e5f1b888c2ff44d8baa1";
    private static final String REDIRECT_URI = "http://localhost:8080/controller";
    private static final String CLIENT_SECRET = "440d1d3d30aff1f482be546063582643d14deb43";
    private static final String SCOPE = "read:user";

    private static final String PATH_AFTER_AUTHORIZATION_PAGE = "/jsp/afterAuthorization.jsp";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Router router = new Router();

        String userName;
        String accessToken;

        if(request.getParameter("code") == null) {
            request.getSession().setAttribute("gotGhCode", "true");
            authorize(response);
        } else {
            accessToken = getAccessToken(request.getParameter("code"));
            if(accessToken != null) {
                userName = getUserName(accessToken);
                request.getSession().setAttribute("userName", userName);
            }
        }

        router.setPagePath(PATH_AFTER_AUTHORIZATION_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }

    public void authorize(HttpServletResponse response) throws IOException {
        String url = String.format("https://github.com/login/oauth/authorize?" +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=" + SCOPE);

        response.sendRedirect(url);
    }

    public String getAccessToken(String code) throws IOException {
        String url = String.format("https://github.com/login/oauth/access_token?" +
                "client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&grant_type=authorization_code" +
                "&code=" + code);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String str;

        while ((str = reader.readLine()) != null) {
            builder.append(str);
        }
        reader.close();
        return builder.toString().substring(13, 53);
    }

    public String getUserName(String token) throws IOException {
        String url = String.format("https://api.github.com/user?access_token=" + token);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String str;

        while((str = reader.readLine()) != null){
            builder.append(str);
        }

        JSONTokener jsonTokener = new JSONTokener(builder.toString());
        JSONObject jsonObject = new JSONObject(jsonTokener);
        String login = jsonObject.getString("login");
        reader.close();
        return login;
    }
}
