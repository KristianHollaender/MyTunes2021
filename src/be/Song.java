package be;

import java.sql.Time;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private Time time;

    public Song(int id, String title, String artist, String category, Time time){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", category='" + category + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object s) {
        if(s instanceof Song){
            //s is a song
            //Typecast is created here
            Song other = (Song) s;
            return this.id == other.getId();
        }
        else{
            //s is not a song
            return super.equals(s);
        }
    }
}
