/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controller.DAO.ServicosDAO;
import Model.DTO.ServicosDTO;
import Controller.DAO.UsuarioDAO;
import Model.DTO.UsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonas Steindorff
 */
public class LoginServlet extends HttpServlet {

    public static List<ServicosDTO> listaServicos = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        UsuarioDTO objUsuarioDTO = new UsuarioDTO();
        objUsuarioDTO.setNome_usuario(login);
        objUsuarioDTO.setSenha_usuario(senha);
        UsuarioDAO objusuarioDAO = new UsuarioDAO();
        UsuarioDTO usuarioDTO = null;
        try {
            usuarioDTO = objusuarioDAO.autenticacaoUsuario(objUsuarioDTO);
            if (usuarioDTO !=null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuarioDTO);
                ServicosDAO servicosDAO = new ServicosDAO();
                ServicosDTO servicosDTO = new ServicosDTO();
                if (listaServicos.isEmpty()) {
                    servicosDAO.consultarServicos(servicosDTO);
                    session.setAttribute("servicos", listaServicos);
                } else {
                    session.setAttribute("servicos", listaServicos);
                }
                response.sendRedirect("clienteServico.jsp");
            } else {
                request.setAttribute("erro", "Usuário e/ou senha incorretos");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
