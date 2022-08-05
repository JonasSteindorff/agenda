    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.DAO;

import Controller.Conexao.Conexao;
import Model.DTO.UsuarioDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jonas Steindorff
 */
public class UsuarioDAO {

    Connection conexao;

    public UsuarioDTO autenticacaoUsuario(UsuarioDTO objusuarioDTO) throws SQLException, ClassNotFoundException {
        conexao = new Conexao().conexao();
        try {

            String sql = "select idtbcliente, nome, email, cel from tbcliente inner join tbusuario on tbcliente.idtbusuario = tbusuario.id_usuario where username = ? and senha=md5(?)";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objusuarioDTO.getNome_usuario());
            pstm.setString(2, objusuarioDTO.getSenha_usuario());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setId_usuario(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(3));
                usuario.setCel(rs.getLong(4));
                usuario.setNome_usuario(objusuarioDTO.getNome_usuario());
                usuario.setSenha_usuario(objusuarioDTO.getSenha_usuario());

                return usuario;
            } else {
                System.out.println("Usuário ou senha inválido!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro Consulta UsuarioDAL: " + e);
            return null;
        }

    }
    
    public int criarUsuario(UsuarioDTO usuario) throws ClassNotFoundException, SQLException {
        Connection conexao = new Conexao().conexao();
        String sql = "INSERT INTO tbusuario(username,senha) VALUES(?,md5(?))";
        PreparedStatement pstm = conexao.prepareStatement(sql);
        pstm.setString(1, usuario.getNome_usuario());
        pstm.setString(2, usuario.getSenha_usuario());
        pstm.executeUpdate();
        UsuarioDTO susu = new UsuarioDTO();
        susu = buscarUsuario(usuario);
        
        return susu.getId_usuario();
    }
    public boolean criarCadastro(UsuarioDTO usuario) throws ClassNotFoundException, SQLException {
       usuario.setId_usuario(criarUsuario(usuario));
       
//        Date dataBD = Date.valueOf(usuario.getDataNascimento());

        Connection conexao = new Conexao().conexao();
        String sql = "INSERT INTO tbcliente(nome, email, cel, idtbusuario) VALUES(?,?,?,?)";
        PreparedStatement pstm = conexao.prepareStatement(sql);
        pstm.setString(1, usuario.getNome_usuario());
        pstm.setString(2, usuario.getEmail());
        pstm.setLong(3, usuario.getCel());
        pstm.setInt(4, usuario.getId_usuario());
        int retorno = pstm.executeUpdate();
        return retorno>0;
    }
    
    public UsuarioDTO buscarUsuario(UsuarioDTO objusuarioDTO) throws SQLException, ClassNotFoundException {
        conexao = new Conexao().conexao();
        try {

            String sql = "select * from tbusuario where username = ? and senha=md5(?)";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objusuarioDTO.getNome_usuario());
            pstm.setString(2, objusuarioDTO.getSenha_usuario());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setId_usuario(rs.getInt(1));
                return usuario;
            } else {
                System.out.println("Usuário ou senha inválido!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro Consulta UsuarioDAL: " + e);
            return null;
        }

    }
    
    public void usuarioImprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
