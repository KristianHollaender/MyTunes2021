package dal;

import be.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SongsDAO {

    private static final String SONGS_FILE = "data";
    private static final Path path = new File(SONGS_FILE).toPath();

    public SongsDAO() {

    }

    public List<Song> getAllMovies() throws IOException {
        return null;
    }
}
