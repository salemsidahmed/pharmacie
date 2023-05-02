package com.mycompany.pharmacy.servlets;

import com.mycompany.pharmacy.aide.ConnectionDB;
import com.mycompany.pharmacy.dao.PharmacienDao;
import com.mycompany.pharmacy.entities.Pharmacien;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PharmacienRegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String tel = request.getParameter("tel");
            String password = request.getParameter("password");
            String confirmePassword = request.getParameter("confirmePassword");

            if (!password.equals(confirmePassword)) {
                request.setAttribute("errorMessage", "Les deux mots de passe doivent être identiques.");
                RequestDispatcher rd = request.getRequestDispatcher("pharmacien_register.jsp");
                rd.forward(request, response);
                
                return;
            }

            Pharmacien pharmacien = new Pharmacien();

            pharmacien.setNom(nom);
            pharmacien.setEmail(email);
            pharmacien.setTel(tel);
            pharmacien.setPassword(password);

            //Enregistrer pharmacien
            PharmacienDao pDao = new PharmacienDao(ConnectionDB.getFactory());
            pDao.savePharmacien(pharmacien);

            out.println("Pharmacien enregistrer avec sucess...");

            HttpSession httpSession = request.getSession();

            httpSession.setAttribute("messages", "Pharmacien enregistrer avec sucess...");

            response.sendRedirect("pharmacien_register.jsp");

            return;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
