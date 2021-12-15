package be;


public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private double songLength;
    private String url;


    /**
     * Constructor with id, title, artist, songLength, category and url
     * @param id
     * @param title
     * @param artist
     * @param songLength
     * @param category
     * @param url
     */
    public Song(int id, String title, String artist, double songLength, String category, String url){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.songLength = songLength;
        this.category = category;
        this.url = url;
    }

    /**
     * Gets value of id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set value of id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets value of title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets value of title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets value of artist
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets value of artist
     * @param artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets value of category
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets value of category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets value of songLength
     * @return
     */
    public double getSongLength() {
        return songLength;
    }

    /**
     * Sets value of songLength
     * @param songLength
     */
    public void setSongLength(double songLength) {
        this.songLength = songLength;
    }

    /**
     * Gets value of url
     * @return
     */
    public String getUrl(){
        return url;
    }

    /**
     * Sets value of url
     * @param url
     */
    public void setUrl(String url){
        this.url = url;
    }
}
