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
    }

    public List<Playlist> getPlaylist() throws SQLException {
       List<Playlist> allPlaylist = playlistDAO.getPlaylist();
       return allPlaylist;
    }

    public void createPlaylist(String title) throws SQLException {
        playlistDAO.createPlaylist(title);
    }

    public void deletePlaylist(int id) throws SQLException {
        playlistDAO.deletePlaylist(id);
    }

    public List<Song> loadSongsFromPlaylist(int playlist_id) throws SQLException {
    return null;
    }

    public void AddSongToPlaylist(int playlist_id, int song_id) throws SQLException {
    return;
    }

    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {

    }

    public void editPlaylist(Playlist playlist) throws SQLException {
        playlistDAO.editPlaylist(playlist);
    }

    public static void main(String[] args) throws SQLException {
        PlaylistManager playlistManager = new PlaylistManager();

        List<Playlist> allPlaylist = playlistManager.getPlaylist();

        System.out.println(allPlaylist);
    }

}
