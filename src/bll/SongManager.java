package bll;

import be.Song;
import dal.SongsDAO;

import java.util.List;

public class SongManager {

    private SongsDAO songsDAO;

    public List<Song> getSongs() throws Exception {
        List<Song> allSongs = songsDAO.getSongs();
        return  allSongs;
    }
}
