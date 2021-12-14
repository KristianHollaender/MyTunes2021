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

    /**
     * Making a reference to the databaseConnector, so we can connect to the SQL Database.
     */
    public PlaylistDAO() throws SQLException {
        databaseConnector = new DatabaseConnector();
    }


    /**
     * Making a Playlist-list, connecting to the database and adding the results to our ArrayList.
     * We're using a for-loop for counting the playlist size and then getting the total duration of the songs inside the playlist.
     */
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
                for (int i = 0; i < allPlaylist.size(); i++) {
                    var playlist = allPlaylist.get(i);
                    if (playlist != null) {
                        var totalLength = getTotalDuration(playlist.getId());
                        playlist.setTime(totalLength);
                        var totalSongs = getSongsOnPlaylist(playlist.getId());
                        playlist.setSongs(totalSongs.size());
                    }
                }
                return allPlaylist;
            }
            
        }catch (SQLException ex){
            System.out.println(ex);
            return null;
        }
        return allPlaylist;
    }

    /**
     * Making a Song ArrayList, connecting to the database and adding the song to our ArrayList.
     */
    public List<Song> getSongsOnPlaylist(int playlist_id) {
        ArrayList<Song> allPlaylist = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Song INNER JOIN SongsOnPlaylist ON SongsOnPlaylist.song_id = song.id WHERE SongsOnPlaylist.playlist_id = ?;";
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, playlist_id);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()) {
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                Double songLength = rs.getDouble("songLength");
                String category = rs.getString("Category");
                String url = rs.getString("url");
                if (url != null)
                    allPlaylist.add(new Song(id, title, artist, songLength, category, url));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return allPlaylist;
    }

    /**
     * Creating a playlist and inserting/storing the value in our SQL database.
     */
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
        String sql = "UPDATE playlist SET Title=? WHERE PlaylistID=?;";
        try (var con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, playlist.getTitle());
            preparedStatement.setInt(2, playlist.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes the selected playlist based on the PlaylistID.
     */
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

    /**
     * Adds the selected song to the SongsOnPlaylist table,
     * which holds the values for both the Playlist ID and the Song ID.
     */
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

    /**
     * Deletes the selected song from the SongsOnPlaylist table,
     * which holds the values for both the Playlist ID and the Song ID.
     */
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

    /**
     * Takes the value of the songLength column and adds it to the totalDuration variable.
     * Adds them up for the totalDuration.
     */
    public double getTotalDuration(int playlist_id) throws SQLException {
        String sql = "SELECT * FROM Song FULL OUTER JOIN SongsOnPlaylist ON SongsOnPlaylist.song_id = song.id WHERE SongsOnPlaylist.playlist_id = ?;";
        double totalDuration = 0;
        try (var con = databaseConnector.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlist_id);
            st.execute();

            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                double song_length = rs.getDouble("songLength");
                totalDuration += song_length;
            }

            return totalDuration;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }
    
    /**
     * This method is using only for testing purposes.
     */
    public static void main(String[] args) throws SQLException {
        //PlaylistDAO playlistDAO = new PlaylistDAO();
        //List<Song> allSongs = playlistDAO.getSongsOnPlaylist(45);
        //playlistDAO.getTotalDuration(53);
        //System.out.println();
        //playlistDAO.addSongToPlaylist(45,39);
        //playlistDAO.deleteFromPlaylist(18,23);
        //playlistDAO.deletePlaylist(3);
        //System.out.println(allPlaylist);
    }

}
