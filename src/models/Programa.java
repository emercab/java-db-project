/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author FAMILIA CABRERA
 */
public class Programa {

  private int id;
  private String nombrePrograma;
  private String descripcion;
  private int duracion;

  public Programa(int id, String nombrePrograma, String descripcion, int duracion) {
    this.id = id;
    this.nombrePrograma = nombrePrograma;
    this.descripcion = descripcion;
    this.duracion = duracion;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombrePrograma() {
    return nombrePrograma;
  }

  public void setNombrePrograma(String nombrePrograma) {
    this.nombrePrograma = nombrePrograma;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getDuracion() {
    return duracion;
  }

  public void setDuracion(int duracion) {
    this.duracion = duracion;
  }

}
