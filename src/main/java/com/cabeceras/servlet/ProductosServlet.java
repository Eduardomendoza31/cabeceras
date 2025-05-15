package com.cabeceras.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cabeceras.models.Producto;
import com.cabeceras.service.ProductoService;
import com.cabeceras.service.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/productos.xls", "/productos", "/productojson"})
public class ProductosServlet extends HttpServlet {

    private ProductoService service = new ProductoServiceImplement();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Producto> productos = service.listar();

        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");
        boolean esJson = servletPath.endsWith("productojson");


        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=productos.xls");
        }
        if (esJson) {
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("[");
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    out.println("  {");
                    out.println("    \"id\": " + p.getId() + ",");
                    out.println("    \"nombre\": \"" + p.getNombre() + "\",");
                    out.println("    \"tipo\": \"" + p.getTipo() + "\",");
                    out.println("    \"precio\": " + p.getPrecio());
                    out.print("  }");
                    if (i < productos.size() - 1) {
                        out.println(",");
                    } else {
                        out.println();
                    }
                }
                out.println("]");
            }
            return; // Importante para que no se ejecute el resto del HTML
        }

        try (PrintWriter out = resp.getWriter()) {
            if (!esXls) {
                // Creo la plantilla HTML
                out.print("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Listado de Productos</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Listado de productos</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/productos.xls\">exportar a excel</a></p>");
                out.println("<p><a href=\"" + req.getContextPath() + "/productojson\">mostrar json</a></p>");
            }

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            out.println("<th>precio</th>");
            out.println("</tr>");

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                out.println("<td>" + p.getPrecio() + "</td>");
                out.println("</tr>");
            });

            out.println("</table>");

            if (!esXls) {
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
