/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class checks whether a string is Palindrome, regardless of numbers and punctuation.
 * @author Yilong Chang
 * @version 1.0
 */
@WebServlet(name = "PalindromeServlet",
        urlPatterns = {"/judgePalindrome"})
public class PalindromeServlet extends HttpServlet {

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
        // If a GET request, returns the index page. 
        boolean mobile;
        String docType=getDocType(request);
        request.setAttribute("doctype", docType);
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
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
        String docType=getDocType(request);
        request.setAttribute("doctype", docType);
        // Get the text and trans to lower case
        String text = request.getParameter("word").toLowerCase();
        String nextView = "result.jsp";
        boolean result = true;
        int length = text.length();
        // If zero lenth and any one char, it is Palindrome 
        if (length == 0 || length == 1) {
            request.setAttribute("result", result);
        } else {
            // Get the pure letter combination and the reverse one 
            String word = "", reverse = "";
            for (int i = 0; i < text.length() - 1; i++) {
                if (text.charAt(i) >= 'a' && text.charAt(i) <= 'z') {
                    word += text.charAt(i);
                }
                if (text.charAt(length - i - 1) >= 'a' && text.charAt(length - i - 1) <= 'z') {
                    reverse += text.charAt(length - i - 1);
                }
            }
            // Check whether the two String are equal
            result = word.equals(reverse);
            request.setAttribute("result", result);
        }
        // Return
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * This method check user agent and return corresponding doc type.
     * @param request Http request
     * @return doc type
     */
    private String getDocType(HttpServletRequest request){
         String userAgent = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (userAgent != null && ((userAgent.indexOf("Android") != -1) || (userAgent.indexOf("iPhone") != -1))) {
            mobile = true;
            return "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">";
        } else {
            mobile = false;
            return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
        }
    }
}
