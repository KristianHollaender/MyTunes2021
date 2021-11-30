package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongsDAO {

    private DatabaseConnector databaseConnector;

    public SongsDAO() throws IOException {
        databaseConnector = new DatabaseConnector(); //New Database object, used to creating the connection.
    }

    public List<Song> getSongs() {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sqlStatement = "SELECT * FROM Song";
            Statement statement = connection.createStatement();

            if (statement.execute(sqlStatement)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String category = resultSet.getString("category");
                    Time duration = resultSet.getTime("duration");

                    Song song = new Song(ID, title, artist, category, duration); // Creating a song object from the retrieved values
                    allSongs.add(song); // Adding the song to the ArrayList
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return allSongs;
    }

    public static void main(String[] args) throws Exception {

        SongsDAO songsDAO = new SongsDAO();

        List<Song> allSongs = songsDAO.getSongs();

        System.out.println(allSongs);
    }

}