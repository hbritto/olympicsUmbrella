package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
    private JTextField txtUser;
    private JTextField txtPassword;

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
        btnSelect.setEnabled(true);
        btnSelect.setActionCommand("sql-select");
        btnSelect.addActionListener(this);

        lblAddress = new JLabel("Usuário:");
        lblPort = new JLabel("Senha:");
        txtUser = new JTextField("8910441", 10);
        txtPassword = new JTextField("", 10);

        panConnect = new JPanel();
        panOperations = new JPanel();

        // Adiciona os componentes no JPanel
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0;
        c.gridy = 0;
        panConnect.add(lblAddress, c);
        c.gridx++;
        panConnect.add(txtUser, c);
        c.gridx++;
        panConnect.add(lblPort, c);
        c.gridx++;
        panConnect.add(txtPassword, c);
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
                String user = txtUser.getText();
                String password = txtPassword.getText();
                Integer port = null;
                if(sql.connect(user, password)) {
                    btnConnect.setText("Desconectar");
                    btnConnect.setActionCommand("sql-disconnect");
                    txtUser.setEnabled(false);
                    txtPassword.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Falha na conexão!");
                }
                ArrayList<String> results = sql.listTables();
                for(int i = 0; i < results.size(); i++)
                    System.out.println(results.get(i));
                break;
            case "sql-disconnect":
                sql.disconnect();
                btnConnect.setText("Conectar");
                btnConnect.setActionCommand("sql-connect");
                txtUser.setEnabled(true);
                txtPassword.setEnabled(true);
                break;
            case "gui-exit":
                // Realizar SQL close
                dispose();
                break;
            case "sql-select":
                select();
                break;

            default:
                System.err.println("ActionEvent desconhecido: " + actionEvent.toString());
                break;
        }
    }

    private void select() {
        ArrayList<String> tablesList = sql.listTables();
        String[] tables = new String[tablesList.size()];
        tables = tablesList.toArray(tables);
        String favoritePizza = (String) JOptionPane.showInputDialog(null,
                "Escolha uma tabela para listar",
                "Listar",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tables,
                tables[0]);

        // favoritePizza will be null if the user clicks Cancel
        System.out.printf("Select %s.\n", favoritePizza);
        System.exit(0);
    }

}
