package models;

import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaModel {

  // Método para obtener todos los programas
  public List<Programa> obtenerProgramas() throws SQLException {
    List<Programa> programas = new ArrayList<>();
    String sql = "SELECT id, nombre_programa, descripcion, duracion FROM programas";

    try (Connection conn = Database.conectar(); Statement consulta = conn.createStatement(); ResultSet rs = consulta.executeQuery(sql)) {

      while (rs.next()) {
        programas.add(new Programa(
          rs.getInt("id"),
          rs.getString("nombre_programa"),
          rs.getString("descripcion"),
          rs.getInt("duracion")
        ));
      }
    }
    return programas;
  }

  // Método para agregar un nuevo programa
  public void agregarPrograma(String nombre, String descripcion, int duracion) throws SQLException {
    String sql = "INSERT INTO programas (nombre_programa, descripcion, duracion) VALUES (?, ?, ?)";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setString(1, nombre);
      consulta.setString(2, descripcion);
      consulta.setInt(3, duracion);
      consulta.executeUpdate();
    }
  }

  // Método para actualizar un programa existente
  public void actualizarPrograma(int id, String nombre, String descripcion, int duracion) throws SQLException {
    String sql = "UPDATE programas SET nombre_programa = ?, descripcion = ?, duracion = ? WHERE id = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setString(1, nombre);
      consulta.setString(2, descripcion);
      consulta.setInt(3, duracion);
      consulta.setInt(4, id);
      consulta.executeUpdate();
    }
  }

  // Método para eliminar un programa por ID
  public void eliminarPrograma(int id) throws SQLException {
    String sql = "DELETE FROM programas WHERE id = ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setInt(1, id);
      consulta.executeUpdate();
    }
  }

  // Método para buscar programas por texto (nombre o descripción)
  public List<Programa> buscarProgramas(String texto) throws SQLException {
    List<Programa> programas = new ArrayList<>();
    String sql = "SELECT id, nombre_programa, descripcion, duracion "
      + "FROM programas WHERE nombre_programa LIKE ? OR descripcion LIKE ?";

    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setString(1, "%" + texto + "%");
      consulta.setString(2, "%" + texto + "%");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        programas.add(new Programa(
          rs.getInt("id"),
          rs.getString("nombre_programa"),
          rs.getString("descripcion"),
          rs.getInt("duracion")
        ));
      }
    }
    return programas;
  }

  // Método para obtener el ID de un programa por nombre
  public int obtenerIdPorNombre(String nombrePrograma) throws SQLException {
    String sql = "SELECT id FROM programas WHERE nombre_programa = ?";
    try (Connection conn = Database.conectar(); PreparedStatement consulta = conn.prepareStatement(sql)) {

      consulta.setString(1, nombrePrograma);
      ResultSet rs = consulta.executeQuery();
      if (rs.next()) {
        return rs.getInt("id");
      }
    }
    return -1; // Retorna -1 si no encuentra el programa
  }
}
