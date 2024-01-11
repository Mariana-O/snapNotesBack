package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseEndPoint {
    public static void enviarResponseJson(HttpExchange exchange, JSONObject response, Integer statusCode)
            throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        byte[] responseBytes = response.toString().getBytes("UTF-8");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    public static void enviarResponse(HttpExchange exchange, String response, Integer statusCode)throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
