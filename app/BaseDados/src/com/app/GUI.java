package com.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
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
    private JPanel panList;

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

        lblAddress = new JLabel("Usuário:");
        lblPort = new JLabel("Senha:");
        txtUser = new JTextField("8910441", 10);
        txtPassword = new JTextField("Gui@orcl!1029", 10);

        panConnect = new JPanel();
        panOperations = new JPanel();
        panList = new JPanel();

        // Adiciona os componentes no JPanel
        panConnect.add(lblAddress);
        panConnect.add(txtUser);
        panConnect.add(lblPort);
        panConnect.add(txtPassword);
        panConnect.add(btnConnect);
        panConnect.add(btnExit);

        // Segunda linha
        panOperations.add(lblModalidade);
        panOperations.add(btnInsert);
        panOperations.add(btnUpdate);
        panOperations.add(btnDelete);
        panOperations.add(btnSelect);

        tblData = new JTable();
        panList.add(new JScrollPane(tblData));

        createFrame();
    }

    private void createFrame() {
        //Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Add contents to the window.
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(panConnect, BorderLayout.NORTH);
        topPanel.add(panOperations, BorderLayout.SOUTH);
        add(topPanel,BorderLayout.PAGE_START);
        add(new JScrollPane(tblData),BorderLayout.CENTER);

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
                    btnInsert.setEnabled(true);
                    btnDelete.setEnabled(true);
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
                btnInsert.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "gui-exit":
                sql.disconnect();
                dispose();
                break;
            case "sql-insert":
                insertModalidade();
                break;
            case "sql-delete":
                deleteModalidade();
                break;
            case "sql-select":
                selectModalidade();
                break;
            default:
                System.err.println("ActionEvent desconhecido: " + actionEvent.toString());
                break;
        }
    }

    private void insertModalidade() {
        MaskFormatter formatterName = null;
        MaskFormatter formatterCategory = null;
        MaskFormatter formatterMax = null;
        try {
            formatterName = new MaskFormatter("**************************************************");
            formatterCategory = new MaskFormatter("****************************************");
            formatterMax = new MaskFormatter("##");

        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Get name
        JFormattedTextField jftf = new JFormattedTextField(formatterName);
        jftf.setColumns(25);
        JLabel jl = new JLabel("Nome: ");
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jftf);
        String name = "";
        while(name.length() == 0) {
            int x = JOptionPane.showConfirmDialog(null,
                    box,
                    "Inserir modalidade",
                    JOptionPane.CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(x != 0)
                return;
            name = jftf.getText().trim();
        }

        // Get category
        jl.setText("Categoria: ");
        jftf.setValue("");
        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterCategory));
        String category = "";
        while(category.length() == 0) {
            int x = JOptionPane.showConfirmDialog(null,
                    box,
                    "Inserir modalidade",
                    JOptionPane.CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(x != 0)
                return;
            category = jftf.getText().trim();
        }

        // Get sport name
        ArrayList<String> esportes = sql.selectColumn("nomeEsporte","esporte");
        String[] tables = new String[esportes.size()];
        tables = esportes.toArray(tables);
        String sport = (String) JOptionPane.showInputDialog(null,
                "Esporte:",
                "Inserir modalidade",
                JOptionPane.PLAIN_MESSAGE,
                null,
                tables,
                tables[0]);

        // Get maximum athletes
        jl.setText("Número máximo de atletas: ");
        jftf.setValue("");
        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterMax));
        jftf.setColumns(10);
        String nMax = "";
        while(nMax.length() == 0) {
            int x = JOptionPane.showConfirmDialog(null,
                    box,
                    "Inserir modalidade",
                    JOptionPane.CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(x != 0)
                return;
            nMax = jftf.getText().trim();
        }

        // Construct and execute SQL query
        String q = "INSERT INTO modalidade VALUES('" +
                name + "', '" +
                category + "', '" +
                sport + "', " +
                nMax + ")";
        if(!sql.query(q)) {
            JOptionPane.showMessageDialog(null,
                    "A inserção falhou!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: adicionar as outras colunas e fazer que um campo em banco desconsidere a condição da coluna em questão.
    private void deleteModalidade() {
        MaskFormatter formatterName = null;
        MaskFormatter formatterCategory = null;
        try {
            formatterName = new MaskFormatter("**************************************************");
            formatterCategory = new MaskFormatter("****************************************");

        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Get name
        JFormattedTextField jftf = new JFormattedTextField(formatterName);
        jftf.setColumns(25);
        JLabel jl = new JLabel("Nome: ");
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jftf);
        JOptionPane.showConfirmDialog(null,
                box,
                "Remover modalidade",
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        String name = jftf.getText().trim();
// Get category
        jl.setText("Categoria: ");
        jftf.setValue("");
        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterCategory));
        JOptionPane.showConfirmDialog(null,
                box,
                "Inserir modalidade",
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        String category = jftf.getText().trim();

        // Construct and execute SQL query
        String q = "DELETE FROM modalidade WHERE(nomeMod='" +
                name + "' AND catMod='" +
                category + "')";
        if(!sql.query(q)) {
            JOptionPane.showMessageDialog(null,
                    "A remoção falhou!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectModalidade() {
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
