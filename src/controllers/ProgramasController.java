package controllers;

import models.Programa;
import models.ProgramaModel;
import views.ProgramasView;
import views.MenuPrincipalView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ProgramasController {

  private ProgramasView vista;
  private ProgramaModel programaModel;
  private Integer idProgramaSeleccionado = null; // Guarda el ID si se está editando

  public ProgramasController(ProgramasView vista) {
    this.vista = vista;
    this.programaModel = new ProgramaModel();

    inicializarEventos();
    cargarProgramas();
  }

  private void inicializarEventos() {
    vista.getBtnNuevo().addActionListener(e -> prepararNuevoPrograma());
    vista.getBtnAgregar().addActionListener(e -> agregarPrograma());
    vista.getBtnActualizar().addActionListener(e -> actualizarPrograma());
    vista.getBtnEliminar().addActionListener(e -> eliminarPrograma());
    vista.getBtnBuscar().addActionListener(e -> buscarProgramas());
    vista.getBtnRecargar().addActionListener(e -> recargarProgramas());
    vista.getBtnCancelar().addActionListener(e -> cancelarProgramas());

    vista.getTablaProgramas().getSelectionModel().addListSelectionListener(e -> cargarProgramaSeleccionado());
  }

  // Cargar todos los programas en la tabla
  private void cargarProgramas() {
    try {
      List<Programa> programas = programaModel.obtenerProgramas();
      vista.cargarProgramasEnTabla(programas);
      vista.limpiarFormulario();
      idProgramaSeleccionado = null;
      gestionarBotonesIniciales();
    } catch (SQLException e) {
      mostrarError("Error al cargar programas: " + e.getMessage());
    }
  }

  // Preparar el formulario para un nuevo programa
  private void prepararNuevoPrograma() {
    vista.limpiarFormulario();
    idProgramaSeleccionado = null;
    gestionarBotonesNuevo();
  }

  // Agregar un nuevo programa
  private void agregarPrograma() {
    try {
      String nombre = vista.getNombrePrograma();
      String descripcion = vista.getDescripcionPrograma();
      int duracion = Integer.parseInt(vista.getDuracionPrograma());

      programaModel.agregarPrograma(nombre, descripcion, duracion);
      mostrarMensaje("Programa agregado correctamente.");
      cargarProgramas();
    } catch (NumberFormatException ex) {
      mostrarError("La duración debe ser un número válido.");
    } catch (SQLException ex) {
      mostrarError("Error al agregar programa: " + ex.getMessage());
    }
  }

  // Actualizar un programa existente
  private void actualizarPrograma() {
    if (idProgramaSeleccionado != null) {
      try {
        String nombre = vista.getNombrePrograma();
        String descripcion = vista.getDescripcionPrograma();
        int duracion = Integer.parseInt(vista.getDuracionPrograma());

        programaModel.actualizarPrograma(idProgramaSeleccionado, nombre, descripcion, duracion);
        mostrarMensaje("Programa actualizado correctamente.");
        cargarProgramas();
      } catch (NumberFormatException ex) {
        mostrarError("La duración debe ser un número válido.");
      } catch (SQLException ex) {
        mostrarError("Error al actualizar programa: " + ex.getMessage());
      }
    }
  }

  // Eliminar un programa
  private void eliminarPrograma() {
    if (idProgramaSeleccionado != null) {
      int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este programa?", "Confirmar", JOptionPane.YES_NO_OPTION);
      if (confirmacion == JOptionPane.YES_OPTION) {
        try {
          programaModel.eliminarPrograma(idProgramaSeleccionado);
          mostrarMensaje("Programa eliminado correctamente.");
          cargarProgramas();
        } catch (SQLException ex) {
          mostrarError("Error al eliminar programa: " + ex.getMessage());
        }
      }
    }
  }

  // Buscar programas
  private void buscarProgramas() {
    String buscarTexto = vista.getBuscarTexto();
    if (!buscarTexto.isEmpty()) {
      try {
        List<Programa> programas = programaModel.buscarProgramas(buscarTexto);
        vista.cargarProgramasEnTabla(programas);
      } catch (SQLException e) {
        mostrarError("Error al buscar programas: " + e.getMessage());
      }
    } else {
      cargarProgramas();
    }
  }

  // Recargar todos los programas
  private void recargarProgramas() {
    cargarProgramas();
  }

  // Cargar el programa seleccionado en el formulario
  private void cargarProgramaSeleccionado() {
    int fila = vista.getTablaProgramas().getSelectedRow();
    if (fila != -1) {
      idProgramaSeleccionado = (Integer) vista.getTablaProgramas().getValueAt(fila, 0);
      vista.cargarDatosEnFormulario(new Programa(
        idProgramaSeleccionado,
        (String) vista.getTablaProgramas().getValueAt(fila, 1),
        (String) vista.getTablaProgramas().getValueAt(fila, 2),
        (Integer) vista.getTablaProgramas().getValueAt(fila, 3)
      ));
      gestionarBotonesEdicion();
    }
  }
  
  private void cancelarProgramas() {
    vista.dispose(); // Cierra la ventana actual
    new MenuPrincipalView(); // Abre el Menu Principal
  }

  // Gestión de botones
  private void gestionarBotonesIniciales() {
    vista.getBtnAgregar().setEnabled(false);
    vista.getBtnActualizar().setEnabled(false);
    vista.getBtnEliminar().setEnabled(false);
  }

  private void gestionarBotonesNuevo() {
    vista.getBtnAgregar().setEnabled(true);
    vista.getBtnActualizar().setEnabled(false);
    vista.getBtnEliminar().setEnabled(false);
  }

  private void gestionarBotonesEdicion() {
    vista.getBtnAgregar().setEnabled(false);
    vista.getBtnActualizar().setEnabled(true);
    vista.getBtnEliminar().setEnabled(true);
  }

  private void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(vista, mensaje);
  }

  private void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
