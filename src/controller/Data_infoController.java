/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author minami
 */
public class Data_infoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button bsimpan;
    @FXML
    private TextField tnama, tpemilik, tnohp, temail,tbulat;
    @FXML
    private TextArea talamat;
    Filecontrol fc = new Filecontrol();
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        baca();
        simpan();
    }

    private void baca() {
        tnama.setText(fc.namaperusahaan());
        tpemilik.setText(fc.namapemilik());
        tnohp.setText(fc.nohp());
        temail.setText(fc.email());
        talamat.setText(fc.alamat());
        tbulat.setText(fc.pembulatan());
    }

    private void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                try {
                    fc.simpaninfo(tnama.getText(), tpemilik.getText(), 
                            tnohp.getText(), temail.getText(), talamat.getText(),tbulat.getText());
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Pemberitahuan");
                    al.setHeaderText("Pengaturan berhasil disimpan");
                    al.setContentText("Restart aplikasi untuk menerapkan perubahan");
                    al.show();
                } catch (Exception e) {
                    err.showerror(e);
                }

            }
        });
    }

}
