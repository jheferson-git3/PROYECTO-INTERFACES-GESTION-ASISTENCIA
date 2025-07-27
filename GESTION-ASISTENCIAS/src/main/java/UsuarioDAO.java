package com.instituto.dao;

import com.instituto.model.Rol;
import com.instituto.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String id, String contrasena) {
        String sql = "SELECT usuario, nombre, contrasena, rol FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = com.instituto.util.ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    Rol rol = Rol.valueOf(rs.getString("rol"));
                    return new Usuario(rs.getString("usuario"), nombre, contrasena, rol);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // metodo  obtener el siguiente ID
    public static String obtenerSiguienteId() {
        String sql = "SELECT MAX(CAST(usuario AS UNSIGNED)) AS max_id FROM usuarios";
        try (Connection conn = com.instituto.util.ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return String.valueOf(maxId + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "1";
    }
}
