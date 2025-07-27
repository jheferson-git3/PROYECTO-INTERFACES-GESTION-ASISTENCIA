package com.instituto.controller;
// TODO: autenti y gest de usur en BD
import com.instituto.model.Usuario;
import com.instituto.model.Rol;
import com.instituto.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Authcontroller {
    // TODO: selct T usuars      ID PASS  int verfsi existn usuario en la base de datos con ese id contraseña.
    public static Usuario autenticar(String id, String contrasena) {
        // TODO: dev usuario -                                              vald contrasñ
        String sql = "SELECT usuario, nombre, contrasena, rol FROM usuarios WHERE usuario=? AND contrasena=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            pst.setString(2, contrasena);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String usuario = rs.getString("usuario");
                String nombre = rs.getString("nombre");
                String pass = rs.getString("contrasena");
                Rol rol = Rol.valueOf(rs.getString("rol"));
                return new Usuario(usuario, nombre, pass, rol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //TODO: reg user BD
    public static boolean registrarUsuario(Usuario nuevo) {
        String sql = "INSERT INTO usuarios (usuario, nombre, contrasena, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nuevo.getId());
            pst.setString(2, nuevo.getNombre());
            pst.setString(3, nuevo.getContrasena());
            pst.setString(4, nuevo.getRol().name());
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // TODO: mtdo obtnr usuario BD
    public static List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT usuario, nombre, contrasena, rol FROM usuarios";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String nombre = rs.getString("nombre");
                String pass = rs.getString("contrasena");
                Rol rol = Rol.valueOf(rs.getString("rol"));
                lista.add(new Usuario(usuario, nombre, pass, rol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
