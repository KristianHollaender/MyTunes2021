package dal;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private final DatabaseConnector databaseConnector;

    public PlaylistDAO() throws SQLException {
        databaseConnector = new DatabaseConnector();
    }

    public Playlist getPlaylist(String name) throws SQLException{
        var sql = "SELECT FROM playlist WHERE playlist_name = ?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("PlaylistID");
            var title = resultSet.getString("title");
            var numbersOfSongs = resultSet.getInt("NumbersOfSongs");
            var totalPlaytime = resultSet.getDouble("totalPlaytime");
            var playlist = new Playlist(id, title, numbersOfSongs, totalPlaytime);
            return playlist;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Playlist createPlaylist(String title, int numberOfSongs, double totalPlaytime) throws SQLServerException {
        var sql = "INSERT INTO Playlist(Title, NumberOfSongs, totalPlaytime) values (?, ?, ?);";
        Connection connection = databaseConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, numberOfSongs);
            preparedStatement.setDouble(3, totalPlaytime);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            Playlist playlist = new Playlist(id, title, numberOfSongs,totalPlaytime);
            return playlist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;


    }

    public void editPlaylist(Playlist playlist){

    }

    public void deletePlaylist(Playlist playlist){

    }

    public static void main(String[] args) throws SQLException {
        PlaylistDAO playlistDAO = new PlaylistDAO();

        playlistDAO.createPlaylist("Nicklas er en donkey", 1, 50.02);


    }

}
