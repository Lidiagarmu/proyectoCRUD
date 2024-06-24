package org.example.platzi.main;

import org.example.platzi.util.DatabaseConnection;
import org.example.platzi.model.Employee;
import org.example.platzi.repository.EmployeeRepository;
import org.example.platzi.repository.Repository;
import org.example.platzi.view.SwingApp;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;

public class Main {
     public static void main(String[] args) throws SQLException {

        SwingApp app = new SwingApp();
        app.setVisible(true);





















         //Aqui aunque parazca la misma conexion en consola, no lo es. Se está implementando un pool por detrás. varias conexiones
         //asi optimizamos los recursos

         /*
           System.out.println("LISTANDO TODOS----------");
         Repository<Employee> repo = new EmployeeRepository();
         repo.findAll().forEach(System.out::println);

         System.out.println("---.BUSCANDO POR ID------");
         System.out.println(repo.getById(2));
          */



    }
}




