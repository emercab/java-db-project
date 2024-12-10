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

public class RegistroView extends BaseView {

  private JTextField txtNombre, txtApellido, txtEmail, txtTelefono;
  private JPasswordField txtPassword, txtConfirmarPassword;
  private JButton btnRegistrar, btnCancelar;

  public RegistroView() {
    super("Registro de Usuario", 650, 400); // Título, Ancho, Alto
    setVisible(true); // Hacer visible la ventana
  }

  @Override
  protected void inicializarComponentes() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST; // Alineación izquierda por defecto

    setLayout(new GridBagLayout()); // Layout principal

    // Título
    JLabel lblTitulo = new JLabel("Registro de Usuario");
    Tema.estiloLabel(lblTitulo, Tema.COLOR_PRIMARIO, "titulo");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER; // Centrar el título
    add(lblTitulo, gbc);

    gbc.anchor = GridBagConstraints.WEST; // Alinear los siguientes componentes a la izquierda
    gbc.gridwidth = 1;

    // Campos del formulario
    addComponent(gbc, "Nombre:", txtNombre = new JTextField(20), 1);
    addComponent(gbc, "Apellido:", txtApellido = new JTextField(20), 2);
    addComponent(gbc, "Correo:", txtEmail = new JTextField(20), 3);
    addComponent(gbc, "Teléfono:", txtTelefono = new JTextField(20), 4);
    addComponent(gbc, "Contraseña:", txtPassword = new JPasswordField(20), 5);
    addComponent(gbc, "Confirmar Contraseña:", txtConfirmarPassword = new JPasswordField(20), 6);

    // Botones
    gbc.gridy = 7;
    gbc.gridx = 0;
    btnRegistrar = new JButton("Registrar");
    Tema.estiloBoton(btnRegistrar, Tema.COLOR_PRIMARIO, Tema.COLOR_BLANCO);
    add(btnRegistrar, gbc);

    gbc.gridx = 1;
    btnCancelar = new JButton("Cancelar");
    Tema.estiloBoton(btnCancelar, Tema.COLOR_GRIS_CLARO, Tema.COLOR_BLANCO);
    add(btnCancelar, gbc);
  }

  // Método reutilizable para añadir componentes
  private void addComponent(GridBagConstraints gbc, String label, JComponent component, int row) {
    gbc.gridy = row;
    gbc.gridx = 0;
    JLabel lblComponent = new JLabel(label);
    Tema.estiloLabel(lblComponent, Tema.COLOR_NEGRO, "normal");
    add(lblComponent, gbc);

    gbc.gridx = 1;
    Tema.estiloTexto(component, 450, 20);
    add(component, gbc);
  }

  // Getters para obtener los valores ingresados
  public String getNombre() {
    return txtNombre.getText();
  }

  public String getApellido() {
    return txtApellido.getText();
  }

  public String getEmail() {
    return txtEmail.getText();
  }

  public String getTelefono() {
    return txtTelefono.getText();
  }

  public String getPassword() {
    return new String(txtPassword.getPassword());
  }

  public String getConfirmarPassword() {
    return new String(txtConfirmarPassword.getPassword());
  }

  public JButton getBtnRegistrar() {
    return btnRegistrar;
  }

  public JButton getBtnCancelar() {
    return btnCancelar;
  }
}
