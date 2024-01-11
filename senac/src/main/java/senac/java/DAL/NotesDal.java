package senac.java.DAL;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JSpinner.ListEditor;

import senac.java.Domain.Notes;

import java.util.List;

public class NotesDal {

    public Connection conectar() {
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi";

            String usuario = "116128412023.1";
            String senha = "senac@12841";

            conexao = DriverManager.getConnection(url, usuario, senha);

            if (conexao != null) {
                return conexao;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("O erro foi:" + e);

        } finally {

            try {

                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println("O erro no finally foi:" + e);

            }

        }
        return conexao;
    }

    // INSERIR - CREATE
    public int insertNote(String content) throws SQLException {
        String sql = "INSERT INTO Notes (content) VALUES(?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, content);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
            conexao.close();
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi:" + e);
            conexao.close();
        }
        conexao.close();
        return linhasAfetadas;
    }

    // READ - LISTAR
    public List listNotes() throws SQLException {
        String sql = "SELECT * FROM Notes";
        ResultSet result = null;

        List<Notes> notesArray = new ArrayList<>();

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();

            System.out.println("Listagem de notas: ");

            while (result.next()) {
                int id = result.getInt("id");
                String content = result.getString("content");

                System.out.println("id" + id);
                System.out.println("content" + content);
                System.out.println("  ");

                Notes currentNotes = new Notes(id, content);

                notesArray.add(currentNotes);
            }

            result.close();

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi:" + e);


        }
        return notesArray;
    }

    // UPDATE - Atualizar
    public int updateNote(int id, String content) throws SQLException {
        String sql = "UPDATE Notes SET content = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            statement.setString(1, content);
            statement.setInt(2, id);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");

            return linhasAfetadas;

        } catch (SQLException e) {

            System.out.println("O erro na atualização de dados foi:" + e);
        }
        return linhasAfetadas;
    }

    // DELETE - DELETAR
    public int deleteNote(int id) throws SQLException {
        String sql = "DELETE FROM Notes WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            statement.setInt(1, id);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na exclusão de dados foi:" + e);
        }
        return linhasAfetadas;
    }
}