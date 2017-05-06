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
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mulyadi
 */
public class MastercontainerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView bbiaya,bbiaya1;

    @FXML
    private ImageView bpelanggan,bpelanggan1;

    @FXML
    private AnchorPane childcontainer;

    @FXML
    private ImageView blayanan,blayanan1;

    @FXML
    private ImageView bpengguna,bpengguna1;
    @FXML
    private SplitPane splitpane;
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadhome();
        loadbiaya();
        loadlayanan();
        loadpelanggan();
        loadpengguna();
        splitpane.setDividerPositions(0.26);
        //splitpane.setStyle("-fx-box-border: #999");
        blayanan1.setVisible(false);
        bbiaya1.setVisible(false);
        bpelanggan1.setVisible(false);
        bpengguna1.setVisible(false);
        hover();
        

    }
    
    private void hover(){
        bbiaya.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bbiaya1.setVisible(true);
            }
        });
        
        bbiaya.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bbiaya1.setVisible(false);
            }
        });
        
        bpelanggan.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpelanggan1.setVisible(true);
            }
        });
        
        bpelanggan.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpelanggan1.setVisible(false);
            }
        });
        
        blayanan.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                blayanan1.setVisible(true);
            }
        });
        
        blayanan.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                blayanan1.setVisible(false);
            }
        });
        
        bpengguna.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpengguna1.setVisible(true);
            }
        });
        
        bpengguna.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bpengguna1.setVisible(false);
            }
        });
    }

    private void loadhome() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_member.fxml"));
            AnchorPane node = (AnchorPane) fxmlLoader.load();
            childcontainer.getChildren().clear();
            childcontainer.getChildren().add(node);
            node.autosize();
            node.prefWidthProperty().bind(childcontainer.widthProperty());
            node.prefHeightProperty().bind(childcontainer.heightProperty());
        } catch (IOException ex) {
            Logger.getLogger(MastercontainerController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void loadbiaya() {
        bbiaya.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_biaya.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MastercontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadlayanan() {
        blayanan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_layanan.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MastercontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadpelanggan() {
        bpelanggan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_member.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MastercontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void loadpengguna() {
        bpengguna.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Data_akun.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    childcontainer.getChildren().clear();
                    childcontainer.getChildren().add(node);
                    node.autosize();
                    node.prefWidthProperty().bind(childcontainer.widthProperty());
                    node.prefHeightProperty().bind(childcontainer.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MastercontainerController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

}
