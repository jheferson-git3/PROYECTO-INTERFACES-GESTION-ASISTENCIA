package com.instituto.view;

import com.instituto.dao.AlumnoDAO;
import com.instituto.model.Alumno;
import com.instituto.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;

public class RegistroAsistencia extends JFrame {
    private JPanel panelMain;
    private JTable tablaAsistencia;
    private JButton btnGuardar, btnResetear;
    private JComboBox<String> cbSemana;
    private JLabel lblResumen;

    private Usuario docenteActual;
    private List<Alumno> alumnos;
    private DefaultTableModel model;
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();

    // preference  guardar  ultima semana selecc
    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    private static final String KEY_SEMANA = "ultimaSemanaSeleccionada";



    //TODO: metod registroa

    public RegistroAsistencia(Usuario docente) {
        this.docenteActual = docente;
        setTitle("Registro de Asistencia - " + docente.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        cargarAlumnos();

        // leer ultima semana seleccionada
        int ultimaSemana = prefs.getInt(KEY_SEMANA, 1);
        if (ultimaSemana < 1 || ultimaSemana > 8) ultimaSemana = 1;
        cbSemana.setSelectedIndex(ultimaSemana - 1);

        // cargar asis para esa semana
        cargarAsistenciasSemana();
    }

    private void initComponents() {
        panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelMain.setBackground(new Color(245, 248, 255));
        setContentPane(panelMain);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(panelMain.getBackground());
        topPanel.add(new JLabel("Semana:"));
        cbSemana = new JComboBox<>();
        for (int i = 1; i <= 8; i++) cbSemana.addItem("Semana " + i);
        topPanel.add(cbSemana);

        // guardar seleccion y cargar asistencias  cambia la sem
        cbSemana.addActionListener(e -> {
            cargarAsistenciasSemana();
            prefs.putInt(KEY_SEMANA, cbSemana.getSelectedIndex() + 1);
        });

        panelMain.add(topPanel, BorderLayout.NORTH);

        String[] columnas = {"#", "Alumno", "Presente", "Totales", "%"};
        model = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int row, int col) {
                return col == 2;
            }

            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 2) ? Boolean.class : String.class;
            }
        };

        tablaAsistencia = new JTable(model);
        tablaAsistencia.setRowHeight(28);
        tablaAsistencia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaAsistencia.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaAsistencia.setGridColor(new Color(200, 200, 200));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tablaAsistencia.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablaAsistencia.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tablaAsistencia.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tablaAsistencia);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(panelMain.getBackground());

        lblResumen = new JLabel("RESUMEN: 0/0 presentes (0%)", SwingConstants.CENTER);
        lblResumen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResumen.setForeground(new Color(20, 20, 20));
        lblResumen.setPreferredSize(new Dimension(350, 30));
        bottomPanel.add(lblResumen);

        btnResetear = new JButton("RESETEAR ASISTENCIAS");
        btnResetear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnResetear.setBackground(new Color(200, 30, 30));
        btnResetear.setForeground(Color.WHITE);
        btnResetear.setFocusPainted(false);
        btnResetear.setPreferredSize(new Dimension(180, 40));
        bottomPanel.add(btnResetear);

        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(0, 140, 70));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(btnGuardar);

        panelMain.add(bottomPanel, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarAsistencia());
        btnResetear.addActionListener(e -> resetearAsistencias());
    }
    //TODO: metod cargar

    private void cargarAlumnos() {
        alumnos = alumnoDAO.obtenerAlumnosConTotales();
        model.setRowCount(0);

        for (int i = 0; i < alumnos.size(); i++) {
            Alumno alumno = alumnos.get(i);
            String totales = alumno.getAsistencias() + "/" + alumno.getTotal();
            model.addRow(new Object[]{
                    i + 1,
                    alumno.getNombre(),
                    true,
                    totales,
                    alumno.getPorcentaje() > 0 ? String.format("%.0f%%", alumno.getPorcentaje()) : "0%"
            });
        }
    }

    private void cargarAsistenciasSemana() {
        if (alumnos == null) return;
        int semana = cbSemana.getSelectedIndex() + 1;
        List<Boolean> asistenciasSemana = alumnoDAO.obtenerAsistenciasPorSemana(semana);

        for (int i = 0; i < alumnos.size(); i++) {
            boolean asistio = (i < asistenciasSemana.size()) ? asistenciasSemana.get(i) : true;
            model.setValueAt(asistio, i, 2);
        }

        actualizarResumen();
    }
    //TODO: metod registr guardr------

    private void guardarAsistencia() {
        int semana = cbSemana.getSelectedIndex() + 1;

        for (int i = 0; i < alumnos.size(); i++) {
            boolean presente = (boolean) model.getValueAt(i, 2);
            Alumno alumno = alumnos.get(i);

            try {
                alumnoDAO.guardarAsistencia(alumno.getId(), semana, presente);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar asistencia del alumno " + alumno.getNombre() + "\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        cargarAlumnos();
        actualizarResumen();


        int nextSemana = semana + 1;
        if (nextSemana > 8) nextSemana = 8;
        cbSemana.setSelectedIndex(nextSemana - 1);

        // Guardar  semana actual en preferencs
        prefs.putInt(KEY_SEMANA, nextSemana);

        cargarAsistenciasSemana();

        JOptionPane.showMessageDialog(this, "Asistencia guardada correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
    }
    //TODO: reset

    private void resetearAsistencias() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea resetear todas las asistencias?\nEsto pondrá totales y asistencias a 0 y borrará los registros.",
                "Confirmar reset",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                alumnoDAO.resetearTodasAsistencias();

                // refrescar alumnos y tabla
                cargarAlumnos();

                // seleccionar semana 1 y cargar asistencias
                cbSemana.setSelectedIndex(0);

                // guardar en preferencias que la semana es 1
                prefs.putInt(KEY_SEMANA, 1);

                cargarAsistenciasSemana();

                actualizarResumen();
                JOptionPane.showMessageDialog(this, "Asistencias reseteadas correctamente.", "Reset", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al resetear asistencias:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //TODO: mtdo actualizr
    private void actualizarResumen() {
        int presentes = 0;
        int total = model.getRowCount();

        for (int i = 0; i < total; i++) {
            if ((Boolean) model.getValueAt(i, 2)) presentes++;
        }

        int porcentaje = total == 0 ? 0 : (presentes * 100) / total;
        lblResumen.setText("RESUMEN: " + presentes + "/" + total + " presentes (" + porcentaje + "%)");
    }





}
