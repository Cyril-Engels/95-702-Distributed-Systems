/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles3projectserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is the web server providing functions for adding, updating,
 * deleting spy and getting spy information.
 *
 * @author changyilong
 */
@WebServlet(name = "SpyListCollection", urlPatterns = {"/SpyListCollection/*"})
public class SpyListCollection extends HttpServlet {

    private static final SpyList spyList = SpyList.getInstance();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String responseString = "";
        String type = request.getHeader("Accept");
        if (type == null) { // No accept type
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (pathInfo != null) {
            /* Get a spy by name. */
            String name = pathInfo.substring(1);
            Spy spy;
            if ((spy = spyList.get(name)) == null) { // Spy does not exist
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (type.equals("text/plain")) {
                responseString = spy.toString();
            } else if (type.equals("text/xml")) {
                responseString = spy.toXML();
            } else { // Illegal type
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            /* Get spy list. */
            if (type.equals("text/plain")) { // Get plain text 
                responseString = spyList.toString();
            } else if (type.equals("text/xml")) { // Get XML 
                responseString = spyList.toXML();
            } else { // Illegal type
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
        /* Write back to client. */
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(responseString);
            out.flush();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Read client-sent message. */
        String line;
        String requestString = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while ((line = in.readLine()) != null) {
            requestString += line;
        }

        SpyMessage sm = new SpyMessage(requestString);
        Spy spy = sm.getSpy();
        // Spy does not exist
        if (spyList.get(spy.getName()) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        spyList.add(spy);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null) { // No name provided
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String name = path.substring(1);
        Spy spy;
        if ((spy = spyList.get(name)) == null) { // Spy does not exists
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        spyList.delete(spy);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Read client-sent message. */
        String line;
        String requestString = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while ((line = in.readLine()) != null) {
            requestString += line;
        }

        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);
        if (pathInfo == null) { // No name provided
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String name = pathInfo.substring(1);
        Spy spy;
        if ((spy = spyList.get(name)) != null) { // Spy already exists
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        SpyMessage sm = new SpyMessage(requestString);
        spy = sm.getSpy();
        spyList.add(spy);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
