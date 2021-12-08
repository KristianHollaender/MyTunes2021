package gui.controller;

import be.Song;
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

public class EditSongsController extends MyTunesHomeController implements Initializable {

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
    private Button btnAdd;
    @FXML
    private Button chooseFile;
    @FXML
    private Button btnUndo;
    @FXML
    private MenuButton chooseGenre;
    @FXML
    private ChoiceBox<String> cbProof;

    private Song songToAdd;
    private Song selectedSong;
    private MyTunesHomeController myTunesHomeController;


    public EditSongsController() throws Exception {
    }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareProofs();

    }

    /**
     * Add the new song to database.
     */
    public void chooseMP3(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music files", "*.mp3", "*.wav"));
        Media file = new Media(selectedFile.toURI().toString());
        if (selectedFile != null){
            txtFieldFile.appendText(selectedFile.getAbsolutePath());
            txtFieldTime.appendText(String.valueOf(file.getDuration()));
        }else{
            System.out.println("File is invalid");
        }

    }

    public void getDuration(File file) throws UnsupportedAudioFileException, IOException {

        // musicPlayer.getMedia().getDuration();
    }

    public void setSelectedSong(Song song) {
        if (song != null) {
            selectedSong = song;
            txtFieldTitle.setText(selectedSong.getTitle());
            txtFieldFile.setText(selectedSong.getUrl());
            txtFieldArtist.setText(selectedSong.getArtist());
            cbProof.getSelectionModel().select(selectedSong.getCategory());
        }
    }

    /**
     * Saves the changes made to the selected song.
     */
    public void saveSong() {
        try {
            if (selectedSong != null) {
                selectedSong.setTitle(txtFieldTitle.getText());
                selectedSong.setUrl(txtFieldFile.getText());
                selectedSong.setArtist(txtFieldArtist.getText());
                //selectedSong.setCategory(getCategoryIdFromName(selectedCategory));

                myTunesHomeController.getSongManager().editSong(selectedSong);
                myTunesHomeController.reloadSongTable();
                Stage stage = (Stage) btnAdd.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cancelSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    public void createOrEditSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }


}
