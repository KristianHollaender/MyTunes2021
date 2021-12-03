package gui.model;

import be.Song;
import bll.SongManager;
import dal.SongsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {

    private ObservableList<Song> allSongs;
    private SongsDAO songsDAO;
    private SongManager songManager;

    public SongModel() throws Exception {
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(songsDAO.getSongs());
    }

    public void searchSong(String query) throws Exception {
        List<Song> searchResults = songManager.searchSongs(query);
        allSongs.clear();
        allSongs.addAll(searchResults);
    }
}
