/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.DAO;

import Controller.Conexao.Conexao;
import Model.DTO.DataDTO;
import Model.DTO.HorarioDTO;
import Model.DTO.ServicosDTO;
import Servlets.Agenda;
import static Servlets.Agenda.listaDia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas Steindorff
 */
public class HorarioDAO {

    public HorarioDTO buscarHora(LocalTime time, int idDia) {
        HorarioDTO horarioDTO = new HorarioDTO();
        try {

            Connection conexao = new Conexao().conexao();
            String sql = "SELECT tbhora.idHora, tbhora.id_dia, tbhora.hora, tbhora.duracao, tbhora.duracao FROM tbdia INNER JOIN tbhora ON tbhora.id_dia = tbdia.idtbDia WHERE tbhora.hora = ? and tbhora.id_dia = ?";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            Time timeBD = Time.valueOf(time);
            pstm.setTime(1, timeBD);
            pstm.setInt(2, idDia);
            ResultSet rs = pstm.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    horarioDTO.setId(rs.getInt(1));
                    horarioDTO.setData(rs.getInt(2));
                    LocalTime hora = LocalTime.of(rs.getTime(3).getHours(), rs.getTime(3).getMinutes(), rs.getTime(3).getSeconds());
                    horarioDTO.setHora(hora);
                    horarioDTO.setDuracao(rs.getTime(4));

                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Falha HorarioDAO Conexao: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Falha HorarioDAO Statement: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horarioDTO;
    }

    public void consultarAgenda(HorarioDTO horarioDTO) {
        try {
            Connection conexao = new Conexao().conexao();
            String sql = "SELECT tbhora.idHora, tbhora.hora, tbhora.id_dia, tbdia.dia FROM tbdia INNER JOIN tbhora ON tbhora.id_dia = tbdia.idtbDia WHERE ORDER BY dia, hora";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HorarioDTO horario = new HorarioDTO();
                horario.setId(rs.getInt(1));
                LocalTime time = LocalTime.of(rs.getTime(2).getHours(), rs.getTime(2).getMinutes(), rs.getTime(2).getSeconds());
                horario.setHora(time);
                horario.setData(rs.getInt(3));
//                Agenda.listaHora.add(horario);
                for (int i = 0; i < listaDia.size(); i++) {
                    DataDTO indice = Agenda.listaDia.get(i);
                    System.out.println("Indice Data: " + indice + " " + i);
                }

            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Falha HorarioDAO Conexao: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Falha HorarioDAO Statement: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<HorarioDTO> adicionarHora(LocalTime time, int idDia, List<ServicosDTO> servicosDTO) {
        ArrayList<HorarioDTO> listaHora = new ArrayList<>();
        HorarioDTO horarioDTO = new HorarioDTO();
        try {
            Connection conexao = new Conexao().conexao();
            Time timeBD = Time.valueOf(time);
            for (int i = 0; i < servicosDTO.size(); i++) {
                String sql = "INSERT INTO tbhora(hora, id_dia, duracao) VALUES (?,?,?)";
                PreparedStatement pstm = conexao.prepareStatement(sql);

                pstm.setTime(1, timeBD);
                pstm.setInt(2, idDia);
                pstm.setTime(3, servicosDTO.get(i).getDuracao());
                pstm.executeUpdate();

                HorarioDAO horarioDAO = new HorarioDAO();
                horarioDTO = horarioDAO.buscarHora(time, idDia);
                listaHora.add(horarioDTO);
                time = time.plusMinutes(servicosDTO.get(i).getDuracao().getMinutes() + (servicosDTO.get(i).getDuracao().getHours() * 60));
                timeBD = Time.valueOf(time);
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Falha HorarioDAO Conexao: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Falha HorarioDAO Statement: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaHora;
    }
}
