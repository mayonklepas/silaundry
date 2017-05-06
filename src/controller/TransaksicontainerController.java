/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mulyadi
 */
public class TransaksicontainerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView bjournal,bjournal1;

    @FXML
    private SplitPane splitpane;

    @FXML
    private ImageView bpembayaran,bpembayaran1;

    @FXML
    private ImageView bsms,bsms1;

    @FXML
    private ImageView bcucimasuk,bcucimasuk1;

    @FXML
    private ImageView bcuciselesai,bcuciselesai1;

    @FXML
    private AnchorPane childcontainer;
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadhome();
        loadcucimasuk();
        loadbayar();
        loadcucianselesai();
        loadjournal();
        loadsms();
        splitpane.setDividerPositions(0.26);
        //splitpane.setStyle("-fx-box-border: #999;");
        bpembayaran1.setVisible(false);
        bjournal1.setVisible(false);
        bsms1.setVisible(false);
        bcucimasuk1.setVisible(false);
        bcuciselesai1.setVisible(false);
        hover();

    }
    
    
    private void hover(){
        bpembayaran.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpembayaran1.setVisible(true);
            }
        });
        
        bpembayaran.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpembayaran1.setVisible(false);
            }
        });
        
        bjournal.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bjournal1.setVisible(true);
            }
        });
        
        bjournal.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bjournal1.setVisible(false);
            }
        });
        
        bsms.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bsms1.setVisible(true);
            }
        });
        
        bsms.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bsms1.setVisible(false);
            }
        });
        
        bcucimasuk.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bcucimasuk1.setVisible(true);
            }
        });
        
        bcucimasuk.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bcucimasuk1.setVisible(false);
            }
        });
        
        bcuciselesai.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bcuciselesai1.setVisible(true);
            }
        });
        
        bcuciselesai.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bcuciselesai1.setVisible(false);
            }
        });
    }

    private void loadhome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_barang_masuk.fxml"));
            AnchorPane node = (AnchorPane) fxmlLoader.load();
            childcontainer.getChildren().clear();
            childcontainer.getChildren().add(node);
            node.autosize();
            node.prefWidthProperty().bind(childcontainer.widthProperty());
            node.prefHeightProperty().bind(childcontainer.heightProperty());
        } catch (IOException ex) {
            Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void loadcucimasuk() {
        bcucimasuk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_barang_masuk.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadbayar() {
        bpembayaran.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_transaksi.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadcucianselesai() {
        bcuciselesai.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_barang_selesai.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadjournal() {
        bjournal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_journal.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadsms() {
        bsms.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_sms.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(TransaksicontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

}
