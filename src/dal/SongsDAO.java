package dal;

import be.Song;
<<<<<<< Updated upstream
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
=======
import dal.db.JDBCConnectionPool;
>>>>>>> Stashed changes

import java.io.IOException;
<<<<<<< Updated upstream
import java.sql.*;
import java.util.ArrayList;
=======
import java.nio.file.Path;
import java.sql.*;
>>>>>>> Stashed changes
import java.util.List;

public class SongsDAO {

<<<<<<< Updated upstream
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
=======
    private static final String SONGS_FILE = "data";
    private final JDBCConnectionPool connectionPool;
    private static final Path path = new File(SONGS_FILE).toPath();

    public SongsDAO() throws IOException {
        connectionPool = new JDBCConnectionPool();
>>>>>>> Stashed changes

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

    public Song createSong(String title, String artist, String category, Time time){
        return createSong(title, artist, category, time);
    }

<<<<<<< Updated upstream
    public void updateSong(Song song){

    }

    public void editSong(Song song){

    }

    /**
     * Used for testing purposes
     */
    public static void main(String[] args) throws Exception {

        SongsDAO songsDAO = new SongsDAO();

        List<Song> allSongs = songsDAO.getSongs();

        System.out.println(allSongs);
    }

}
=======
    public Song createSong(String title, String artist, float songLength, String category, String url) throws SQLException {
        String sql = "INSERT INTO SONG(Title, Artist, songLength, category, Url) values (?,?,?,?,?);";
        Connection connection = connectionPool.checkOut();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artist);
            preparedStatement.setFloat(3, songLength);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, url);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            Song song = new Song(id, title, artist, songLength, category, url);
            return song;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }
}
>>>>>>> Stashed changes
