/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nimrod
 *
 */
public class JFrameMain extends javax.swing.JFrame {

    /**
     * Variables
     */
    /*private String valorARB;
    private String valorCPH;
    private String valorCPS;
    private String valorDES;
    private String valorCRB;*/
    private Hashtable<String, String> listaMensaje = new Hashtable<String, String>();
    Conexion conexion = new Conexion();
    Connection conect;

    /**
     * Creates new form JFrameMain
     */
    public JFrameMain() {
        initComponents();

        conexion.CrearBd();
        conect = conexion.cargarDB();
        conexionRegistros();

        if (conect != null) {
            mostrarRegistros();
        }

    }

    /**
     * Metodos
     */
    private void mostrarRegistros() {
        String columnas[] = {"arb", "cph", "cps", "des", "crb"};
        DefaultTableModel dft = new DefaultTableModel(null, columnas);

        try {
            Statement orden = conect.createStatement();
            ResultSet r = orden.executeQuery("select*from mensaje where id=1");

            if (r.next()) {
                arb.setText(r.getString("arb"));
                cph.setText(r.getString("cph"));
                cps.setText(r.getString("cps"));
                des.setText(r.getString("des"));
                crb.setText(r.getString("crb"));

                /*valorARB = r.getString("arb");
                valorCPH = r.getString("cph");
                valorCPS = r.getString("cps");
                valorDES = r.getString("des");
                valorCRB = r.getString("crb");*/

            }

            System.out.println("Registro Cargado!");
            r.close();
            orden.close();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void conexionRegistros() {
        conect = conexion.cargarDB();
        System.out.println(conect);
        if (conect != null) {
            try {
                Statement orden = conect.createStatement();
                String crear = "insert into mensaje(id,arb,cph,cps,des,crb,descripcion) values(1"
                        + ",'" + arb.getText() + "','" + cph.getText() + "','" + cps.getText() + "','" + des.getText()
                        + "','" + crb.getText() + "','Hispano Soluciones')";
                orden.executeUpdate(crear);
                System.out.println("Registro Creado!");
            } catch (SQLException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void modificaRegistros() {
        conect = conexion.cargarDB();

        if (conect != null) {
            try {
                Statement orden = conect.createStatement();
                String editar = "update mensaje set arb='" + arb.getText() + "',cph='" + cph.getText() + "',cps='"
                        + cps.getText() + "',des='" + des.getText() + "',crb='" + crb.getText() + "' where id=1";

                orden.executeUpdate(editar);

                System.out.println("Registro Editado!");
                conexionRegistros();

                conect.close();
                orden.close();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void copy(String copiar) {

        StringSelection selection = new StringSelection(copiar);
        Clipboard resultado = Toolkit.getDefaultToolkit().getSystemClipboard();

        resultado.setContents(selection, null);

    }

    private String paste() {

        String resultado = "";

        Clipboard valor = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable contenido = valor.getContents(null);

        if (contenido.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                resultado = (String) contenido.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return resultado;

    }

    private void limpiarCheck() {
        checkARB.setSelected(false);
        checkCPH.setSelected(false);
        checkCPS.setSelected(false);
        checkDES.setSelected(false);
        checkCRB.setSelected(false);
    }

    private void mostrarMensaje() {

        String mensaje = "";
        String[] claves = (String[]) listaMensaje.keySet().toArray(new String[0]);

        java.util.Arrays.sort(claves);

        for (String clave : claves) {
            if (clave == "a") {
                mensaje += listaMensaje.get(clave) + "\n\n";
            } else {
                mensaje += listaMensaje.get(clave) + "\n\n";
            }
        }

        enviar.setText(mensaje);

    }

    private void eventoCheckbox(int flag) {

        boolean check = false;

        switch (flag) {
            case 1:
                if (checkARB.isSelected() == true) {
                    check = true;
                } else if (checkARB.isSelected() == false) {
                    check = false;
                }
                break;
            case 2:
                if (checkCPH.isSelected() == true) {
                    check = true;
                } else if (checkCPH.isSelected() == false) {
                    check = false;
                }
                break;
            case 3:
                if (checkCPS.isSelected() == true) {
                    check = true;
                } else if (checkCPS.isSelected() == false) {
                    check = false;
                }
                break;
            case 4:
                if (checkDES.isSelected() == true) {
                    check = true;
                } else if (checkDES.isSelected() == false) {
                    check = false;
                }
                break;
            case 5:
                if (checkCRB.isSelected() == true) {
                    check = true;
                } else if (checkCRB.isSelected() == false) {
                    check = false;
                }
                break;
        }

        try {
            conect = conexion.cargarDB();
            Statement orden = conect.createStatement();
            ResultSet r = orden.executeQuery("select*from mensaje where id=1");

            if (r.next()) {
                if (flag == 1) {
                    if (check == true) {
                        listaMensaje.put("a", r.getString("arb"));
                    } else {
                        listaMensaje.remove("a");
                    }
                }

                if (flag == 2) {
                    if (check == true) {
                        listaMensaje.put("b", r.getString("cph"));
                    } else {
                        listaMensaje.remove("b");
                    }
                }

                if (flag == 3) {
                    if (check == true) {
                        listaMensaje.put("c", r.getString("cps"));
                    } else {
                        listaMensaje.remove("c");
                    }
                }

                if (flag == 4) {
                    if (check == true) {
                        listaMensaje.put("d", r.getString("des"));
                    } else {
                        listaMensaje.remove("d");
                    }
                }

                if (flag == 5) {
                    if (check == true) {
                        listaMensaje.put("e", r.getString("crb"));
                    } else {
                        listaMensaje.remove("e");
                    }
                }

            }

            System.out.println("Registro Cargado!");
            r.close();
            orden.close();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        mostrarMensaje();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recibido = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        enviar = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        checkARB = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        checkCPH = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        checkCPS = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        checkDES = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        checkCRB = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        arb = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        cph = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        cps = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        des = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        crb = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(2, 2));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(1, 3));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        recibido.setColumns(20);
        recibido.setLineWrap(true);
        recibido.setRows(5);
        recibido.setToolTipText("A");
        recibido.setWrapStyleWord(true);
        recibido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(recibido);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        enviar.setColumns(20);
        enviar.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        enviar.setLineWrap(true);
        enviar.setRows(5);
        enviar.setToolTipText("B");
        enviar.setWrapStyleWord(true);
        enviar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(enviar);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel8);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(5, 4));

        checkARB.setBackground(new java.awt.Color(255, 255, 255));
        checkARB.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        checkARB.setText("ARB");
        checkARB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkARBActionPerformed(evt);
            }
        });
        jPanel5.add(checkARB);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel6);

        jButton1.setText("Copy to B");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel15);

        checkCPH.setBackground(new java.awt.Color(255, 255, 255));
        checkCPH.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        checkCPH.setText("CPH");
        checkCPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCPHActionPerformed(evt);
            }
        });
        jPanel5.add(checkCPH);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel9);

        jButton2.setText("Paste in A");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel13);

        checkCPS.setBackground(new java.awt.Color(255, 255, 255));
        checkCPS.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        checkCPS.setText("CPS");
        checkCPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCPSActionPerformed(evt);
            }
        });
        jPanel5.add(checkCPS);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel10);

        jButton3.setText("Clean All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel14);

        checkDES.setBackground(new java.awt.Color(255, 255, 255));
        checkDES.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        checkDES.setText("DES");
        checkDES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDESActionPerformed(evt);
            }
        });
        jPanel5.add(checkDES);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel11);

        jButton4.setText("Clean A");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel16);

        checkCRB.setBackground(new java.awt.Color(255, 255, 255));
        checkCRB.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        checkCRB.setText("CRB");
        checkCRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCRBActionPerformed(evt);
            }
        });
        jPanel5.add(checkCRB);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel12);

        jButton5.setText("Clean B");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton5);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel17);

        jPanel3.add(jPanel5);

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(5, 1));

        arb.setColumns(20);
        arb.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        arb.setLineWrap(true);
        arb.setRows(5);
        arb.setToolTipText("ARB");
        arb.setWrapStyleWord(true);
        arb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                arbFocusLost(evt);
            }
        });
        jScrollPane3.setViewportView(arb);

        jPanel4.add(jScrollPane3);

        cph.setColumns(20);
        cph.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cph.setLineWrap(true);
        cph.setRows(5);
        cph.setToolTipText("CPH");
        cph.setWrapStyleWord(true);
        cph.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cphFocusLost(evt);
            }
        });
        jScrollPane4.setViewportView(cph);

        jPanel4.add(jScrollPane4);

        cps.setColumns(20);
        cps.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cps.setLineWrap(true);
        cps.setRows(5);
        cps.setToolTipText("CPS");
        cps.setWrapStyleWord(true);
        cps.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cpsFocusLost(evt);
            }
        });
        jScrollPane5.setViewportView(cps);

        jPanel4.add(jScrollPane5);

        des.setColumns(20);
        des.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        des.setLineWrap(true);
        des.setRows(5);
        des.setToolTipText("DES");
        des.setWrapStyleWord(true);
        des.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                desFocusLost(evt);
            }
        });
        jScrollPane6.setViewportView(des);

        jPanel4.add(jScrollPane6);

        crb.setColumns(20);
        crb.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        crb.setLineWrap(true);
        crb.setRows(5);
        crb.setToolTipText("CRB");
        crb.setWrapStyleWord(true);
        crb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                crbFocusLost(evt);
            }
        });
        jScrollPane7.setViewportView(crb);

        jPanel4.add(jScrollPane7);

        jPanel1.add(jPanel4);

        jTabbedPane1.addTab("Hispanos Soluciones", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 972, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Proyecto 2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        copy(enviar.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        recibido.setText(paste());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        enviar.setText("");
        recibido.setText("");
        limpiarCheck();
        listaMensaje.clear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        recibido.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        enviar.setText("");
        limpiarCheck();
        listaMensaje.clear();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void arbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_arbFocusLost
        // TODO add your handling code here:
        modificaRegistros();

    }//GEN-LAST:event_arbFocusLost

    private void cphFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cphFocusLost
        // TODO add your handling code here:
        modificaRegistros();
    }//GEN-LAST:event_cphFocusLost

    private void cpsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cpsFocusLost
        // TODO add your handling code here:
        modificaRegistros();
    }//GEN-LAST:event_cpsFocusLost

    private void desFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_desFocusLost
        // TODO add your handling code here
        modificaRegistros();
    }//GEN-LAST:event_desFocusLost

    private void crbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_crbFocusLost
        // TODO add your handling code here:
        modificaRegistros();
    }//GEN-LAST:event_crbFocusLost

    private void checkARBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkARBActionPerformed
        // TODO add your handling code here:
        eventoCheckbox(1);
    }//GEN-LAST:event_checkARBActionPerformed

    private void checkCPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCPHActionPerformed
        // TODO add your handling code here:
        eventoCheckbox(2);
    }//GEN-LAST:event_checkCPHActionPerformed

    private void checkCPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCPSActionPerformed
        // TODO add your handling code here:
        eventoCheckbox(3);
    }//GEN-LAST:event_checkCPSActionPerformed

    private void checkDESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDESActionPerformed
        // TODO add your handling code here:
        eventoCheckbox(4);
    }//GEN-LAST:event_checkDESActionPerformed

    private void checkCRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCRBActionPerformed
        // TODO add your handling code here:
        eventoCheckbox(5);
    }//GEN-LAST:event_checkCRBActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Toolkit pantalla = Toolkit.getDefaultToolkit();
                JFrameMain mail = new JFrameMain();
                mail.setTitle("App Mail");

                Image icono = pantalla.getImage("images/mail.png");
                mail.setIconImage(icono);

                mail.setLocationRelativeTo(null);

                mail.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea arb;
    private javax.swing.JCheckBox checkARB;
    private javax.swing.JCheckBox checkCPH;
    private javax.swing.JCheckBox checkCPS;
    private javax.swing.JCheckBox checkCRB;
    private javax.swing.JCheckBox checkDES;
    private javax.swing.JTextArea cph;
    private javax.swing.JTextArea cps;
    private javax.swing.JTextArea crb;
    private javax.swing.JTextArea des;
    private javax.swing.JTextArea enviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea recibido;
    // End of variables declaration//GEN-END:variables
}
