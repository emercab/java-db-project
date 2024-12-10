/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author FAMILIA CABRERA
 */
import views.LoginView;
import views.RegistroView;
import config.Database;
import utils.PasswordUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistroController {

  private RegistroView vista;

  public RegistroController(RegistroView vista) {
    this.vista = vista;

    vista.getBtnRegistrar().addActionListener(e -> registrarUsuario());
    vista.getBtnCancelar().addActionListener(e -> abrirLogin());
  }

  private void registrarUsuario() {
    String nombre = vista.getNombre();
    String apellido = vista.getApellido();
    String email = vista.getEmail();
    String telefono = vista.getTelefono();
    String password = vista.getPassword();
    String confirmarPassword = vista.getConfirmarPassword();

    // Validaciones
    // Campos obligatorios
    if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(vista, "Nombre, apellido, email y contraseña son obligatorios.");
      return;
    }

    // Que ambas contraseñas coincidan
    if (!password.equals(confirmarPassword)) {
      JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
      return;
    }

    // Que no exista el correo en la DB
    if (existeEmail(email)) {
      JOptionPane.showMessageDialog(vista, "El correo ingresado ya está registrado.");
      return;
    }

    // Guardar en la DB
    try (Connection conn = Database.conectar()) {
      String sql = "INSERT INTO usuarios (nombre, apellido, email, telefono, password, id_tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, nombre);
      stmt.setString(2, apellido);
      stmt.setString(3, email);
      stmt.setString(4, telefono);
      stmt.setString(5, PasswordUtils.hashPassword(password));
      stmt.setInt(6, 1); // Tipo de usuario por defecto: 1 = Normal

      stmt.executeUpdate();

      JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente.");
      abrirLogin();
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(vista, "Error al registrar el usuario.");
    }
  }

  private void abrirLogin() {
    vista.dispose();
    new LoginController(new LoginView());
  }

  // Valida si un correo ya existe en la DB para evitar registros duplicados
  private boolean existeEmail(String email) {
    boolean respuesta = false;
    try (Connection conn = Database.conectar()) {
      String sql = "SELECT email FROM usuarios WHERE email = ?";
      // Armo la consulta
      PreparedStatement consulta = conn.prepareStatement(sql);
      consulta.setString(1, email);

      // Ejecuto la consulta
      ResultSet resp = consulta.executeQuery();
      
      respuesta = resp.next(); // Si no encuentra nada, el metodo next retorna false
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return respuesta;
  }
}
