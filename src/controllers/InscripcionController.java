package controllers;

import models.AspiranteModel;
import models.InscripcionModel;
import utils.Sesion;
import views.InscripcionView;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import views.MenuPrincipalView;

public class InscripcionController {

  private InscripcionView vista;
  private AspiranteModel aspiranteModel;
  private InscripcionModel inscripcionModel;

  public InscripcionController(InscripcionView vista) {
    this.vista = vista;
    this.aspiranteModel = new AspiranteModel();
    this.inscripcionModel = new InscripcionModel();

    inicializarEventos();
    cargarProgramas();
  }

  // Configura los listeners de los botones
  private void inicializarEventos() {
    vista.getBtnGuardar().addActionListener(e -> guardarInscripcion());
    vista.getBtnCancelar().addActionListener(e -> cancelar());
    vista.getBtnSubirDocumentos().addActionListener(e -> subirDocumentos());
  }

  private void guardarInscripcion() {
    try {
      // Validar campos
      String documentoIdentidad = vista.getDocumentoIdentidad();
      String nacionalidad = vista.getNacionalidad();
      String fechaTexto = vista.getFechaNacimiento();
      int idPrograma = vista.getComboProgramas().getSelectedIndex() + 1; // ID programa (inicia desde 1)
      int idUsuario = Sesion.getInstance().getIdUsuario();

      if (documentoIdentidad.isEmpty() || nacionalidad.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Documento de identidad y nacionalidad son obligatorios.");
        return;
      }

      // Validar y convertir la fecha de nacimiento
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento = dateFormat.parse(fechaTexto);

      // Insertar el aspirante
      int idAspirante = aspiranteModel.guardarAspirante(idUsuario, documentoIdentidad, nacionalidad, fechaNacimiento);

      // Actualizar tipo de usuario
      aspiranteModel.actualizarTipoUsuario(idUsuario);
      Sesion.getInstance().setIdTipoUsuario(2); // Actualizar sesión

      // Crear la inscripción
      inscripcionModel.guardarInscripcion(idAspirante, idPrograma);

      JOptionPane.showMessageDialog(vista, "Inscripción guardada correctamente.");
      vista.limpiarFormulario();
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(vista, "Formato de fecha inválido. Use el formato yyyy-MM-dd.");
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(vista, "Error al guardar la inscripción: " + e.getMessage());
    }
  }

  private void cargarProgramas() {
    // Aquí simulamos una lista de programas; en producción se cargaría desde la base de datos
    String[] programas = {"Doctorado en Ciencias", "Maestría en Ingeniería", "Especialización en Finanzas"};
    vista.setProgramas(programas);
  }

  private void cancelar() {
    vista.dispose();
    new MenuPrincipalView();
  }

  private void subirDocumentos() {
    JOptionPane.showMessageDialog(vista, "Funcionalidad de carga de documentos pendiente de implementación.");
  }
}
