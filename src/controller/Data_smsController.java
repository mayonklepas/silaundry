/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_sms_entity;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Data_smsController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param
     */
    @FXML
    private TableView<Data_sms_entity> tbview;
    @FXML
    private TableColumn<Data_sms_entity, String> id, nohp, nama_member, alamat_member, pesan;
    @FXML
    private Button bsmsbaru, bbalas, bhapus, brefresh, bprev, bnext;
    @FXML
    private TextField tcari, tlimit;
    @FXML
    private Label ltotal, lnama, lnohp;
    @FXML
    private TextFlow tfalamat, tfpesan;
    ObservableList ols = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        viewdata();
        smsbaru();
        balas();
        hapus();
        refresh();
        pagging();
        cari();
        selectrow();
        humancontrol();
        bbalas.setGraphic(new ImageView(getClass().getResource("/image/mail-outbox.png").toString()));
        bbalas.setTooltip(new Tooltip("Balas SMS"));
        bhapus.setGraphic(new ImageView(getClass().getResource("/image/edit-delete-5.png").toString()));
        bhapus.setTooltip(new Tooltip("Hapus SMS"));
        bsmsbaru.setGraphic(new ImageView(getClass().getResource("/image/mail-new3.png").toString()));
        bsmsbaru.setTooltip(new Tooltip("BUat SMS Baru"));
        bhapus.setGraphic(new ImageView(getClass().getResource("/image/edit-delete-6.png").toString()));
        bhapus.setTooltip(new Tooltip("Hapus data akun"));
        bnext.setGraphic(new ImageView(getClass().getResource("/image/go-next-3.png").toString()));
        bnext.setTooltip(new Tooltip("Data selanjutnya"));
        bprev.setGraphic(new ImageView(getClass().getResource("/image/go-previous-3.png").toString()));
        bprev.setTooltip(new Tooltip("Data sebelumnya"));
        brefresh.setGraphic(new ImageView(getClass().getResource("/image/view_refresh.png").toString()));
        brefresh.setTooltip(new Tooltip("Refresh Data awal"));

    }
    
    public void humancontrol() {
        tbview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bhapus.setDisable(false);
                bbalas.setDisable(false);
            }
        });

    }

    private void viewdata() {
        try {
            //bprev.setDisable(true);
             bhapus.setDisable(true);
             bbalas.setDisable(true);
            
            offset = 0;
            limit = Integer.parseInt(tlimit.getText());
            tbview.getItems().clear();
            tbview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            m.connect();
            ResultSet res = m.read("SELECT id,nohp,nama_member,alamat_member,pesan FROM data_sms "
                    + "LEFT JOIN data_member ON "
                    + "substring(data_sms.nohp from 1 for 2)=substring(data_member.nohp_member from 1 for 2) "
                    + "ORDER BY id DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
            while (res.next()) {
                ols.add(new Data_sms_entity(res.getString("id"),
                        res.getString("nohp"),
                        res.getString("nama_member"),
                        res.getString("alamat_member"),
                        res.getString("pesan")));

            }
            ResultSet res2 = m.read("SELECT COUNT(id) AS total FROM data_sms").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
            pesan.setCellValueFactory(new PropertyValueFactory<>("pesan"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void pagging() {
        bprev.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    offset = offset - Integer.parseInt(tlimit.getText());
                    tbview.getItems().clear();
                    m.connect();
                    ResultSet res = m.read("SELECT id,nohp,nama_member,alamat_member,pesan FROM data_sms "
                            + "LEFT JOIN data_member ON "
                            + "substring(data_sms.nohp from 1 for 2)=substring(data_member.nohp_member from 1 for 2)"
                            + "ORDER BY id DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new Data_sms_entity(res.getString("id"),
                                res.getString("nohp"),
                                res.getString("nama_member"),
                                res.getString("alamat_member"),
                                res.getString("pesan")));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id) AS total FROM data_sms").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
                    pesan.setCellValueFactory(new PropertyValueFactory<>("pesan"));
                    tbview.setItems(ols);
                } catch (Exception ex) {
                    Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });

        bnext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    offset = offset + Integer.parseInt(tlimit.getText());
                    tbview.getItems().clear();
                    m.connect();
                    ResultSet res = m.read("SELECT id,nohp,nama_member,alamat_member,pesan FROM data_sms "
                            + "LEFT JOIN data_member ON "
                            + "substring(data_sms.nohp from 1 for 2)=substring(data_member.nohp_member from 1 for 2)"
                            + "ORDER BY id DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new Data_sms_entity(res.getString("id"),
                                res.getString("nohp"),
                                res.getString("nama_member"),
                                res.getString("alamat_member"),
                                res.getString("pesan")));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id) AS total FROM data_sms").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
                    pesan.setCellValueFactory(new PropertyValueFactory<>("pesan"));
                    tbview.setItems(ols);
                } catch (Exception ex) {
                    Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });

    }

    private void smsbaru() {
        bsmsbaru.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    Form_smsController.nohp = "";
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_sms.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("SMS Baru");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);

                }
            }
        });

    }

    private void balas() {
        bbalas.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    int i = tbview.getSelectionModel().getSelectedIndex();
                    Form_smsController.nohp = nohp.getCellData(i);
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_sms.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Balas SMS");
                    st.setScene(sc);
                    st.showAndWait();
                    viewdata();
                } catch (Exception ex) {
                    Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    public void hapus() {

        bhapus.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                    Alert alc = new Alert(Alert.AlertType.CONFIRMATION);
                    alc.setTitle("Konfirmasi");
                    alc.setHeaderText("Data yang dipilih akan dihapus dari database");
                    alc.setContentText("Yakin ingin melanjutkan ?");
                    ButtonType ok = new ButtonType("Ya");
                    ButtonType no = new ButtonType("Tidak");
                    alc.getButtonTypes().setAll(ok, no);
                    Optional<ButtonType> resb = alc.showAndWait();
                    if (resb.get() == ok) {
                        ObservableList<Data_sms_entity> selection
                                = tbview.getSelectionModel().getSelectedItems();
                        m.connect();
                        for (int i = 0; i < selection.size(); i++) {
                            m.delete("DELETE FROM data_sms WHERE id::text=?", id.getCellData(selection.get(i)));
                        }
                        m.disconnect();
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle("Pemberitahuan");
                        al.setHeaderText("Data Berhasil Dihapus");
                        al.setContentText("Refresh Data, jika perubahan belum terlihat");
                        al.showAndWait();
                        viewdata();
                    } else {

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void refresh() {
        brefresh.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                viewdata();
                tcari.setText("");
                bprev.setDisable(false);
                bnext.setDisable(false);

            }
        });
    }

    private void rawcari() {
        try {
            tbview.getItems().clear();
            Object[] o = new Object[5];
            o[0] = "%" + tcari.getText() + "%";
            o[1] = "%" + tcari.getText() + "%";
            o[2] = "%" + tcari.getText() + "%";
            o[3] = "%" + tcari.getText() + "%";
            o[4] = "%" + tcari.getText() + "%";
            m.connect();
            ResultSet res = m.readdetail("SELECT id,nohp,nama_member,alamat_member,pesan FROM data_sms "
                    + "LEFT JOIN data_member ON "
                    + "substring(data_sms.nohp from 1 for 2)=substring(data_member.nohp_member from 1 for 2)"
                    + " WHERE nama_member ILIKE ? OR nohp ILIKE ? OR"
                    + " tanggal ILIKE ? OR pesan ILIKE ? OR alamat_member ILIKE ? ORDER BY id DESC ", 5, o).executeQuery();
            while (res.next()) {
                ols.add(new Data_sms_entity(res.getString("id"),
                        res.getString("nohp"),
                        res.getString("nama_member"),
                        res.getString("alamat_member"),
                        res.getString("pesan")));

            }
            ResultSet res2 = m.read("SELECT COUNT(id) AS total FROM data_sms").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
            pesan.setCellValueFactory(new PropertyValueFactory<>("pesan"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_smsController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void cari() {

        tcari.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tcari.clear();
            }
        });

        tcari.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bprev.setDisable(true);
                bnext.setDisable(true);
                rawcari();
            }
        });
    }

    private void selectrow() {

        tbview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int i = tbview.getSelectionModel().getSelectedIndex();
                lnohp.setText(String.valueOf(nohp.getCellData(i)));
                lnama.setText(String.valueOf(nama_member.getCellData(i)));
                Text talamat = new Text(String.valueOf(alamat_member.getCellData(i)));
                Text tpesan = new Text(String.valueOf(pesan.getCellData(i)));
                tfalamat.getChildren().clear();
                tfpesan.getChildren().clear();
                tfalamat.getChildren().add(talamat);
                tfpesan.getChildren().add(tpesan);

            }
        });

    }

}
