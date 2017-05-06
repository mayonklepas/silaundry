/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_biaya_entity;
import Entity.Sessionuser;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_biayaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea tnama;
    @FXML
    private TextField tidbiaya;
    @FXML
    private ComboBox<String> ckategori;
    @FXML
    private Button bsimpan, bclear;
    public static String id, nama, kategori;

    Model m = new Model();
    Data_biaya_entity e = new Data_biaya_entity();
    Sessionuser ses = new Sessionuser();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        bersihkan();
        tnama.setText(nama);
        tidbiaya.setText(id);
        loadcombo();
        tnama.requestFocus();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihka Field"));

    }

    public void loadcombo() {
        ckategori.getItems().clear();
        ObservableList ols = FXCollections.observableArrayList("Pengeluaran", "Pendapatan");
        ckategori.setItems(ols);
    }

    private void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    m.connect();
                    if (id.equals("")) {
                        Object[] o = new Object[3];
                        o[0] = tidbiaya.getText();
                        o[1] = tnama.getText();
                        o[2] = ckategori.getSelectionModel().getSelectedItem();
                        m.insert("INSERT INTO data_biaya(id_biaya,nama_biaya,kategori_biaya)"
                                + "VALUES(?,?,?)", 3, o);

                    } else {
                        Object[] o = new Object[4];
                        o[0] = tidbiaya.getText();
                        o[1] = tnama.getText();
                        o[2] = ckategori.getSelectionModel().getSelectedItem();
                        o[3] = Integer.parseInt(id);
                        m.insert("UPDATE data_biaya SET id_biaya=?,nama_biaya=?,kategori_biaya=? WHERE id_biaya=?", 4, o);
                    }
                    m.disconnect();
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Pemberitahuan");
                    al.setHeaderText("Data Berhasil Disimpan");
                    al.setContentText("Refresh Data jika perubahan belum terlihat");
                    al.showAndWait();

                } catch (SQLException | NumberFormatException ex) {
                    Logger.getLogger(Form_biayaController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
        tnama.requestFocus();
    }

    public void bersihkan() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tnama.clear();
            }
        });
    }
}
