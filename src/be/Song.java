package be;

import java.sql.Time;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private double songLength;
    private String url;

    public Song(int id, String title, String artist, double songLength, String category, String url){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.songLength = songLength;
        this.url = url;
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

    public double getsonglength() {
        return songLength;
    }

    public void setsonglength(double songlength) {
        this.songLength = songlength;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", category='" + category + '\'' +
                ", songlength=" + songLength +
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
