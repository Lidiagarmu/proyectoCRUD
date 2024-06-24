package org.example.platzi.repository;
import org.example.platzi.model.Employee;
import org.example.platzi.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {



    //------------------------CLASE CON MÉTODOS BUSCARUNO, BUSCARTODOS, GUARDAR, BORRAR...
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }


    @Override //metodo para leer todos
    public List<Employee> findAll() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        try (Connection myCon = getConnection();
                Statement myStat = getConnection().createStatement();
             ResultSet myRes = myStat.executeQuery("SELECT * FROM employees")) {
            while (myRes.next()) {
                Employee e = createEmployee(myRes); //el metodo createEmployee() lo extrajimos. Lo tenemos abajo de todo. Para que fuera mas legible
                employees.add(e); //va agregando cada empleado mientras recorre para despues poderlo retornar
            }
        }
        return employees;
    }


    @Override //metodo para leer uno
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;

        try (Connection myCon = getConnection();
                PreparedStatement myStat = getConnection().prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            myStat.setInt(1, id);
            try (ResultSet myRes = myStat.executeQuery()) {

                if (myRes.next()) {
                    employee = createEmployee(myRes); //el metodo createEmployee() lo extrajimos. Lo tenemos abajo de todo. Para que fuera mas legible

                }
            }
        }
        return employee;
    }


    @Override //metodo para insertar o guardar empleado
    public void save(Employee employee) throws SQLException {
        String sql;
        //si  id es null y contiene un valor numerico mayor que 0, entonces existe id y es un update
        if(employee.getId()!= null && employee.getId()>0){
            sql = "UPDATE employees SET first_name=?, pa_surname=?, ma_surname=?, email=?, salary=?, curp=? WHERE id=?";
            //sino no existe y se inserta
        }else {
            sql = "INSERT INTO employees (first_name, pa_surname, ma_surname, email,salary,curp) VALUES (?,?,?,?,?,?)";
        }
        try(Connection myCon = getConnection();
                PreparedStatement myStat = getConnection().prepareStatement(sql)){
            myStat.setString(1,employee.getFirst_name());
            myStat.setString(2, employee.getPa_surname());
            myStat.setString(3,employee.getMa_surname());
            myStat.setString(4,employee.getEmail());
            myStat.setFloat(5,employee.getSalary());
            myStat.setString(6, employee.getCurp());
            if(employee.getId() != null && employee.getId()>0) {
                myStat.setInt(7,employee.getId());
            }
            myStat.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override //metodo para borrar empleado
    public void delete(Integer id) {

        try(PreparedStatement myStat = getConnection().prepareStatement("DELETE FROM employees WHERE id=?")){
            myStat.setInt(1,id);
            myStat.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Employee  createEmployee(ResultSet myRes) throws SQLException {
        Employee e = new Employee();
        e.setId(myRes.getInt("id"));
        e.setFirst_name(myRes.getString("first_name"));
        e.setPa_surname(myRes.getString("pa_surname"));
        e.setMa_surname(myRes.getString("ma_surname"));
        e.setEmail(myRes.getString("email"));
        e.setSalary(myRes.getFloat("salary"));
        e.setCurp(myRes.getString("curp"));
        return e;
    }


}
