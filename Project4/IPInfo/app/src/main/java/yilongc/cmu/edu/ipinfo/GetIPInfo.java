package yilongc.cmu.edu.ipinfo;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class provides capabilities to search location of a given a IP.  The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of an AsyncTask inner class that will do the network
 * operations in a separate worker thread.  However, any UI updates should be done in the UI thread so avoid any synchronization problems.
 * onPostExecution runs in the UI thread, and it calls the locationReady method to do the update.
 */
public class GetIPInfo {
    HostIPInfo hostIPInfo = null;

    /**
     * This method is the public GetLocation method.This provides a callback path such that the
     * locationReady method in that object is called when the location is available from the search.
     *
     * @param searchIP   IP to be searched
     * @param hostIPInfo HostIPInfo object
     */
    public void search(String searchIP, HostIPInfo hostIPInfo) {
        this.hostIPInfo = hostIPInfo;
        new AsyncHostIPSearch().execute(searchIP);
    }

    /**
     * This class is the separate thread to do the search.
     * The onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncHostIPSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... ips) {
            return search(ips[0]);
        }

        /**
         * @param location location info
         */
        protected void onPostExecute(String location) {
            hostIPInfo.locationReady(location);
        }

        /**
         * Make an HTTP request to the server for a HTTP request to Host IP Info for the IP search,
         * and return a location.
         *
         * @param searchIP IP to be searched
         */
        private String search(String searchIP) {
            String response = "";
            URL url;
            HttpURLConnection con = null;
            try {
                // Search URL
                if (searchIP != null) {
                    url = new URL("https://salty-cove-7807.herokuapp.com/HostIPInfoServlet/" + searchIP);
                } else {
                    url = new URL("https://salty-cove-7807.herokuapp.com/HostIPInfoServlet/");
                }
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                /* Read response. */
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response += line;
                }
                in.close();
                // Return parsed location
                return parse(getDocument(response));
            } catch (FileNotFoundException e) { // Catch 404
                try {
                    return String.valueOf(con.getResponseCode());
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * This method parses the DOM from server to get the city and country of IP.
         * @param document DOM representation of XML
         * @return parsed result
         */
        private String parse(Document document) {
            try {
                document.getDocumentElement().normalize();
                NodeList nodeLocations = document.getElementsByTagName("location");
                Node location;
                if ((location = nodeLocations.item(0)) != null) {
                    NodeList nodeListLocation = location.getChildNodes();
                    String city = nodeListLocation.item(0).getTextContent();
                    String country = nodeListLocation.item(1).getTextContent();
                    StringBuilder result = new StringBuilder("city: ");
                    result.append(city).append(", country: ").append(country);
                    return result.toString();
                }
                return null;
            } catch (Exception e) {
                System.out.print("Yikes, hit the error: " + e);
                return null;
            }
        }

        /**
         * Get the DOM representation of the server response XML String
         *
         * @param xmlString server response XML String
         * @return DOM representation of server response XML String
         */
        private Document getDocument(String xmlString) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document doc = null;
            try {
                builder = factory.newDocumentBuilder();
                doc = builder.parse(new InputSource(new StringReader(xmlString)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return doc;
        }
    }
}
