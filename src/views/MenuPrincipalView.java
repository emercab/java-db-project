package views;

import controllers.DocumentosController;
import controllers.InscripcionController;
import controllers.ProgramasController;
import utils.Sesion;
import utils.Tema;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends BaseView {

  private JButton btnOtro, btnIniciarInscripcion, btnGestionarProgramas, btnCerrarSesion;

  public MenuPrincipalView() {
    super("Menú Principal", 900, 600);
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // -------------------- PANEL SUPERIOR: TÍTULOS --------------------
    JPanel panelTitulos = new JPanel(new BorderLayout());
    panelTitulos.setBackground(Tema.COLOR_BLANCO);

    // Título principal
    JLabel lblTitulo = new JLabel("Programas de Posgrado");
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

    // -------------------- PANEL CENTRAL: DATOS DEL USUARIO Y BOTONES --------------------
    JPanel panelCentral = new JPanel(new BorderLayout());
    panelCentral.setBackground(Tema.COLOR_BLANCO);

    // PANEL DATOS DEL USUARIO
    JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    panelUsuario.setBackground(Tema.COLOR_BLANCO);

    String nombreCompleto = "Usuario: " + Sesion.getInstance().getNombre() + " " + Sesion.getInstance().getApellido();
    String tipoUsuario = obtenerTipoUsuario();
    JLabel lblNombreUsuario = new JLabel(nombreCompleto + " (" + tipoUsuario + ")");
    Tema.estiloLabel(lblNombreUsuario, Tema.COLOR_NEGRO, "normal");

    panelUsuario.add(lblNombreUsuario);
    panelUsuario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado entre paneles
    panelCentral.add(panelUsuario, BorderLayout.NORTH);

    // PANEL DE BOTONES
    JPanel panelBotones = new JPanel(new GridBagLayout());
    panelBotones.setBackground(Tema.COLOR_BLANCO);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(20, 0, 0, 0); // Espaciado entre botones (20px vertical)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    // ------------------- OPCIONES PARA USUARIOS NORMALES -------------------
    if (Sesion.getInstance().esUsuarioNormal()) {
      // Botón Iniciar Inscripción
      agregarBotonConEtiqueta(
        panelBotones,
        gbc,
        "¡Inscríbete ya a uno de nuestros programas!",
        "Iniciar Inscripción",
        this::abrirIniciarInscripcion
      );
    } // ------------------- FIN DE OPCIONES PARA USUARIOS NORMALES -------------------

    // ------------------- OPCIONES PARA ASPIRANTES -------------------
    if (Sesion.getInstance().esAspirante()) {
      // Botón Ver Documentos
      agregarBotonConEtiqueta(
        panelBotones,
        gbc,
        "Suba sus documentos aquí",
        "Ver Documentos",
        this::abrirDocumentos
      );

      // Botón Consultar Estado de Inscripción
      agregarBotonConEtiqueta(
        panelBotones,
        gbc,
        "",
        "Ver Estado de Inscripción",
        this::consultarEstadoInscripcion
      );

      // Botón Ver Valoraciones solo para aspirantes
      agregarBotonConEtiqueta(
        panelBotones,
        gbc,
        "",
        "Ver Valoraciones",
        this::abrirValoraciones
      );
    } // ------------------- FIN DE OPCIONES PARA ASPIRANTES -------------------

    // ------------------- OPCIONES PARA ADMINISTRADORES -------------------
    if (Sesion.getInstance().esAdministrador()) {
      // Botón Gestionar Programas
      agregarBotonConEtiqueta(
        panelBotones,
        gbc,
        "Gestionar Programas",
        "Gestionar Programas",
        this::abrirGestionProgramas
      );
    } // ------------------- FIN DE OPCIONES PARA ADMINISTRADORES -------------------

    panelCentral.add(panelBotones, BorderLayout.CENTER);
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
  private String obtenerSubtituloPorTipoUsuario() {
    if (Sesion.getInstance().esAdministrador()) {
      return "Bienvenido al panel de administración";
    } else if (Sesion.getInstance().esAspirante()) {
      return "¡Bienvenido de nuevo, Aspirante!";
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

  // Agrega un botón al panel dado, con etiqueta y su acción
  private void agregarBotonConEtiqueta(JPanel panel, GridBagConstraints gbc, String textoEtiqueta, String textoBoton, Runnable accion) {
    if (textoEtiqueta != null && !textoEtiqueta.isEmpty()) {
      gbc.gridy++; // Incrementar la fila para la etiqueta
      JLabel lblEtiqueta = new JLabel(textoEtiqueta);
      Tema.estiloLabel(lblEtiqueta, Tema.COLOR_NEGRO, "normal");
      panel.add(lblEtiqueta, gbc);
    }
    // Botón
    gbc.gridy++; // Incrementar la fila para el botón
    JButton boton = new JButton(textoBoton);
    Tema.estiloBoton(boton, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    boton.setPreferredSize(new Dimension(240, 35));
    panel.add(boton, gbc);

    boton.addActionListener(e -> accion.run());
  }

  private void abrirGestionProgramas() {
    dispose();
    new ProgramasController(new ProgramasView());
  }

  private void abrirIniciarInscripcion() {
    dispose();
    new InscripcionController(new InscripcionView());
  }

  private void abrirDocumentos() {
    dispose();
    new DocumentosController(new DocumentosView());
  }

  private void consultarEstadoInscripcion() {
    JOptionPane.showMessageDialog(this, "Su inscripción se encuentra en estado: ");
  }

  private void abrirValoraciones() {
    dispose();
    //new ValoracionesController(new ValoracionesView());
  }

  private void cerrarSesion() {
    Sesion.getInstance().cerrarSesion();
    JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
    dispose();
    new controllers.LoginController(new LoginView());
  }
}
