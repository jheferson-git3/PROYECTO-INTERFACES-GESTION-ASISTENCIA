package com.instituto.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AsistenciaDAO {
    private final Connection conn;

    public AsistenciaDAO(Connection conn) {
        this.conn = conn;
    }

    // obtener todas  asistencias  un alumno en forma de mapa <semana, presente>
    public Map<Integer, Boolean> obtenerAsistenciaAlumno(int alumnoId) throws SQLException {
        String sql = "SELECT WEEK(fecha, 1) AS semana, presente FROM asistencias WHERE alumno_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, alumnoId);
        ResultSet rs = ps.executeQuery();

        Map<Integer, Boolean> mapa = new HashMap<>();
        while (rs.next()) {
            int semana = rs.getInt("semana");
            boolean presente = rs.getBoolean("presente");
            mapa.put(semana, presente);
        }
        rs.close();
        ps.close();
        return mapa;
    }

    // inserta o actualiza asistencia para un alumno y fecha (solo inserta, si quieres evita duplicados con UNIQUE KEY)
    public void guardarAsistencia(int alumnoId, LocalDate fecha, boolean presente) throws SQLException {
        String sql = "INSERT INTO asistencias (alumno_id, fecha, presente) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE presente = VALUES(presente)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, alumnoId);
        ps.setDate(2, Date.valueOf(fecha));
        ps.setBoolean(3, presente);
        ps.executeUpdate();
        ps.close();
    }
}
