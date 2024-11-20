package org.actividadut02.application;

import org.actividadut02.dataaccess.ClassicModelsCP;
import org.actividadut02.dataaccess.ProductDataAccess;
import org.actividadut02.dataaccess.ProductDataAccessImpl;
import org.actividadut02.entities.Product;
import org.actividadut02.enums.ProductLine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/* Clase para probar los metodos
*
* */
public class ProductTests {

    public static void main(String[] args) throws SQLException {

//    Comprobación del metodo findAll
        ProductDataAccess productDataAccess = new ProductDataAccessImpl();
        List<Product> products = productDataAccess.findAll();
        products.forEach(System.out::println);

//    Comprobación del metodo save
        try(Connection connection = ClassicModelsCP.getInstance().getConnection()) {
            connection.setAutoCommit(false);

        Product product = new Product();
        product.setProductCode("S10_1678");
        product.setProductName("1969 Harley Davidson Ultimate Chopper");
        product.setProductLine(ProductLine.valueOf("MOTORCYCLES"));
        product.setProductVendor("Min Lin Diecast");
        product.setQuantityInStock(10);
        product.setBuyPrice(48.81);
        product.setMSRP(95.70);

        productDataAccess.save(product);
        connection.commit();
        }catch (SQLException e) {
            System.out.println("No se pudo insertar el producto: " + e.getMessage());
        }

    }



}
