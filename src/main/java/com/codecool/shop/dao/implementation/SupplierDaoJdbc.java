package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SupplierDaoJdbc implements SupplierDao {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private static SupplierDaoJdbc ourInstance = new SupplierDaoJdbc();

    public static SupplierDaoJdbc  getInstance() {
        return ourInstance;
    }

    private SupplierDaoJdbc (){}

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO product (" +
                "name, " +
                "description, " +
                "department, " +
                "VALUES ('"
                + supplier.getName() + "', '"
                + supplier.getDescription() + "', '"
                + "');";
        executeQuery(query);
    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT * FROM supplier " +
                "WHERE id =" + String.valueOf(id) + ";";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                return supplier;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Supplier find(String name) {
        String query = "SELECT * FROM product_category " +
                "WHERE name =" + name + ";";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                return supplier;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void remove(int id){
        String query = "DELETE * FROM supplier " +
                "WHERE id =" + id + ";";
        executeQuery(query);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM supplier;";

        List<Supplier> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                resultList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
