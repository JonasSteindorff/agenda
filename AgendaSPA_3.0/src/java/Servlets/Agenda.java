/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controller.DAO.DataDAO;
import Model.DTO.DataDTO;
import Model.DTO.HorarioDTO;
import Model.DTO.ServicosDTO;
import Model.DTO.UsuarioDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonas Steindorff
 */
@WebServlet(name = "Agenda", urlPatterns = {"ConsultaAgenda"})

public class Agenda extends HttpServlet {

    public static List<DataDTO> listaDia = new ArrayList<>();
//    public static List<HorarioDTO> listaHora = new ArrayList<>();
    public static List<ServicosDTO> listaServicos = new ArrayList<>();
//    public static List<ServicosDTO> listaReservados = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpSession sessao = http.getSession();
        UsuarioDTO usuarioDTO = (UsuarioDTO) sessao.getAttribute("usuario");

        if (usuarioDTO == null) {
            response.sendRedirect("login.jsp");
        } else {
            String duracao2[];
            duracao2 = request.getParameterValues("idServico");
            if (duracao2 == null) {
                response.sendRedirect("clienteServico.jsp");
            } else {
                listaServicos = (List<ServicosDTO>) sessao.getAttribute("servicos");
                DataDTO dataDTO = new DataDTO();
                LocalDate ini = LocalDate.now();
                dataDTO.setDiaAtendimento(ini);
                HorarioDTO horaDTO = new HorarioDTO();
                LocalTime hora = LocalTime.now();
                horaDTO.setHora(hora);
                DataDAO dataDAO = new DataDAO();
                dataDAO.consultarAgendaCliente(dataDTO, horaDTO);

                List<ServicosDTO> listaReservados = new ArrayList<>();
                int duracao = 0;

                for (int i = 0; i < duracao2.length; i++) {
                    int lt = Integer.parseInt(duracao2[i]);
                    for (int e = 0; e < listaServicos.size(); e++) {
                        if (listaServicos.get(e).getId() == lt) {

                            ServicosDTO servico = new ServicosDTO();
                            servico.setId(listaServicos.get(e).getId());
                            servico.setNomeServico(listaServicos.get(e).getNomeServico());
                            servico.setDuracao(listaServicos.get(e).getDuracao());
                            servico.setValor(listaServicos.get(e).getValor());
                            listaReservados.add(servico);
                            sessao.setAttribute("servicosReservados", listaReservados);
                            duracao = duracao + servico.getDuracao().getMinutes() + (servico.getDuracao().getHours() * 60);
                        }
                    }
                }

                try (PrintWriter out = response.getWriter()) {
                    LocalDate dataInicio = LocalDate.now();
                    LocalDate dataTermino = LocalDate.now().plusDays(10);

//                    Metodos.imprimirData(dataInicio, dataTermino);
                    LocalTime inicioExpediente = LocalTime.of(8, 00);
                    LocalTime terminoExpediente = LocalTime.of(17, 00);
                    LocalDateTime novo = LocalDateTime.now();

                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Reservar horário</title>");
//                    out.println("<link rel='stylesheet' type='text/css' href='botao.css'/>");

                    out.println("</head>");
                    out.println("<body>");
                    out.println("<ul>");
                    out.println("<li>" + usuarioDTO.getEmail() + "</li>");
                    out.print("<li> <a href='LogoutServlet'>Sair </a></li>");
                    out.println("</ul>");
                    out.println("<h1>Calendário de agendamento</h1>");
                    out.println("<div>");
                    out.println("<h1>Duração total: " + duracao + " minutos</h1>");
                    LocalTime duracao3 = LocalTime.of(00, 00).plusMinutes(duracao);
                    out.println("<h1>Duração total: " + duracao3 + "</h1>");
                    out.println("<h1>Horários disponíveis: </h1>");
                    out.print("<form method='POST' action='Agenda'>");
                    boolean parar = true;
                    int l = 0;
                    while (parar) {
//                        Date converter = listaDia.get(l).getDiaAtendimento();
                        LocalDate date = listaDia.get(l).getDiaAtendimento();
                        while (dataInicio.isAfter(date)) {
                            l++;
                            date = listaDia.get(l).getDiaAtendimento();
                        }
                        if (dataInicio.equals(date)) {
                            out.println("<ul>" + listaDia.get(l).getDiaAtendimento() + "</ul>");
                            boolean pararHora = true;
                            int e = 0;
                            int maximo = listaDia.get(l).getListHorarios().size();
                            while (pararHora) {
                                if (maximo > e) {
                                    if (listaDia.get(l).getListHorarios().get(e).getHora().equals(inicioExpediente)) {
                                        out.println("<li>" + listaDia.get(l).getListHorarios().get(e).getHora() + " Reservado</li>");
                                        inicioExpediente = inicioExpediente.plusMinutes(listaDia.get(l).getListHorarios().get(e).getDuracao().getMinutes() + listaDia.get(l).getListHorarios().get(e).getDuracao().getHours() * 60);
                                        e++;
                                    } else if (listaDia.get(l).getListHorarios().get(e).getHora().isAfter(inicioExpediente) && listaDia.get(l).getListHorarios().get(e).getHora().isBefore(inicioExpediente.plusMinutes(duracao))) {
                                        out.println("<li>" + inicioExpediente + "</li>");
                                        out.println("<li>" + listaDia.get(l).getListHorarios().get(e).getHora() + " Reservado</li>");
                                        inicioExpediente = listaDia.get(l).getListHorarios().get(e).getHora().plusMinutes(listaDia.get(l).getListHorarios().get(e).getDuracao().getMinutes() + (listaDia.get(l).getListHorarios().get(e).getDuracao().getHours() * 60));
                                        e++;
                                    } else {
                                        novo = LocalDateTime.of(dataInicio, inicioExpediente);
                                        out.println("<li> <input type='radio' id=" + inicioExpediente + " name='reservar' value=" + novo + ">");
                                        out.println("<label for=" + inicioExpediente + ">" + inicioExpediente + "</label></li>");
                                        inicioExpediente = inicioExpediente.plusMinutes(duracao);
                                    }
                                } else {
                                    novo = LocalDateTime.of(dataInicio, inicioExpediente);
                                    out.println("<li> <input type='radio' id=" + inicioExpediente + " name='reservar' value=" + novo + ">");
                                    out.println("<label for=" + inicioExpediente + ">" + inicioExpediente + "</label></li>");
                                    inicioExpediente = inicioExpediente.plusMinutes(duracao);
                                }
                                if (inicioExpediente.plusMinutes(duracao).isAfter(terminoExpediente)) {
                                    novo = LocalDateTime.of(dataInicio, inicioExpediente);
                                    out.println("<li> <input type='radio' id=" + inicioExpediente + " name='reservar' value=" + novo + ">");
                                    out.println("<label for=" + inicioExpediente + ">" + inicioExpediente + "</label></li>");
                                    inicioExpediente = LocalTime.of(8, 00);
                                    pararHora = false;
                                }
                            }
                        } else {
                            out.println("<ul>" + dataInicio + "</ul>");
                            boolean stop = true;
                            while (stop) {
                                novo = LocalDateTime.of(dataInicio, inicioExpediente);
                                out.println("<li> <input type='radio' id=" + inicioExpediente + " name='reservar' value=" + novo + ">");
                                out.println("<label for=" + inicioExpediente + ">" + inicioExpediente + "</label></li>");
                                inicioExpediente = inicioExpediente.plusMinutes(duracao);
                                if (inicioExpediente.plusMinutes(duracao).isAfter(terminoExpediente)) {
                                    novo = LocalDateTime.of(dataInicio, inicioExpediente);
                                    out.println("<li> <input type='radio' id=" + inicioExpediente + " name='reservar' value=" + novo + ">");
                                    out.println("<label for=" + inicioExpediente + ">" + inicioExpediente + "</label></li>");
                                    inicioExpediente = LocalTime.of(8, 00);
                                    stop = false;
                                }
                            }
                        }
                        dataInicio = dataInicio.plusDays(1);
                        if (dataInicio.isEqual(dataTermino)) {
                            parar = false;
                        }
                    }

                    out.print("<ul><input type='submit' value='Confirmar'></ul>");
                    out.print("</form>");
                    out.println("<a href='clienteServico.jsp'>Cancelar</a>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest http = (HttpServletRequest) request;
        HttpSession sessao = http.getSession();
        UsuarioDTO usuarioDTO = (UsuarioDTO) sessao.getAttribute("usuario");
        if (usuarioDTO == null) {
            response.sendRedirect("login.jsp");
        } else {
            String metodo = request.getParameter("metodooo");
            if (metodo != null) {
                if (metodo.equals("PUT")) {
                    doPut(request, response);
                }
            } else {
                String reserva = request.getParameter("reservar");
                if (reserva == null) {
                    response.sendRedirect("clienteServico.jsp");
                } else {
                    List<ServicosDTO> listaReservados = new ArrayList<>();
                    listaReservados = (List<ServicosDTO>) sessao.getAttribute("servicosReservados");
                    try (PrintWriter out = response.getWriter()) {
                        LocalDateTime pegar = LocalDateTime.parse(reserva);
                        sessao.setAttribute("confirmarReserva", pegar);
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Reservas</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<ul>");
                        out.println("<li>" + usuarioDTO.getEmail() + "</li>");
                        out.print("<li> <a href='LogoutServlet'>Sair </a></li>");
                        out.println("</ul>");
                        out.println("<h1>Confirmar horários para reserva?</h1>");
                        out.println("<ul>");
                        BigDecimal valorTotal = null;
                        LocalDateTime ateAs = LocalDateTime.from(pegar);
                        for (int i = 0; i < listaReservados.size(); i++) {
                            BigDecimal somar = listaReservados.get(i).getValor();
                            if (valorTotal != null) {
                                valorTotal = valorTotal.add(somar);
                            } else {
                                valorTotal = somar;
                            }
                            out.println("<li>" + listaReservados.get(i).getNomeServico() + "</li>");
                            ateAs = ateAs.plusMinutes(listaReservados.get(i).getDuracao().getMinutes() + (listaReservados.get(i).getDuracao().getHours() * 60));
                        }
                        out.println("<p>No dia " + pegar.getDayOfMonth() + "/" + pegar.getMonthValue() + "/" + pegar.getYear() + "<br>");
                        out.println("Das " + pegar.getHour() + ":" + pegar.getMinute() + " Até às " + ateAs.getHour() + ":" + ateAs.getMinute() + "<br>");
                        out.println("Valor total: R$ " + valorTotal + "</p>");
                        out.println("<form method='Post' action='Agenda'>");
                        out.println("<input type='hidden' name='metodooo' value='PUT'>");
                        out.println("<button type='submit' formmethod='Post'>Confirmar</button>");
                        out.println("</form>");
                        out.println("</ul>");
                        out.println("<div>");
                        out.println("<h1>Calendário: </h1>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpSession sessao = http.getSession();
        UsuarioDTO usuarioDTO = (UsuarioDTO) sessao.getAttribute("usuario");
        if (usuarioDTO == null) {
            response.sendRedirect("login.jsp");
        } else {
            LocalDateTime reservaConfirmada = (LocalDateTime) sessao.getAttribute("confirmarReserva");
            LocalDate data = reservaConfirmada.toLocalDate();
            LocalTime hora = reservaConfirmada.toLocalTime();
//            listaServicos = (List<ServicosDTO>) sessao.getAttribute("servicos");
            List<ServicosDTO> listaReservados = new ArrayList<>();
            listaReservados = (List<ServicosDTO>) sessao.getAttribute("servicosReservados");
            boolean retorno = false;
            try {
                usuarioDTO = Metodos.adicionarReserva(data, hora, usuarioDTO, listaReservados, listaDia);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger("Erro ao adicionar horário na agenda: " + Agenda.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger("Erro ao adicionar horário na agenda SQL: " + Agenda.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Confirmada a reserva de horário!</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>");
                out.println("Horário confirmado: "+retorno);
                                out.println("</h1>");

                for (int i = 0; i < listaReservados.size(); i++) {
                    out.println("<li>" + listaReservados.get(i).getNomeServico() + "</li>");
                }
                out.println("<body>");

                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
