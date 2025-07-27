package com.instituto.view;

import com.instituto.controller.Authcontroller;
import com.instituto.model.Rol;
import com.instituto.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class UserRegister extends JFrame {
    private JPanel panelMain;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JPasswordField txtPassword;
    private JComboBox<Rol> cbRol;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    private final Usuario usuarioActual;

    public UserRegister(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setTitle("Registro de Usuario");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelMain.setBackground(new Color(245, 248, 255));

        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelMain.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setOpaque(false);




        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtPassword = new JPasswordField();
        cbRol = new JComboBox<>(Rol.values());





        formPanel.add(new JLabel("Usuario:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Contraseña:"));
        formPanel.add(txtPassword);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellido);
        formPanel.add(new JLabel("Rol:"));
        formPanel.add(cbRol);

        panelMain.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setBackground(new Color(0, 140, 70));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(200, 30, 30));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        panelMain.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(panelMain);

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> {
            dispose();
            new AdminMenu(usuarioActual).setVisible(true);
        });
    }
    //TODO: metodo obt

    private void registrarUsuario() {






        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String contrasena = new String(txtPassword.getPassword()).trim();
        Rol rol = (Rol) cbRol.getSelectedItem();






        //TODO:                  dialog
        if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombreCompleto = nombre + " " + apellido;
        Usuario nuevoUsuario = new Usuario(id, nombreCompleto, contrasena, rol);

        //TODO: Auth BD     dialog

        if (Authcontroller.registrarUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this,
                    "Usuario registrado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AdminMenu(usuarioActual).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "El ID ya existe o ocurrió un error.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
