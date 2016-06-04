package ws;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.Categoria;
import model.Movimento;

/**
 *
 * @author PhelipeRocha
 */
@WebService(serviceName = "FinancasService")
public class FinancasService {

    /**
     * This is a sample web service operation
     * @return 
     */
    @WebMethod(operationName = "listarMovimentos")
    public ArrayList<Movimento> listarMovimentos() {
        
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String query = "SELECT M.id, C.nome as categoria, M.nome, M.valor, M.data, M.frequencia FROM movimento M JOIN categoria C ON M.id_cat = C.id";
        String userName = "root", password = "";
        ArrayList<Movimento> listMovimento = new ArrayList();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String categoria = rs.getString("categoria");
                String nome = rs.getString("nome");
                double valor = rs.getDouble("valor");
                String data = rs.getDate("data").toString();
                boolean frequencia = rs.getBoolean("frequencia");
                
                Movimento movimento = new Movimento(id, categoria, nome, valor, data, frequencia);
                listMovimento.add(movimento);
            }
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return listMovimento;

    }
    
    @WebMethod(operationName = "listarCategorias")
    public ArrayList<Categoria> listarCategorias() {
        
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String query = "SELECT id, nome FROM categoria";
        String userName = "root", password = "";
        ArrayList<Categoria> listCategoria = new ArrayList();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                
                Categoria categoria = new Categoria(id, nome);
                listCategoria.add(categoria);
            }
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return listCategoria;

    }

    /**
     * Web service operation
     * @param type
     * @return 
     */
    @WebMethod(operationName = "getTotal")
    public double getTotal(@WebParam(name = "type") String type) {
        
        double total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query;
        
        switch(type) {
            case "receita":
                query = "SELECT sum(valor) as total FROM movimento WHERE valor > 0";
                break;
            case "despesa":
                query = "SELECT sum(valor) as total FROM movimento WHERE valor < 0";
                break;
            default:
                query = "SELECT sum(valor) as total FROM movimento";
                break;
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            rs.first();
            total = rs.getDouble("total");
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    /**
     * Web service operation
     * @param nome
     * @param frequencia
     * @param categoria
     * @param valor
     * @param data
     * @return 
     */
    @WebMethod(operationName = "inserirMovimento")
    public int inserirMovimento(
            @WebParam(name = "nome") String nome,
            @WebParam(name = "frequencia") boolean frequencia,
            @WebParam(name = "categoria") int categoria,
            @WebParam(name = "valor") int valor,
            @WebParam(name = "data") String data
    ) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query = "INSERT INTO movimento (id, id_cat, nome, valor, data, frequencia) VALUES (NULL, " + categoria + ", '" + nome + "', " + valor + ", '" + data + "', " + frequencia + ")";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    @WebMethod(operationName = "inserirCategoria")
    public int inserirCategoria(
            @WebParam(name = "nome") String nome
    ) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query = "INSERT INTO categoria (id, nome) VALUES (NULL, '" + nome + "')";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    @WebMethod(operationName = "deletarMovimento")
    public int deletarMovimento(@WebParam(name = "id") int id) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query = "DELETE FROM movimento WHERE id = " + id;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    @WebMethod(operationName = "deletarCategoria")
    public int deletarCategoria(@WebParam(name = "id") String id) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query = "DELETE FROM categoria WHERE id = " + id;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    /**
     * Web service operation
     * @param id
     * @param nome
     * @param frequencia
     * @param categoria
     * @param valor
     * @param data
     * @return 
     */
    @WebMethod(operationName = "atualizarMovimento")
    public int atualizarMovimento(
            @WebParam(name = "id") int id,
            @WebParam(name = "col") int col,
            @WebParam(name = "value") String value
    ) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        String query = "";
        
        switch(col) {
            case 2:
                query = "UPDATE movimento SET data = '" + value + "' WHERE id = " + id + ";";
                break;
            case 3:
                query = "UPDATE movimento SET nome = '" + value + "' WHERE id = " + id + ";";
                break;
            case 4:
                query = "UPDATE movimento SET id_cat = (SELECT id FROM categoria WHERE nome = '" + value + "') WHERE id = " + id + ";";
                break;
            case 5:
                query = "UPDATE movimento SET frequencia = '" + value + "' WHERE id = " + id + ";";
                break;
            case 6:
                query = "UPDATE movimento SET valor = '" + value + "' WHERE id = " + id + ";";
                break;
            default:
                query = "";
                break;
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
    /**
     * Web service operation
     * @param id
     * @param nome
     * @return 
     */
    @WebMethod(operationName = "atualizarCategoria")
    public int atualizarCategoria(
            @WebParam(name = "id") String id,
            @WebParam(name = "nome") String nome
    ) {
        
        int total = 0;
        String dbUrl = "jdbc:mysql://localhost/financasDB?zeroDateTimeBehavior=convertToNull";
        String dbClass = "com.mysql.jdbc.Driver";
        String userName = "root", password = "";
        
        String query = "UPDATE categoria SET nome = '" + nome + "' WHERE id = " + id + ";";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl, userName, password);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);

            total = rs;
            
            con.close();
        }
        
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        
        return total;
    }
    
}
