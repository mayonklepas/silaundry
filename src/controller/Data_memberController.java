/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_member_entity;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Data_memberController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param
     */
    @FXML
    private TableView<Data_member_entity> tbview;
    @FXML
    private TableColumn<Data_member_entity, String> id_member, nama_member, alamat_member, nohp_member, tanggal_daftar;
    @FXML
    private Button btambah, bedit, bhapus, brefresh, bprev, bnext;
    @FXML
    private TextField tcari, tlimit;
    @FXML
    private Label ltotal;
    ObservableList ols = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    Errorhandler err=new Errorhandler();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        viewdata();
        tambah();
        edit();
        hapus();
        refresh();
        pagging();
        cari();
        humancontrol();

        btambah.setGraphic(new ImageView(getClass().getResource("/image/dialog-more.png").toString()));
        btambah.setTooltip(new Tooltip("Tambah data akun"));
        bedit.setGraphic(new ImageView(getClass().getResource("/image/edit-6.png").toString()));
        bedit.setTooltip(new Tooltip("Edit data akun"));
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
                bedit.setDisable(false);
            }
        });

    }

    private void viewdata() {
        try {
            //bprev.setDisable(true);
            bhapus.setDisable(true);
            bedit.setDisable(true);
            offset = 0;
            limit = Integer.parseInt(tlimit.getText());
            tbview.getItems().clear();
            tbview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            m.connect();
            ResultSet res = m.read("SELECT id_member,nama_member,alamat_member,nohp_member,tanggal_daftar::date FROM data_member LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
            while (res.next()) {
                ols.add(new Data_member_entity(res.getString("id_member"),
                        res.getString("nama_member"),
                        res.getString("alamat_member"),
                        res.getString("nohp_member"),
                        res.getString("tanggal_daftar")));

            }
            ResultSet res2 = m.read("SELECT COUNT(id_member) AS total FROM data_member").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
            nohp_member.setCellValueFactory(new PropertyValueFactory<>("nohp_member"));
            tanggal_daftar.setCellValueFactory(new PropertyValueFactory<>("tanggal_daftar"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
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
                    ResultSet res = m.read("SELECT id_member,nama_member,alamat_member,nohp_member,tanggal_daftar::date  FROM data_member LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new Data_member_entity(res.getString("id_member"),
                                res.getString("nama_member"),
                                res.getString("alamat_member"),
                                res.getString("nohp_member"),
                                res.getString("tanggal_daftar")));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_member) AS total FROM data_member").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");
                    m.disconnect();
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
                    nohp_member.setCellValueFactory(new PropertyValueFactory<>("nohp_member"));
                    tanggal_daftar.setCellValueFactory(new PropertyValueFactory<>("tanggal_daftar"));
                    tbview.setItems(ols);
                } catch (Exception ex) {
                    Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
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
                    ResultSet res = m.read("SELECT id_member,nama_member,alamat_member,nohp_member,tanggal_daftar::date  FROM data_member LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new Data_member_entity(res.getString("id_member"),
                                res.getString("nama_member"),
                                res.getString("alamat_member"),
                                res.getString("nohp_member"),
                                res.getString("tanggal_daftar")));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_member) AS total FROM data_member").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");
                    m.disconnect();
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
                    nohp_member.setCellValueFactory(new PropertyValueFactory<>("nohp_member"));
                    tanggal_daftar.setCellValueFactory(new PropertyValueFactory<>("tanggal_daftar"));
                    tbview.setItems(ols);

                } catch (Exception ex) {
                    Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });

    }

    private void tambah() {
        btambah.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    Form_memberController.id = "";
                    Form_memberController.nama = "";
                    Form_memberController.alamat = "";
                    Form_memberController.nohp = "";
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_member.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Tambah Data Member");
                    st.setScene(sc);
                    st.showAndWait();
                    viewdata();
                } catch (IOException ex) {
                    Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

    }

    private void edit() {
        bedit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    int i = tbview.getSelectionModel().getSelectedIndex();
                    Form_memberController.id = id_member.getCellData(i);
                    Form_memberController.nama = nama_member.getCellData(i);
                    Form_memberController.alamat = alamat_member.getCellData(i);
                    Form_memberController.nohp = nohp_member.getCellData(i);
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_member.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Edit Data Member");
                    st.setScene(sc);
                    st.showAndWait();
                    viewdata();
                } catch (Exception ex) {
                    Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
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
                        ObservableList<Data_member_entity> selection
                                = tbview.getSelectionModel().getSelectedItems();
                        m.connect();
                        for (int i = 0; i < selection.size(); i++) {
                            m.delete("DELETE FROM data_member WHERE id_member=?", id_member.getCellData(selection.get(i)));
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
                    Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
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
            ResultSet res = m.readdetail("SELECT id_member,nama_member,alamat_member,nohp_member,tanggal_daftar::date  FROM data_member WHERE "
                    + "id_member ILIKE ? OR nama_member ILIKE ? OR alamat_member ILIKE ? "
                    + "OR nohp_member ILIKE ? OR tanggal_daftar::text ILIKE ? ", 5, o).executeQuery();
            while (res.next()) {
                ols.add(new Data_member_entity(res.getString("id_member"),
                        res.getString("nama_member"),
                        res.getString("alamat_member"),
                        res.getString("nohp_member"),
                        res.getString("tanggal_daftar")));

            }

            ResultSet res2 = m.read("SELECT COUNT(id_member) AS total FROM data_member").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            alamat_member.setCellValueFactory(new PropertyValueFactory<>("alamat_member"));
            nohp_member.setCellValueFactory(new PropertyValueFactory<>("nohp_member"));
            tanggal_daftar.setCellValueFactory(new PropertyValueFactory<>("tanggal_daftar"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_memberController.class.getName()).log(Level.SEVERE, null, ex);
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

}
