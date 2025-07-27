package com.instituto.view;

import com.instituto.controller.Authcontroller;
import com.instituto.model.Usuario;
import com.instituto.model.Rol;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginForm extends JFrame {
    private JPanel panelMain;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    public LoginForm() {
        setTitle("Inicio de Sesion");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // panel principal
        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panelMain);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Label Usuario
        gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        panelMain.add(lblUsuario, gbc);

        // Campo Usuario con ícono
        gbc.gridy = 1;
        JPanel userPanel = new JPanel(new BorderLayout(5, 0));
        userPanel.setOpaque(false);

        JLabel userIcon = new JLabel(createScaledIcon("/user.png", 24, 24));
        userPanel.add(userIcon, BorderLayout.WEST);

        // TODO: campotexto

        txtUsuario = new JTextField(15);


        txtUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        userPanel.add(txtUsuario, BorderLayout.CENTER);

        panelMain.add(userPanel, gbc);

        //  Label Contraseña
        gbc.gridy = 2;
        JLabel lblPass = new JLabel("Contraseña:");
        panelMain.add(lblPass, gbc);

        // Campo Contraseña con ícono
        gbc.gridy = 3;
        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        passPanel.setOpaque(false);

        JLabel lockIcon = new JLabel(createScaledIcon("/lock.png", 24, 24));
        passPanel.add(lockIcon, BorderLayout.WEST);

        // TODO: campo pass

        txtContrasena = new JPasswordField(15);


        txtContrasena.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passPanel.add(txtContrasena, BorderLayout.CENTER);

        panelMain.add(passPanel, gbc);

        //  Bot ingresar
        gbc.gridy = 4;
        btnIngresar = new JButton("Ingresar");
        panelMain.add(btnIngresar, gbc);

        //  label de mensaje
        gbc.gridy = 5;
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        panelMain.add(lblMensaje, gbc);

        // accion del boton
        btnIngresar.addActionListener(e -> {
            String id = txtUsuario.getText().trim();
            String password = new String(txtContrasena.getPassword()).trim();
        //TODO: authcontroll           autenticr       verificar si es correcto
            Usuario usuario = Authcontroller.autenticar(id, password);

            if (usuario != null) {
                dispose(); // cerrar Login


                //TODO:  admin menu
                if (usuario.getRol() == Rol.ADMIN) {
                    new AdminMenu(usuario).setVisible(true);
                    //TODO: docente menu
                } else if (usuario.getRol() == Rol.DOCENTE) {
                    new DocenteMenu(usuario).setVisible(true);
                }
            } else {
                lblMensaje.setText("Usuario o contraseña incorrectos");
            }
        });
    }

    //
    private ImageIcon createScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
