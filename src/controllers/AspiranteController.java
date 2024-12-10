package controllers;

import models.Aspirante;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AspiranteController {

  // Método para insertar un nuevo aspirante
  public int guardarAspirante(Aspirante aspirante) throws SQLException {
    String sql = "INSERT INTO aspirantes (id_usuario, documento_identidad, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?, ?)";
    int idAspirante = -1;

    try (Connection conn = Database.conectar(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, aspirante.getIdUsuario());
      ps.setString(2, aspirante.getDocumentoIdentidad());
      ps.setString(3, aspirante.getNacionalidad());
      ps.setDate(4, java.sql.Date.valueOf(aspirante.getFechaNacimiento()));
      ps.executeUpdate();

      var generatedKeys = ps.getGeneratedKeys();
      if (generatedKeys.next()) {
        idAspirante = generatedKeys.getInt(1);
      }
    }
    return idAspirante;
  }

  // Método para actualizar el tipo de usuario a 'aspirante'
  public void actualizarTipoUsuario(int idUsuario) throws SQLException {
    String sql = "UPDATE usuarios SET id_tipo_usuario = 2 WHERE id_usuario = ?";

    try (Connection conn = Database.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idUsuario);
      ps.executeUpdate();
    }
  }
}
