package senac.java.Services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import senac.java.Controllers.NotesController;




import java.io.IOException;
import java.net.InetSocketAddress;

public class Server{

    public void lojaServer() throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(4000), 0);

        HttpHandler notesHandler = new NotesController.NotesHandler();
    

        server.createContext("/api/notes", exchange -> {
            configureCorsHeaders(exchange);
            notesHandler.handle(exchange);
        });


        server.setExecutor(null);
        System.out.println("Servidor Iniciado");
        server.start();

    }

    private void configureCorsHeaders(HttpExchange exchange){
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "GET, OPTIONS, POST, PUT, DELETE");
        headers.set("Access-Control-Allow-Headers", "Content-Type");
    }
}