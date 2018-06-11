package command;

import controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
