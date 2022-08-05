/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.DAO;

import Controller.Conexao.Conexao;
import Model.DTO.HorarioDTO;
import Model.DTO.OrdemServicoDTO;
import Model.DTO.ServicosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas Steindorff
 */
public class OrdemServicoDAO {

    public OrdemServicoDTO buscarOrdemServico(int cliente, int servico, int horario) throws ClassNotFoundException, SQLException {
        Connection conexao = new Conexao().conexao();
        OrdemServicoDTO ordemServicoDTO = new OrdemServicoDTO();
        String sql = "SELECT * FROM ordemservico WHERE id_cliente = ? and id_servico = ? and id_horario=?";
        PreparedStatement pstm = conexao.prepareStatement(sql);
        pstm.setInt(1, cliente);
        pstm.setInt(2, servico);
        pstm.setInt(3, horario);
        ResultSet rs = pstm.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                ordemServicoDTO.setId(rs.getInt(1));
                ordemServicoDTO.setCliente(rs.getInt(2));
                ordemServicoDTO.setServico(rs.getInt(3));
                ordemServicoDTO.setHorario(rs.getInt(4));
            }
        }
        return ordemServicoDTO;
    }

    public ArrayList<OrdemServicoDTO> adicionarOrdemServico(int cliente, List<ServicosDTO> servicosDTO, List<HorarioDTO> horario) throws ClassNotFoundException, SQLException {
        Connection conexao = new Conexao().conexao();
       ArrayList<OrdemServicoDTO> listaOrdemServico = new ArrayList<>();
        OrdemServicoDTO ordemServico = new OrdemServicoDTO();
        if (!horario.isEmpty()) {
                for (int i = 0; i < horario.size(); i++) {

                    String sql = "INSERT INTO ordemservico(id_cliente, id_servico, id_horario) VALUES (?,?,?)";
                    PreparedStatement pstmData = conexao.prepareStatement(sql);
                    pstmData.setInt(1, cliente);
                    pstmData.setInt(2, servicosDTO.get(i).getId());
                    pstmData.setInt(3, horario.get(i).getId());
                    pstmData.executeUpdate();
                    ordemServico = buscarOrdemServico(cliente, servicosDTO.get(i).getId(), horario.get(i).getId());
                    listaOrdemServico.add(ordemServico);
            }
        }
        return listaOrdemServico;
    }

}
