package gui.controller;

import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSongsController implements Initializable {

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
     * Add the new song to database.
     */
    public void chooseMP3(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music files", "*.mp3", "*.wav"));
        if (selectedFile != null){
            txtFieldFile.appendText(selectedFile.getAbsolutePath());
        }else{
            System.out.println("File is invalid");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareProofs();

    }
}
