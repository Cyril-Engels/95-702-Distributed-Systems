/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles3projectclient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the Proxy Server of WebServiceDesignStyles3ProjectServer.
 *
 * @author changyilong
 */
public class WebServiceDesignStyles3ProjectProxyServer {

    /**
     * This method uses the <code>doGet</code> method on Web Server to get
     * corresponding Spy info.
     *
     * @param type type of requested response (XML or plain text)
     * @param name name of Spy to be got
     * @return Spy info or 404
     */
    public String doGet(String type, String name) {
        URL url;
        String response = "";
        HttpURLConnection con = null;
        try {
            if (name != null) {
                url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/" + "SpyListCollection/" + name);
            } else {
                url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/" + "SpyListCollection");
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/" + type);

            /* Read response. */
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response += line + "\n";
            }
            in.close();
            if (response.length() > 0) {
                response = response.substring(0, response.length() - 1);
            }
            return response;
        } catch (FileNotFoundException e) { // Catch 404
            try {
                return String.valueOf(con.getResponseCode());
            } catch (IOException ex) {
                Logger.getLogger(WebServiceDesignStyles3ProjectClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {

        }
        return response;
    }

    /**
     * This method uses the <code>doPost</code> method on Web Server to update a
     * spy in spy list.
     *
     * @param spy Spy to be updated
     * @return HTTP response code
     */
    public String doPost(Spy spy) {
        String response = "";
        BufferedOutputStream out;
        URL url;
        try {
            url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/"
                    + "SpyListCollection/" + spy.getName());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            /* Write to server. */
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(spy.toXML().getBytes("UTF-8"));
            out.flush();
            out.close();
            response = String.valueOf(con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * This method uses the <code>doPut</code> method on Web Server to add a spy
     * to spy list.
     *
     * @param spy Spy to be added
     * @return HTTP response code
     */
    public String doPut(Spy spy) {
        String response = "";
        BufferedOutputStream out;
        URL url;
        try {
            url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/"
                    + "SpyListCollection/" + spy.getName());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            /* Write to server. */
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(spy.toXML().getBytes("UTF-8"));
            out.flush();
            out.close();
            response = String.valueOf(con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * This method uses the <code>doDelete</code> method on Web Server to delete
     * a spy in spy list.
     *
     * @param name name of spy to be deleted
     * @return HTTP response code
     */
    public String doDelete(String name) {
        String response = "";
        URL url;
        try {
            url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/"
                    + "SpyListCollection/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            response = String.valueOf(con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
