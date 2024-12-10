package models;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InscripcionModel {

  // Método para insertar una nueva inscripción
  public void guardarInscripcion(int idAspirante, int idPrograma) throws SQLException {
    String sql = "INSERT INTO inscripciones (id_aspirante, id_programa, fecha_inscripcion, id_estado_inscripcion, documentos_completos, observaciones) "
      + "VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";

    try (Connection conn = Database.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idAspirante);
      ps.setInt(2, idPrograma);
      ps.setInt(3, 1); // Estado inicial: Pendiente
      ps.setBoolean(4, false); // Documentos incompletos por defecto
      ps.setString(5, ""); // Observaciones vacías por defecto
      ps.executeUpdate();
    }
  }
}
