/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DTO;

import com.mysql.cj.result.LocalDateValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Jonas Steindorff
 */
public class DataDTO {

    private int id;
    private LocalDate diaAtendimento;
    private ArrayList<HorarioDTO> listHorarios;

    public DataDTO() {
        listHorarios = new ArrayList<HorarioDTO>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<HorarioDTO> getListHorarios() {
        return listHorarios;
    }

    public void setListHorarios(ArrayList<HorarioDTO> listHorarios) {
        this.listHorarios = listHorarios;
    }

    public void adicionarHorario(HorarioDTO horario) {
        listHorarios.add(horario);
    }

    public void adicionarListaHorario(ArrayList<HorarioDTO> horario) {
        listHorarios= horario;
    }

    public HorarioDTO getHorario(int posicao) {
        return listHorarios.get(posicao);
    }

    public int quantidadeAgendados() {
        return listHorarios.size();
    }

    public void cadastrar(int codigo, String dataLista, int listaHorarios) {
        this.setId(codigo);
//        this.setDataLista(dataLista);
        this.setListHorarios(listHorarios);
    }

    public LocalDate getDiaAtendimento() {
        return diaAtendimento;
    }

    public void setDiaAtendimento(LocalDate ld) {
        this.diaAtendimento = ld;
    }

    void setDiaAtendimento(LocalDateValueFactory data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
