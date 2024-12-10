/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author FAMILIA CABRERA
 */
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

      // Método para generar el hash de una contraseña
    public static String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt(12); // Factor de costo 12
        return BCrypt.hashpw(plainPassword, salt);
    }

    // Método para verificar si la contraseña coincide con el hash
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
