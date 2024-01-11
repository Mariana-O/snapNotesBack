package senac.java.Domain;

import org.json.JSONObject;

import java.util.List;

public class Notes {
    public int id = 0;
    public String content = "";

    public Notes() {
    }

    public Notes(int id, String content) {
        this.content = content;
    }

    public int getId(){
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("content", content);
        return json;
    }

    public JSONObject arrayToJson(List<Notes> arrayNotesList) {
        JSONObject json = new JSONObject();

        if (!arrayNotesList.isEmpty()) {
            int keyjson = 0;
            for (Notes note : arrayNotesList) {
                JSONObject jsonNote = new JSONObject();
                jsonNote.put("content", content = note.getContent());
                json.put(String.valueOf(keyjson), jsonNote);
                keyjson++;
            }
            return json;
        } else {
            return null;
        }
    }

    public static Notes getNote(int index, List<Notes> notesList) {
        if (index >= 0 && index < notesList.size()) {
            return notesList.get(index);
        } else {
            return null;
        }
    }

    public static List<Notes> getAllNotes(List<Notes> notesList) {
        return notesList;
    }
}