/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Model;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author minami
 */
public class LaporanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button blihat;
    @FXML
    private SwingNode swingnode;
    @FXML
    private ComboBox ckategori;
    @FXML
    private DatePicker ddari, dsampai;

    Model m = new Model();
    Filecontrol fc = new Filecontrol();
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadlist();
        lihatlaporan();
        selectkkategorilaporan();

        blihat.setGraphic(new ImageView(getClass().getResource("/image/report.png").toString()));
        blihat.setTooltip(new Tooltip("Generate Laporan"));
    }

    private void loadlist() {
        ObservableList ols = FXCollections.observableArrayList("Daftar Biaya", "Laporan Pendapatan Harian",
                "Laporan Pengeluaran Harian", "Rekap Pengeluaran dan Pendapatan", "Rekap Transaksi Pelanggan",
                "Laporan Saldo Kasir", "Laporan Transaksi Pelanggan", "Laporan Barang Masuk");
        ckategori.setItems(ols);
    }

    public Connection conn() throws SQLException {
        m.conn = DriverManager.getConnection(m.host, m.user, m.password);
        return m.conn;
    }

    private void lihatlaporan() {
        blihat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage st = new Stage(StageStyle.UNDECORATED);
                st.setTitle("Memproses Data");
                st.resizableProperty().setValue(Boolean.FALSE);
                st.initModality(Modality.APPLICATION_MODAL);
                VBox vb = new VBox();
                vb.setPrefSize(300, 0);
                vb.setSpacing(5);
                vb.setPadding(new Insets(5, 5, 5, 5));
                ProgressBar pb = new ProgressBar();
                pb.setMaxWidth(Double.MAX_VALUE);
                vb.getChildren().addAll(pb);
                Scene sc = new Scene(vb);
                st.setScene(sc);
                HashMap hash = null;
                String path = "";
                int i = ckategori.getSelectionModel().getSelectedIndex();
                if (i == 0) {
                    hash = new HashMap(2);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("kategori", ckategori.getSelectionModel().getSelectedItem().toString());
                    path = "laporan/laporan_biaya.jasper";
                } else if (i == 1) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(3);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("kategori", ckategori.getSelectionModel().getSelectedItem().toString());
                    hash.put("tanggal", new Date().from(dari));
                    path = "laporan/laporan_pendapatan.jasper";

                } else if (i == 2) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(3);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("kategori", ckategori.getSelectionModel().getSelectedItem().toString());
                    hash.put("tanggal", new Date().from(dari));
                    path = "laporan/laporan_pengeluaran.jasper";
                } else if (i == 3) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    Instant sampai = Instant.from(dsampai.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(4);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("kategori", ckategori.getSelectionModel().getSelectedItem().toString());
                    hash.put("dari", new Date().from(dari));
                    hash.put("sampai", new Date().from(sampai));
                    path = "laporan/rekap_laporan.jasper";
                } else if (i == 4) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    Instant sampai = Instant.from(dsampai.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(3);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("dari", new Date().from(dari));
                    hash.put("hingga", new Date().from(sampai));
                    path = "laporan/laporan_rekap_transaksi.jasper";
                } else if (i == 5) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    Instant sampai = Instant.from(dsampai.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(3);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("keterangan", fc.namapemilik());
                    hash.put("dari", new Date().from(dari));
                    hash.put("hingga", new Date().from(sampai));
                    path = "laporan/saldo_kasir.jasper";
                } else if (i == 6) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    Instant sampai = Instant.from(dsampai.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(3);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("keterangan", fc.namapemilik());
                    hash.put("dari", new Date().from(dari));
                    hash.put("hingga", new Date().from(sampai));
                    path = "laporan/laporan_rekap_transaksi_bulat.jasper";
                } else if (i == 7) {
                    Instant dari = Instant.from(ddari.getValue().atStartOfDay(ZoneId.of("GMT")));
                    Instant sampai = Instant.from(dsampai.getValue().atStartOfDay(ZoneId.of("GMT")));
                    hash = new HashMap(4);
                    hash.put("perusahaan", fc.namaperusahaan());
                    hash.put("keterangan", fc.namapemilik());
                    hash.put("dari", new Date().from(dari));
                    hash.put("hingga", new Date().from(sampai));
                    hash.put("SUBREPORT_DIR", "laporan/");
                    path = "laporan/laporan_barang_masuk.jasper";
                } else {
                    path = null;
                }
                if (path != null) {
                    loadreport lr = new loadreport(path, hash);
                    lr.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            st.close();
                        }
                    });
                    pb.progressProperty().bind(lr.progressProperty());
                    Thread th = new Thread(lr);
                    th.setDaemon(true);
                    th.start();
                    st.showAndWait();
                } else {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setHeaderText("Tidak bisa mencetak");
                    al.setContentText("Anda harus memilih kategori laporan yang ingin dicetak");
                    al.showAndWait();
                }

            }
        });
    }

    private void selectkkategorilaporan() {
        ckategori.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (newValue.intValue() == 0) {
                    ddari.setDisable(true);
                    dsampai.setDisable(true);
                    ddari.getEditor().clear();
                    dsampai.getEditor().clear();
                } else if (newValue.intValue() == 1) {
                    ddari.getEditor().clear();
                    dsampai.getEditor().clear();
                    ddari.setDisable(false);
                    dsampai.setDisable(true);
                    ddari.setPromptText("Pilih Tanggal");
                    ddari.setValue(LocalDate.now());
                } else if (newValue.intValue() == 2) {
                    ddari.getEditor().clear();
                    dsampai.getEditor().clear();
                    ddari.setDisable(false);
                    dsampai.setDisable(true);
                    ddari.setPromptText("Pilih Tanggal");
                    ddari.setValue(LocalDate.now());
                } else if (newValue.intValue() == 3) {
                    ddari.getEditor().clear();
                    dsampai.getEditor().clear();
                    ddari.setDisable(false);
                    dsampai.setDisable(false);
                    ddari.setPromptText("Pilih Tanggal Dari");
                    dsampai.setPromptText("Pilih Tanggal Hingga");
                    ddari.setValue(LocalDate.now());
                    dsampai.setValue(LocalDate.now());
                } else {
                    ddari.getEditor().clear();
                    dsampai.getEditor().clear();
                    ddari.setDisable(false);
                    dsampai.setDisable(false);
                    ddari.setPromptText("Pilih Tanggal Dari");
                    dsampai.setPromptText("Pilih Tanggal Hingga");
                    ddari.setValue(LocalDate.now());
                    dsampai.setValue(LocalDate.now());
                }
            }
        });
    }

    public class loadreport extends Task<Void> {

        String path;
        HashMap hash;

        public loadreport(String path, HashMap hash) {
            this.path = path;
            this.hash = hash;
        }

        @Override
        protected Void call() throws Exception {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            try {
                long start = System.currentTimeMillis();
                //JasperDesign jd = JRXmlLoader.load(path);
                //JasperReport jr = JasperCompileManager.compileReport(jd);
                //JasperPrint jp = JasperFillManager.fillReport(jr, hash, conn());
                JasperReport jr = (JasperReport) JRLoader.loadObject(new File(path));
                JasperPrint jp = JasperFillManager.fillReport(jr, hash, conn());
                long stop = System.currentTimeMillis();
                updateProgress(start, stop);
                updateMessage("Proses selesai");
                swingnode.setContent(new JRViewer(jp));
            } catch (JRException | SQLException ex) {
                Logger.getLogger(LaporanController.class.getName()).log(Level.SEVERE, null, ex);
                err.showerror(ex);
            }
            return null;
        }
    }

}
