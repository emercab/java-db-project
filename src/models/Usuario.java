/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author FAMILIA CABRERA
 */
public class Usuario {

  private int idUsuario;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private String password;
  private int idTipoUsuario; // Relación con la tabla tipos_usuario

  // Constructor
  public Usuario(String nombre, String apellido, String email, String telefono, String password, int idTipoUsuario) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.telefono = telefono;
    this.password = password;
    this.idTipoUsuario = idTipoUsuario;
  }

  // Getters y Setters
  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getIdTipoUsuario() {
    return idTipoUsuario;
  }

  public void setIdTipoUsuario(int idTipoUsuario) {
    this.idTipoUsuario = idTipoUsuario;
  }

}
