package command;

import controller.Router;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class YdAuthorizationCommand implements Command {

    private static final String CLIENT_ID = "2d78cd627773488c8bd12d3438f98f6f";
    private static final String REDIRECT_URI = "http://localhost:8080/controller";
    private static final String CLIENT_SECRET = "7772c0ceafa24c529b8552d266678662";

    private static final String PATH_AFTER_AUTHORIZATION_PAGE = "/jsp/afterAuthorization.jsp";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Router router = new Router();

        String userName;
        String accessToken;

        if(request.getParameter("code") == null) {
            request.getSession().setAttribute("gotYdCode", "true");
            authorize(response);
        } else {
            System.out.println(request.getParameter("code"));
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
        String url= String.format("https://oauth.yandex.ru/authorize" +
                "?response_type=code" +
                "&client_id="+ CLIENT_ID);

        response.sendRedirect(url);
    }

    public String getAccessToken(String code) throws IOException {
        String url= String.format("https://oauth.yandex.ru/token");
        //https://oauth.yandex.ru/token?grant_type=authorization_code&code=&client_id=2d78cd627773488c8bd12d3438f98f6f&client_secret=7772c0ceafa24c529b8552d266678662

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String body = "grant_type=authorization_code" +
                "&code=" + code +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET;

        OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
        wr.write(body);
        wr.close();

//        OutputStream os = connection.getOutputStream();
//        os.write(body.getBytes("UTF-8"));
//        os.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

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
        String url= "https://login.yandex.ru/info" +
                "?format=json" +
                "&oauth_token=" + token;

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
        JSONObject response = new JSONObject(jsonTokener);
        String userName = response.getString("real_name");
        reader.close();

        return userName;
    }
}
