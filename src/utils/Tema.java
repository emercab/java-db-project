/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author FAMILIA CABRERA
 */
import java.awt.*;
import javax.swing.*;

public class Tema {

  // Colores personalizados
  public static final Color COLOR_PRIMARIO = new Color(19, 141, 117);     // Verde oscuro
  public static final Color COLOR_ROJO = new Color(171, 68, 89);          // Rojo
  public static final Color COLOR_AMARILLO = new Color(250, 218, 122);    // Amarillo
  public static final Color COLOR_GRIS_CLARO = new Color(166, 174, 191);  // Gris claro
  public static final Color COLOR_AZUL = new Color(52, 152, 219);         // Azul
  public static final Color COLOR_BLANCO = Color.WHITE;
  public static final Color COLOR_NEGRO = new Color(30, 32, 30);          // Negro

  // Tipos de letra
  public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 22);
  public static final Font FUENTE_SUBTITULO = new Font("Arial", Font.PLAIN, 18);
  public static final Font FUENTE_TEXTO = new Font("Arial", Font.PLAIN, 14);
  public static final Font FUENTE_BOTON = new Font("Arial", Font.BOLD, 14);

  // Estilo para botones con efecto hover
  public static void estiloBoton(JButton boton, Color colorFondo, Color colorTexto) {
    boton.setBackground(colorFondo);
    boton.setForeground(colorTexto);
    boton.setFont(FUENTE_BOTON);
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);

    // Efecto Hover
    boton.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        if (boton.isEnabled()) {
          boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
          boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent evt) {
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
    });
  }

  // Estilo para labels
  public static void estiloLabel(JLabel label, Color colorTexto, String tipo) {
    label.setForeground(colorTexto);
    switch (tipo) {
      case "titulo":
        label.setFont(FUENTE_TITULO);
        break;
      case "subtitulo":
        label.setFont(FUENTE_SUBTITULO);
        break;
      default:
        label.setFont(FUENTE_TEXTO);
    }
  }

  // Estilo para JTextField y JTextArea
  public static void estiloTexto(JComponent componente, int ancho, int altura) {
    componente.setFont(FUENTE_TEXTO);
    componente.setBackground(COLOR_BLANCO);
    componente.setForeground(COLOR_NEGRO);
    if (componente instanceof JTextField || componente instanceof JTextArea) {
      componente.setBorder(BorderFactory.createLineBorder(COLOR_GRIS_CLARO, 1));
      componente.setPreferredSize(new Dimension(ancho, altura)); // Ajustar ancho y altura
    }
  }
}
