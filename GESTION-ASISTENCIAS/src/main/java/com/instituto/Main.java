package com.instituto;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.instituto.view.LoginForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            FlatIntelliJLaf.setup(); // Cambia a FlatDarkLaf si prefieres oscuro
        } catch (Exception ex) {
            System.err.println("No se pudo aplicar FlatLaf.");
        }

        // ver
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
