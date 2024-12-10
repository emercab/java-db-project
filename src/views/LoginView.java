/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author FAMILIA CABRERA
 */
import javax.swing.*;
import java.awt.*;
import utils.Tema;

public class LoginView extends BaseView {

  private JTextField txtEmail;
  private JPasswordField txtPassword;
  private JButton btnLogin, btnRegistro;

  public LoginView() {
    super("Iniciar Sesión", 600, 400); // Título, Ancho, Alto
    setVisible(true); // Hacer visible la ventana
  }

  @Override
  protected void inicializarComponentes() {
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    // Título
    JLabel lblTitulo = new JLabel("Iniciar Sesión");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(lblTitulo, gbc);
    
    gbc.anchor = GridBagConstraints.WEST; // Alinear los siguientes componentes a la izquierda
    gbc.gridwidth = 1;

    // Correo
    gbc.gridy++;
    gbc.gridwidth = 1;
    JLabel lblEmail = new JLabel("Correo:");
    Tema.estiloLabel(lblEmail, Tema.COLOR_NEGRO, "normal");
    add(lblEmail, gbc);

    gbc.gridx = 1;
    txtEmail = new JTextField(20);
    txtEmail.setText("emercab@gmail.com"); // Login default
    Tema.estiloTexto(txtEmail, 350, 30);
    add(txtEmail, gbc);

    // Contraseña
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel lblPassword = new JLabel("Contraseña:");
    Tema.estiloLabel(lblPassword, Tema.COLOR_NEGRO, "normal");
    add(lblPassword, gbc);

    gbc.gridx = 1;
    txtPassword = new JPasswordField(20);
    txtPassword.setText("password"); // Login default
    Tema.estiloTexto(txtPassword, 350, 30);
    add(txtPassword, gbc);

    // Botón Login
    gbc.gridy++;
    gbc.gridx = 0;
    btnLogin = new JButton("Iniciar Sesión");
    Tema.estiloBoton(btnLogin, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    add(btnLogin, gbc);

    // Botón Registro
    gbc.gridx++;
    btnRegistro = new JButton("Registrarse");
    Tema.estiloBoton(btnRegistro, Tema.COLOR_AMARILLO, Tema.COLOR_NEGRO);
    add(btnRegistro, gbc);
  }

  public String getEmail() {
    return txtEmail.getText();
  }

  public String getPassword() {
    return new String(txtPassword.getPassword());
  }

  public JButton getBtnLogin() {
    return btnLogin;
  }

  public JButton getBtnRegistro() {
    return btnRegistro;
  }
}
