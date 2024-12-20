package models;

public class Inscripcion {

  private int id;
  private int idPrograma;
  private String fechaInscripcion;
  private int idEstadoInscripcion;
  private boolean documentosCompletos;
  private String observaciones;

  public Inscripcion(int id, int idPrograma, String fechaInscripcion) {
    this.id = id;
    this.idPrograma = idPrograma;
    this.fechaInscripcion = fechaInscripcion;
    this.idEstadoInscripcion = 1; // Estado inicial: Pendiente
    this.documentosCompletos = false;
    this.observaciones = "";
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public int getIdPrograma() {
    return idPrograma;
  }

  public String getFechaInscripcion() {
    return fechaInscripcion;
  }

  public int getIdEstadoInscripcion() {
    return idEstadoInscripcion;
  }

  public boolean isDocumentosCompletos() {
    return documentosCompletos;
  }

  public String getObservaciones() {
    return observaciones;
  }
}
