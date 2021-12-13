package gui.model;

import be.Playlist;
import bll.PlaylistManager;

import java.sql.SQLException;

public class PlaylistModel {

    PlaylistManager playlistManager;

    public PlaylistModel() throws SQLException {
        playlistManager = new PlaylistManager();
    }

    public void updatePlaylist(Playlist playlist) throws SQLException {
        playlistManager.editPlaylist(playlist);
    }
}
