package models;

import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoModel {

  // Guardar un documento en la base de datos
  public void guardarDocumento(int idAspirante, int idTipoDocumento, String rutaDocumento) throws SQLException {
    String sql = "INSERT INTO documentos (id_aspirante, id_tipo_documento, ruta_documento) VALUES (?, ?, ?)";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {
      consulta.setInt(1, idAspirante);
      consulta.setInt(2, idTipoDocumento);
      consulta.setString(3, rutaDocumento);
      consulta.executeUpdate();
    }
  }

  // Obtener documentos de un aspirante
  public List<Object[]> obtenerDocumentosPorAspirante(int idAspirante) throws SQLException {
    List<Object[]> documentos = new ArrayList<>();
    String sql = "SELECT t.descripcion, d.fecha_subida, d.ruta_documento "
      + "FROM documentos d "
      + "JOIN tipos_documento t ON d.id_tipo_documento = t.id "
      + "WHERE d.id_aspirante = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {
      consulta.setInt(1, idAspirante);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        documentos.add(new Object[]{
          rs.getString("descripcion"),
          rs.getTimestamp("fecha_subida"),
          rs.getString("ruta_documento")
        });
      }
    }
    return documentos;
  }

  // Verificar si existe un documento específico
  public boolean existeDocumento(int idAspirante, int idTipoDocumento) throws SQLException {
    String sql = "SELECT COUNT(*) AS total FROM documentos WHERE id_aspirante = ? AND id_tipo_documento = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {
      consulta.setInt(1, idAspirante);
      consulta.setInt(2, idTipoDocumento);
      ResultSet rs = consulta.executeQuery();

      return rs.next() && rs.getInt("total") > 0;
    }
  }

  // Eliminar un documento
  public void eliminarDocumento(String rutaDocumento) throws SQLException {
    String sql = "DELETE FROM documentos WHERE ruta_documento = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {
      consulta.setString(1, rutaDocumento);
      consulta.executeUpdate();
    }
  }
}
