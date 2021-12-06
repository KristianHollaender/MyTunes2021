package bll;

import be.Playlist;
import be.Song;
import dal.PlaylistDAO;
import gui.controller.MyTunesHomeController;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {

    private PlaylistDAO playlistDAO = new PlaylistDAO();

    private MyTunesHomeController myTunesHomeController;

    public PlaylistManager() throws SQLException {
        return;
    }

    public List<Playlist> getPlaylist() throws SQLException {
       List<Playlist> allPlaylist = playlistDAO.getPlaylist();
       return allPlaylist;
    }

    public List<Playlist> loadPlaylist() throws SQLException {
    return null;
    }

    public void createPlaylist(String name) throws SQLException {
    return;
    }

    public void deletePlaylist(Playlist playlist) throws SQLException {
    return;
    }

    public List<Song> loadSongsFromPlaylist(int playlist_id) throws SQLException {
    return null;
    }

    public void AddSongToPlaylist(int playlist_id, int song_id) throws SQLException {
    return;
    }

    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {
    return;
    }

    public void updatePlaylist(Playlist playlist) throws SQLException {
    return;
    }

    //public double getTotalDurationOfPlaylist(Playlist playlist) throws SQLException {}

}
