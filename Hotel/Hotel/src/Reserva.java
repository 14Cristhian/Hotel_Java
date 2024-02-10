/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author moral
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Reserva {

    String dbUrl = "jdbc:postgresql://localhost:5432/hotel";
    String usuario = "postgres";
    String contrasena = "cristhian14";

    String num_habitacion;
    String num_cliente;
    String fecha_llegada;
    String fecha_salida;
    String id_reserva;

    public void reservar() throws ClassNotFoundException, SQLException {
        Reserva reserva = new Reserva();
        boolean salir = false;

        while (!salir) {
            String opcionStr = JOptionPane.showInputDialog(
                    "-----RESERVAS-----\n"
                    + "1. Buscar\n"
                    + "2. Actualizar\n"
                    + "3. Eliminar\n"
                    + "4. Ingresar\n"
                    + "5. Salir\n\n"
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
                        reserva.Buscar();
                        break;
                    case 2:
                        reserva.ActualizarReserva();
                        break;
                    case 3:
                       reserva.Delete();
                        break;
                    case 4:
                       
                        reserva.InsertarReserva();
                        
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, elige una opción válida (1-5).");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
            }
        }
    }

    public void Buscar() throws ClassNotFoundException, SQLException {
        String dbUrlc = "jdbc:postgresql://localhost/hotel";
        String usuario = "postgres";
        String contrasena = "cristhian14";
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
// Código SQL:
        try ( Statement s = c.createStatement()) {
            // Código SQL:
            ResultSet r = s.executeQuery("SELECT num_habitacion,num_cliente,fecha_llegada,fecha_salida,id_reserva " + "FROM reservas ");

            while (r.next()) {

                JOptionPane.showMessageDialog(null, "Id Reserva: " + r.getString("id_reserva") + "\n"
                        + "Numero Habitacion: " + r.getString("num_habitacion") + "\n"
                        + "Numero Cliente: " + r.getString("num_cliente") + "\n"
                        + "Fecha de  Llegada: " + r.getString("fecha_llegada") + "\n"
                        + "Fecha  de Salida: " + r.getString("fecha_salida"));
            }
        }

    }

   public void InsertarReserva() {
        try {
            String num_cliente = obtenerNumeroClienteExistente();
            String num_habitacion = obtenerNumeroHabitacionExistente();

            if (num_cliente != null && num_habitacion != null) {
                id_reserva = JOptionPane.showInputDialog("Ingresar id de la reserva");
                 fecha_llegada = JOptionPane.showInputDialog("Ingresar hora de llegada");
                 fecha_salida = JOptionPane.showInputDialog("Ingresar hora de salida");

                Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
                PreparedStatement insert;
                insert = c.prepareStatement("INSERT INTO reservas (num_cliente, num_habitacion, fecha_llegada, fecha_salida, id_reserva) VALUES (?, ?, ?, ?,?)");
                insert.setString(1, num_cliente);
                insert.setString(2, num_habitacion);
                insert.setString(3, fecha_llegada);
                insert.setString(4, fecha_salida);
                insert.setString(5, id_reserva);
                insert.executeUpdate();
                JOptionPane.showMessageDialog(null, "Reserva insertada con éxito.");
                c.close();
            } else {
                JOptionPane.showMessageDialog(null, "El número de reserva o habitación no existe. No se puede hacer la reserva.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerNumeroClienteExistente() {
        String num_cliente = null;
        try {
            String numClienteInput = JOptionPane.showInputDialog("Ingresar número de cliente");

            Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
            PreparedStatement select = c.prepareStatement("SELECT num_cliente FROM clientes WHERE num_cliente = ?");
            select.setString(1, numClienteInput);
            ResultSet resultSet = select.executeQuery();

            if (resultSet.next()) {
                num_cliente = resultSet.getString("num_cliente");
            }

            resultSet.close();
            select.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar número de cliente en la base de datos.");
        }
        return num_cliente;
    }

    private String obtenerNumeroHabitacionExistente() {
        String num_habitacion = null;
        try {
            String numHabitacionInput = JOptionPane.showInputDialog("Ingresar número de habitación");

            Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
            PreparedStatement select = c.prepareStatement("SELECT num_habitacion FROM habitaciones WHERE num_habitacion = ?");
            select.setString(1, numHabitacionInput);
            ResultSet resultSet = select.executeQuery();

            if (resultSet.next()) {
                num_habitacion = resultSet.getString("num_habitacion");
            }

            resultSet.close();
            select.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar número de habitación en la base de datos.");
        }
        return num_habitacion;
    }

    
    public void ActualizarReserva() {
        id_reserva = JOptionPane.showInputDialog("Ingresar el numero de la revarda para actualizar");
    try {
        String num_cliente = obtenerNumeroClienteExistente();
        String num_habitacion = obtenerNumeroHabitacionExistente();

        if (num_cliente != null && num_habitacion != null) {
            Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

            // Verificar si la reserva existe
            PreparedStatement select;
            select = c.prepareStatement("SELECT * FROM reservas WHERE num_cliente = ? AND num_habitacion = ? AND id_reserva = ?");
            select.setString(1, num_cliente);
            select.setString(2, num_habitacion);
            select.setString(3, id_reserva);
            ResultSet resultSet = select.executeQuery();

            if (resultSet.next()) {
                // Mostrar los detalles actuales de la reserva
                String id_reserva = resultSet.getString("id_reserva");
                String fecha_llegada_actual = resultSet.getString("fecha_llegada");
                String fecha_salida_actual = resultSet.getString("fecha_salida");
                
                JOptionPane.showMessageDialog(null, "Número de Cliente: " + num_cliente + "\n"
                        + "Número de Habitación: " + num_habitacion + "\n"
                        + "ID de Reserva: " + id_reserva + "\n"
                        + "Fecha de Llegada Actual: " + fecha_llegada_actual + "\n"
                        + "Fecha de Salida Actual: " + fecha_salida_actual);

               
          
                String nuevaFechaLlegada = JOptionPane.showInputDialog("Ingresar nueva fecha de llegada");
                String nuevaFechaSalida = JOptionPane.showInputDialog("Ingresar nueva fecha de salida");

                // Realizar la actualización
                PreparedStatement update;
                update = c.prepareStatement("UPDATE reservas SET  fecha_llegada = ?, fecha_salida = ? WHERE num_cliente = ?  AND num_habitacion = ? AND id_reserva = ?");
                update.setString(1, nuevaFechaLlegada);
                update.setString(2, nuevaFechaSalida);
                update.setString(3, num_cliente);
                update.setString(4, num_habitacion);
                update.setString(5, id_reserva);
                update.executeUpdate();

                JOptionPane.showMessageDialog(null, "Reserva actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No existe una reserva para el número de cliente y habitación proporcionados.");
            }

            resultSet.close();
            select.close();
            c.close();
        } else {
            JOptionPane.showMessageDialog(null, "El número de cliente o habitación no existe. No se puede editar la reserva.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
      public void Delete() throws ClassNotFoundException, SQLException {
        id_reserva = JOptionPane.showInputDialog("Ingresar id de la reserva para borrar");
        PreparedStatement select, delete;
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        select = c.prepareStatement("SELECT id_reserva FROM reservas WHERE id_reserva = ?");
        select.setString(1, id_reserva);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {
            delete = c.prepareStatement("DELETE FROM reservas WHERE id_reserva = ?");
            delete.setString(1, id_reserva);
            delete.executeUpdate();
            JOptionPane.showMessageDialog(null, "Reserva eliminada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "La reserva no existe.");
        }

        resultSet.close();
        select.close();
        c.close();
    }

    
}
