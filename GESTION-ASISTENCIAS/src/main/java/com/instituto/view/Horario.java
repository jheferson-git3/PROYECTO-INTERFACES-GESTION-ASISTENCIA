package com.instituto.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Horario extends JFrame {
    private JPanel panelMain;
    private JTable tablaHorario;
    private JButton btnCerrar;

    public Horario() {
        setTitle("Horario Semanal");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelMain = new JPanel(new BorderLayout(15, 15));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panelMain);

        // Título arriba
        JLabel lblTitulo = new JLabel("Horario Semanal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(30, 30, 60));
        panelMain.add(lblTitulo, BorderLayout.NORTH);

        // datos y columnas - solo un curso por día
        String[] columnas = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        Object[][] datos = {
                {"08:00 – 10:00", "Matematica", "Ingles", "Quimica", "Ciencias", "Fisica"},
        };

        // tabla y modelo
        tablaHorario = new JTable(new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // tabla no editable
            }
        });

        // ajustes visuales tabla
        tablaHorario.setRowHeight(40);
        tablaHorario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tablaHorario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tablaHorario.getTableHeader().setBackground(new Color(220, 230, 250));
        tablaHorario.setSelectionBackground(new Color(180, 200, 255));
        tablaHorario.setGridColor(new Color(200, 200, 220));
        tablaHorario.setFillsViewportHeight(true);

        // scroll
        JScrollPane scrollPane = new JScrollPane(tablaHorario);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        // panel boton cerrar
        JPanel panelBoton = new JPanel();
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnCerrar.setBackground(new Color(70, 130, 180));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());         //  cerrar Jframe


        panelBoton.add(btnCerrar);
        panelMain.add(panelBoton, BorderLayout.SOUTH);
    }
}
