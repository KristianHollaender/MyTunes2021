package gui.model;

import be.Song;
import bll.SongManager;

import java.io.IOException;

public class SongModel {

    SongManager songManager;

    public SongModel() throws IOException {
        songManager = new SongManager();
    }

    public void updateSong(Song song) throws Exception {
        songManager.editSong(song);
    }
}
