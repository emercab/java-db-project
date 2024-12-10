/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author FAMILIA CABRERA
 */
public class Sesion {

  private static Sesion instancia;

  private int idUsuario;
  private String nombre;
  private String apellido;
  private String email;
  private int idTipoUsuario;

  // Constructor privado para evitar que otras clases puedan instanciarlo, así solo habrá una instancia en toda la app
  private Sesion() {
  }

  // Retorna la instancia unica de la sesión clase
  public static Sesion getInstance() {
    if (instancia == null) {
      instancia = new Sesion();
    }
    return instancia;
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

  public int getIdTipoUsuario() {
    return idTipoUsuario;
  }

  public void setIdTipoUsuario(int idTipoUsuario) {
    this.idTipoUsuario = idTipoUsuario;
  }

  // Métodos
  // Cerrar sesión
  public void cerrarSesion() {
    this.idUsuario = 0;
    this.nombre = null;
    this.apellido = null;
    this.email = null;
    this.idTipoUsuario = 0;
  }

  // Verificar si el usuario está autenticado
  public boolean usuarioAutenticado() {
    boolean respuesta = (idUsuario > 0) && (nombre != null) && (apellido != null) && (email != null) && (idTipoUsuario > 0);
    return respuesta;
  }

  // Determinar el tipo de usuario
  // Determina si es usuario normal
  public boolean esUsuarioNormal() {
    return this.idTipoUsuario == 1;
  }

  // Determina si es aspirante
  public boolean esAspirante() {
    return this.idTipoUsuario == 2;
  }

  // Determina si es admitido
  public boolean esAdmitido() {
    return this.idTipoUsuario == 3;
  }

  // Determina si es estudiante matriculado
  public boolean esEstudiante() {
    return this.idTipoUsuario == 4;
  }

  // Determina si es evaluador
  public boolean esEvaluador() {
    return this.idTipoUsuario == 5;
  }

  // Determina si es auxiliar de registro
  public boolean esAuxiliarRegistro() {
    return this.idTipoUsuario == 6;
  }

  // Determina si es coordinador
  public boolean esCoordinador() {
    return this.idTipoUsuario == 7;
  }

  // Determina si es administrador
  public boolean esAdministrador() {
    return this.idTipoUsuario == 8;
  }

}
