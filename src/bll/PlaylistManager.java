package bll;

import be.Playlist;
import be.Song;
import dal.PlaylistDAO;
import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {

    private PlaylistDAO playlistDAO = new PlaylistDAO();

    /**
     * Constructor for PlaylistManager
     */
    public PlaylistManager() throws SQLException {
    }

    /**
     * Gets the list of playlist using the getPlaylist method in PlaylistDAO.
     */
    public List<Playlist> getPlaylist() throws SQLException {
       List<Playlist> allPlaylist = playlistDAO.getPlaylist();
       return allPlaylist;
    }

    /**
     * Gets the list of songs on the playlists by taking the playlist_id
     * Uses the method in PlaylistDAO.
     */
    public List<Song> getSongsOnPlaylist(int playlist_id) throws SQLException {
        return playlistDAO.getSongsOnPlaylist(playlist_id);
    }

    /**
     * Creates a playlist with a given title using the method in PlaylistDAO.
     * @param title
     * @throws SQLException
     */
    public void createPlaylist(String title) throws SQLException {
        playlistDAO.createPlaylist(title);
    }

    /**
     * Edits a playlist by selecting a playlist using the method in PlaylistDAO.
     * @param playlist
     * @throws SQLException
     */
    public void editPlaylist(Playlist playlist) throws SQLException {
        playlistDAO.editPlaylist(playlist);
    }

    /**
     * Deletes a playlist by taking the ID using the method in PlaylistDAO.
     * @param id
     * @throws SQLException
     */
    public void deletePlaylist(int id) throws SQLException {
        playlistDAO.deletePlaylist(id);
    }

    /**
     * Adds a song to the playlist by taking the playlist_id and song_id and using the method in PlaylistDAO.
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    public void addSongToPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistDAO.addSongToPlaylist(playlist_id, song_id);
    }

    /**
     * Deletes a song from the playlist by taken playlist_id and song_id and using the method in PlaylistDAO.
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistDAO.deleteFromPlaylist(playlist_id, song_id);
    }

}

