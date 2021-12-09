package bll;

import be.Playlist;
import be.Song;
import dal.PlaylistDAO;
import gui.controller.MyTunesHomeController;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {

    private PlaylistDAO playlistDAO = new PlaylistDAO();


    public PlaylistManager() throws SQLException {
    }

    public List<Playlist> getPlaylist() throws SQLException {
       List<Playlist> allPlaylist = playlistDAO.getPlaylist();
       return allPlaylist;
    }

    public List<Song> getSongsOnPlaylist(int playlist_id) throws SQLException {
        return playlistDAO.getSongsOnPlaylist(playlist_id);
    }

    public void createPlaylist(String title) throws SQLException {
        playlistDAO.createPlaylist(title);
    }

    public void editPlaylist(Playlist playlist) throws SQLException {
        playlistDAO.editPlaylist(playlist);
    }

    public void deletePlaylist(int id) throws SQLException {
        playlistDAO.deletePlaylist(id);
    }

    public void addSongToPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistDAO.addSongToPlaylist(playlist_id, song_id);
    }

    public void deleteFromPlaylist(int playlist_id, int song_id) throws SQLException {
        playlistDAO.deleteFromPlaylist(playlist_id, song_id);
    }

    public static void main(String[] args) throws SQLException {
        PlaylistManager playlistManager = new PlaylistManager();

        List<Song> allPlaylist = playlistManager.getSongsOnPlaylist(18);

        System.out.println(allPlaylist);
    }

}
