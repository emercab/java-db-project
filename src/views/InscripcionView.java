package views;

import utils.Sesion;
import utils.Tema;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InscripcionView extends BaseView {

  private JTextField txtDocumentoIdentidad, txtNacionalidad, txtFechaNacimiento;
  private JComboBox<String> comboProgramas;
  private JButton btnSubirDocumentos, btnGuardar, btnCancelar;

  public InscripcionView() {
    super("Proceso de Inscripción", 800, 500);
    inicializarComponentes();
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // -------------------- PANEL SUPERIOR: TÍTULO --------------------
    JPanel panelTitulo = new JPanel(new BorderLayout());
    panelTitulo.setBackground(Tema.COLOR_BLANCO);
    panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

    JLabel lblTitulo = new JLabel("Programas de Posgrado");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

    String nombreUsuario = Sesion.getInstance().getNombre() + " " + Sesion.getInstance().getApellido();
    JLabel lblSubtitulo = new JLabel("¡Bienvenido, Usuario " + nombreUsuario + "!");
    Tema.estiloLabel(lblSubtitulo, Tema.COLOR_NEGRO, "subtitulo");
    lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

    panelTitulo.add(lblTitulo, BorderLayout.NORTH);
    panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
    add(panelTitulo, BorderLayout.NORTH);

    // -------------------- PANEL CENTRAL: FORMULARIO --------------------
    JPanel panelFormulario = new JPanel(new GridBagLayout());
    panelFormulario.setBackground(Tema.COLOR_BLANCO);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Campo: Documento de Identidad
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel lblDocumentoIdentidad = new JLabel("Documento de Identidad:");
    Tema.estiloLabel(lblDocumentoIdentidad, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblDocumentoIdentidad, gbc);

    gbc.gridx = 1;
    txtDocumentoIdentidad = new JTextField();
    Tema.estiloTexto(txtDocumentoIdentidad, 400, 25);
    panelFormulario.add(txtDocumentoIdentidad, gbc);

    // Campo: Nacionalidad
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel lblNacionalidad = new JLabel("Nacionalidad:");
    Tema.estiloLabel(lblNacionalidad, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblNacionalidad, gbc);

    gbc.gridx = 1;
    txtNacionalidad = new JTextField();
    Tema.estiloTexto(txtNacionalidad, 400, 25);
    panelFormulario.add(txtNacionalidad, gbc);

    // Campo: Fecha de Nacimiento
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento (aaaa-mm-dd):");
    Tema.estiloLabel(lblFechaNacimiento, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblFechaNacimiento, gbc);

    gbc.gridx = 1;
    txtFechaNacimiento = new JTextField("2005-08-17"); // Valor por defecto
    Tema.estiloTexto(txtFechaNacimiento, 400, 25);
    panelFormulario.add(txtFechaNacimiento, gbc);

    // ComboBox: Seleccionar Programa
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel lblPrograma = new JLabel("Seleccione su programa de Posgrado:");
    Tema.estiloLabel(lblPrograma, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblPrograma, gbc);

    gbc.gridx = 1;
    comboProgramas = new JComboBox<>();
    comboProgramas.setPreferredSize(new Dimension(400, 25));
    panelFormulario.add(comboProgramas, gbc);

    // Botón: Subir Documentos
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    btnSubirDocumentos = new JButton("Subir Documentos");
    Tema.estiloBoton(btnSubirDocumentos, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    btnSubirDocumentos.setPreferredSize(new Dimension(200, 30));
    btnSubirDocumentos.setFocusPainted(false);
    panelFormulario.add(btnSubirDocumentos, gbc);

    add(panelFormulario, BorderLayout.CENTER);

    // -------------------- PANEL INFERIOR: BOTONES --------------------
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
    panelBotones.setBackground(Tema.COLOR_BLANCO);

    btnGuardar = new JButton("Guardar");
    Tema.estiloBoton(btnGuardar, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    btnGuardar.setPreferredSize(new Dimension(100, 30));

    btnCancelar = new JButton("Cancelar");
    Tema.estiloBoton(btnCancelar, Tema.COLOR_GRIS_CLARO, Tema.COLOR_BLANCO);
    btnCancelar.setPreferredSize(new Dimension(100, 30));

    panelBotones.add(btnGuardar);
    panelBotones.add(btnCancelar);

    add(panelBotones, BorderLayout.SOUTH);
  }

  // -------------------- MÉTODOS GETTERS --------------------
  public String getDocumentoIdentidad() {
    return txtDocumentoIdentidad.getText();
  }

  public String getNacionalidad() {
    return txtNacionalidad.getText();
  }

  public String getFechaNacimiento() throws ParseException {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    formato.setLenient(false);
    String fechaTexto = txtFechaNacimiento.getText();
    formato.parse(fechaTexto);
    return fechaTexto;
  }

  public String getProgramaSeleccionado() {
    return (String) comboProgramas.getSelectedItem();
  }

  public JButton getBtnSubirDocumentos() {
    return btnSubirDocumentos;
  }

  public JButton getBtnGuardar() {
    return btnGuardar;
  }

  public JButton getBtnCancelar() {
    return btnCancelar;
  }

  public void setProgramas(List<String> programas) {
    comboProgramas.removeAllItems();
    for (String programa : programas) {
      comboProgramas.addItem(programa);
    }
  }

  public void limpiarFormulario() {
    txtDocumentoIdentidad.setText("");
    txtNacionalidad.setText("");
    txtFechaNacimiento.setText("2001-12-19");
    comboProgramas.setSelectedIndex(-1);
  }
}
