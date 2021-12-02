package bll.util;

import be.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongSearcher {

    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song : searchBase) {
            if(compareToSongId(query, song) || compareToSongTitle(query, song) || compareToSongArtist(query, song)
                    || compareToSongCategory(query, song) || compareToSongLength(query, song) || compareToSongUrl(query, song))
            {
                searchResult.add(song);
            }
        }

        return searchResult;
    }
    private boolean compareToSongId(String query, Song song){
        return Integer.toString(song.getId()).contains(query);
    }

    private boolean compareToSongTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToSongArtist(String query, Song song) {
        return song.getArtist().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToSongCategory(String query, Song song) {
        return song.getCategory().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToSongLength(String query, Song song){
        return Double.toString(song.getsonglength()).contains(query);
    }

    private boolean compareToSongUrl(String query, Song song) {
        return song.getUrl().toLowerCase().contains(query.toLowerCase());
    }
}
