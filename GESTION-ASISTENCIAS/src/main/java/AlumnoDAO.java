package com.instituto.dao;

import com.instituto.model.Alumno;
import com.instituto.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlumnoDAO {

    public List<Alumno> obtenerAlumnosConTotales() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, total_clases, asistencias FROM alumnos";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setTotal(rs.getInt("total_clases"));
                alumno.setAsistencias(rs.getInt("asistencias"));
                lista.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void guardarAsistencia(int alumnoId, int semana, boolean presente) throws SQLException {
        String sqlSelect = "SELECT id, presente FROM asistencias WHERE alumno_id = ? AND semana = ?";
        String sqlUpdate = "UPDATE asistencias SET presente = ? WHERE id = ?";
        String sqlInsert = "INSERT INTO asistencias (alumno_id, semana, presente) VALUES (?, ?, ?)";
        String sqlUpdateAlumno = "UPDATE alumnos SET total_clases = ?, asistencias = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, alumnoId);
                psSelect.setInt(2, semana);
                ResultSet rs = psSelect.executeQuery();

                boolean existe = rs.next();
                boolean antesPresente = false;
                int idAsistencia = 0;
                if (existe) {
                    antesPresente = rs.getBoolean("presente");
                    idAsistencia = rs.getInt("id");
                }

                if (existe) {
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                        psUpdate.setBoolean(1, presente);
                        psUpdate.setInt(2, idAsistencia);
                        psUpdate.executeUpdate();
                    }
                } else {
                    try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                        psInsert.setInt(1, alumnoId);
                        psInsert.setInt(2, semana);
                        psInsert.setBoolean(3, presente);
                        psInsert.executeUpdate();
                    }
                }

                // calcular acumulados actuales
                String sqlCountTotal = "SELECT COUNT(*) FROM asistencias WHERE alumno_id = ?";
                String sqlCountPresentes = "SELECT COUNT(*) FROM asistencias WHERE alumno_id = ? AND presente = true";

                int totalClases = 0;
                int asistencias = 0;

                try (PreparedStatement psCountTotal = conn.prepareStatement(sqlCountTotal)) {
                    psCountTotal.setInt(1, alumnoId);
                    ResultSet rsTotal = psCountTotal.executeQuery();
                    if (rsTotal.next()) totalClases = rsTotal.getInt(1);
                }

                try (PreparedStatement psCountPresentes = conn.prepareStatement(sqlCountPresentes)) {
                    psCountPresentes.setInt(1, alumnoId);
                    ResultSet rsAsist = psCountPresentes.executeQuery();
                    if (rsAsist.next()) asistencias = rsAsist.getInt(1);
                }

                // actualizar acumulados en alumnos
                try (PreparedStatement psUpdateAlumno = conn.prepareStatement(sqlUpdateAlumno)) {
                    psUpdateAlumno.setInt(1, totalClases);
                    psUpdateAlumno.setInt(2, asistencias);
                    psUpdateAlumno.setInt(3, alumnoId);
                    psUpdateAlumno.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Boolean> obtenerAsistenciasPorSemana(int semana) {
        List<Boolean> asistencias = new ArrayList<>();
        String sql = "SELECT alumno_id, presente FROM asistencias WHERE semana = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, semana);
            ResultSet rs = ps.executeQuery();

            Map<Integer, Boolean> mapAsistencias = new HashMap<>();
            while (rs.next()) {
                mapAsistencias.put(rs.getInt("alumno_id"), rs.getBoolean("presente"));
            }

            List<Alumno> alumnos = obtenerAlumnosConTotales();
            for (Alumno alumno : alumnos) {
                asistencias.add(mapAsistencias.getOrDefault(alumno.getId(), true)); // Por defecto true
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asistencias;
    }

    public void resetearTodasAsistencias() throws SQLException {
        String sqlDeleteAsistencias = "DELETE FROM asistencias";
        String sqlResetAlumnos = "UPDATE alumnos SET total_clases = 0, asistencias = 0";

        try (Connection conn = ConexionDB.getConnection()) {
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDeleteAsistencias)) {
                psDelete.executeUpdate();
            }
            try (PreparedStatement psReset = conn.prepareStatement(sqlResetAlumnos)) {
                psReset.executeUpdate();
            }
        }
    }
    public void insertarAlumno(String nombreCompleto) throws SQLException {
        String sql = "INSERT INTO alumnos (nombre, total_clases, asistencias) VALUES (?, 0, 0)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreCompleto);
            ps.executeUpdate();
        }
    }








    // metodo  obtener la sem max registrada
    public int obtenerSemanaMaxima() {
        int maxSemana = 0;
        String sql = "SELECT MAX(semana) FROM asistencias";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) maxSemana = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxSemana;
    }
}
