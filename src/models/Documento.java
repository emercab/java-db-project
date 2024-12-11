package models;

import java.util.Date;

public class Documento {

  private int id;
  private int idAspirante;
  private int idTipoDocumento;
  private String rutaDocumento;
  private Date fechaSubida;

  public Documento(int id, int idAspirante, int idTipoDocumento, String rutaDocumento, Date fechaSubida) {
    this.id = id;
    this.idAspirante = idAspirante;
    this.idTipoDocumento = idTipoDocumento;
    this.rutaDocumento = rutaDocumento;
    this.fechaSubida = fechaSubida;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public int getIdAspirante() {
    return idAspirante;
  }

  public int getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public String getRutaDocumento() {
    return rutaDocumento;
  }

  public Date getFechaSubida() {
    return fechaSubida;
  }
}
