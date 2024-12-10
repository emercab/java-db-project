package views;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author FAMILIA CABRERA
 */
import models.Programa;
import views.BaseView;
import utils.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.TitledBorder;

public class ProgramasView extends BaseView {

  private JTextField txtNombre, txtDuracion, txtBuscar;
  private JTextArea txtDescripcion;
  private JButton btnNuevo, btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnRecargar, btnCancelar;
  private JTable tablaProgramas;
  private DefaultTableModel modeloTabla;

  public ProgramasView() {
    super("Gestión de Programas", 900, 600);
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // --------------------- PANEL SUPERIOR (Formulario) ---------------------
    JPanel panelFormulario = new JPanel(new GridBagLayout());
    TitledBorder tituloFormulario = BorderFactory.createTitledBorder("Gestionar Programas de Posgrado");
    tituloFormulario.setTitleFont(Tema.FUENTE_TITULO);
    tituloFormulario.setTitleColor(Tema.COLOR_PRIMARIO); // Aplica el color primario del Tema
    panelFormulario.setBorder(tituloFormulario);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Campo: Nombre del Programa
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel lblNombre = new JLabel("Nombre del Programa:");
    Tema.estiloLabel(lblNombre, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblNombre, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    txtNombre = new JTextField();
    Tema.estiloTexto(txtNombre, 600, 25);
    panelFormulario.add(txtNombre, gbc);

    // Campo: Descripción
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel lblDescripcion = new JLabel("Descripción:");
    Tema.estiloLabel(lblDescripcion, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblDescripcion, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    txtDescripcion = new JTextArea(6, 20);
    txtDescripcion.setLineWrap(true);          // Habilita el ajuste automático de línea
    txtDescripcion.setWrapStyleWord(true);     // Ajusta las líneas respetando las palabras
    Tema.estiloTexto(txtDescripcion, 600, 120);
    JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
    scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desactiva la barra horizontal
    panelFormulario.add(scrollDescripcion, gbc);

    // Campo: Duración
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel lblDuracion = new JLabel("Duración (semestres):");
    Tema.estiloLabel(lblDuracion, Tema.COLOR_NEGRO, "normal");
    panelFormulario.add(lblDuracion, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    txtDuracion = new JTextField();
    Tema.estiloTexto(txtDuracion, 600, 25);
    panelFormulario.add(txtDuracion, gbc);

    add(panelFormulario, BorderLayout.NORTH);

    // --------------------- PANEL CENTRAL (Tabla) ---------------------
    String[] columnas = {"ID", "Nombre", "Descripción", "Duración"};
    modeloTabla = new DefaultTableModel(columnas, 0);
    tablaProgramas = new JTable(modeloTabla);
    tablaProgramas.setRowHeight(25);
    // Ajustar el ancho de las columnas
    tablaProgramas.getColumnModel().getColumn(0).setPreferredWidth(20);   // ID
    tablaProgramas.getColumnModel().getColumn(1).setPreferredWidth(250);  // Nombre
    tablaProgramas.getColumnModel().getColumn(2).setPreferredWidth(450);  // Descripción
    tablaProgramas.getColumnModel().getColumn(3).setPreferredWidth(70);   // Duración

    JScrollPane scrollTabla = new JScrollPane(tablaProgramas);
    scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen alrededor de la tabla
    add(scrollTabla, BorderLayout.CENTER);

    // --------------------- PANEL INFERIOR (Búsqueda y Botones) ---------------------
    JPanel panelInferior = new JPanel(new BorderLayout());

    // Panel de Búsqueda
    JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrado
    panelBuscar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Separación
    JLabel lblBuscar = new JLabel("Buscar Programa:");
    Tema.estiloLabel(lblBuscar, Tema.COLOR_NEGRO, "normal");
    txtBuscar = new JTextField();
    Tema.estiloTexto(txtBuscar, 400, 25);
    btnBuscar = new JButton("Buscar");
    Tema.estiloBoton(btnBuscar, Tema.COLOR_AZUL, Tema.COLOR_BLANCO);

    panelBuscar.add(lblBuscar);
    panelBuscar.add(txtBuscar);
    panelBuscar.add(btnBuscar);

    panelInferior.add(panelBuscar, BorderLayout.NORTH);

    // Panel de Botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Separación adicional
    btnNuevo = new JButton("Nuevo");
    btnAgregar = new JButton("Agregar");
    btnActualizar = new JButton("Actualizar");
    btnEliminar = new JButton("Eliminar");
    btnRecargar = new JButton("Recargar");
    btnCancelar = new JButton("Cancelar");

    // Aplicar estilos a los botones
    Tema.estiloBoton(btnNuevo, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    Tema.estiloBoton(btnAgregar, Tema.COLOR_AZUL, Tema.COLOR_BLANCO);
    Tema.estiloBoton(btnActualizar, Tema.COLOR_AMARILLO, Tema.COLOR_NEGRO);
    Tema.estiloBoton(btnEliminar, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);
    Tema.estiloBoton(btnRecargar, Tema.COLOR_GRIS_CLARO, Tema.COLOR_NEGRO);
    Tema.estiloBoton(btnCancelar, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);

    panelBotones.add(btnNuevo);
    panelBotones.add(btnAgregar);
    panelBotones.add(btnActualizar);
    panelBotones.add(btnEliminar);
    panelBotones.add(btnRecargar);
    panelBotones.add(btnCancelar);

    panelInferior.add(panelBotones, BorderLayout.CENTER);

    add(panelInferior, BorderLayout.SOUTH);

    // Estado inicial de los botones
    btnAgregar.setEnabled(false);
    btnActualizar.setEnabled(false);
    btnEliminar.setEnabled(false);
  }

  // --------------------- MÉTODOS GETTER ---------------------
  public JButton getBtnNuevo() {
    return btnNuevo;
  }

  public JButton getBtnAgregar() {
    return btnAgregar;
  }

  public JButton getBtnActualizar() {
    return btnActualizar;
  }

  public JButton getBtnEliminar() {
    return btnEliminar;
  }

  public JButton getBtnBuscar() {
    return btnBuscar;
  }

  public JButton getBtnRecargar() {
    return btnRecargar;
  }

  public JButton getBtnCancelar() {
    return btnCancelar;
  }

  public JTable getTablaProgramas() {
    return tablaProgramas;
  }

  public String getNombrePrograma() {
    return txtNombre.getText();
  }

  public String getDescripcionPrograma() {
    return txtDescripcion.getText();
  }

  public String getDuracionPrograma() {
    return txtDuracion.getText();
  }

  public String getBuscarTexto() {
    return txtBuscar.getText();
  }

  public void limpiarFormulario() {
    txtNombre.setText("");
    txtDescripcion.setText("");
    txtDuracion.setText("");
    txtBuscar.setText("");
    tablaProgramas.clearSelection();
  }

  public void cargarProgramasEnTabla(List<Programa> programas) {
    modeloTabla.setRowCount(0); // Limpiar la tabla
    for (Programa p : programas) {
      modeloTabla.addRow(new Object[]{p.getId(), p.getNombrePrograma(), p.getDescripcion(), p.getDuracion()});
    }
  }

  public void cargarDatosEnFormulario(Programa programa) {
    txtNombre.setText(programa.getNombrePrograma());
    txtDescripcion.setText(programa.getDescripcion());
    txtDuracion.setText(String.valueOf(programa.getDuracion()));
  }
}
