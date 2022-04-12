package service.product;

import config.ConnectionJDBC;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    public List<Product> selectAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionJDBC.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("select product.id as id,\n" +
                     "       product.name as name,\n" +
                     "       product.price as price,\n" +
                     "       product.quantity as quantity,\n" +
                     "       product.color as color,\n" +
                     "       product.description as description,\n" +
                     "       c.id as category_id,\n" +
                     "       c.name as categoryname\n" +
                     "from  product join productmanager.category c on c.id = product.id_category;")) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                int id_category = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("categoryname");
                Category category = new Category(id_category, categoryName);
                Product product = new Product(id, name, price, quantity, color, description,category);
                 products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void  insert(Product product) throws SQLException {
        System.out.println();
        try(Connection connection = ConnectionJDBC.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into product(name, price, quantity, color, description, id_category) VALUES (?,?,?,?,?,?);");)
        {
            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getCategory().getId());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public Product getById(int id) {
        Product product = null;
        try(Connection connection = ConnectionJDBC.getConnect();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product join productmanager.category c on c.id = product.id_category where product.id = ?;")) {
             preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                int id_category = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("categoryname");
                Category category = new Category(id_category, categoryName);
                 product = new Product(id, name, price, quantity, color, description,category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        return false;
    }

//    @Override
//    public boolean delete(int id) throws SQLException {
//        boolean rowDeleted;
//        try(Connection connection = ConnectionJDBC.getConnect();
//            PreparedStatement statement = connection.prepareStatement("delete from product where id = 1;");) {
//            statement.setInt(1,id);
//            rowDeleted = statement.executeUpdate()>0;
//        }
//        return rowDeleted;
//    }

//    @Override
//    public boolean update(Product product) throws SQLException {
//        boolean rowUpdated;
//        try (Connection connection = ConnectionJDBC.getConnect();
//             PreparedStatement statement = connection.prepareStatement();){
//            statement.setString(1,product.getName());
//            statement.setString(2,classes.getDescription());
//            statement.setInt(3,classes.getId());
//
//            rowUpdated = statement.executeUpdate()>0;
//        }
//        return rowUpdated;
//    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}