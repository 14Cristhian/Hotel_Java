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

public class Cliente {

    String dbUrl = "jdbc:postgresql://localhost:5432/hotel";
    String usuario = "postgres";
    String contrasena = "cristhian14";

    String num_cliente;
    String nombre;
    String direccion;
    String telefono1;
    String telefono2;
    String email;
    String nacionalidad;

    public void clientes() throws ClassNotFoundException, SQLException {
        Cliente cliente = new Cliente();
        boolean salir = false;

        while (!salir) {
            String opcionStr = JOptionPane.showInputDialog(
                    "CLIENTES\n"
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
                        cliente.Buscar();
                        break;
                    case 2:
                        cliente.Actualizar();
                        break;
                    case 3:
                        cliente.Delete();
                        break;
                    case 4:
                        cliente.Ingresar();
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
            ResultSet r = s.executeQuery("SELECT num_cliente, nombre, direccion,telefono1,telefono2,email,nacionalidad " + "FROM clientes ");

            while (r.next()) {

                JOptionPane.showMessageDialog(null, "1"
                        + "Numero Cliente: " + r.getString("num_cliente") + "\n"
                        + "Nombre: " + r.getString("nombre") + "\n"
                        + "Direccion: " + r.getString("direccion") + "\n"
                        + "Telefono 1: " + r.getString("telefono1") + "\n"
                        + "Telefono 2: " + r.getString("telefono2") + "\n"
                        + "Email: " + r.getString("email") + "\n"
                        + "Nacionalidad: " + r.getString("nacionalidad"));
            }
        }

    }

    public void Actualizar() throws ClassNotFoundException, SQLException {
        num_cliente = JOptionPane.showInputDialog("Ingresar el numero de cliente  para actualizar");

        PreparedStatement select, update;
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        // Verificar si la habitación existe antes de intentar actualizarla
        select = c.prepareStatement("SELECT num_cliente, nombre, direccion, telefono1 , telefono2, email, nacionalidad FROM clientes WHERE num_cliente = ?");
        select.setString(1, num_cliente);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {
            nombre = JOptionPane.showInputDialog("Ingresar nuevo nombre");
            direccion = JOptionPane.showInputDialog("Ingresar nueva direccion");
            telefono1 = JOptionPane.showInputDialog("Ingresar nuevo telefono1");
            telefono2 = JOptionPane.showInputDialog("Ingresar nuevo telefono2");
            email = JOptionPane.showInputDialog("Ingresar nuevo email");
            nacionalidad = JOptionPane.showInputDialog("Ingresar nueva nacionalidad");
            // La habitación existe, obtener los valores actuales de la base de datos
            String nombreActual = resultSet.getString("nombre");
            String direccionActual = resultSet.getString("direccion");
            String telefono1Actual = resultSet.getString("telefono1");
            String telefono2Actual = resultSet.getString("telefono2");
            String EmailActual = resultSet.getString("email");
            String NacionalidadActual = resultSet.getString("nacionalidad");

            // Verificar si los nuevos valores no son nulos o vacíos y actualizarlos
            if (nombre != null && !nombre.isEmpty()) {
                nombreActual = nombre;
            }
            if (direccion != null && !direccion.isEmpty()) {
                direccionActual = direccion;
            }
            if (telefono1 != null && !telefono1.isEmpty()) {
                telefono1Actual = telefono1;
            }
            if (telefono2 != null && !telefono2.isEmpty()) {
                telefono2Actual = telefono2;
            }
            if (email != null && !email.isEmpty()) {
                EmailActual = email;
            }
            if (nacionalidad != null && !nacionalidad.isEmpty()) {
                NacionalidadActual = nacionalidad;
            }
            // Realizar la actualización con los nuevos valores o los valores actuales si no se cambiaron
            update = c.prepareStatement("UPDATE clientes SET nombre = ?, direccion = ?, telefono1 = ?, telefono2 = ?, email = ?, nacionalidad = ? WHERE num_cliente = ?");
            update.setString(1, nombreActual);
            update.setString(2, direccionActual);
            update.setString(3, telefono1Actual);
            update.setString(4, telefono2Actual);
            update.setString(5, EmailActual);
            update.setString(6, NacionalidadActual);
            update.setString(7, num_cliente);
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
        num_cliente = JOptionPane.showInputDialog("Ingresar numero de cliente para borrar");
        PreparedStatement select, delete;
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        select = c.prepareStatement("SELECT num_cliente FROM clientes WHERE num_cliente = ?");
        select.setString(1, num_cliente);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {
            delete = c.prepareStatement("DELETE FROM clientes WHERE num_cliente = ?");
            delete.setString(1, num_cliente);
            delete.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente  eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "El cliente no existe.");
        }

        resultSet.close();
        select.close();
        c.close();
    }

    public void Ingresar() throws ClassNotFoundException, SQLException {

        num_cliente = JOptionPane.showInputDialog("Ingresar el numero de cliente ");
        nombre = JOptionPane.showInputDialog("Ingresar nombre");
        direccion = JOptionPane.showInputDialog("Ingresar direccion");
        telefono1 = JOptionPane.showInputDialog("Ingresar telefono1 ");
        telefono2 = JOptionPane.showInputDialog("Ingresar telefono2 ");
        email = JOptionPane.showInputDialog("Ingresar email ");
        nacionalidad = JOptionPane.showInputDialog("Ingresar nacionalidad ");
        PreparedStatement select, insert;
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

        select = c.prepareStatement("SELECT num_cliente FROM clientes WHERE num_cliente = ?");
        select.setString(1, num_cliente);
        ResultSet resultSet = select.executeQuery();

        if (resultSet.next()) {

            JOptionPane.showMessageDialog(null, "El número de cliente ya existe. No se puede ingresar.");
        } else {
            // La habitación no existe, proceder con la inserción
            insert = c.prepareStatement("INSERT INTO clientes VALUES (?,?,?,?,?,?,?)");
            insert.setString(1, num_cliente);
            insert.setString(2, nombre);
            insert.setString(3, direccion);
            insert.setString(4, telefono1);
            insert.setString(5, telefono2);
            insert.setString(6, email);
            insert.setString(7, nacionalidad);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente ingresada con éxito.");
        }

        resultSet.close();
        select.close();
        c.close();
    }
}
