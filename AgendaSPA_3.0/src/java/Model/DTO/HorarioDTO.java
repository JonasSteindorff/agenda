package Model.DTO;

import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author Jonas Steindorff
 */
public class HorarioDTO {

    

    private int id;
    private int data;
    private LocalTime hora;
    private Time duracao;

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Time getDuracao() {
        return duracao;
    }

    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }


    public void cadastrar(int codigo, int idData, String horario, int cliente, int funcionario) {
        this.setId(id);
    }

    public void cadastrar(int codigo, int idData, String horario, int cliente) {
        this.setId(id);
        this.setData(data);
    }


}
