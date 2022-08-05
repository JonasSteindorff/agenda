/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.DAO;

import Controller.Conexao.Conexao;
import Model.DTO.DataDTO;
import Model.DTO.HorarioDTO;
import Servlets.Agenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas Steindorff
 */
public class DataDAO {

    public DataDTO buscarDia(Date dataDB) throws ClassNotFoundException, SQLException {
        Connection conexao = new Conexao().conexao();
        DataDTO dataDTO = new DataDTO();
        String sqlData = "SELECT * FROM tbdia WHERE dia = ? ORDER BY dia";
        PreparedStatement pstmData = conexao.prepareStatement(sqlData);
        pstmData.setDate(1, dataDB);
        ResultSet rsData = pstmData.executeQuery();
        while (rsData.next()) {
            dataDTO.setId(rsData.getInt(1));
            Date date = new Date(0, 0, 0);
            date = rsData.getDate(2);
            LocalDate data = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            dataDTO.setDiaAtendimento(data);
        }

        return dataDTO;
    }
    public DataDTO buscarDiaID(int id) throws ClassNotFoundException, SQLException {
        Connection conexao = new Conexao().conexao();
        DataDTO dataDTO = new DataDTO();
        String sqlData = "SELECT * FROM tbdia WHERE idtbDia = ?";
        PreparedStatement pstmData = conexao.prepareStatement(sqlData);
        pstmData.setInt(1, id);
        ResultSet rsData = pstmData.executeQuery();
        while (rsData.next()) {
            dataDTO.setId(rsData.getInt(1));
            Date date = new Date(0, 0, 0);
            date = rsData.getDate(2);
            LocalDate data = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            dataDTO.setDiaAtendimento(data);
        }

        return dataDTO;
    }

    public void consultarAgendaCliente(DataDTO dataInicio, HorarioDTO horarioInicio) {
        try {
            Connection conexao = new Conexao().conexao();
            String sqlData = "SELECT * FROM tbdia WHERE (dia > ?) ORDER BY dia";
            PreparedStatement pstmData = conexao.prepareStatement(sqlData);

            LocalDate diainicio = LocalDate.now();

            Date dataDB = Date.valueOf(diainicio.minusDays(1));
            pstmData.setDate(1, dataDB);
            ResultSet rsData = pstmData.executeQuery();

            while (rsData.next()) {
                DataDTO dia = new DataDTO();
                dia.setId(rsData.getInt(1));

                Date date = new Date(0, 0, 0);
                date = rsData.getDate(2);
                LocalDate data = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                dia.setDiaAtendimento(data);
                Agenda.listaDia.add(dia);
                System.out.println("Resultado do banco de dados Dia: " + dia.getDiaAtendimento());
                String sqlHora = "SELECT tbhora.idHora, tbhora.hora, tbhora.id_dia,tbhora.duracao, tbdia.dia FROM tbdia INNER JOIN tbhora ON tbhora.id_dia = tbdia.idtbDia WHERE tbhora.id_dia = ? ORDER BY dia, hora";
                PreparedStatement pstmHora = conexao.prepareStatement(sqlHora);
                pstmHora.setInt(1, dia.getId());
                ResultSet rsHora = pstmHora.executeQuery();
                while (rsHora.next()) {
                    HorarioDTO horario = new HorarioDTO();
                    horario.setId(rsHora.getInt(1));
                    LocalTime time = LocalTime.of(rsHora.getTime(2).getHours(), rsHora.getTime(2).getMinutes(), rsHora.getTime(2).getSeconds());
                    horario.setHora(time);
                    horario.setData(rsHora.getInt(3));
                    horario.setDuracao(rsHora.getTime(4));
                    dia.adicionarHorario(horario);
                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Falha DateDAO Conexao: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Falha DateDAO Statement: " + ex);
            Logger.getLogger(HorarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DataDTO adicionarReservaData(LocalDate data) throws ClassNotFoundException, SQLException {
        Date dataBD = Date.valueOf(data);
        DataDTO diaDTO = new DataDTO();
        Connection conexao = new Conexao().conexao();
        String sql = "INSERT INTO tbdia(dia) VALUES(?)";
        PreparedStatement pstm = conexao.prepareStatement(sql);
        pstm.setDate(1, dataBD);
        pstm.executeUpdate();
        diaDTO = buscarDia(dataBD);
        return diaDTO;
    }

}
