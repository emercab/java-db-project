package controllers;

import models.AspiranteModel;
import models.DocumentoModel;
import models.TipoDocumento;
import models.TipoDocumentoModel;
import utils.Sesion;
import views.DocumentosView;
import views.MenuPrincipalView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
      JOptionPane.showMessageDialog(vista, "Error al cargar los documentos: " + e.getMessage());
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
    String nombreDocumento = (String) vista.getComboTiposDocumentos().getSelectedItem();
    String rutaDocumento = vista.getRutaArchivoSeleccionado();

    if (nombreDocumento == null || rutaDocumento == null || rutaDocumento.isEmpty()) {
      JOptionPane.showMessageDialog(vista, "Debe seleccionar un tipo de documento y un archivo.");
      return;
    }

    try {
      int idTipoDocumento = obtenerIdTipoDocumento(nombreDocumento);

      if (idTipoDocumento <= 0) {
        JOptionPane.showMessageDialog(vista, "Tipo de documento inválido.");
        return;
      }

      if (!documentoModel.existeDocumento(this.idAspirante, idTipoDocumento)) {
        guardarDocumento(idTipoDocumento, rutaDocumento);
        JOptionPane.showMessageDialog(vista, "Documento guardado correctamente.");
        cargarDocumentos(); // Recargar los documentos en la tabla
      } else {
        JOptionPane.showMessageDialog(vista, "Este documento ya fue subido para este usuario.");
      }
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(vista, "Error al guardar el archivo: " + ex.getMessage());
    }
  }

  // Método auxiliar: Obtener ID del tipo de documento
  private int obtenerIdTipoDocumento(String nombreDocumento) throws SQLException {
    return tipoDocumentoModel.obtenerIdPorDescripcionDocumento(nombreDocumento);
  }

  // Método auxiliar: Guardar documento en la base de datos
  private void guardarDocumento(int idTipoDocumento, String rutaDocumento) throws SQLException {
    documentoModel.guardarDocumento(this.idAspirante, idTipoDocumento, rutaDocumento);
  }

  private void eliminarDocumento() {
    int filaSeleccionada = vista.getTablaDocumentos().getSelectedRow();

    if (filaSeleccionada == -1) {
      JOptionPane.showMessageDialog(vista, "Seleccione un documento a eliminar.");
      return;
    }

    String rutaDocumento = (String) vista.getTablaDocumentos().getValueAt(filaSeleccionada, 2);

    int confirmacion = JOptionPane.showConfirmDialog(
      vista, "¿Está seguro de eliminar el documento?",
      "Confirmar Eliminación", JOptionPane.YES_NO_OPTION
    );

    if (confirmacion == JOptionPane.YES_OPTION) {
      try {
        documentoModel.eliminarDocumento(rutaDocumento);
        cargarDocumentos(); // Recargar la tabla
        JOptionPane.showMessageDialog(vista, "Documento eliminado correctamente.");
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(vista, "Error al eliminar el documento: " + e.getMessage());
      }
    }
  }

  private void cancelar() {
    vista.dispose();
    new MenuPrincipalView();
  }
}
