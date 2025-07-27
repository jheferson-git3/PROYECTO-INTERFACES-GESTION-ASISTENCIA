package com.instituto.view;

import com.instituto.controller.Authcontroller;
import com.instituto.model.Rol;
import com.instituto.model.Usuario;
import com.instituto.dao.UsuarioDAO;
import com.instituto.dao.AlumnoDAO;

import javax.swing.*;
import java.awt.*;

public class RegistrarEstudiante extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    private Usuario usuarioActual;

    public RegistrarEstudiante(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setTitle("Registrar Alumno");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // TODO: metodo regist estudiante text
        btnRegistrar.addActionListener(e -> {



            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();




            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y apellido son obligatorios.");
                return;
            }

            String nombreCompleto = nombre + " " + apellido;

            // obt sgt ID desde UsuarioDAO
            String nuevoId = UsuarioDAO.obtenerSiguienteId();

            Usuario nuevoAlumno = new Usuario(nuevoId, nombreCompleto, "", Rol.ALUMNO);

            if (Authcontroller.registrarUsuario(nuevoAlumno)) {
                // TODO: Insertar en tabla alumnos BSDTS
                try {
                    AlumnoDAO alumnoDAO = new AlumnoDAO();
                    alumnoDAO.insertarAlumno(nombreCompleto);
                } catch (Exception ex) {


                    JOptionPane.showMessageDialog(this,
                            "Usuario registrado, pero error al crear registro en alumnos:\n" + ex.getMessage(),
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }


                JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
                new AdminMenu(usuarioActual).setVisible(true);
                dispose();


            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar alumno.");
            }
        });

        // accion  boton cancelar
        btnCancelar.addActionListener(e -> {
            new AdminMenu(usuarioActual).setVisible(true);
            dispose();
        });
    }
}
