package gui.model;

import be.Song;
import bll.SongManager;
import dal.SongsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {

    private ObservableList<Song> allSongs;
    private SongsDAO songsDAO = new SongsDAO();
    private SongManager songManager;

    public SongModel() throws Exception {
        songManager = new SongManager();
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(songManager.getSongs());


    }

    public void searchSong(String query) throws Exception {
        List<Song> searchResults = songManager.searchSongs(query);
        allSongs.clear();
        allSongs.addAll(searchResults);
    }

    public ObservableList<Song> getObservableSong() {
        return allSongs;
    }
}
