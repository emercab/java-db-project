package models;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class AspiranteModel {

  // Método para insertar un nuevo aspirante en la base de datos
  public int guardarAspirante(int idUsuario, String documentoIdentidad, String nacionalidad, java.util.Date fechaNacimiento) throws SQLException {
    String sql = "INSERT INTO aspirantes (id_usuario, documento_identidad, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?, ?)";
    int idAspirante = -1;

    try (Connection conn = Database.conectar(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, idUsuario);
      ps.setString(2, documentoIdentidad);
      ps.setString(3, nacionalidad);
      ps.setDate(4, new Date(fechaNacimiento.getTime())); // Conversión de java.util.Date a java.sql.Date
      ps.executeUpdate();

      var generatedKeys = ps.getGeneratedKeys();
      if (generatedKeys.next()) {
        idAspirante = generatedKeys.getInt(1); // Obtener el ID generado
      }
    }
    return idAspirante;
  }

  // Método para actualizar el tipo de usuario en la tabla usuarios
  public void actualizarTipoUsuario(int idUsuario) throws SQLException {
    String sql = "UPDATE usuarios SET id_tipo_usuario = 2 WHERE id_usuario = ?";

    try (Connection conn = Database.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idUsuario);
      ps.executeUpdate();
    }
  }
}
