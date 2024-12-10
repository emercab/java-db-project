package controllers;

import models.AspiranteModel;
import models.InscripcionModel;
import views.InscripcionView;
import views.MenuPrincipalView;
import utils.Sesion;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InscripcionController {

  private InscripcionView vista;
  private AspiranteModel aspiranteModel;
  private InscripcionModel inscripcionModel;

  public InscripcionController(InscripcionView vista) {
    this.vista = vista;
    this.aspiranteModel = new AspiranteModel();
    this.inscripcionModel = new InscripcionModel();

    inicializarEventos();
  }

  private void inicializarEventos() {
    vista.getBtnGuardar().addActionListener(e -> guardarInscripcion());
    vista.getBtnCancelar().addActionListener(e -> cancelar());
  }

  private void guardarInscripcion() {
    try {
      // 1. Validar campos del formulario
      String documentoIdentidad = vista.getDocumentoIdentidad();
      String nacionalidad = vista.getNacionalidad();
      String fechaTexto = null;
      try {
        fechaTexto = vista.getFechaNacimiento();
      } catch (ParseException ex) {
        Logger.getLogger(InscripcionController.class.getName()).log(Level.SEVERE, null, ex);
      }
      int idPrograma = vista.getComboProgramas().getSelectedIndex() + 1; // ID del programa seleccionado
      int idUsuario = Sesion.getInstance().getIdUsuario();

      if (documentoIdentidad.isEmpty() || nacionalidad.isEmpty() || fechaTexto.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
        return;
      }

      // 2. Convertir fecha
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento;
      try {
        fechaNacimiento = dateFormat.parse(fechaTexto);
      } catch (ParseException e) {
        JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto. Use yyyy-MM-dd.");
        return;
      }

      // 3. Insertar aspirante y actualizar usuario
      int idAspirante = aspiranteModel.guardarAspirante(idUsuario, documentoIdentidad, nacionalidad, fechaNacimiento);
      aspiranteModel.actualizarTipoUsuario(idUsuario);
      Sesion.getInstance().setIdTipoUsuario(2); // Actualizar tipo de usuario en sesión

      // 4. Insertar inscripción
      inscripcionModel.guardarInscripcion(idAspirante, idPrograma);

      JOptionPane.showMessageDialog(vista, "Inscripción guardada correctamente.");
      vista.limpiarFormulario();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(vista, "Error al guardar la inscripción: " + e.getMessage());
    }
  }

  private void cancelar() {
    vista.dispose();
    new MenuPrincipalView();
  }
}
