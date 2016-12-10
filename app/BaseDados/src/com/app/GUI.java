package com.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
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

    private MaskFormatter formatterName;
    private MaskFormatter formatterCategory;
    private MaskFormatter formatterMax;

    public GUI(SQL sql) {
        super("Base de dados");
        this.sql = sql;
        try {
            formatterName = new MaskFormatter("**************************************************");
            formatterCategory = new MaskFormatter("****************************************");
            formatterMax = new MaskFormatter("##");

        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                    btnUpdate.setEnabled(true);
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
            case "sql-update":
                updateModalidade();
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
        // Get name
//        JFormattedTextField jftf = new JFormattedTextField(formatterName);
//        jftf.setColumns(25);
//        JLabel jl = new JLabel("Nome: ");
//        Box box = Box.createHorizontalBox();
//        box.add(jl);
//        box.add(jftf);
//        String name = "";
//        while(name.length() == 0) {
//            int reply = JOptionPane.showConfirmDialog(null,
//                    box,
//                    "Inserir Modalidade",
//                    JOptionPane.OK_CANCEL_OPTION);
//            if(reply == JOptionPane.CANCEL_OPTION) return;
//            name = jftf.getText().trim();
//        }
        String name = retrieveUserInput(formatterName, "Nome: ", "Inserir Modalidade");
        if(name == null)
            return;

        // Get category
//        jl.setText("Categoria: ");
//        jftf.setValue("");
//        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterCategory));
//        String category = "";
//        while(category.length() == 0) {
//            int reply = JOptionPane.showConfirmDialog(null,
//                    box,
//                    "Inserir modalidade",
//                    JOptionPane.CANCEL_OPTION,
//                    JOptionPane.PLAIN_MESSAGE);
//            if(reply == JOptionPane.CANCEL_OPTION) return;
//            category = jftf.getText().trim();
//        }
        String category = retrieveUserInput(formatterCategory, "Categoria", "Inserir Modalidade");
        if(category == null)
            return;

        // Get sport name
//        ArrayList<String> esportes = sql.selectColumn("nomeEsporte","esporte");
//        String[] tables = new String[esportes.size()];
//        tables = esportes.toArray(tables);
//        String sport = (String) JOptionPane.showInputDialog(null,
//                "Esporte:",
//                "Inserir modalidade",
//                JOptionPane.PLAIN_MESSAGE,
//                null,
//                tables,
//                tables[0]);
        String sport = retrieveSports("Inserir modalidade");

        // Get maximum athletes
//        jl.setText("Número máximo de atletas: ");
//        jftf.setValue("");
//        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterMax));
//        jftf.setColumns(10);
//        String nMax = "";
//        while(nMax.length() == 0) {
//            int reply = JOptionPane.showConfirmDialog(null,
//                    box,
//                    "Inserir modalidade",
//                    JOptionPane.CANCEL_OPTION,
//                    JOptionPane.PLAIN_MESSAGE);
//            if(reply == JOptionPane.CANCEL_OPTION) return;
//            nMax = jftf.getText().trim();
//        }
        String nMax = retrieveUserInput(formatterMax, "Número Máximo de atletas", "Inserir Modalidade");
        if(nMax == null)
            return;

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

    private void updateModalidade() {
        if(tblData.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null,
                    "Nenhuma linha selecionada!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = tblData.getSelectedRow();
        DefaultTableModel tableModel = (DefaultTableModel) tblData.getModel();
        Object dataObject = tableModel.getDataVector().elementAt(row);
        Vector data = (Vector) dataObject;
//        for(int i = 0; i < 3; i++) System.out.println(data.elementAt(i));
        String title = "Atualizar Modalidade";
        String nomeMod = (String) data.elementAt(0);
        String catMod = (String)data.elementAt(1);
        String nomeEsporte = (String) data.elementAt(2);
        BigDecimal maxAtletas = (BigDecimal) data.elementAt(3);
//        nomeMod = retrieveUserInput(formatterName, "Nome (" + nomeMod + "): ", title);
//        catMod = retrieveUserInput(formatterCategory, "Categoria (" + catMod + "): ", title);
        nomeEsporte = retrieveSports(title, "Esporte (" + nomeEsporte + "):");
        String maxAtletasStr = retrieveUserInput(formatterMax, "Número máximo de atletas ("+ maxAtletas.toEngineeringString() + "): ", title);
        if(maxAtletasStr == null) {
            return;
        }
        maxAtletas = new BigDecimal(maxAtletasStr);
        DecimalFormat df = new DecimalFormat("00");
        String q = "UPDATE modalidade SET " +
                "nomeEsporte='" + nomeEsporte + "', " +
                "maxAtletas=" + df.format(maxAtletas) + " " +
                "WHERE nomeMod='" + nomeMod + "' AND " +
                "catMod='" + catMod + "';";
        if(!sql.query(q)) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro!", title, JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: adicionar as outras colunas e fazer que um campo em banco desconsidere a condição da coluna em questão.
    private void deleteModalidade() {
        // Get name
        JFormattedTextField jftf = new JFormattedTextField(formatterName);
        jftf.setColumns(25);
        JLabel jl = new JLabel("Nome: ");
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jftf);
        int reply = JOptionPane.showConfirmDialog(null,
                box,
                "Remover modalidade",
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(reply == JOptionPane.CANCEL_OPTION) return;
        String name = jftf.getText().trim();
        // Get category
        jl.setText("Categoria: ");
        jftf.setValue("");
        jftf.setFormatterFactory(new DefaultFormatterFactory(formatterCategory));
        reply = JOptionPane.showConfirmDialog(null,
                box,
                "Remover modalidade",
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(reply == JOptionPane.CANCEL_OPTION) return;
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
        if(selectedTable != null)
            list(selectedTable);
    }

    private void list(String table) {
        try {
            ResultSet rs = sql.selectTable(table);
            tblData.setModel(buildTableModel(rs));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    private String retrieveUserInput(MaskFormatter maskFormatter, String label, String title) {
        JFormattedTextField jftf = new JFormattedTextField(maskFormatter);
        jftf.setColumns(25);
        JLabel jl = new JLabel(label);
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jftf);
        String str = "";
        while(str.length() == 0) {
            int reply = JOptionPane.showConfirmDialog(null,
                    box,
                    title,
                    JOptionPane.OK_CANCEL_OPTION);
            if(reply == JOptionPane.CANCEL_OPTION) return null;
            str = jftf.getText().trim();
        }
        return str;
    }

    private String retrieveSports(String title) {
        ArrayList<String> esportes = sql.selectColumn("nomeEsporte","esporte");
        String[] tables = new String[esportes.size()];
        tables = esportes.toArray(tables);
        return (String) JOptionPane.showInputDialog(null,
                "Esporte:",
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                tables,
                tables[0]);
    }

    private String retrieveSports(String title, String msg) {
        ArrayList<String> esportes = sql.selectColumn("nomeEsporte","esporte");
        String[] tables = new String[esportes.size()];
        tables = esportes.toArray(tables);
        return (String) JOptionPane.showInputDialog(null,
                msg,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                tables,
                tables[0]);
    }
}
