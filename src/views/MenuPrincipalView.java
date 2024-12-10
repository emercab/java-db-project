package views;

import controllers.InscripcionController;
import controllers.ProgramasController;

import javax.swing.*;
import java.awt.*;

import views.BaseView;
import utils.Sesion;
import utils.Tema;

public class MenuPrincipalView extends BaseView {

  private JButton btnIniciarInscripcion, btnGestionarProgramas, btnCerrarSesion;

  public MenuPrincipalView() {
    super("Menú Principal", 700, 500);
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());
    getContentPane().setBackground(Tema.COLOR_BLANCO);

    // -------------------- PANEL SUPERIOR: TÍTULOS --------------------
    JPanel panelTitulos = new JPanel(new BorderLayout());
    panelTitulos.setBackground(Tema.COLOR_BLANCO);
    panelTitulos.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Margen superior e inferior

    JLabel lblTitulo = new JLabel("Programas de Posgrado");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

    String subtitulo = obtenerSubtituloPorTipoUsuario();
    JLabel lblSubtitulo = new JLabel(subtitulo);
    Tema.estiloLabel(lblSubtitulo, Tema.COLOR_NEGRO, "subtitulo");
    lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

    panelTitulos.add(lblTitulo, BorderLayout.NORTH);
    panelTitulos.add(lblSubtitulo, BorderLayout.SOUTH);
    add(panelTitulos, BorderLayout.NORTH);

    // -------------------- PANEL CONTENEDOR CENTRAL --------------------
    JPanel panelCentral = new JPanel(new BorderLayout());
    panelCentral.setBackground(Tema.COLOR_BLANCO);

    // -------------------- PANEL DATOS DEL USUARIO --------------------
    JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    panelUsuario.setBackground(Tema.COLOR_BLANCO);
    panelUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Espacio inferior

    String nombreCompleto = "Usuario: " + Sesion.getInstance().getNombre() + " " + Sesion.getInstance().getApellido();
    String tipoUsuario = obtenerTipoUsuario();
    JLabel lblNombreUsuario = new JLabel(nombreCompleto + " (" + tipoUsuario + ")");
    Tema.estiloLabel(lblNombreUsuario, Tema.COLOR_NEGRO, "normal");

    panelUsuario.add(lblNombreUsuario);
    panelCentral.add(panelUsuario, BorderLayout.NORTH); // Agregar datos de usuario arriba

    // -------------------- PANEL BOTONES --------------------
    JPanel panelBotones = new JPanel();
    panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
    panelBotones.setBackground(Tema.COLOR_BLANCO);
    panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Margen general

    // Botón Iniciar Inscripción (solo para usuario normales)
    if (Sesion.getInstance().esUsuarioNormal()) {
      agregarBotonYLabel(panelBotones, "¡Inscríbete ya a uno de nuestros programas!",
        "Iniciar Inscripción", Tema.COLOR_PRIMARIO, e -> abrirIniciarInscripcion());
    }

    // Botón Gestionar Programas (solo para administradores)
    if (Sesion.getInstance().esAdministrador()) {
      agregarBotonYLabel(panelBotones, "Gestionar Programas",
        "Gestionar Programas", Tema.COLOR_PRIMARIO, e -> abrirGestionProgramas());
    }

    panelCentral.add(panelBotones, BorderLayout.CENTER); // Agregar botones en el centro

    add(panelCentral, BorderLayout.CENTER);

    // -------------------- BOTÓN CERRAR SESIÓN --------------------
    JPanel panelCerrarSesion = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelCerrarSesion.setBackground(Tema.COLOR_BLANCO);

    btnCerrarSesion = new JButton("Cerrar Sesión");
    Tema.estiloBoton(btnCerrarSesion, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);
    btnCerrarSesion.setPreferredSize(new Dimension(200, 40));
    btnCerrarSesion.addActionListener(e -> cerrarSesion());

    panelCerrarSesion.add(btnCerrarSesion);
    add(panelCerrarSesion, BorderLayout.SOUTH);
  }

  // -------------------- MÉTODOS AUXILIARES --------------------
  private void agregarBotonYLabel(JPanel panel, String textoLabel, String textoBoton, Color colorFondo, java.awt.event.ActionListener action) {
    JLabel label = new JLabel(textoLabel);
    Tema.estiloLabel(label, Tema.COLOR_NEGRO, "normal");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton boton = new JButton(textoBoton);
    Tema.estiloBoton(boton, colorFondo, Tema.COLOR_BLANCO);
    boton.setAlignmentX(Component.CENTER_ALIGNMENT);
    boton.setMaximumSize(new Dimension(200, 40));
    boton.addActionListener(action);

    panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio superior
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre label y botón
    panel.add(boton);
  }

  private String obtenerSubtituloPorTipoUsuario() {
    if (Sesion.getInstance().esAdministrador()) {
      return "Bienvenido al panel de administración";
    } else if (Sesion.getInstance().esUsuarioNormal()) {
      return "Estás a un paso de hacer parte de esta comunidad";
    }
    return "¡Bienvenido!";
  }

  private String obtenerTipoUsuario() {
    return Sesion.getInstance().esAdministrador() ? "Administrador" : "Usuario Normal";
  }

  private void abrirGestionProgramas() {
    dispose();
    new ProgramasController(new ProgramasView());
  }

  private void abrirIniciarInscripcion() {
    dispose();
    new InscripcionController(new InscripcionView());
  }

  private void cerrarSesion() {
    Sesion.getInstance().cerrarSesion();
    JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
    dispose();
    new controllers.LoginController(new LoginView());
  }
}
