/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_akun_entity;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_akunController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tnama, tusername, tpassword, trepassword;
    @FXML
    private ComboBox<String> ctipe;
    @FXML
    private Button bsimpan, bclear;
    public static String id, nama, username, password, tipe;

    Model m = new Model();
    Data_akun_entity e = new Data_akun_entity();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        bersihkan();
        loadcombo();
        tnama.setText(nama);
        tusername.setText(username);
        tpassword.setText(password);
        tnama.requestFocus();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihkan Field"));
    }

    public void loadcombo() {
        ctipe.getItems().clear();
        ObservableList<String> ols = FXCollections.observableArrayList("User", "Admin");
        ctipe.setItems(ols);
    }

    private void rawsimpan() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();
            if (id.equals("")) {
                Object[] o = new Object[5];
                o[0] = new Setnumber().nourutv3("AK", "id_akun", "data_akun");
                o[1] = tnama.getText();
                o[2] = tusername.getText();
                o[3] = tpassword.getText();
                o[4] = ctipe.getSelectionModel().getSelectedItem();
                m.insert("INSERT INTO data_akun(id_akun,nama_akun,username,password,tipe)"
                        + "VALUES(?,?,?,?,?)", 5, o);

            } else {
                Object[] o = new Object[5];
                o[0] = tnama.getText();
                o[1] = tusername.getText();
                o[2] = tpassword.getText();
                o[3] = ctipe.getSelectionModel().getSelectedItem();
                o[4] = id;
                m.insert("UPDATE data_akun SET nama_akun=?,username=?,password=?,tipe=? WHERE id_akun=?", 5, o);
            }
            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Data Berhasil Disimpan");
            al.setContentText("Refresh Data jika perubahan belum terlihat");
            al.showAndWait();
            tnama.requestFocus();

        } catch (SQLException ex) {
            Logger.getLogger(Form_akunController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                rawsimpan();
            }
        });

        trepassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rawsimpan();
                }
            }
        });
    }

    public void bersihkan() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tnama.clear();
                tusername.clear();
                tpassword.clear();
                trepassword.clear();
            }
        });
    }
    
   
}
