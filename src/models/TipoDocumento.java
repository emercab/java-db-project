package models;

public class TipoDocumento {

  private int id;
  private String descripcion;

  public TipoDocumento(int id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
