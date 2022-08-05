<%-- 
    Document   : AgendaEscolha
    Created on : 18/06/2022, 16:13:19
    Author     : Jonas Steindorff
--%>

<%@page import="Model.DTO.ServicosDTO"%>
<%@page import="java.util.List"%>
<%@page import="Model.DTO.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente serviços</title>
    </head>
    <body>
        <h1>Serviços do salão.</h1>
        <a>
            <%
                UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
                if (usuarioDTO != null) {
                    System.out.print(usuarioDTO.getEmail());
                    out.print(usuarioDTO.getEmail());
                    out.print("<a href='LogoutServlet'>Sair </a>");

                    List<ServicosDTO> lista = (List<ServicosDTO>) session.getAttribute("servicos");
                    out.print("<form name='Agenda' action='Agenda'>");
                    for (ServicosDTO s : lista) {

                        out.print("<input type = 'checkbox' id = " + s.getId() + " name = 'idServico' value = " + s.getId()+ ">");
                        out.print("<label for = " + s.getId() + "> Fazer " + s.getNomeServico() + ", valor do serviço: R$ " + s.getValor() + ", duração: " + s.getDuracao() + "</label><br>");
                    }
                    out.print("<input type='submit' value='Submit'>");
                    out.print("</form>");
                } else {
                    response.sendRedirect("login.jsp");
                }
            %>
        </a>
    </body>
</html>
