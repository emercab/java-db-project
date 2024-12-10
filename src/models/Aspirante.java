package models;

public class Aspirante {

  private int idAspirante;
  private int idUsuario;
  private String documentoIdentidad;
  private String nacionalidad;
  private String fechaNacimiento;

  public Aspirante(int idUsuario, String documentoIdentidad, String nacionalidad, String fechaNacimiento) {
    this.idUsuario = idUsuario;
    this.documentoIdentidad = documentoIdentidad;
    this.nacionalidad = nacionalidad;
    this.fechaNacimiento = fechaNacimiento;
  }

  // Getters y Setters
  public int getIdAspirante() {
    return idAspirante;
  }

  public void setIdAspirante(int idAspirante) {
    this.idAspirante = idAspirante;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public String getDocumentoIdentidad() {
    return documentoIdentidad;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }
}
