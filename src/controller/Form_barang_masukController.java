/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_akun_entity;
import Entity.Data_form_barang_masuk_entity;
import Entity.Sessionuser;
import Entity.data_barang_masuk_entity;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import static java.util.Objects.hash;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Model;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.mozilla.javascript.Token;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_barang_masukController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tjumlah, tuangmuka, tnama, talamat, tnohp;
    @FXML
    private TableView<Data_form_barang_masuk_entity> tvbarangmasuk;
    @FXML
    private TableColumn<Data_form_barang_masuk_entity, String> layanan, jumlah, keterangan, biaya, idbarangmasuk;
    @FXML
    private TextArea tketerangan;
    @FXML
    private DatePicker dtanggalselesai;
    @FXML
    private Button bsimpan, btambahcucian, bhapus, bcetak;
    @FXML
    private Label ltotal, lsisa;
    public static String id_barang, tanggalselesai, nohp, uangmuka, tipe;
    String globalid_member;

    Model m = new Model();
    Sessionuser ses = new Sessionuser();
    Filecontrol fc = new Filecontrol();
    NumberFormat nf = NumberFormat.getInstance();
    ObservableList ols = FXCollections.observableArrayList();
    double total = 0, uangmukadoub;
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO]
        viewdata();
        simpan();
        edittable();
        tambahcucianbaru();
        hapus();
        humancontrol();
        tnohp.setText(nohp);
        tuangmuka.setText(uangmuka);
        dtanggalselesai.setValue(LocalDate.parse(tanggalselesai));
        cekmember();
        cetak();
        tnohp.requestFocus();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bcetak.setGraphic(new ImageView(getClass().getResource("/image/report.png").toString()));
        bcetak.setTooltip(new Tooltip("Cetak Struck"));

        try {
            double sisa;
            if (tuangmuka.getText().isEmpty()) {
                sisa = 0;
            } else {
                sisa = total - Double.parseDouble(tuangmuka.getText().replaceAll("[,.]", ""));
            }
            lsisa.setText(nf.format(sisa));
        } catch (Exception e) {
            err.showerror(e);
        }

        rawcekmember();

        if (tipe.equals("tambah")) {
            bcetak.setDisable(true);
        } else {
            bcetak.setDisable(false);
        }

    }

    private void viewdata() {
        try {
            //bprev.setDisable(true);
            bhapus.setDisable(true);
            tvbarangmasuk.getItems().clear();
            tvbarangmasuk.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            m.connect();
            Object[] o = new Object[1];
            o[0] = id_barang;
            ResultSet res = m.readdetail("SELECT id,nama_layanan,jumlah,jumlah*tarif_layanan as biaya,keterangan "
                    + "FROM data_barang_masuk LEFT JOIN "
                    + "data_layanan ON data_barang_masuk.id_layanan=data_layanan.id_layanan "
                    + "WHERE id_barang=? ORDER by id ASC", 1, o).executeQuery();
            while (res.next()) {
                ols.add(new Data_form_barang_masuk_entity(res.getString("id"), res.getString("nama_layanan"),
                        nf.format(res.getDouble("jumlah")),
                        res.getString("keterangan"),
                        nf.format(res.getDouble("biaya"))));

            }
            ResultSet res2 = m.readdetail("SELECT SUM(jumlah*tarif_layanan) AS total FROM data_barang_masuk "
                    + "LEFT JOIN data_layanan ON data_barang_masuk.id_layanan=data_layanan.id_layanan "
                    + "WHERE id_barang=?", 1, o).executeQuery();
            res2.next();
            total = res2.getDouble("total");
            ltotal.setText(nf.format(total));

            m.disconnect();
            idbarangmasuk.setCellValueFactory(new PropertyValueFactory<>("idbarangmasuk"));
            layanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
            jumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
            keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
            biaya.setCellValueFactory(new PropertyValueFactory<>("biaya"));
            tvbarangmasuk.setItems(ols);

            tuangmuka.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    try {
                        double sisa;
                        if (tuangmuka.getText().isEmpty()) {
                            sisa = 0;
                        } else {
                            sisa = total - Double.parseDouble(tuangmuka.getText().replaceAll("[,.]", ""));
                        }
                        lsisa.setText(nf.format(sisa));
                    } catch (Exception e) {
                        err.showerror(e);
                    }

                }
            });

        } catch (Exception ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void rawsimpan() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();
            Object[] ocek = new Object[1];
            ocek[0] = tnohp.getText();
            String id_member_simpan = "";
            ResultSet rescek = m.readdetail("SELECT id_member FROM data_member WHERE nohp_member=? LIMIT 1", 1, ocek).executeQuery();
            while (rescek.next()) {
                id_member_simpan = rescek.getString("id_member");
            }

            if (tuangmuka.getText().equals("")) {
                uangmukadoub = 0;
            } else {
                uangmukadoub = Double.parseDouble(tuangmuka.getText().replaceAll("[,.]", ""));
            }

            if (id_member_simpan.equals("")) {
                Object[] omember = new Object[4];
                omember[0] = new Setnumber().nourutv3("MB", "id_member", "data_member");
                omember[1] = tnama.getText();
                omember[2] = talamat.getText();
                omember[3] = tnohp.getText();
                m.insert("INSERT INTO data_member(id_member,nama_member,alamat_member,nohp_member)"
                        + "VALUES(?,?,?,?)", 4, omember);

                Object[] ocek2 = new Object[1];
                ocek2[0] = tnohp.getText();
                String id_member_simpan2 = "";
                ResultSet rescek2 = m.readdetail("SELECT id_member FROM data_member WHERE nohp_member=? LIMIT 1",
                        1, ocek2).executeQuery();
                while (rescek2.next()) {
                    id_member_simpan2 = rescek2.getString("id_member");
                }

                Object[] o = new Object[6];
                o[0] = id_barang;
                o[1] = id_member_simpan2;
                o[2] = dtanggalselesai.getValue().toString();
                o[3] = uangmukadoub;
                o[4] = ses.getId_akun();
                o[5] = GetPembulatan(total);
                m.insert("INSERT INTO data_master_barang(id_barang,id_member,tanggal_selesai,uangmuka,id_akun,pembulatan)"
                        + "VALUES(?,?,?::date,?,?,?)", 6, o);

                printstruk();
                printstruk();

            } else if (!id_member_simpan.equals("")) {

                if (tipe.equals("tambah")) {
                    Object[] o = new Object[6];
                    o[0] = id_barang;
                    o[1] = id_member_simpan;
                    o[2] = dtanggalselesai.getValue().toString();
                    o[3] = uangmukadoub;
                    o[4] = ses.getId_akun();
                    o[5] = GetPembulatan(total);
                    m.insert("INSERT INTO data_master_barang(id_barang,id_member,tanggal_selesai,uangmuka,id_akun,pembulatan)"
                            + "VALUES(?,?,?::date,?,?,?)", 6, o);
                    printstruk();
                    printstruk();
                } else {
                    Object[] o = new Object[6];
                    o[0] = id_member_simpan;
                    o[1] = dtanggalselesai.getValue().toString();
                    o[2] = uangmukadoub;
                    o[3] = ses.getId_akun();
                    o[4] = GetPembulatan(total);
                    o[5] = id_barang;

                    m.update("UPDATE data_master_barang SET id_member=?,tanggal_selesai=?::date,uangmuka=?,id_akun=?,pembulatan=? WHERE id_barang=?", 6, o);
                }

            }

            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Data Berhasil Disimpan");
            al.setContentText("Refresh Data jika perubahan belum terlihat");
            al.showAndWait();
            tnohp.requestFocus();
        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (total == 0 || tnohp.getText().equals("")) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Informasi");
                    al.setHeaderText("Data tidak Lengkap");
                    al.setContentText("Mohon mengisikan data barang lebih dulu sebelum menyimpan");
                    al.showAndWait();
                } else {
                    rawsimpan();
                    Node node = (Node) event.getSource();
                    Stage st = (Stage) node.getScene().getWindow();
                    st.close();
                }

            }
        });

    }

    private void hapus() {
        bhapus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Alert alc = new Alert(Alert.AlertType.CONFIRMATION);
                    alc.setTitle("Konfirmasi");
                    alc.setHeaderText("Data yang dipilih akan dihapus dari database");
                    alc.setContentText("Yakin ingin melanjutkan ?");
                    ButtonType ok = new ButtonType("Ya");
                    ButtonType no = new ButtonType("Tidak");
                    alc.getButtonTypes().setAll(ok, no);
                    Optional<ButtonType> resb = alc.showAndWait();
                    if (resb.get() == ok) {
                        int i = tvbarangmasuk.getSelectionModel().getSelectedIndex();
                        m.connect();
                        m.delete("DELETE FROM data_barang_masuk WHERE id::text=?", idbarangmasuk.getCellData(i));
                        m.disconnect();
                        viewdata();
                    } else {

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    /*public void loadcombo() {
        cmember.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    cmember.show();
                    Object[] o = new Object[2];
                    o[0] = "%" + cmember.getEditor().getText() + "%";
                    o[1] = "%" + cmember.getEditor().getText() + "%";
                    m.connect();
                    ResultSet res = m.readdetail("SELECT id_member,nama_member "
                            + "FROM data_member WHERE id_member ILIKE ? OR nama_member ILIKE ? "
                            + "", 2, o).executeQuery();
                    ObservableList ols = FXCollections.observableArrayList();
                    while (res.next()) {
                        ols.add(res.getString("id_member") + ":" + res.getString("nama_member"));
                    }
                    cmember.setItems(ols);
                    m.disconnect();

                } catch (SQLException ex) {
                    Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }

        });

   }*/
    private void tambahcucianbaru() {
        btambahcucian.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    m.connect();
                    Object[] o = new Object[2];
                    o[0] = id_barang;
                    o[1] = ses.getId_akun();
                    m.insert("INSERT INTO data_barang_masuk(id_barang,id_layanan,jumlah,keterangan,id_akun)"
                            + " VALUES(?,1,1,' ',?)", 2, o);
                    m.disconnect();
                    viewdata();
                } catch (SQLException ex) {
                    Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void updatelayanan(int idlayanan, int id) {
        try {
            m.connect();
            Object[] o = new Object[2];
            o[0] = idlayanan;
            o[1] = id;
            m.insert("UPDATE data_barang_masuk SET id_layanan=? WHERE id=?", 2, o);
            m.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void updatejumlah(Double jumlah, int id) {
        try {
            m.connect();
            Object[] o = new Object[2];
            o[0] = jumlah;
            o[1] = id;
            m.insert("UPDATE data_barang_masuk SET jumlah=? WHERE id=?", 2, o);
            m.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void updateketerangan(String keterangan, int id) {
        try {
            m.connect();
            Object[] o = new Object[2];
            o[0] = keterangan;
            o[1] = id;
            m.insert("UPDATE data_barang_masuk SET keterangan=? WHERE id=?", 2, o);
            m.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void edittable() {
        ObservableList ols = FXCollections.observableArrayList();
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();
            ResultSet res = m.read("SELECT id_layanan,nama_layanan "
                    + "FROM data_layanan").executeQuery();
            while (res.next()) {
                ols.add(res.getString("id_layanan") + ":" + res.getString("nama_layanan"));
            }
            m.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
        layanan.setCellFactory(ComboBoxTableCell.forTableColumn(ols));
        layanan.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String> event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int i = tvbarangmasuk.getSelectionModel().getSelectedIndex();
                int ids = Integer.parseInt(idbarangmasuk.getCellData(i));
                String idlayananarray[] = event.getNewValue().split(":");
                int idlayanans = Integer.parseInt(idlayananarray[0]);
                updatelayanan(idlayanans, ids);
                viewdata();
            }
        });
        jumlah.setCellFactory(TextFieldTableCell.forTableColumn());
        jumlah.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String> event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int i = tvbarangmasuk.getSelectionModel().getSelectedIndex();
                int ids = Integer.parseInt(idbarangmasuk.getCellData(i));
                Double jumlahkg = Double.parseDouble(event.getNewValue().replaceAll("[,.]", "."));
                updatejumlah(jumlahkg, ids);
                viewdata();
            }
        });

        keterangan.setCellFactory(TextFieldTableCell.forTableColumn());
        keterangan.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data_form_barang_masuk_entity, String> event) {
//                /throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int i = tvbarangmasuk.getSelectionModel().getSelectedIndex();
                int ids = Integer.parseInt(idbarangmasuk.getCellData(i));
                updateketerangan(event.getNewValue(), ids);
                viewdata();
            }
        });

    }

    public Connection conn() throws SQLException {
        m.conn = DriverManager.getConnection(m.host, m.user, m.password);
        return m.conn;
    }

    public void cetak() {
        bcetak.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                printstruk();
            }
        });
    }

    public void printstruk() {
        try {
            HashMap hash = new HashMap();
            hash.put("perusahaan", fc.namaperusahaan());
            hash.put("alamat", fc.alamat());
            hash.put("nohp", fc.nohp());
            hash.put("keterangan", fc.namapemilik());
            hash.put("user", ses.getNamauser());
            hash.put("id_barang", id_barang);
            hash.put("uangmuka", uangmukadoub);
            JasperReport jr = (JasperReport) JRLoader.loadObject(new File("laporan/Struck.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jr, hash, conn());
            JasperPrintManager.printReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    public void humancontrol() {
        tvbarangmasuk.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bhapus.setDisable(false);
            }
        });

    }

    public void rawcekmember() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();
            Object[] ocek = new Object[1];
            ocek[0] = tnohp.getText();
            ResultSet rescek = m.readdetail("SELECT id_member,nama_member,alamat_member FROM data_member WHERE nohp_member=? LIMIT 1", 1, ocek).executeQuery();
            while (rescek.next()) {
                globalid_member = rescek.getString("id_member");
                tnama.setText(rescek.getString("nama_member"));
                talamat.setText(rescek.getString("alamat_member"));
            }
            m.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    public void cekmember() {
        tnohp.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                rawcekmember();
            }
        });
    }

    private double GetPembulatan(double TotalPenjualan) {
        double result = 0;
        double pembulatan = Double.parseDouble(fc.pembulatan());
        double sisa = 0;
        sisa = (TotalPenjualan % pembulatan);

        if (TotalPenjualan > 0) {
            if ((pembulatan - sisa) < pembulatan) {
                result = (pembulatan - sisa);
            }
        } else if ((pembulatan + sisa) < pembulatan) {
            result = (-1 * (pembulatan + sisa));
        }
        return result;
    }

}
