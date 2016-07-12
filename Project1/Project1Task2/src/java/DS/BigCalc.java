/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class validates six kinds of Big Integer operations.
 * Add, Multiply, Relatively Prime, Mod, Mod Inverse and Power
 * @author Yilong Chang
 */
@WebServlet(name = "BigCalc",
        urlPatterns = {"/BigCalc"})
public class BigCalc extends HttpServlet {
    /* The header for error and normal result. */
    private final String ERROR = "Error: ";
    private final String RESULT = "Result is: ";
    /**
     * This method validates the addition of two Big Integers.
     * @param firstNum the first operand
     * @param secondNum the second operand 
     * @return result
     */
    String bigIntAdd(String firstNum, String secondNum) {
        return RESULT + (new BigInteger(firstNum)).add(new BigInteger(secondNum)).toString();
    }
    /**
     * This method validates the multiplication of two Big Integers.
     * @param firstNum the first operand
     * @param secondNum the second operand 
     * @return result
     */
    String bigIntMultiply(String firstNum, String secondNum) {
        return RESULT + (new BigInteger(firstNum)).multiply(new BigInteger(secondNum)).toString();
    }
    /**
     * This method computes whether the two Big Integers are relatively prime.
     * @param firstNum the first operand
     * @param secondNum the second operand 
     * @return result
     */
    String bigIntRelativelyPrime(String firstNum, String secondNum) {
        return RESULT + ((new BigInteger(firstNum)).gcd(new BigInteger(secondNum)).equals(new BigInteger("1")));
    }
    /**
     * This method computes the mod of two Big Integers.
     * @param firstNum the first operand
     * @param secondNum the second operand, should not be 0
     * @return result
     */
    String bigIntMod(String firstNum, String secondNum) {
        StringBuilder result = new StringBuilder();
        try {
            result.append(RESULT).append((new BigInteger(firstNum)).mod(new BigInteger(secondNum)).toString());
        } catch (ArithmeticException e) {
            result.append(ERROR).append(e.getMessage());
        }
        return result.toString();
    }
    /**
     * This method computes the inverse mod of two Big Integers.
     * @param firstNum the first operand
     * @param secondNum the second operand, should not be 0
     * @return result
     */
    String bigIntModInverse(String firstNum, String secondNum) {
        StringBuilder result = new StringBuilder();
        try {
            result.append(RESULT).append((new BigInteger(firstNum)).modInverse(new BigInteger(secondNum)).toString());
        } catch (ArithmeticException e) {
            result.append(ERROR).append(e.getMessage());
        }
        return result.toString();
    }
    /**
     * This method validates the power of Big Integer. 
     * If the absolute value of the second number is too large,
     * an error would be returned.
     * @param firstNum the first operand
     * @param secondNum the second operand, integer
     * @return result
     */
    String bigIntPower(String firstNum, String secondNum) {
        StringBuilder result = new StringBuilder();
        long exponent = Long.parseLong(secondNum);
        // Check the second number
        if (exponent > Integer.MAX_VALUE || exponent < Integer.MIN_VALUE) {
            result.append(ERROR).append("Please input a smaller exponent.");
        } else {
            result.append(RESULT).append((new BigInteger(firstNum)).pow(Integer.parseInt(secondNum)).toString());
        }
        return result.toString();
    }

    /**
     * This method check whether the number and operator are valid.
     * If there is an error, the error would be returned.
     * @param firstNum first operand
     * @param secondNum second operand
     * @param operator operation to perform
     * @return error
     */
    public String validate(String firstNum, String secondNum, String operator) {
        StringBuilder error = new StringBuilder(ERROR);
        ArrayList<String> operations = new ArrayList<>();
        operations.add("1");
        operations.add("2");
        operations.add("3");
        operations.add("4");
        operations.add("5");
        operations.add("6");
        try {
            BigInteger bigA = new BigInteger(firstNum);
            BigInteger bigB = new BigInteger(secondNum);

        } catch (Exception e) {
            error.append(e.getMessage());
        }
        if (!operations.contains(operator)) {
            error.append("\nYou must choose an appropriate operation!");
        }
        return error.toString();
    }
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
        // Get parameters from request
        String operation = request.getParameter("operations");
        String firstNum = request.getParameter("firstNum");
        String secondNum = request.getParameter("secondNum");
        // Validate the numbers and operation.
        String error = validate(firstNum, secondNum, operation);
        // Pass the user input string to the next view.
        request.setAttribute("firstNum", firstNum);
        request.setAttribute("secondNum", secondNum);
        // Check error
        if (error.length() > ERROR.length()) {
            request.setAttribute("result", error);
        } else {// Choose operation 
            switch (operation) {
                case "1":
                    request.setAttribute("result", bigIntAdd(firstNum, secondNum));
                    break;
                case "2":
                    request.setAttribute("result", bigIntMultiply(firstNum, secondNum));
                    break;
                case "3":
                    request.setAttribute("result", bigIntRelativelyPrime(firstNum, secondNum));
                    break;
                case "4":
                    request.setAttribute("result", bigIntMod(firstNum, secondNum));
                    break;
                case "5":
                    request.setAttribute("result", bigIntModInverse(firstNum, secondNum));
                    break;
                case "6":
                    request.setAttribute("result", bigIntPower(firstNum, secondNum));
                    break;
            }
        }
        // Return result.
        String nextView = "result.jsp";
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
    }// </editor-fold
}
