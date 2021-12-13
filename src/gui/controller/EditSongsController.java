package gui.controller;

import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;

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
    private Button btnEditSong;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button btnCancel;
    @FXML
    private ChoiceBox<String> cbProof;

    private Song selectedSong;
    private MyTunesHomeController myTunesHomeController;
    private MediaPlayer mediaPlayer;


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
     * Saves the newly added song.
     */
    public void saveSongButton() throws Exception {
        Stage stage = new Stage();
        try {
            if (selectedSong != null) {
                selectedSong.setTitle(txtFieldTitle.getText());
                selectedSong.setArtist(txtFieldArtist.getText());
                selectedSong.setCategory(cbProof.getSelectionModel().getSelectedItem());
                selectedSong.setSongLength(selectedSong.getSongLength());
                selectedSong.setUrl(selectedSong.getUrl());

                myTunesHomeController.getSongManager().editSong(selectedSong);
                myTunesHomeController.reloadSongTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/MyTunesHome.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
    }

    //todo Use this method somehow
    public void setSelectedSong(Song song) {
            txtFieldTitle.setText(song.getTitle());
            txtFieldFile.setText(song.getUrl());
            txtFieldArtist.setText(song.getArtist());
            cbProof.getSelectionModel().select(song.getCategory());
        }
}
