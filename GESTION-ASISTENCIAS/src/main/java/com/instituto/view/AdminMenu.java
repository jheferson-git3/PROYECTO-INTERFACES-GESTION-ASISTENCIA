package com.instituto.view;

import com.instituto.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class AdminMenu extends JFrame {
    private JPanel panelMain;
    private JButton btnRegistrarUsuario;
    private JButton btnRegistrarAlumno;
    private JButton btnVerHorario;
    private JButton btnCerrarSesion;
    private JLabel lblBienvenida;
    private Usuario usuarioActual;

    public AdminMenu(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Menu Administrador");
        setSize(450, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // panel principal
        panelMain = new JPanel();
        panelMain.setBackground(new Color(245, 248, 255));
        panelMain.setLayout(new BorderLayout(20, 20));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setContentPane(panelMain);

        // label  bienvenida
        lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBienvenida.setForeground(new Color(30, 30, 30));
        panelMain.add(lblBienvenida, BorderLayout.NORTH);

        // panel  botones en columna
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new GridLayout(4, 1, 15, 15));  // 4 filas
        botonesPanel.setBackground(panelMain.getBackground());

        // botones
        btnRegistrarUsuario = crearBoton("REGISTRAR USUARIO", new Color(0, 123, 255));
        btnRegistrarAlumno = crearBoton("REGISTRAR ALUMNO", new Color(40, 167, 69)); // verde
        btnVerHorario = crearBoton("VER HORARIO", new Color(52, 152, 219));
        btnCerrarSesion = crearBoton("CERRAR SESION", new Color(231, 76, 60));

        // agregar al panel
        botonesPanel.add(btnRegistrarUsuario);
        botonesPanel.add(btnRegistrarAlumno);
        botonesPanel.add(btnVerHorario);
        botonesPanel.add(btnCerrarSesion);

        panelMain.add(botonesPanel, BorderLayout.CENTER);





        // acciones abrir interfaz
        btnRegistrarUsuario.addActionListener(e -> {
            new UserRegister(usuarioActual).setVisible(true);
            dispose();
        });





        btnRegistrarAlumno.addActionListener(e -> {
            new RegistrarEstudiante(usuarioActual).setVisible(true); //
            dispose();
        });

        btnVerHorario.addActionListener(e -> {
            new Horario().setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    // cust  botones
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(200, 40));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return boton;
    }
}
