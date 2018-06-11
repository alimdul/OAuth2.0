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

public class VkAuthorizationCommand implements Command {

    private static final String CLIENT_ID = "6487374";
    private static final String REDIRECT_URI = "http://localhost:8080/controller";
    private static final String CLIENT_SECRET = "rFxg3ukgIeAYZtbIG50b";

    private static final String PATH_AFTER_AUTHORIZATION_PAGE = "/jsp/afterAuthorization.jsp";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Router router = new Router();

        String userName;
        String accessToken;

        if(request.getParameter("code") == null) {
            request.getSession().setAttribute("gotVkCode", "true");
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
        String url= String.format("https://oauth.vk.com/authorize" +
                "?client_id="+ CLIENT_ID +
                "&display=page" +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=users" +
                "&response_type=code" +
                "&v=5.74");

        response.sendRedirect(url);
    }

    public String getAccessToken(String code) throws IOException {
        String url= String.format("https://oauth.vk.com/access_token" +
                "?client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&redirect_uri=" + REDIRECT_URI +
                "&code=" + code);

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String string;

        while((string = reader.readLine()) != null){
            builder.append(string);
        }

        JSONObject response = new JSONObject(builder.toString());
        String accessToken = response.getString("access_token");
        reader.close();

        return accessToken;
    }

    public String getUserName(String token) throws IOException {
        String url= "https://api.vk.com/method/users.get?access_token="+ token +"&v=5.74";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String string;

        while((string = reader.readLine())!=null){
            builder.append(string);
        }

        JSONTokener jsonTokener = new JSONTokener(builder.toString());
        JSONObject response = new JSONObject(jsonTokener).getJSONArray("response").getJSONObject(0);
        String userName = response.getString("last_name") + " " + response.getString("first_name");
        reader.close();

        return userName;
    }
}
