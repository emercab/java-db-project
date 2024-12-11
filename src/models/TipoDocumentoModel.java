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
}
