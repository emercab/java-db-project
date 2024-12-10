package views;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author FAMILIA CABRERA
 */
import javax.swing.*;
import java.awt.*;

import views.BaseView;
import utils.Sesion;
import utils.Tema;
import controllers.ProgramasController;

public class MenuPrincipalView extends BaseView {

  private JButton btnGestionarProgramas, btnCerrarSesion;

  public MenuPrincipalView() {
    super("Menú Principal", 600, 400);
    setVisible(true);
  }

  @Override
  protected void inicializarComponentes() {
    setLayout(new BorderLayout());

    // Título
    JLabel lblTitulo = new JLabel("Menú Principal");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    add(lblTitulo, BorderLayout.NORTH);

    // Panel de botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
    panelBotones.setBackground(Tema.COLOR_BLANCO);

    // Botón Gestionar Programas (solo para administradores)
    if (Sesion.getInstance().esAdministrador()) {
      btnGestionarProgramas = new JButton("Gestionar Programas");
      btnGestionarProgramas.setPreferredSize(new Dimension(200, 40)); // Tamaño fijo
      Tema.estiloBoton(btnGestionarProgramas, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
      panelBotones.add(btnGestionarProgramas);

      btnGestionarProgramas.addActionListener(e -> abrirGestionProgramas());
    }

    add(panelBotones, BorderLayout.CENTER);

    // Botón Cerrar Sesión en la parte baja
    btnCerrarSesion = new JButton("Cerrar Sesión");
    Tema.estiloBoton(btnCerrarSesion, Tema.COLOR_ROJO, Tema.COLOR_BLANCO);
    btnCerrarSesion.setPreferredSize(new Dimension(200, 40));
    btnCerrarSesion.addActionListener(e -> cerrarSesion());
    add(btnCerrarSesion, BorderLayout.SOUTH);
  }

  private void abrirGestionProgramas() {
    dispose(); // Cerrar esta vista
    new ProgramasController(new ProgramasView());
  }

  private void cerrarSesion() {
    Sesion.getInstance().cerrarSesion();
    JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
    dispose();
    new controllers.LoginController(new LoginView());
  }
}
