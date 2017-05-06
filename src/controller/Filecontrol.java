/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 *
 * @author minami
 */
public class Filecontrol {

    File dbsetting = new File("dbsetting");
    File modemsetting = new File("modemsetting");
    File info = new File("info");
    Errorhandler err=new Errorhandler();

    public Filecontrol() {
        if (!dbsetting.exists()) {
            try {
                dbsetting.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
                err.showerror(ex);
            }
        }

        if (!modemsetting.exists()) {
            try {
                modemsetting.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
                err.showerror(ex);
            }
        }
        
        
        if (!info.exists()) {
            try {
                info.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
                err.showerror(ex);
            }
        }
    }

    //database
    public String host() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 1) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String port() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 2) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String username() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 3) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String password() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 4) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String database() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 5) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    //modem
    public String modem() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 1) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String commport() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 2) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String bautrate() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 3) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String vendor() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 4) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String tipe() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 5) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String smscenter() {

        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 6) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String simpin() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(modemsetting));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 7) {
                    dataline = data;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    //info
    public String namaperusahaan() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 1) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String namapemilik() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 2) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }

    public String nohp() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 3) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }
    
    
    
     public String email() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 4) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }
    
     
      public String alamat() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 5) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
           err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }
      
      
      public String pembulatan() {
        String data = "";
        int line = 0;
        String dataline = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(info));

            while ((data = br.readLine()) != null) {
                line++;
                if (line == 6) {
                    dataline = data;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
           err.showerror(ex);
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

        return dataline;
    }
      
      

    //simpan
    public void simpanpengaturandb(String host, String port, String user, String password, String db) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dbsetting));
            bw.write(host);
            bw.newLine();
            bw.write(port);
            bw.newLine();
            bw.write(user);
            bw.newLine();
            bw.write(password);
            bw.newLine();
            bw.write(db);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    public void simpanpengaturanmodem(String modem, String comport, String bautrate, String vendor, String tipe,
            String smscenter, String simpin) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(modemsetting));
            bw.write(modem);
            bw.newLine();
            bw.write(comport);
            bw.newLine();
            bw.write(bautrate);
            bw.newLine();
            bw.write(vendor);
            bw.newLine();
            bw.write(tipe);
            bw.newLine();
            bw.write(smscenter);
            bw.newLine();
            bw.write(simpin);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }
    
    
    public void simpaninfo(String namaperusahaan, String namapemilik, String nohp, String email, String alamat,String pembulatan) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(info));
            bw.write(namaperusahaan);
            bw.newLine();
            bw.write(namapemilik);
            bw.newLine();
            bw.write(nohp);
            bw.newLine();
            bw.write(email);
            bw.newLine();
            bw.write(alamat);
            bw.newLine();
            bw.write(pembulatan);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Filecontrol.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

}
