/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.DAO;

import Controller.Conexao.Conexao;
import Model.DTO.ServicosDTO;
import Servlets.LoginServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas Steindorff
 */
public class ServicosDAO {
     public void consultarServicos(ServicosDTO servicosDTO) {
    try {
            Connection conexao = new Conexao().conexao();
            String sql = "SELECT * FROM tbservicos ORDER BY nomeServico";
            PreparedStatement pstmData = conexao.prepareStatement(sql);
            ResultSet rs = pstmData.executeQuery();

            while (rs.next()) {
                ServicosDTO servico = new ServicosDTO();
                servico.setId(rs.getInt(1));
                servico.setNomeServico(rs.getString(2));
                servico.setDuracao(rs.getTime(3));
                servico.setValor(rs.getBigDecimal(4));
                LoginServlet.listaServicos.add(servico);
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Falha DateDAO Conexao: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Falha DateDAO Statement: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
