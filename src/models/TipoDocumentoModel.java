package models;

import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoModel {

  // Obtener todos los tipos de documentos
  public List<TipoDocumento> obtenerTiposDocumentos() throws SQLException {
    List<TipoDocumento> tipos = new ArrayList<>();
    String sql = "SELECT id, descripcion FROM tipos_documento";

    try (Connection conn = Database.conectar(); Statement consulta = conn.createStatement(); ResultSet rs = consulta.executeQuery(sql)) {
      while (rs.next()) {
        tipos.add(new TipoDocumento(
          rs.getInt("id"),
          rs.getString("descripcion")
        ));
      }
    }
    return tipos;
  }
  
  // Obtener el ID de un tipo de documento por su descripcion
  public int obtenerIdPorDescripcionDocumento(String DescripcionDocumento) throws SQLException {
    String sql = "SELECT id FROM tipos_documento WHERE descripcion = ?";
    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setString(1, DescripcionDocumento);
      ResultSet rs = consulta.executeQuery();
      if (rs.next()) {
        return rs.getInt("id");
      }
    }
    return 0; // Retorna 0 si no encuentra el nombre del tipo de documento
  }
}
