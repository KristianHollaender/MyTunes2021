package dal;

import be.Playlist;
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

    public List<Playlist> getPlaylist() throws SQLException{
        ArrayList<Playlist> allPlaylist = new ArrayList<>();

        try(Connection connection = databaseConnector.getConnection()){
            String sqlStatement = "SELECT * FROM Playlist";
            Statement statement = connection.createStatement();

            if(statement.execute(sqlStatement)){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("PlaylistID");
                    String title = resultSet.getString("Title");

                    Playlist playlist = new Playlist(id, title);
                    allPlaylist.add(playlist);
                }
            }
        }catch (SQLException ex){
            System.out.println(ex);
            return null;
        }
        return allPlaylist;
    }

    public Playlist createPlaylist(String title) throws SQLServerException {
        String sql = "INSERT INTO Playlist(Title) values (?,);";
        Connection connection = databaseConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            Playlist playlist = new Playlist(id, title);
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
        //List<Playlist> allPlaylist = playlistDAO.getPlaylist();
        List<Playlist> allPlaylist = (List<Playlist>) playlistDAO.createPlaylist("Nicklas er lidt gay");
        System.out.println(allPlaylist);
    }

}
