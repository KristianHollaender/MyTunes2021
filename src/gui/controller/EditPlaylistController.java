package gui.controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.PlaylistDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditPlaylistController {

    @FXML
    private TextField txtFieldPlaylist;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnUndo;

    private PlaylistDAO playlistDAO = new PlaylistDAO();

    public EditPlaylistController() throws SQLException {
    }

    public void cancelPlaylist(ActionEvent actionEvent) throws SQLServerException {
        Stage stage = (Stage) btnUndo.getScene().getWindow();
        stage.close();
    }

    public void createPlaylist(ActionEvent actionEvent) throws SQLServerException {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        playlistDAO.createPlaylist(txtFieldPlaylist.getText());
        stage.close();
    }
}
