package views;

import javax.swing.*;
import java.awt.*;

import views.BaseView;
import utils.Sesion;
import utils.Tema;
import controllers.ProgramasController;

public class MenuPrincipalView extends BaseView {

  private JButton btnIniciarInscripcion, btnGestionarProgramas, btnCerrarSesion;

  public MenuPrincipalView() {
    super("Menú Principal", 700, 500); // Ajuste del tamaño para mejor visualización
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // -------------------- PANEL SUPERIOR: TÍTULOS --------------------
    JPanel panelTitulos = new JPanel(new BorderLayout());
    panelTitulos.setBackground(Tema.COLOR_BLANCO);

    // Título principal
    JLabel lblTitulo = new JLabel("Menú Principal");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    panelTitulos.add(lblTitulo, BorderLayout.NORTH);

    // Subtítulo dinámico
    String subtitulo = obtenerSubtituloPorTipoUsuario();
    JLabel lblSubtitulo = new JLabel(subtitulo);
    Tema.estiloLabel(lblSubtitulo, Tema.COLOR_NEGRO, "subtitulo");
    lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
    panelTitulos.add(lblSubtitulo, BorderLayout.SOUTH);

    add(panelTitulos, BorderLayout.NORTH);

    // -------------------- PANEL DATOS DEL USUARIO --------------------
    JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    panelUsuario.setBackground(Tema.COLOR_BLANCO);

    String nombreCompleto = "Usuario: " + Sesion.getInstance().getNombre() + " " + Sesion.getInstance().getApellido();
    String tipoUsuario = obtenerTipoUsuario();
    JLabel lblNombreUsuario = new JLabel(nombreCompleto + " (" + tipoUsuario + ")");
    Tema.estiloLabel(lblNombreUsuario, Tema.COLOR_NEGRO, "normal");

    panelUsuario.add(lblNombreUsuario);

    // Espacio entre panel de usuario y botones
    panelUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
    add(panelUsuario, BorderLayout.CENTER);

    // -------------------- PANEL DE BOTONES --------------------
    JPanel panelBotones = new JPanel();
    panelBotones.setBackground(Tema.COLOR_BLANCO);
    panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS)); // Layout vertical

// Espaciado entre botones
    panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// Botón Iniciar Inscripción (solo para usuario normales)
    if (Sesion.getInstance().esUsuarioNormal()) {
      JLabel lblIniciaInscripcion = new JLabel("¡Inscríbete ya a uno de nuestros programas!");
      Tema.estiloLabel(lblIniciaInscripcion, Tema.COLOR_NEGRO, "normal");
      lblIniciaInscripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

      btnIniciarInscripcion = new JButton("Iniciar Inscripción");
      btnIniciarInscripcion.setPreferredSize(new Dimension(200, 40));
      Tema.estiloBoton(btnIniciarInscripcion, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
      btnIniciarInscripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

      panelBotones.add(lblIniciaInscripcion);
      panelBotones.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado vertical
      panelBotones.add(btnIniciarInscripcion);

      btnIniciarInscripcion.addActionListener(e -> abrirIniciarInscripcion());
    }

// Botón Gestionar Programas (solo para administradores)
    if (Sesion.getInstance().esAdministrador()) {
      JLabel lblGestionarProgramas = new JLabel("Gestionar Programas");
      Tema.estiloLabel(lblGestionarProgramas, Tema.COLOR_NEGRO, "normal");
      lblGestionarProgramas.setAlignmentX(Component.CENTER_ALIGNMENT);

      btnGestionarProgramas = new JButton("Gestionar Programas");
      btnGestionarProgramas.setPreferredSize(new Dimension(200, 40));
      Tema.estiloBoton(btnGestionarProgramas, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
      btnGestionarProgramas.setAlignmentX(Component.CENTER_ALIGNMENT);

      panelBotones.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado vertical
      panelBotones.add(lblGestionarProgramas);
      panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
      panelBotones.add(btnGestionarProgramas);

      btnGestionarProgramas.addActionListener(e -> abrirGestionProgramas());
    }

    // Forzar actualización del panel
    panelBotones.revalidate();
    panelBotones.repaint();

    // Agregar el panel de botones en la parte central
    add(panelBotones, BorderLayout.CENTER);

    // -------------------- BOTÓN CERRAR SESIÓN --------------------
    JPanel panelCerrarSesion = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelCerrarSesion.setBackground(Tema.COLOR_BLANCO);

    btnCerrarSesion = new JButton("Cerrar Sesión");
    Tema.estiloBoton(btnCerrarSesion, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);
    btnCerrarSesion.setPreferredSize(new Dimension(200, 40));
    btnCerrarSesion.addActionListener(e -> cerrarSesion());

    panelCerrarSesion.add(btnCerrarSesion);

    // Agregar el panel de cerrar sesión en la parte inferior
    add(panelCerrarSesion, BorderLayout.SOUTH);
  }

  // -------------------- MÉTODOS AUXILIARES --------------------
  private String obtenerSubtituloPorTipoUsuario() {
    if (Sesion.getInstance().esAdministrador()) {
      return "Bienvenido al panel de administración";
    } else if (Sesion.getInstance().esAspirante()) {
      return "Panel de Inscripciones";
    } else if (Sesion.getInstance().esAdmitido()) {
      return "Panel de Admitido";
    } else if (Sesion.getInstance().esEstudiante()) {
      return "Panel del Estudiante";
    } else if (Sesion.getInstance().esEvaluador()) {
      return "Panel del Evaluador";
    } else if (Sesion.getInstance().esAuxiliarRegistro()) {
      return "Panel de Registro";
    } else if (Sesion.getInstance().esCoordinador()) {
      return "Panel del Coordinador";
    } else {
      return "Estás a un paso de hacer parte de esta comunidad";
    }
  }

  private String obtenerTipoUsuario() {
    if (Sesion.getInstance().esAdministrador()) {
      return "Administrador";
    } else if (Sesion.getInstance().esAspirante()) {
      return "Aspirante";
    } else if (Sesion.getInstance().esAdmitido()) {
      return "Admitido";
    } else if (Sesion.getInstance().esEstudiante()) {
      return "Estudiante";
    } else if (Sesion.getInstance().esEvaluador()) {
      return "Evaluador";
    } else if (Sesion.getInstance().esAuxiliarRegistro()) {
      return "Auxiliar de Registro";
    } else if (Sesion.getInstance().esCoordinador()) {
      return "Coordinador";
    } else {
      return "Usuario Normal";
    }
  }

  private void abrirGestionProgramas() {
    dispose(); // Cerrar esta vista
    new ProgramasController(new ProgramasView());
  }

  private void abrirIniciarInscripcion() {
    dispose();
    new InscripcionView();
    // new InscripcionController(new InscripcionView());
  }

  private void cerrarSesion() {
    Sesion.getInstance().cerrarSesion();
    JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
    dispose();
    new controllers.LoginController(new LoginView());
  }
}
