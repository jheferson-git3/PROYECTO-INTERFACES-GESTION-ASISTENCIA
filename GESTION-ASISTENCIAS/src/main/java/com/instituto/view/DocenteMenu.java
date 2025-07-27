package com.instituto.view;

import com.instituto.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DocenteMenu extends JFrame {

    private JPanel panelMain;
    private JButton btnRegistrarAsistencia;
    private JButton btnCerrarSesion;
    private JLabel lblBienvenida;
    private Usuario usuarioActual;

    public DocenteMenu(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Menu Docente");
        setSize(600, 400); // ventana más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBorder(new EmptyBorder(30, 50, 30, 50)); // padding más grande
        panelMain.setBackground(new Color(245, 248, 252)); // fondo claro azulado
        add(panelMain);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 25, 25, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 26)); // fuente más grande
        lblBienvenida.setForeground(new Color(60, 60, 60));
        gbc.gridy = 0;
        panelMain.add(lblBienvenida, gbc);

        btnRegistrarAsistencia = new JButton("REGISTRAR ASISTENCIA");
        btnRegistrarAsistencia.setIcon(createIcon("/register_icon.png", 32, 32)); // icono más grande
        styleModernButton(btnRegistrarAsistencia, new Color(0, 123, 255), Color.WHITE, 22, 60); // botón más grande
        gbc.gridy = 1;
        panelMain.add(btnRegistrarAsistencia, gbc);

        btnCerrarSesion = new JButton("CERRAR SESION");
        btnCerrarSesion.setIcon(createIcon("/logout_icon.png", 32, 32)); // icono más grande
        styleModernButton(btnCerrarSesion, Color.WHITE, new Color(220, 53, 69), 22, 60);
        btnCerrarSesion.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 3));
        gbc.gridy = 2;
        panelMain.add(btnCerrarSesion, gbc);
        //  abrir intrfz

        btnRegistrarAsistencia.addActionListener(e -> {
            new RegistroAsistencia(usuarioActual).setVisible(true);
        });




        //  abrir intrz

        btnCerrarSesion.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }





    private ImageIcon createIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    //  ajustar tam fuent y alt de b
    private void styleModernButton(JButton button, Color bgColor, Color fgColor, int fontSize, int height) {
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        Dimension size = new Dimension(300, height);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }
}
