package models;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

public class AspiranteModel {

  // Método para insertar un nuevo aspirante en la base de datos
  public int guardarAspirante(int idUsuario, String documentoIdentidad, String nacionalidad, java.util.Date fechaNacimiento) throws SQLException {
    String sql = "INSERT INTO aspirantes (id_usuario, documento_identidad, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?, ?)";
    int idAspirante = -1;

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

      consulta.setInt(1, idUsuario);
      consulta.setString(2, documentoIdentidad);
      consulta.setString(3, nacionalidad);
      consulta.setDate(4, new Date(fechaNacimiento.getTime())); // Conversión de java.util.Date a java.sql.Date
      consulta.executeUpdate();

      var generatedKeys = consulta.getGeneratedKeys();
      if (generatedKeys.next()) {
        idAspirante = generatedKeys.getInt(1); // Obtener el ID generado
      }
    }
    return idAspirante;
  }

  // Método para actualizar el tipo de usuario en la tabla usuarios
  public void actualizarTipoUsuario(int idUsuario) throws SQLException {
    String sql = "UPDATE usuarios SET id_tipo_usuario = 2 WHERE id_usuario = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setInt(1, idUsuario);
      consulta.executeUpdate();
    }
  }
  
  // Método para obtener el id del aspirante a partir del id de usuario
  public int obtenerIdAspirante(int idUsuario) throws SQLException {
    String sql = "SELECT id_aspirante FROM aspirantes WHERE id_usuario = ?";
    int idAspirante = 0;
    
    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {
      consulta.setInt(1, idUsuario);
      ResultSet rs = consulta.executeQuery();
      
      if (rs.next()) {
        idAspirante = rs.getInt("id_aspirante");
      }
    }
    
    return idAspirante;
  }
}
