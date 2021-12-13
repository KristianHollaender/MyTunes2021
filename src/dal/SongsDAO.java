package dal;

import be.Song;
import dal.db.DatabaseConnector;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.nio.file.Path;
import java.util.List;


public class SongsDAO {

    private final DatabaseConnector databaseConnector;

    private static final String SONGS_FILE = "data";
    private static final Path path = new File(SONGS_FILE).toPath();
    String oldSearchQuery = "";


    /**
     * Making a reference to the databaseConnector, so we can connect to the SQL Database.
     */
    public SongsDAO() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * Method for getting the songs in the database
     * We define the labels we want to retrieve from the database
     * The ResultSet function is used to read the tables, where we define if we want a string or int
     */
    public List<Song> getSongs() {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sqlStatement = "SELECT * FROM Song";
            Statement statement = connection.createStatement();

            if (statement.execute(sqlStatement)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    double songLength = resultSet.getDouble("songLength");
                    String category = resultSet.getString("category");
                    String url = resultSet.getString("url");
                    int ID = resultSet.getInt("ID");

                    Song song = new Song(ID, title, artist, songLength, category, url); // Creating a song object from the retrieved values
                    allSongs.add(song); // Adding the song to an ArrayList
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return allSongs;
    }

    /**
     * Creating a song and inserting/storing the value in our SQL database where we have defined the columns.
     */
    public Song createSong(String title, String artist, double songLength, String category, String url) throws SQLException {
        String sql = "INSERT INTO SONG(Title, Artist, songLength, category, Url) values (?,?,?,?,?);";
        Connection connection = databaseConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artist);
            preparedStatement.setDouble(3, songLength);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, url);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            Song song = new Song(id, title, artist, songLength, category, url);
            return song;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * Method for deleting a song from the SQL database. The selected songs ID is used.
     */
    public void deleteSong(int id) throws SQLException {
        String sql = "DELETE FROM Song WHERE ID = ?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Tries to edit the given song and updating the parameters in the SQL table.
     */
    public void editSong(Song song) throws SQLException {
        var sql = "UPDATE song SET Title = ?, Artist = ?, songLength = ?, category = ?, Url=? WHERE Id = ?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, song.getTitle());
            st.setString(2, song.getArtist());
            st.setDouble(3, song.getSongLength());
            st.setString(4, song.getCategory());
            st.setString(5, song.getUrl());
            st.setInt(6, song.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Searches through the list of songs in lower case.
     * If the method is called again, it clears the search and shows the entire song table again.
     */
    public List<Song> searchSong(String searchQuery) {
        String SavedSearchedQuery = searchQuery;

        if(searchQuery.equals(oldSearchQuery) && searchQuery != ""){
            SavedSearchedQuery = "";
            oldSearchQuery = "";
        }else{
            oldSearchQuery = SavedSearchedQuery;
        }
        List<Song> resultSongs = new ArrayList<>();
        try (var connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM song WHERE LOWER(title) LIKE LOWER(?) OR Artist LIKE LOWER(?) OR LOWER(songLength) LIKE LOWER(?) OR LOWER(category) LIKE LOWER(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + SavedSearchedQuery + "%");
            preparedStatement.setString(2, "%" + SavedSearchedQuery + "%");
            preparedStatement.setString(3, "%" + SavedSearchedQuery + "%");
            preparedStatement.setString(4, "%" + SavedSearchedQuery + "%");
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    var id = resultSet.getInt("Id");
                    var title = resultSet.getString("Title");
                    var artist = resultSet.getString("Artist");
                    var songLength = resultSet.getDouble("songLength");
                    var category = resultSet.getString("category");
                    var song = new Song(id, title, artist, songLength, category, "not done yet");
                    resultSongs.add(song);
                }
                return resultSongs;
            } else {
                System.out.println(String.format("Couldn't find the song: %s", SavedSearchedQuery));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSongs;
    }

    /**
     * This method is only used for testing purposes.
     */
        public static void main (String[]args) throws Exception {
            //SongsDAO songsDAO = new SongsDAO();
            //List<Song> allSongs = (List<Song>) songsDAO.createSong("Test11", "John", 3.24, "Rap", "data/Come-Play-with-Me.mp3");
            //songsDAO.editSong();
            //List<Song> allSongs1 = songsDAO.editSong();
            //List<Song> allSongs = songsDAO.getSongs();
            //List<Song> allsongs1 = songsDAO.deleteSong(5);
            //List<Song> allSong1 = songsDAO.getSongs();
            //System.out.println(allSongs);
        }
}
