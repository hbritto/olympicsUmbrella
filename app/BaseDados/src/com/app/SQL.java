package com.app;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class SQL {

    private Connection conn;
    private String user;

    public SQL() {
        conn = null;
    }


    public boolean connect(String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl",user,password);
            this.user = user;
            return !conn.isClosed();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean query(String q) {
        if(conn != null) {
            try {
                Statement statement = conn.createStatement();
                // insert the data
                System.out.println(q);
                statement.executeUpdate(q);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public ArrayList<String> listTables() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            DatabaseMetaData md = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet rs = md.getTables(null, user, "%", types);
            while (rs.next()) {
                results.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ResultSet selectTable(String table) {
        PreparedStatement statement =
                null;
            try {
            statement = conn.prepareStatement("SELECT * FROM " + table);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getColumns(String table) {
        if(conn != null) {
            try {
                DatabaseMetaData meta = conn.getMetaData();
                return meta.getColumns(null, user, table,  "%");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getColumnNames(String table) {
        if(conn != null) {
            ArrayList<String> results = new ArrayList<String>();

            try {
                ResultSet rs = getColumns(table);
                while (rs.next()) {
                    results.add(rs.getString(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return results;
        }
        return null;
    }

    public ArrayList<String> selectColumn(String column, String table) {
        ArrayList<String> results = new ArrayList<String>();
        if(conn != null) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT " + column + " FROM " + table);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    results.add(rs.getString(1));
                }
                return results;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    public void report() throws IOException {

        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement("Select Part.nome, count(*) from Participante Part join Atleta A on Part.passaporte = A.passaporte join Integra I on A.passaporte = I.idAtleta join Participa P on I.idEquipe = P.idEquipe group by Part.nome having count(*) >= 3");

            rs = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;


        // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        String title = "Relatório - Atletas que já participaram de três ou mais jogos";
        float fontSize = 16;
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        int marginTop = 30; // Or whatever margin you want.

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
        contentStream.showText(title);
        contentStream.endText();

        int i = 0;
        fontSize = 12;
        contentStream.setFont(font, 12);
        String text = "Nome do atleta - Nº de jogos";
        titleWidth = font.getStringWidth(text) / 1000 * fontSize;
        titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        contentStream.beginText();
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight - 100 - (i*20));
        contentStream.showText(text);
        contentStream.endText();

        i++;
        try {
            while(rs.next()) {
                text = rs.getString(1) + " - " + rs.getString(2);
                titleWidth = font.getStringWidth(text) / 1000 * fontSize;
                titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
                contentStream.beginText();
                contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight - 100 - (i*20));
                contentStream.showText(text);
                contentStream.endText();
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        contentStream.endText();
        contentStream.close();

        // Save the results and ensure that the document is properly closed:
        document.save("Relatório.pdf");
        document.close();
    }
}
