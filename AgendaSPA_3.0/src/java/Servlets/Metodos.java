/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controller.DAO.DataDAO;
import Model.DTO.DataDTO;
import Controller.DAO.HorarioDAO;
import Model.DTO.HorarioDTO;
import Model.DTO.ServicosDTO;
import Model.DTO.UsuarioDTO;
import Controller.DAO.OrdemServicoDAO;
import Model.DTO.OrdemServicoDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas Steindorff
 */
public class Metodos {

//    public static void imprimirData(LocalDate inicio, LocalDate termino) {
//        boolean parar = true;
//
//        while (parar) {
//            DataDTO agen = new DataDTO();
//            agen.setDiaAtendimento(inicio);
//            listaDia.add(agen);
//            inicio = inicio.plusDays(1);
//            if (inicio.isEqual(termino)) {
//                parar = false;
//            }
//        }
//
//    }
    public static UsuarioDTO adicionarReserva(LocalDate data, LocalTime hora, UsuarioDTO usuarioDTO, List<ServicosDTO> servicosDTO, List<DataDTO> listaData) throws ClassNotFoundException, SQLException {
        DataDTO dataDTO = new DataDTO();
        HorarioDTO horaDTO = new HorarioDTO();
        HorarioDAO horarioDAO = new HorarioDAO();

        DataDAO dataDAO = new DataDAO();
        ArrayList<HorarioDTO> horariosDTO = new ArrayList<>();
        ArrayList<OrdemServicoDTO> ordemServicos = new ArrayList<>();
        for (int i = 0; i < listaData.size(); i++) {
            if (listaData.get(i).getDiaAtendimento().equals(data)) {
                int id = listaData.get(i).getId();
                dataDTO = dataDAO.buscarDiaID(id);
                break;
            }
        }

        if (dataDTO.getId() == 0) {
            dataDTO = dataDAO.adicionarReservaData(data);
            horaDTO = horarioDAO.buscarHora(hora, dataDTO.getId());
        }

        if (horaDTO.getId() == 0 && dataDTO.getId() > 0) {
            horariosDTO = horarioDAO.adicionarHora(hora, dataDTO.getId(), servicosDTO);
            if (!horariosDTO.isEmpty()) {
                OrdemServicoDAO ordem = new OrdemServicoDAO();
                ordemServicos = ordem.adicionarOrdemServico(usuarioDTO.getId_usuario(), servicosDTO, horariosDTO);
            }
        }
        dataDTO.adicionarListaHorario(horariosDTO);
               
        usuarioDTO.adicionarReserva(dataDTO);
        usuarioDTO.setOrdemServicos(ordemServicos);
        return usuarioDTO;
    }
}

//    public static void imprimirAgenda(LocalDate inicio, LocalDate termino, LocalTime inicioExpediente, LocalTime terminoExpediente, Time duracao) {
//        boolean parar = true;
//
//        while (parar) {
//            DataDTO agen = new DataDTO();
//            agen.setDiaAtendimento(inicio);
//            listaDia.add(agen);
//            inicio = inicio.plusDays(1);
//            if (inicio.isEqual(termino)) {
//                parar = false;
//
//            }
//        }
//        boolean stop = true;
//        while (stop) {
//            HorarioDTO horario = new HorarioDTO();
//            horario.setHora(inicioExpediente);
//            inicioExpediente = inicioExpediente.plusMinutes(duracao.getMinutes());
//            if (inicioExpediente.equals(terminoExpediente)) {
//                stop = false;
//            }
//        }
//    }
//}
