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
import utils.Tema;

public abstract class BaseView extends JFrame {

  // Constructor
  public BaseView(String titulo, int ancho, int alto) {
    // Configuración básica
    setTitle(titulo);
    setSize(ancho, alto);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana
    setBackground(Tema.COLOR_BLANCO); // Fondo blanco
    getContentPane().setBackground(Tema.COLOR_BLANCO);
    setLayout(new BorderLayout()); // Layout por defecto (puede cambiarse por vista)

    inicializarComponentes(); // Llama al método abstracto
  }

  // Método abstracto para inicializar componentes propios de cada vista
  protected abstract void inicializarComponentes();
}
