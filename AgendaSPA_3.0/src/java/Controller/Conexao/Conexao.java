package Controller.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    public Connection conexao() throws ClassNotFoundException{
        Connection conexao = null;
        String ulr = "jdbc:mysql://localhost:3306/dbteste?useTimeZone=true&serverTimezone="
                + "UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "Jonas_$teindorff";
        String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
        try {
            conexao = DriverManager.getConnection(ulr, user, password);
                    System.out.println("Conexão Bem Sucedida");
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Falha na conexão!"+ ex);
        }
        return conexao;
    }
}
