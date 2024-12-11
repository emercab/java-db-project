package controllers;

import models.AspiranteModel;
import models.DocumentoModel;
import models.TipoDocumento;
import models.TipoDocumentoModel;
import utils.Sesion;
import views.DocumentosView;
import views.MenuPrincipalView;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentosController {

  private final DocumentosView vista;
  private final AspiranteModel aspiranteModel;
  private final DocumentoModel documentoModel;
  private final TipoDocumentoModel tipoDocumentoModel;
  private String rutaArchivoSeleccionado;
  private int idAspirante;

  public DocumentosController(DocumentosView vista) {
    this.vista = vista;
    this.aspiranteModel = new AspiranteModel();
    this.documentoModel = new DocumentoModel();
    this.tipoDocumentoModel = new TipoDocumentoModel();

    int idUsuario = Sesion.getInstance().getIdUsuario();
    try {
      this.idAspirante = aspiranteModel.obtenerIdAspirante(idUsuario);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(vista, "Error al cargar id del aspirante.");
    }

    inicializarEventos();
    cargarTiposDeDocumentos();
    cargarDocumentos();
  }

  private void inicializarEventos() {
    vista.getBtnSeleccionarArchivo().addActionListener(e -> seleccionarArchivo());
    vista.getBtnEnviarDocumento().addActionListener(e -> enviarDocumento());
    vista.getBtnEliminarDocumento().addActionListener(e -> eliminarDocumento());
    vista.getBtnCancelar().addActionListener(e -> cancelar());
  }

  private void cargarTiposDeDocumentos() {
    try {
      List<String> tipos = new ArrayList<>();
      for (TipoDocumento t : tipoDocumentoModel.obtenerTiposDocumentos()) {
        tipos.add(t.getDescripcion());
      }
      vista.setTiposDocumentos(tipos);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(vista, "Error al cargar tipos de documentos.");
    }
  }

  private void cargarDocumentos() {
    try {
      List<Object[]> documentos = documentoModel.obtenerDocumentosPorAspirante(this.idAspirante);
      vista.cargarDocumentosEnTabla(documentos);

      List<String> faltantes = obtenerDocumentosFaltantes();
      vista.setDocumentosFaltantes(faltantes);
      vista.actualizarContadorDocumentos(documentos.size(), tipoDocumentoModel.obtenerTiposDocumentos().size());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private List<String> obtenerDocumentosFaltantes() throws SQLException {
    List<String> tiposDocumentos = tipoDocumentoModel.obtenerTiposDocumentos()
      .stream()
      .map(TipoDocumento::getDescripcion)
      .toList();

    List<Object[]> documentosSubidos = documentoModel.obtenerDocumentosPorAspirante(this.idAspirante);
    List<String> subidos = documentosSubidos.stream().map(d -> (String) d[0]).toList();

    List<String> faltantes = new ArrayList<>(tiposDocumentos);
    faltantes.removeAll(subidos);

    return faltantes;
  }

  private void seleccionarArchivo() {
    JFileChooser fileChooser = new JFileChooser();
    int resultado = fileChooser.showOpenDialog(vista);

    if (resultado == JFileChooser.APPROVE_OPTION) {
      rutaArchivoSeleccionado = fileChooser.getSelectedFile().getAbsolutePath();
      vista.mostrarSeleccionArchivo(rutaArchivoSeleccionado);
    }
  }

  private void enviarDocumento() {
    // Implementación completa de envío.
  }

  private void eliminarDocumento() {
    // Implementación completa de eliminación.
  }

  private void cancelar() {
    vista.dispose();
    new MenuPrincipalView();
  }
}
