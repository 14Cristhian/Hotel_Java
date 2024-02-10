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

public class Habitacion {

    String dbUrl = "jdbc:postgresql://localhost:5432/hotel";
    String usuario = "postgres";
    String contrasena = "cristhian14";

    String num_habitacion;
    String tipo;
    String precio;
    String camas;

    public void habitaciones() throws ClassNotFoundException, SQLException {
        Habitacion habitacion = new Habitacion();
        boolean salir = false;

        while (!salir) {
            String opcionStr = JOptionPane.showInputDialog(
                    "HABITACIONES\n"
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
                        habitacion.Busacar();
                        break;
                    case 2:
                        habitacion.Actualizar();
                        break;
                    case 3:
                        habitacion.Delete();
                        break;
                    case 4:
                        habitacion.Ingresar();
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

    

    public void Busacar() throws ClassNotFoundException, SQLException {
        String dbUrlc = "jdbc:postgresql://localhost/hotel";
        String usuario = "postgres";
        String contrasena = "cristhian14";
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
// Código SQL:
        try ( Statement s = c.createStatement()) {
            // Código SQL:
            ResultSet r = s.executeQuery("SELECT num_habitacion,tipo,precio,camas " + "FROM habitaciones ");

            while (r.next()) {

                JOptionPane.showMessageDialog(null, "Numero Habitacion: " + r.getString("num_habitacion") + "\n"
                        + "Tipo: " + r.getString("tipo") + "\n"
                        + "Precio: " + r.getString("precio") + "\n"
                        + "Camas: " + r.getString("camas"));
            }
        }

    }

    public void Actualizar() throws ClassNotFoundException, SQLException {
        num_habitacion = JOptionPane.showInputDialog("Ingresar el numero de habitacion para actualizar");

        PreparedStatement select, update;
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        // Verificar si la habitación existe antes de intentar actualizarla
        select = c.prepareStatement("SELECT num_habitacion, tipo, precio, camas FROM habitaciones WHERE num_habitacion = ?");
        select.setString(1, num_habitacion);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {
            tipo = JOptionPane.showInputDialog("Ingresar nuevo tipo");
            precio = JOptionPane.showInputDialog("Ingresar nuevo precio");
            camas = JOptionPane.showInputDialog("Ingresar nuevo número de camas");
            // La habitación existe, obtener los valores actuales de la base de datos
            String tipoActual = resultSet.getString("tipo");
            String precioActual = resultSet.getString("precio");
            String camasActual = resultSet.getString("camas");

            // Verificar si los nuevos valores no son nulos o vacíos y actualizarlos
            if (tipo != null && !tipo.isEmpty()) {
                tipoActual = tipo;
            }
            if (precio != null && !precio.isEmpty()) {
                precioActual = precio;
            }
            if (camas != null && !camas.isEmpty()) {
                camasActual = camas;
            }

            // Realizar la actualización con los nuevos valores o los valores actuales si no se cambiaron
            update = c.prepareStatement("UPDATE habitaciones SET tipo = ?, precio = ?, camas = ? WHERE num_habitacion = ?");
            update.setString(1, tipoActual);
            update.setString(2, precioActual);
            update.setString(3, camasActual);
            update.setString(4, num_habitacion);
            update.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos de la habitación actualizados con éxito.");
        } else {
            // La habitación no existe, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "La habitación no existe. No se pueden actualizar los datos.");
        }

        resultSet.close();
        select.close();
        c.close();
    }

    public void Delete() throws ClassNotFoundException, SQLException {
        num_habitacion = JOptionPane.showInputDialog("Ingresar num_habitacion para borrar");
        PreparedStatement select, delete;
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        select = c.prepareStatement("SELECT num_habitacion FROM habitaciones WHERE num_habitacion = ?");
        select.setString(1, num_habitacion);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {
            delete = c.prepareStatement("DELETE FROM habitaciones WHERE num_habitacion = ?");
            delete.setString(1, num_habitacion);
            delete.executeUpdate();
            JOptionPane.showMessageDialog(null, "Habitación eliminada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "La habitación no existe.");
        }

        resultSet.close();
        select.close();
        c.close();
    }

    public void Ingresar() throws ClassNotFoundException, SQLException {
        num_habitacion = JOptionPane.showInputDialog("Ingresar num_habitacion ");
        tipo = JOptionPane.showInputDialog("Ingresar tipo");
        precio = JOptionPane.showInputDialog("Ingresar precio");
        camas = JOptionPane.showInputDialog("Ingresar camas ");
        PreparedStatement select, insert;
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        select = c.prepareStatement("SELECT num_habitacion FROM habitaciones WHERE num_habitacion = ?");
        select.setString(1, num_habitacion);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {

            JOptionPane.showMessageDialog(null, "El número de habitación ya existe. No se puede ingresar.");
        } else {
            // La habitación no existe, proceder con la inserción
            insert = c.prepareStatement("INSERT INTO habitaciones VALUES (?,?,?,?)");
            insert.setString(1, num_habitacion);
            insert.setString(2, tipo);
            insert.setString(3, precio);
            insert.setString(4, camas);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(null, "Habitación ingresada con éxito.");
        }

        resultSet.close();
        select.close();
        c.close();
    }

}
