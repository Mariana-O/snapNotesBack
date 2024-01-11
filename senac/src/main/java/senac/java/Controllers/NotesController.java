package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.DAL.NotesDal;
import senac.java.Domain.Notes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NotesController {
    public static ResponseEndPoint res = new ResponseEndPoint();
    public static List<Notes> notesList = new ArrayList<>();

    public static class NotesHandler implements HttpHandler {
        static Notes note = new Notes();
        static String response = "";

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if ("GET".equals(exchange.getRequestMethod())) {
                    doGet(exchange);
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    doPost(exchange);
                } else if ("PUT".equals(exchange.getRequestMethod())) {
                    doPut(exchange);
                } else if ("DELETE".equals(exchange.getRequestMethod())) {
                    doDelete(exchange);
                } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                    doOptions(exchange);
                }
            } catch (Exception e) {
                e.toString();
                System.out.println("O erro foi " + e);
            }
        }

        public static void doGet(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                NotesDal notesDal = new NotesDal();

                List<Notes> getAllFromArray = Notes.getAllNotes(notesList);
                if (!getAllFromArray.isEmpty()) {
                    for (Notes notes : getAllFromArray) {
                        System.out.println("Content: " + notes.getContent());
                        System.out.println();
                        System.out.println("*-------------------------------------*");
                        System.out.println();

                        res.enviarResponseJson(exchange, notes.arrayToJson(getAllFromArray), 200);

                    }
                }
                try {
                    notesDal.listNotes();

                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                    String resposta = "Dados não encontrados";
                    res.enviarResponse(exchange, resposta, 200);
                }
            }
        }

        public static void doPost(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                NotesDal notesDal = new NotesDal();

                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                    int resp = 0;

                    Notes note = new Notes();
                    notesList.add(0, note);
                    resp = notesDal.insertNote(note.getContent());

                    res.enviarResponseJson(exchange, note.toJson(), 200);
                    if (resp == 0) {
                        response = "houve um problema ao criar a nota";
                    } else {
                        response = "nota criada com sucesso";
                        res.enviarResponse(exchange, String.valueOf(resp), 200);
                    }
                } catch (Exception e) {
                    String resposta = e.toString();
                    res.enviarResponse(exchange, resposta, 200);
                    System.out.println("O erro foi: " + e);
                }
            }
        }

        public static void doPut(HttpExchange exchange) throws IOException {
            if ("PUT".equals(exchange.getRequestMethod())) {
                NotesDal notesDal = new NotesDal();

                try {
                    notesDal.updateNote(note.id, note.content);

                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                }

                res.enviarResponse(exchange, response, 200);
            }
        }

        public static void doDelete(HttpExchange exchange) throws IOException {
            if ("DELETE".equals(exchange.getRequestMethod())) {
                NotesDal notesDal = new NotesDal();

                try {
                    notesDal.deleteNote(note.getId());
                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                }
                res.enviarResponse(exchange, response, 200);

            }
        }

        public static void doOptions(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                response = "Essa é a rota de notas - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }
        }
    }
}
