package com.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener {
    private SQL sql;
    private JButton btnConnect;
    private JButton btnExit;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSelect;

    private JLabel lblModalidade;
    private JLabel lblAddress;
    private JLabel lblPort;
    private JTextField txtUser;
    private JTextField txtPassword;

    private JTable tblData;

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

        lblModalidade = new JLabel("Modalidade");

        btnInsert = new JButton("Inserir");
        btnInsert.setEnabled(true);
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
        txtPassword = new JTextField("Gui@orcl!1029", 10);

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

//        // Segunda linha
//        c.gridwidth = 4;
//        c.gridx = 0;
//        c.gridy = 1;
        panOperations.add(lblModalidade);
//        c.gridx++;
        panOperations.add(btnInsert);
//        c.gridx++;
        panOperations.add(btnUpdate);
//        c.gridx++;
        panOperations.add(btnDelete);
//        c.gridx++;
        panOperations.add(btnSelect);
//        c.gridx++;
        panOperations.setEnabled(false);

        tblData = new JTable();

        createFrame();
    }

    private void createFrame() {
        //Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        //Add contents to the window.
        // TODO: é preciso melhorar a disposição atual dos paineis. O método atual não trabalha bem com ajustes no tamanho da janela.
        add(panConnect,BorderLayout.NORTH);
        add(panOperations,BorderLayout.CENTER);
        add(new JScrollPane(tblData),BorderLayout.SOUTH);

        //Display the window.
        pack();
        setSize(600, getHeight());
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()) {
            case "sql-connect":
                String user = txtUser.getText();
                String password = txtPassword.getText();
                if(sql.connect(user, password)) {
                    btnConnect.setText("Desconectar");
                    btnConnect.setActionCommand("sql-disconnect");
                    txtUser.setEnabled(false);
                    txtPassword.setEnabled(false);
                    btnSelect.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Falha na conexão!");
                }
                break;
            case "sql-disconnect":
                sql.disconnect();
                btnConnect.setText("Conectar");
                btnConnect.setActionCommand("sql-connect");
                txtUser.setEnabled(true);
                txtPassword.setEnabled(true);
                btnSelect.setEnabled(false);
                break;
            case "gui-exit":
                sql.disconnect();
                dispose();
                break;
            case "sql-insert":
                insert();
                break;
            case "sql-select":
                select();
                break;
            default:
                System.err.println("ActionEvent desconhecido: " + actionEvent.toString());
                break;
        }
    }

    // TODO, function still incomplete
    private void insert() {
        ArrayList<String> tablesList = sql.listTables();
        String[] tables = new String[tablesList.size()];
        tables = tablesList.toArray(tables);
        String selectedTable = (String) JOptionPane.showInputDialog(null,
                "Escolha uma tabela para inserir",
                "Inserir",
                JOptionPane.PLAIN_MESSAGE,
                null,
                tables,
                tables[0]);

        // selectedTable will be null if the user clicks Cancel
        if(selectedTable != null) {
            ResultSet columns = sql.getColumns(selectedTable);
            try {
                ArrayList<String> values = new ArrayList<>();
                String query = "INSERT INTO " + selectedTable + " (";
                while(columns.next()) {
                    String input = (String) JOptionPane.showInputDialog(null,
                            columns.getString("COLUMN_NAME") + " (" + columns.getString("TYPE_NAME").toString() + ")",
                            "Inserir em: " + selectedTable,
                            JOptionPane.PLAIN_MESSAGE);
                    query += columns.getString("COLUMN_NAME") + ", ";
                    values.add(input);
                }
                query = query.substring(0, query.length() - 2); // Remove last comma
                query += ") VALUES";
                sql.insert(selectedTable, query, values);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void select() {
        ArrayList<String> tablesList = sql.listTables();
        String[] tables = new String[tablesList.size()];
        tables = tablesList.toArray(tables);
        String selectedTable = (String) JOptionPane.showInputDialog(null,
                "Escolha uma tabela para listar",
                "Listar",
                JOptionPane.PLAIN_MESSAGE,
                null,
                tables,
                tables[0]);

        // selectedTable will be null if the user clicks Cancel
        System.out.printf("SELECT * FROM %s.\n", selectedTable);
        if(selectedTable != null)
            list(selectedTable);
    }

    private void list(String table) {
        try {
            ResultSet rs = sql.selectTable(table);
            tblData.setModel(buildTableModel(sql.selectTable(table)));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
