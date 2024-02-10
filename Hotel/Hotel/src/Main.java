/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author moral
 */
import javax.swing.JOptionPane;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Reserva reserva = new Reserva();
        Cliente cliente = new Cliente();
        Habitacion habitacion = new Habitacion();
        boolean salir = false;

        while (!salir) {
            String opcionStr = JOptionPane.showInputDialog(
                    "MENU\n"
                    + "1. Clientes\n"
                    + "2. Habitaciones\n"
                    + "3. Reserva\n"
                    + "4. Salir\n\n"
                    + "Escribe el número de la opción deseada:"
            );

            if (opcionStr == null) {
                // El usuario presionó Cancelar o cerró la ventana
                salir = true;
                continue;
            }

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        cliente.clientes();
                        break;
                    case 2:
                        habitacion.habitaciones();
                        break;
                    case 3:
                        reserva.reservar();
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, elige una opción válida (1-4).");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
            }
        }
    }

}
