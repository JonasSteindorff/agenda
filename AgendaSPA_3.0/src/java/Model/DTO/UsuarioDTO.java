/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DTO;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Jonas Steindorff
 */
public class UsuarioDTO {

    private int id_usuario;
    private String nome;
//    private String sobrenome;
//    private String cpf;
//    private LocalDate dataNascimento;
    private String email;
    private Long cel;
    private String nome_usuario;
    private String senha_usuario;
    private ArrayList<DataDTO> reservas;
    private ArrayList<OrdemServicoDTO> ordemServicos;

    public UsuarioDTO() {
        reservas = new ArrayList<DataDTO>();
        ordemServicos = new ArrayList<OrdemServicoDTO>();

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCel() {
        return cel;
    }

    public void setCel(long cel) {
        this.cel = cel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

//    public String getSobrenome() {
//        return sobrenome;
//    }
//
//    public void setSobrenome(String sobrenome) {
//        this.sobrenome = sobrenome;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }
//
//    public LocalDate getDataNascimento() {
//        return dataNascimento;
//    }
//
//    public void setDataNascimento(LocalDate dataNascimento) {
//        this.dataNascimento = dataNascimento;
//    }
    
    public ArrayList<DataDTO> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<DataDTO> reservas) {
        this.reservas = reservas;
    }

    public void adicionarReserva(DataDTO data) {
        reservas.add(data);
    }

    public void setOrdemServicos(ArrayList<OrdemServicoDTO> ordens) {
        ordemServicos = ordens;
    }

    public void usuarioImprimir() {
        System.out.println("ID: " + id_usuario + "\nNome: " + nome_usuario + "\nSenha: ");
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
