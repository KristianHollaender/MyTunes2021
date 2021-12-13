package gui.controller;

import be.Song;
import dal.SongsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSongsController extends MyTunesHomeController implements Initializable {

    public static final String RAP_PROOF = "Rap";
    public static final String POP_PROOF = "Pop";
    public static final String HIP_HOP_PROOF = "Hip hop";
    public static final String ROCK_PROOF = "Rock";
    public static final String REGGAE_PROOF = "Reggae";
    public static final String COUNTRY_PROOF = "Country";
    public static final String FUNK_PROOF = "Funk";
    public static final String JAZ_PROOF = "Jaz";
    public static final String ELECTRONIC_PROOF = "Electronic";
    public static final String DISCO_PROOF = "Disco";

    @FXML
    private TextField txtFieldTitle;
    @FXML
    private TextField txtFieldArtist;
    @FXML
    private TextField txtFieldTime;
    @FXML
    private TextField txtFieldFile;
    @FXML
    private Button btnSaveSong;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button btnCancel;
    @FXML
    private ChoiceBox<String> cbProof;

    private MyTunesHomeController myTunesHomeController = new MyTunesHomeController();
    private MediaPlayer mediaPlayer;


    /**
     * Constructor for CreateSongsController.
     */
    public CreateSongsController() throws Exception {
    }

    /**
     * Makes the comboBox hold the given genres.
     */
    private void prepareProofs() {
        cbProof.getItems().addAll(
                RAP_PROOF,
                POP_PROOF,
                HIP_HOP_PROOF,
                ROCK_PROOF,
                REGGAE_PROOF,
                COUNTRY_PROOF,
                FUNK_PROOF,
                JAZ_PROOF,
                ELECTRONIC_PROOF,
                DISCO_PROOF
        );
    }


    /**
     * Initialize the proofs.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareProofs();
    }

    /**
     * FileChooser for adding a Mp3 file
     * Add the new song to database.
     */
    public void chooseMP3Button() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music files", "*.mp3", "*.wav"));
        Media f = new Media(selectedFile.toURI().toString());
        if (selectedFile != null){
            Media media = new Media(new File(selectedFile.getAbsolutePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            txtFieldFile.appendText("data/" + selectedFile.getName());
            getDuration();
        }else {
            System.out.println("File is invalid");
        }
    }

    /**
     * Gets the duration of the chosen song.
     * Separate minutes and seconds with a .
     */
    public void getDuration(){
        mediaPlayer.setOnReady(() -> {
                txtFieldTime.setText(String.valueOf(mediaPlayer.getMedia().getDuration().toSeconds()));
                String averageSeconds = String.format("%1.0f", mediaPlayer.getMedia().getDuration().toSeconds());
                int minutes = Integer.parseInt(averageSeconds) / 60;
                int seconds = Integer.parseInt(averageSeconds) % 60;
                if (10 > seconds) {
                    txtFieldTime.setText(minutes + "0" + seconds);
                }else{
                    txtFieldTime.setText(minutes + "." + seconds);
                }
        });
    }


    /**
     * Button for saving the newly added song.
     */
    public void saveSongButton() throws Exception {
        String title = txtFieldTitle.getText();
        String artist = txtFieldArtist.getText();
        Double duration = Double.parseDouble(txtFieldTime.getText());
        String category = cbProof.getSelectionModel().getSelectedItem();
        String url = txtFieldFile.getText();

        Song song = getSongManager().createSong(title, artist, duration, category, url);
        myTunesHomeController.reloadSongTable();
        Stage stage = (Stage) btnSaveSong.getScene().getWindow();
        stage.close();
    }

    /**
     * Pressing the cancel button takes you back to the main window.
     */
    public void cancelNewSongButton() {
        Stage stage = (Stage) btnSaveSong.getScene().getWindow();
        stage.close();
    }

}
