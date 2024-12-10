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
import utils.Sesion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

  private LoginView vista;

  public LoginController(LoginView vista) {
    this.vista = vista;

    vista.getBtnLogin().addActionListener(e -> autenticarUsuario());
    vista.getBtnRegistro().addActionListener(e -> abrirRegistro());
  }

  private void autenticarUsuario() {
    // Obtengo los datos que el usuario ingresó en la vista
    String email = vista.getEmail();
    String password = vista.getPassword();

    // Conexión a la DB
    try (Connection conn = Database.conectar()) {
      String sql = "SELECT id_usuario, nombre, apellido, email, id_tipo_usuario, password FROM usuarios WHERE email = ?";
      // Armo la consulta
      PreparedStatement consulta = conn.prepareStatement(sql);
      consulta.setString(1, email);

      // Ejecuto la consulta
      ResultSet resp = consulta.executeQuery();
      // Verifico la autenticación
      if (resp.next() && PasswordUtils.checkPassword(password, resp.getString("password"))) {
        // Significa que encontró un registro y la contraseña ingresada coincide con la de la DB
        
        // Guardo los datos de la sesión
        Sesion sesionActual = Sesion.getInstance();
        sesionActual.setIdUsuario(resp.getInt("id_usuario"));
        sesionActual.setNombre(resp.getString("nombre"));
        sesionActual.setApellido(resp.getString("apellido"));
        sesionActual.setEmail(resp.getString("email"));
        sesionActual.setIdTipoUsuario(resp.getInt("id_tipo_usuario"));
        
        JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso para usuario " + sesionActual.getNombre() + " " + sesionActual.getApellido());
        
        // Cierro vista de login y muestro vista del menú principal
        vista.dispose();
        new views.MenuPrincipalView();
        
      } else {
        JOptionPane.showMessageDialog(vista, "Credenciales incorrectas.");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void abrirRegistro() {
    vista.dispose();
    new RegistroController(new RegistroView());
  }
}
