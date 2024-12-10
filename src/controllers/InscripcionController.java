package controllers;

import models.AspiranteModel;
import models.InscripcionModel;
import models.ProgramaModel;
import utils.Sesion;
import views.InscripcionView;
import views.MenuPrincipalView;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InscripcionController {

  private InscripcionView vista;
  private AspiranteModel aspiranteModel;
  private InscripcionModel inscripcionModel;
  private ProgramaModel programaModel;

  public InscripcionController(InscripcionView vista) {
    this.vista = vista;
    this.aspiranteModel = new AspiranteModel();
    this.inscripcionModel = new InscripcionModel();
    this.programaModel = new ProgramaModel();

    cargarProgramas(); // Cargar los programas desde la base de datos
    inicializarEventos();
  }

  private void inicializarEventos() {
    vista.getBtnGuardar().addActionListener(e -> guardarInscripcion());
    vista.getBtnCancelar().addActionListener(e -> cancelar());
    vista.getBtnSubirDocumentos().addActionListener(e -> subirDocumentos());
  }

  private void guardarInscripcion() {
    try {
      String documentoIdentidad = vista.getDocumentoIdentidad();
      String nacionalidad = vista.getNacionalidad();
      String fechaTexto = vista.getFechaNacimiento();
      String nombrePrograma = vista.getProgramaSeleccionado();
      int idUsuario = Sesion.getInstance().getIdUsuario();

      if (documentoIdentidad.isEmpty() || nacionalidad.isEmpty() || nombrePrograma == null) {
        JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
        return;
      }

      // Validar y convertir la fecha de nacimiento
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento = dateFormat.parse(fechaTexto);

      // Obtener ID del programa seleccionado
      int idPrograma = programaModel.obtenerIdPorNombre(nombrePrograma);

      // Insertar el aspirante
      int idAspirante = aspiranteModel.guardarAspirante(idUsuario, documentoIdentidad, nacionalidad, fechaNacimiento);

      // Actualizar tipo de usuario
      aspiranteModel.actualizarTipoUsuario(idUsuario);
      Sesion.getInstance().setIdTipoUsuario(2);

      // Crear la inscripción
      inscripcionModel.guardarInscripcion(idAspirante, idPrograma);

      JOptionPane.showMessageDialog(vista, "Inscripción guardada correctamente.");

      // Redirigir al menú principal
      vista.dispose(); // Cierra la ventana de inscripción
      new MenuPrincipalView(); // Dirige al Menú Principal
      
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(vista, "Formato de fecha inválido. Use el formato yyyy-MM-dd.");
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(vista, "Error al guardar la inscripción: " + e.getMessage());
    }
  }

  private void cargarProgramas() {
    try {
      List<String> programas = programaModel.obtenerProgramas()
        .stream()
        .map(programa -> programa.getNombrePrograma())
        .collect(Collectors.toList());

      vista.setProgramas(programas);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(vista, "Error al cargar los programas: " + e.getMessage());
    }
  }

  private void cancelar() {
    vista.dispose();
    new MenuPrincipalView();
  }

  private void subirDocumentos() {
    JOptionPane.showMessageDialog(vista, "Funcionalidad de carga de documentos pendiente de implementación.");
  }
}
