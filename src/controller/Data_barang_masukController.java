/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_form_barang_masuk_entity;
import Entity.Sessionuser;
import Entity.data_barang_masuk_entity;
import static controller.Form_barang_masukController.id_barang;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.control.ProgressBar;
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
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Data_barang_masukController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param
     */
    @FXML
    private TableView<data_barang_masuk_entity> tbview;
    @FXML
    private TableColumn<data_barang_masuk_entity, String> id_barang, tanggal_masuk, tanggal_selesai, id_member,
            nama_member, nohp, uangmuka;

    @FXML
    private Button btambah, bedit, bhapus, brefresh, bprev, bnext, bproses;
    @FXML
    private TextField tcari, tlimit;
    @FXML
    private Label ltotal;
    ObservableList ols = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    NumberFormat nf = NumberFormat.getInstance();
    Sessionuser ses = new Sessionuser();
    Errorhandler err = new Errorhandler();

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
        proses();
        humancontrol();
        bproses.setGraphic(new ImageView(getClass().getResource("/image/tick.png").toString()));
        bproses.setTooltip(new Tooltip("Proses Barang"));
        btambah.setGraphic(new ImageView(getClass().getResource("/image/dialog-more.png").toString()));
        btambah.setTooltip(new Tooltip("Tambah data barang"));
        bedit.setGraphic(new ImageView(getClass().getResource("/image/edit-6.png").toString()));
        bedit.setTooltip(new Tooltip("Edit data barnag"));
        bhapus.setGraphic(new ImageView(getClass().getResource("/image/edit-delete-6.png").toString()));
        bhapus.setTooltip(new Tooltip("Hapus data barang"));
        bnext.setGraphic(new ImageView(getClass().getResource("/image/go-next-3.png").toString()));
        bnext.setTooltip(new Tooltip("Data selanjutnya"));
        bprev.setGraphic(new ImageView(getClass().getResource("/image/go-previous-3.png").toString()));
        bprev.setTooltip(new Tooltip("Data sebelumnya"));
        brefresh.setGraphic(new ImageView(getClass().getResource("/image/view_refresh.png").toString()));
        brefresh.setTooltip(new Tooltip("Refresh Data awal"));
    }

    private void viewdata() {
        try {
            //bprev.setDisable(true);
            bhapus.setDisable(true);
            bedit.setDisable(true);
            bproses.setDisable(true);
            offset = 0;
            limit = Integer.parseInt(tlimit.getText());
            tbview.getItems().clear();
            tbview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            m.connect();
            ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status=0 "
                    + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
            while (res.next()) {
                ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

            }
            ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master_barang WHERE status=0").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
            tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
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
                    ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                            + "data_master_barang.id_member as id_member,"
                            + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                            + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status=0 "
                            + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                                res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                                res.getString("id_member"), res.getString("nama_member"),
                                res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master WHERE status=0").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
                    tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
                    tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
                    tbview.setItems(ols);

                } catch (Exception ex) {
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
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
                    ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                            + "data_master_barang.id_member as id_member,"
                            + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                            + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status=0 "
                            + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                                res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                                res.getString("id_member"), res.getString("nama_member"),
                                res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master WHERE status=0").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
                    tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
                    tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
                    tbview.setItems(ols);

                } catch (Exception ex) {
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Form_barang_masukController.tipe = "tambah";
                    Form_barang_masukController.id_barang = new Setnumber().nourut("BR", "id_barang", "data_master_barang");
                    Form_barang_masukController.nohp = "";
                    Form_barang_masukController.uangmuka = "";
                    Form_barang_masukController.tanggalselesai = String.valueOf(LocalDate.now());
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_barang_masuk.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage(StageStyle.UTILITY);
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Tambah Data Barang Masuk");
                    st.setScene(sc);
                    st.showAndWait();
                    viewdata();
                } catch (IOException ex) {
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Form_barang_masukController.tipe = "edit";
                    Form_barang_masukController.id_barang = id_barang.getCellData(i);
                    Form_barang_masukController.nohp = nohp.getCellData(i);
                    Form_barang_masukController.uangmuka = uangmuka.getCellData(i);
                    Form_barang_masukController.tanggalselesai = tanggal_selesai.getCellData(i);
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Form_barang_masuk.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Edit Data Barang Masuk");
                    st.setScene(sc);
                    st.showAndWait();
                    viewdata();
                } catch (Exception ex) {
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
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
                        ObservableList<data_barang_masuk_entity> selection
                                = tbview.getSelectionModel().getSelectedItems();
                        m.connect();
                        for (int i = 0; i < selection.size(); i++) {
                            m.delete("DELETE FROM data_master_barang WHERE id_barang=?", id_barang.getCellData(selection.get(i)));
                            m.delete("DELETE FROM data_barang_masuk WHERE id_barang=?", id_barang.getCellData(selection.get(i)));
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
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    public void proses() {

        bproses.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                    Alert alc = new Alert(Alert.AlertType.CONFIRMATION);
                    alc.setTitle("Konfirmasi");
                    alc.setHeaderText("Data akan diproses");
                    alc.setContentText("Lanjutkan ?");
                    ButtonType ok = new ButtonType("Ya");
                    ButtonType no = new ButtonType("Tidak");
                    alc.getButtonTypes().setAll(ok, no);
                    Optional<ButtonType> resb = alc.showAndWait();
                    if (resb.get() == ok) {
                        int rowselect = tbview.getSelectionModel().getSelectedIndex();
                        m.connect();
                        StringBuilder sb = new StringBuilder();
                        Object[] o = new Object[1];
                        o[0] = id_barang.getCellData(rowselect);
                        double totalbiaya = 0;
                        ResultSet res = m.readdetail("SELECT data_barang_masuk.id_layanan,nama_layanan,"
                                + "jumlah,jumlah*tarif_layanan as biaya,keterangan "
                                + "FROM data_barang_masuk LEFT JOIN "
                                + "data_layanan ON data_barang_masuk.id_layanan=data_layanan.id_layanan "
                                + "WHERE id_barang=? ORDER BY id_barang DESC", 1, o).executeQuery();
                        while (res.next()) {
                            sb.append(res.getString("nama_layanan") + " | "
                                    + nf.format(res.getDouble("jumlah")) + " KG \n"
                                    //+ nf.format(res.getDouble("biaya")) + "\n"
                            );
                            totalbiaya = totalbiaya + res.getDouble("biaya");
                        }
                        
                        double respembulatan=0;
                        ResultSet res2=m.readdetail("SELECT pembulatan FROM data_master_barang WHERE id_barang=? ", 1, o).executeQuery();
                        while (res2.next()) {
                            respembulatan=res2.getDouble("pembulatan");
                        }
                        
                                
                                
                        

                        String nomor = "";
                        if (nohp.getCellData(rowselect) == null) {
                            if (id_member.getCellData(rowselect).matches("[0-9]+")) {
                                nomor = id_member.getCellData(rowselect);
                            } else {
                                nomor = "";
                            }
                        } else {
                            nomor = nohp.getCellData(rowselect);
                        }
                        Filecontrol fc = new Filecontrol();
                        String pesan ="Sdr/i. "+nama_member.getCellData(rowselect)
                                +"\nNo. Nota : " + id_barang.getCellData(rowselect) + " Telah Selesai \n"+
                                String.valueOf(sb) + 
                                "\nTotal : Rp. " + nf.format(totalbiaya+respembulatan) +
                                "\nUM/Cash : Rp. "+uangmuka.getCellData(rowselect)+
                                "\nSisa : Rp. "+
                                nf.format(totalbiaya+respembulatan - Double.parseDouble(uangmuka.getCellData(rowselect).replaceAll("[,.]", "")))+
                                "\n\n(" + fc.namaperusahaan() + ") ";
                        /*Alert al=new Alert(Alert.AlertType.INFORMATION);
                        al.setHeaderText("Pesan");
                        al.setContentText(pesan);
                        al.showAndWait();
                        System.out.println(pesan);*/

                        VBox vb = new VBox();
                        vb.setPrefSize(300, 0);
                        vb.setSpacing(5);
                        vb.setPadding(new Insets(5, 5, 5, 5));
                        ProgressBar pg = new ProgressBar();
                        pg.setMaxWidth(Double.MAX_VALUE);
                        vb.getChildren().add(pg);
                        Stage st = new Stage(StageStyle.UNDECORATED);
                        Scene sc = new Scene(vb);
                        st.setTitle("Memproses Data");
                        st.setScene(sc);

                        if (!nomor.equals("")) {
                            kirimsms kt = new kirimsms(nomor, pesan);
                            kt.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent event) {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                    st.close();
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Pemberitahuan");
                                    al.setHeaderText("Operasi Sukses");
                                    al.setContentText("Pemerosesan dan Pengiriman Pesan Berhasil");
                                    al.show();
                                }
                            });
                            pg.progressProperty().unbind();
                            pg.progressProperty().bind(kt.progressProperty());
                            Thread th = new Thread(kt);
                            th.setDaemon(true);
                            th.start();
                            st.showAndWait();
                        }

                        Object[] o3 = new Object[1];
                        o3[0] = id_barang.getCellData(rowselect);
                        m.update("UPDATE data_master_barang SET status=1 WHERE id_barang=?", 1, o3);
                        m.disconnect();
                    } else {
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle("Pemberitahuan");
                        al.setHeaderText("Dibatalkan");
                        al.setContentText("Perintah Dibatalkan");
                        al.showAndWait();
                    }
                    viewdata();
                } catch (SQLException ex) {
                    Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        }
        );
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
            Object[] o = new Object[6];
            o[0] = "%" + tcari.getText() + "%";
            o[1] = "%" + tcari.getText() + "%";
            o[2] = "%" + tcari.getText() + "%";
            o[3] = "%" + tcari.getText() + "%";
            o[4] = "%" + tcari.getText() + "%";
            o[5] = "%" + tcari.getText() + "%";
            m.connect();
            ResultSet res = m.readdetail("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                    + "data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member "
                    + "WHERE status=0 AND id_barang ILIKE ? OR "
                    + "tanggal_masuk::text ILIKE ? OR "
                    + "tanggal_selesai::text ILIKE ? OR "
                    + "data_master_barang.id_member ILIKE ? OR "
                    + "nama_member ILIKE ? OR "
                    + "nohp_member ILIKE ? ORDER BY id_barang DESC", 6, o).executeQuery();
            while (res.next()) {
                ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

            }
            ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master_barang WHERE status = 0").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
            tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_barang_masukController.class
                    .getName()).log(Level.SEVERE, null, ex);
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

        tcari.setOnKeyTyped(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bprev.setDisable(true);
                bnext.setDisable(true);
                rawcari();
            }
        });

    }
    

    public class kirimsms extends Task<Void> {

        String pesan, nohp;

        public kirimsms(String nohp, String pesan) {
            this.pesan = pesan;
            this.nohp = nohp;
        }

        @Override
        protected Void call() throws Exception {
            try {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                OutboundMessage om = new OutboundMessage(nohp, pesan);
                long start = System.currentTimeMillis();
                Service.getInstance().sendMessage(om);
                long stop = System.currentTimeMillis();
                updateProgress(start, stop);
                updateMessage(" SMS Terkirim ");
            } catch (TimeoutException | GatewayException | IOException | InterruptedException ex) {
                Logger.getLogger(Data_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                err.showerror(ex);
            }
            return null;
        }

    }

    public void humancontrol() {
        tbview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bhapus.setDisable(false);
                bedit.setDisable(false);
                bproses.setDisable(false);
            }
        });

    }

}
