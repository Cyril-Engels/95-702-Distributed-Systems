
package DS;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import sun.misc.BASE64Encoder;

/**
 * This class is the servlet that validates the computation of two kinds of 
 * Hashes code and transforming them into Hexadecimal and Base64 representations.
 * 
 * @author Yilong Chang
 * @version 1.0
 */
@WebServlet(name = "ComputeHashes",
        urlPatterns = {"/ComputeHashes"})
public class ComputeHashes extends HttpServlet {

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
        String word = request.getParameter("word");
        String hashFunction = request.getParameter("hashFunction");
        String nextView;
        if (word != null) {
            // Pass the user input string and chosen function to the next view.
            request.setAttribute("word", word);
            request.setAttribute("hashFunction", hashFunction);
            // Pass the result to the next view.
            request.setAttribute("hashValueHex", hashValueHexadecimal(computeHash(word, hashFunction)));
            request.setAttribute("hashValueBase64", hashValueBase64(computeHash(word, hashFunction)));
            nextView = "result.jsp";
        } else {
            //if no input, return the index page
            nextView = "index.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(nextView);
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
     * This method computes the hash code of the word of chosen method.
     * @param text text to compute
     * @param method method to use (MD5/SHA-1)
     * @return corresponding hash code 
     */
    private byte[] computeHash(String text, String method) {
        byte[] digest = null;
        try {
            MessageDigest messageDegist = MessageDigest.getInstance(method);
            messageDegist.update(text.getBytes());
            digest = messageDegist.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComputeHashes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
    /**
     * This function transform the hash code into hexadecimal representation.
     * @param b byte array of the hash code
     * @return Hexadecimal hash code
     */
    private String hashValueHexadecimal(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    
    /**
     * This function transform the hash code into Base64 representation.
     * @param b byte array of the hash code
     * @return Base64 hash code
     */
    private String hashValueBase64(byte[] b) {
        String result = "";
        result = (new BASE64Encoder()).encode(b);
        return result;
    }
}
