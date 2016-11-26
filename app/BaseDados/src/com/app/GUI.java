package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;

/**
 * Created by guian on 26/11/2016.
 */
public class GUI extends JFrame implements ActionListener {
    private SQL sql;
    private JButton btnConnect;
    private JButton btnExit;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSelect;

    private JLabel lblAddress;
    private JLabel lblPort;
    private JTextField txtAddress;
    private JTextField txtPort;

    private JPanel panConnect;
    private JPanel panOperations;


    public GUI(SQL sql) {
        super("Base de dados");
        this.sql = sql;
    }

    public void display() {
        setLayout(new GridBagLayout());

        btnConnect = new JButton("Conectar");
        btnConnect.setActionCommand("sql-connect");
        btnConnect.addActionListener(this);

        btnExit = new JButton("Sair");
        btnExit.setActionCommand("gui-exit");
        btnExit.addActionListener(this);

        btnInsert = new JButton("Inserir");
        btnInsert.setEnabled(false);
        btnInsert.setActionCommand("sql-insert");
        btnInsert.addActionListener(this);

        btnUpdate = new JButton("Atualizar");
        btnUpdate.setEnabled(false);
        btnUpdate.setActionCommand("sql-update");
        btnUpdate.addActionListener(this);

        btnDelete = new JButton("Deletar");
        btnDelete.setEnabled(false);
        btnDelete.setActionCommand("sql-delete");
        btnDelete.addActionListener(this);

        btnSelect = new JButton("Listar");
        btnSelect.setEnabled(false);
        btnSelect.setActionCommand("sql-select");
        btnSelect.addActionListener(this);

        lblAddress = new JLabel("Endereço:");
        lblPort = new JLabel("Porta:");
        txtAddress = new JTextField(20);
        txtPort = new JTextField(5);

        panConnect = new JPanel();
        panOperations = new JPanel();

        // Adiciona os componentes no JPanel
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0;
        c.gridy = 0;
        panConnect.add(lblAddress, c);
        c.gridx++;
        panConnect.add(txtAddress, c);
        c.gridx++;
        panConnect.add(lblPort, c);
        c.gridx++;
        panConnect.add(txtPort, c);
        c.gridx++;
        panConnect.add(btnConnect, c);
        c.gridx++;
        panConnect.add(btnExit, c);

        // Segunda linha
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 1;
        panOperations.add(btnInsert, c);
        c.gridx++;
        panOperations.add(btnUpdate, c);
        c.gridx++;
        panOperations.add(btnDelete, c);
        c.gridx++;
        panOperations.add(btnSelect, c);
        c.gridx++;
        panOperations.setEnabled(false);

        createFrame();
    }

    private void createFrame() {
        //Create and set up the window.
//        frame = new JFrame("Base de Dados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(2,1));

        //Add contents to the window.
        add(panConnect);
        add(panOperations);

        //Display the window.
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()) {
            case "sql-connect":
//                Inet4Address inet = new Inet4Address(txtAddress.getText(), Integer.parseInt(txtPort.getText()));
                String address = txtAddress.getText();
                Integer port = null;
                try {
                    port = Integer.parseInt(txtPort.getText());
                    if(port < 0 || port > 65535) {
                        JOptionPane.showMessageDialog(null, "Número de porta inválida1!");
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Número de porta inválida2!");
                    break;
                }
                if(sql.connect(address, port)) {
                    btnConnect.setText("Desconectar");
                    btnConnect.setActionCommand("sql-disconnect");
                    txtAddress.setEnabled(false);
                    txtAddress.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Falha na conexão!");
                }
                break;
            case "sql-disconnect":
                break;
            case "gui-exit":
                // Realizar SQL close
                dispose();
                break;
            default:
                System.err.println("ActionEvent desconhecido: " + actionEvent.toString());
                break;
        }
    }
}
