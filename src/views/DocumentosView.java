package views;

import utils.Tema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DocumentosView extends BaseView {

  private JLabel lblDocumentosEnviados, lblDocumentosFaltantes, lblRutaArchivoSeleccionado, lblInstruccionEliminar;
  private JComboBox<String> comboTiposDocumentos;
  private JButton btnSeleccionarArchivo, btnEnviarDocumento, btnEliminarDocumento, btnCancelar;
  private JTable tablaDocumentos;
  private DefaultTableModel modeloTabla;
  private String rutaArchivoSeleccionado;

  public DocumentosView() {
    super("Gestión de Documentos", 1000, 600);
    inicializarComponentes();
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // -------------------- PANEL SUPERIOR: TÍTULO Y DOCUMENTOS FALTANTES --------------------
    JPanel panelTitulo = new JPanel(new GridBagLayout());
    panelTitulo.setBackground(Tema.COLOR_BLANCO);
    panelTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 0, 0, 0);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Título Principal
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel lblTitulo = new JLabel("Subir Documentos");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    panelTitulo.add(lblTitulo, gbc);

    // Subtítulo: Documentos Faltantes
    gbc.gridy++;
    JLabel lblTituloFaltantes = new JLabel("Documentos Faltantes:");
    Tema.estiloLabel(lblTituloFaltantes, Tema.COLOR_PRIMARIO, "subtitulo");
    lblTituloFaltantes.setHorizontalAlignment(SwingConstants.LEFT);
    panelTitulo.add(lblTituloFaltantes, gbc);

    // Label de los documentos faltantes
    gbc.gridy++;
    lblDocumentosFaltantes = new JLabel("Ninguno");
    lblDocumentosFaltantes.setFont(Tema.FUENTE_TEXTO); // Fuente TEXTO del Tema
    lblDocumentosFaltantes.setForeground(Color.RED);
    lblDocumentosFaltantes.setPreferredSize(new Dimension(950, 30)); // Limita el tamaño para evitar desbordes
    lblDocumentosFaltantes.setHorizontalAlignment(SwingConstants.LEFT);
    panelTitulo.add(lblDocumentosFaltantes, gbc);

    // Contador de Documentos Enviados
    gbc.gridy++;
    lblDocumentosEnviados = new JLabel("Documentos enviados: 0 de 0");
    Tema.estiloLabel(lblDocumentosEnviados, Tema.COLOR_NEGRO, "subtitulo");
    panelTitulo.add(lblDocumentosEnviados, gbc);

    // Controles para subir documentos
    gbc.gridy++;
    JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    panelControles.setBackground(Tema.COLOR_BLANCO);

    JLabel lblTipoDocumento = new JLabel("Tipo de Documento:");
    Tema.estiloLabel(lblTipoDocumento, Tema.COLOR_NEGRO, "normal");

    comboTiposDocumentos = new JComboBox<>();
    comboTiposDocumentos.setPreferredSize(new Dimension(300, 25));
    comboTiposDocumentos.setFont(Tema.FUENTE_TEXTO); // Fuente normal del Tema

    btnSeleccionarArchivo = new JButton("Seleccionar Archivo");
    Tema.estiloBoton(btnSeleccionarArchivo, Tema.COLOR_AZUL, Tema.COLOR_BLANCO);

    btnEnviarDocumento = new JButton("Enviar Documento");
    Tema.estiloBoton(btnEnviarDocumento, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);

    lblRutaArchivoSeleccionado = new JLabel("Ruta seleccionada: Ninguna");
    Tema.estiloLabel(lblRutaArchivoSeleccionado, Tema.COLOR_NEGRO, "normal");

    panelControles.add(lblTipoDocumento);
    panelControles.add(comboTiposDocumentos);
    panelControles.add(btnSeleccionarArchivo);
    panelControles.add(btnEnviarDocumento);

    panelTitulo.add(panelControles, gbc);

    // Ruta del Archivo Seleccionado
    gbc.gridy++;
    panelTitulo.add(lblRutaArchivoSeleccionado, gbc);

    add(panelTitulo, BorderLayout.NORTH);

    // -------------------- PANEL CENTRAL: TABLA CON DETALLE DE DOCUMENTOS --------------------
    JPanel panelTabla = new JPanel(new BorderLayout());
    panelTabla.setBackground(Tema.COLOR_BLANCO);
    TitledBorder tituloTabla = BorderFactory.createTitledBorder("Detalle de Documentos");
    tituloTabla.setTitleFont(Tema.FUENTE_TITULO);
    tituloTabla.setTitleColor(Tema.COLOR_PRIMARIO);
    panelTabla.setBorder(tituloTabla);

    String[] columnas = {"Nombre del Documento", "Fecha de Envío", "Ruta"};
    modeloTabla = new DefaultTableModel(columnas, 0);
    tablaDocumentos = new JTable(modeloTabla);
    tablaDocumentos.setRowHeight(25);

    JScrollPane scrollTabla = new JScrollPane(tablaDocumentos);
    scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelTabla.add(scrollTabla, BorderLayout.CENTER);

    add(panelTabla, BorderLayout.CENTER);

    // -------------------- PANEL INFERIOR: MENSAJE Y BOTONES CENTRADOS --------------------
    JPanel panelInferior = new JPanel(new BorderLayout());
    panelInferior.setBackground(Tema.COLOR_BLANCO);

    // Mensaje de ayuda
    lblInstruccionEliminar = new JLabel("Para eliminar un documento, dé clic en la tabla y luego en Eliminar.");
    Tema.estiloLabel(lblInstruccionEliminar, Tema.COLOR_NEGRO, "normal");
    lblInstruccionEliminar.setHorizontalAlignment(SwingConstants.CENTER);
    panelInferior.add(lblInstruccionEliminar, BorderLayout.NORTH);

    // Botones Eliminar y Cancelar en la misma línea
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    panelBotones.setBackground(Tema.COLOR_BLANCO);

    btnEliminarDocumento = new JButton("Eliminar Documento");
    Tema.estiloBoton(btnEliminarDocumento, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);
    btnEliminarDocumento.setPreferredSize(new Dimension(200, 30));

    btnCancelar = new JButton("Cancelar");
    Tema.estiloBoton(btnCancelar, Tema.COLOR_GRIS_CLARO, Tema.COLOR_BLANCO);
    btnCancelar.setPreferredSize(new Dimension(200, 30));

    panelBotones.add(btnEliminarDocumento);
    panelBotones.add(btnCancelar);

    panelInferior.add(panelBotones, BorderLayout.CENTER);
    add(panelInferior, BorderLayout.SOUTH);
  }

  // -------------------- MÉTODOS GETTERS Y SETTERS --------------------
  public JComboBox<String> getComboTiposDocumentos() {
    return comboTiposDocumentos;
  }

  public JButton getBtnSeleccionarArchivo() {
    return btnSeleccionarArchivo;
  }

  public JButton getBtnEnviarDocumento() {
    return btnEnviarDocumento;
  }

  public JButton getBtnEliminarDocumento() {
    return btnEliminarDocumento;
  }

  public JButton getBtnCancelar() {
    return btnCancelar;
  }

  public JTable getTablaDocumentos() {
    return tablaDocumentos;
  }

  public void actualizarContadorDocumentos(int enviados, int total) {
    lblDocumentosEnviados.setText("Documentos enviados: " + enviados + " de " + total);
  }

  public void setDocumentosFaltantes(List<String> documentosFaltantes) {
    String faltantes = String.join(", ", documentosFaltantes);
    lblDocumentosFaltantes.setText(faltantes.isEmpty() ? "Ninguno" : faltantes);
  }

  public void cargarDocumentosEnTabla(List<Object[]> documentos) {
    modeloTabla.setRowCount(0);
    for (Object[] doc : documentos) {
      modeloTabla.addRow(doc);
    }
  }

  public void setTiposDocumentos(List<String> tipos) {
    comboTiposDocumentos.removeAllItems();
    for (String tipo : tipos) {
      comboTiposDocumentos.addItem(tipo);
    }
  }

  public void mostrarSeleccionArchivo(String rutaArchivo) {
    this.rutaArchivoSeleccionado = rutaArchivo;
    lblRutaArchivoSeleccionado.setText("Ruta seleccionada: " + rutaArchivo);
  }

  public String getRutaArchivoSeleccionado() {
    return rutaArchivoSeleccionado;
  }
}
