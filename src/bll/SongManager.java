package bll;

import be.Song;
import dal.SongsDAO;

import java.io.IOException;
import java.util.List;

public class SongManager {

    private SongsDAO songsDAO = new SongsDAO();

    /**
     * Constructor
     * @throws IOException
     */
    public SongManager() throws IOException {
    }

    /**
     * Gets the list of songs
     * @return
     * @throws Exception
     */
    public List<Song> getSongs() {
        List<Song> allSongs = songsDAO.getSongs();
        return  allSongs;
    }

    /**
     * Creates a song with a given title, artist, sonLength, category and url
     * @param title
     * @param artist
     * @param songLength
     * @param category
     * @param url
     * @return
     * @throws Exception
     */
    public Song createSong(String title, String artist, double songLength, String category, String url) throws Exception {
        return songsDAO.createSong(title, artist, songLength, category, url);
    }


    /**
     * Deletes a song by taken the id
     * @param id
     * @throws Exception
     */
    public void deleteSong(int id) throws Exception {
        songsDAO.deleteSong(id);
    }

    /**
     * Edits a song by selecting a song
     * @param song
     * @throws Exception
     */
    public void editSong(Song song) throws Exception {
        songsDAO.editSong(song);
    }

    /**
     * Searching through a list of songs
     * @param query
     * @return
     * @throws Exception
     */
    public List<Song> searchSongs(String query) throws Exception {
        return songsDAO.searchSong(query);
    }

}
