package com.cabeceras.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/cabeceras-request")
public class CabeceraHttpRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String metodoHttp = req.getMethod();
        String requestUri = req.getRequestURI();
        String requestUrl = req.getRequestURL().toString();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String ip = req.getLocalAddr();
        int port = req.getLocalPort();
        String scheme = req.getScheme();
        String host = req.getHeader("host");

        String url = scheme + "://" + host + contextPath + servletPath;
        String url2 = scheme + "://" + ip + ":" + port + contextPath + servletPath;
        String ipCliente = req.getRemoteAddr();

        try (PrintWriter out = resp.getWriter()) {
            out.print("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Cabeceras HTTP Request</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Cabeceras HTTP Request!</h1>");
            out.println("<ul>");
            out.println("<li>Obteniendo el método: " + metodoHttp + "</li>");
            out.println("<li>Request URI: " + requestUri + "</li>");
            out.println("<li>Request URL: " + requestUrl + "</li>");
            out.println("<li>Context Path: " + contextPath + "</li>");
            out.println("<li>Servlet Path: " + servletPath + "</li>");
            out.println("<li>IP: " + ip + "</li>");
            out.println("<li>Puerto: " + port + "</li>");
            out.println("<li>Scheme: " + scheme + "</li>");
            out.println("<li>Host: " + host + "</li>");
            out.println("<li>URL construida: " + url + "</li>");
            out.println("<li>URL2 construida: " + url2 + "</li>");
            out.println("<li>REMOTE CLIENTE: " + ipCliente + "</li>");
            out.println("</ul>");


            out.println("<ul>");
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String cabecera = headerNames.nextElement();
                out.println("<li>" + cabecera + ": " + req.getHeader(cabecera) + "</li>");
            }
            out.println("</ul>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
