package com.app;

public class Main {

    public static void main(String[] args) {
        SQL sql = new SQL();
        GUI gui = new GUI(sql);
        gui.display();
    }
}
