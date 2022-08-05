<%-- 
    Document   : login
    Created on : 19/06/2022, 10:26:12
    Author     : Jonas Steindorff
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
   String erro = (String) request.getAttribute("erro");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Agenda Virtual</h1>
        <form method="POST" action="LoginServlet">
        <fieldset>
            <legend>Autenticar</legend>
                <br>
            Usuário: <input type="text" name="login">
            <br>
            Senha: <input type="password" name="senha">
            <br>
            <input type="submit" value="Entrar">
            <p> <%
                if(erro != null){
                out.print(erro);
                }
            %></p>
        </fieldset>
    </form>
</body>
</html>
