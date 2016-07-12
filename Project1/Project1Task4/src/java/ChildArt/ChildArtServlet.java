
package ChildArt;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 
 * The servlet is acting as the controller in the MVC framework.
 * There are two views - index.jsp and result.jsp.
 * It decides between the two by determining if there is a search parameter or
 * not. If there is no parameter, then it uses the index.jsp view, as a 
 * starting place. If there is a search parameter, then it searches for a 
 * picture and uses the result.jsp view.
 * The model is provided by ChildArtModel.
 *
 * @author Yilong Chang
 */
@WebServlet(name = "ChildArtServlet",
        urlPatterns = {"/childArt"})
public class ChildArtServlet extends HttpServlet {

    ChildArtModel cam = null; 

    /**
     * This method initiates this servlet by instantiating the model that it will use.
     */
    @Override
    public void init() {
        cam = new ChildArtModel();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get the search parameter if it exists
        String search = request.getParameter("searchWord");

        // Determine what type of device
        String userAgent = request.getHeader("User-Agent");

        boolean mobile;
        // Prepare the appropriate DOCTYPE for the view pages
        if (userAgent != null && ((userAgent.indexOf("Android") != -1) || (userAgent.indexOf("iPhone") != -1))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search string.
         * If there is a search parameter, then do the search and return the result.
         */
        if (search != null && search.length()!=0) {
            // Use model to do the search and choose the result size
            String picID = cam.doChildArtSearch(search);
            request.setAttribute("pictureTag", search);
            nextView = "result.jsp";
            if (picID == null) {
                    request.setAttribute("pictureURL", null);
            } else {
                request.setAttribute("pictureURL",cam.childArtPictureSize(picID, (mobile) ? "mobile" : "desktop"));
                // Pass the user search string (pictureTag) also to the view.
            }    
        } else {
            // no search parameter so choose the index view
            nextView = "index.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
