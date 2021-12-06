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
        return null;
    }

    public Playlist createPlaylist(String title) throws SQLServerException {
        return null;
    }

    public void editPlaylist(Playlist playlist){

    }

    public void deletePlaylist(Playlist playlist){

    }

    public static void main(String[] args) throws SQLException {
    }

}
