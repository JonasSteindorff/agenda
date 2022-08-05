/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controller.DAO.UsuarioDAO;
import Model.DTO.UsuarioDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Long;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas Steindorff
 */
public class Cadastro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cadastro!</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Usuário cadastrado com sucesso!");
            out.println("</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean retorno = false;
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNome(request.getParameter("nome"));
//        usuario.setSobrenome(request.getParameter("sobrenome"));
//        usuario.setCpf(request.getParameter("cpf"));
//        LocalDate nascimento = LocalDate.parse(request.getParameter("nascimento"));
//        usuario.setDataNascimento(LocalDate.parse(request.getParameter("nascimento")));
        usuario.setEmail(request.getParameter("email"));
        
        String cel = request.getParameter("cel");
        Long teste = Long.parseLong(cel);
        usuario.setCel(Long.parseLong(request.getParameter("cel")));
        String rua = request.getParameter("rua");
        String bairro = request.getParameter("bairro");
        String numero = request.getParameter("numero");
        usuario.setNome_usuario(request.getParameter("usuario"));
        usuario.setSenha_usuario(request.getParameter("senha"));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            retorno = usuarioDAO.criarCadastro(usuario);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(retorno){
        doGet(request, response);
        } else{
            System.out.println("EROOOOOOOOuuu");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
