package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;

import java.sql.SQLException;
import java.util.List;

public class PlaylistModel {

    PlaylistManager playlistManager;

    /**
     * Constructor for PlaylistModel
     */
    public PlaylistModel() throws SQLException {
        playlistManager = new PlaylistManager();
    }


    /**
     * Edits a playlist by selecting a playlist using the method in PlaylistDAO.
     * @param playlist
     * @throws SQLException
     */
    public void editPlaylist(Playlist playlist) throws SQLException {
        playlistManager.editPlaylist(playlist);
    }


    /**
     * Gets the list of playlist using the getPlaylist method in PlaylistDAO.
     */
    public List<Playlist> getPlaylist() throws SQLException {
        List<Playlist> allPlaylist = playlistManager.getPlaylist();
        return allPlaylist;
    }

    /**
     * Gets the list of songs on the playlists by taking the playlist_id
     * Uses the method in PlaylistDAO.
     */
    public List<Song> getSongsOnPlaylist(int playlist_id) throws SQLException {
        return playlistManager.getSongsOnPlaylist(playlist_id);
    }

    /**
     * Creates a playlist with a given title using the method in PlaylistDAO.
     * @param title
     * @throws SQLException
     */
    public void createPlaylist(String title) throws SQLException {
        playlistManager.createPlaylist(title);
    }



    /**
     * Deletes a playlist by taking the ID using the method in PlaylistDAO.
     * @param id
     * @throws SQLException
     */
    public void deletePlaylist(int id) throws SQLException {
        playlistManager.deletePlaylist(id);
    }

    /**
     * Adds a song to the playlist by taking the playlist_id and song_id and using the method in PlaylistDAO.
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    public void addSongToPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistManager.addSongToPlaylist(playlist_id, song_id);
    }

    /**
     * Deletes a song from the playlist by taken playlist_id and song_id and using the method in PlaylistDAO.
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistManager.deleteFromPlaylist(playlist_id, song_id);
    }

}
