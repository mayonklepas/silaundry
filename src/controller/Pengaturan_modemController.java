/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author minami
 */
public class Pengaturan_modemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tmodem, tcommport, tbaudrate, tvendor, ttipe, tsmscenter, tpin;
    @FXML
    private Button bsimpan;

    Filecontrol fc = new Filecontrol();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        baca();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Pengaturan"));
    }

    public void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String modem = tmodem.getText();
                String commport = tcommport.getText();
                String bautrate = tbaudrate.getText();
                String vendor = tvendor.getText();
                String tipe = ttipe.getText();
                String smscenter = tsmscenter.getText();
                String pin = tpin.getText();
                fc.simpanpengaturanmodem(modem, commport, bautrate, vendor, tipe, smscenter, pin);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Informasi");
                al.setHeaderText("Pengaturan telah disimpan");
                al.setContentText("Rstart Aplikasi untuk menerapkan pengaturan");
                al.showAndWait();
            }
        });
    }

    public void baca() {
        tmodem.setText(fc.modem());
        tcommport.setText(fc.commport());
        tbaudrate.setText(fc.bautrate());
        tvendor.setText(fc.vendor());
        ttipe.setText(fc.tipe());
        tsmscenter.setText(fc.smscenter());
        tpin.setText(fc.simpin());

    }

}
