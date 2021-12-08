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

    public List<Playlist> getPlaylist(){
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

    public List<Song> getSongsOnPlaylist(int playlist_id) {
        ArrayList<Song> allPlaylist = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT Song.*," +
                    "FROM Playlist " +
                    "LEFT JOIN SongsOnPlaylist ON Playlist.Playlist_id = SongsOnPlaylist.Song_id" +
                    "ORDER BY Song.Title;";
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, playlist_id);
            st.executeUpdate();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                Double songLength = rs.getDouble("songLength");
                String category = rs.getString("Category");
                String url = rs.getString("url");

                if(url!=null){
                    allPlaylist.add(new Song(id, title, artist, songLength, category, ""));
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return allPlaylist;
    }

        public Playlist createPlaylist(String title) throws SQLServerException {
        String sql = "INSERT INTO Playlist (Title) VALUES (?);";
        Connection connection = databaseConnector.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            Playlist playlist = new Playlist(id, title);
            return playlist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Changes the name of the playlist if a match is found.
     *
     * @param   playlist a Playlist with the new name, and the original id.
     * @throws  SQLException if it cannot connect to the database or something went wrong.
     */
    public void editPlaylist(Playlist playlist) throws SQLException {
        String sql = "UPDATE playlist SET playlist_name=? WHERE playlist_id=?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, playlist.getTitle());
            preparedStatement.setInt(2, playlist.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deletePlaylist(int id){
        String sql = "DELETE FROM Playlist WHERE PlaylistID = ?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addSongToPlaylist(int playlist_id, int song_id) throws SQLException {
        String sql = "INSERT INTO SongsOnPlaylist (playlist_id, song_id) VALUES (?,?);";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlist_id);
            st.setInt(2, song_id);
            st.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {
        String sql = "DELETE FROM SongsOnPlaylist WHERE playlist_id=? AND song_id=?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlist_id);
            st.setInt(2, song_id);
            st.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        PlaylistDAO playlistDAO = new PlaylistDAO();
        List<Song> allSongs = playlistDAO.getSongsOnPlaylist(18);
        System.out.println(allSongs);
        //playlistDAO.addSongToPlaylist(18,23);
        //playlistDAO.deleteFromPlaylist(18,23);
        //playlistDAO.deletePlaylist(3);
        //System.out.println(allPlaylist);
    }

}
