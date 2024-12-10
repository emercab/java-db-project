/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author FAMILIA CABRERA
 */
import controllers.LoginController;
import views.LoginView;

public class Main {

  public static void main(String[] args) {
    LoginView vista = new LoginView();
    new LoginController(vista);
  }
}
