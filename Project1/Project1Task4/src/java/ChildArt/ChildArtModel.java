package ChildArt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  Make a request to the child art library
 * and then screen scraping the HTML that is returned in order to make up 
 * an image URL according to device size. 
 * 
 * @author Yilong Chang
 */
public class ChildArtModel {

    private String pictureTag; // The search string of the desired picture
    private String pictureURL; // The URL of the picture image

    /**
     * This method searches the library, and randomly chooses a picture ID to
     * return from the result.
     *
     * @param searchTag The tag of the photo to be searched for.
     * @return picture ID, null when no results
     */
    public String doChildArtSearch(String searchTag) {
        /* Wildcard */
        pictureTag = searchTag + "*";
        String response = "";
        // Create a URL for the desired page
        String childArtURL = "http://digital.library.illinoisstate.edu/cdm/search/searchterm/"
                + pictureTag + "/field/subjec/mode/all/conn/and/order/nosort";
        // Response page
        response = fetch(childArtURL);

        /*
         * Find the picture URL to scrape
         * First find the html tag of the result list.
         * Use a regular expression to match the result
         */
        String field = "id=\"cdm_results_list\" value=\".*icca<\\d+>/\"";
        Pattern pattern = Pattern.compile(field);
        Matcher matcher = pattern.matcher(response);

        // If not found, then no such photo is available.
        if (!matcher.find()) {
            return null;
        }
        // Get the pitcture IDs of result list and wipe out result that are restricted.
        ArrayList<String> pictureIDs = new ArrayList<>();
        String[] results = matcher.group(0).split(".*\"icca<|>/icca<");
        for (String s : results) {
            if (!s.isEmpty()) {
                pictureIDs.add(s.split(">")[0]);
            }
        }
        // Pick a picture randomly
        int length = pictureIDs.size();
        String picID = pictureIDs.get((int) (length * Math.random()));

        return picID;
    }

    /*
     * Return a URL of an image of appropriate size
     * 
     * @param picID the Id of the picture to scale
     * @return The URL an image of appropriate size.
     */
    public String childArtPictureSize(String picID, String device) {
        /* Because we need to fit the width of different devices
         * We can choose a larger height to display the picture properly. */
        if (device.equals("mobile")) {
            String scale = "28";
            String width = "300";
            String height = "1000";
            pictureURL = "http://digital.library.illinoisstate.edu/utils/"
                    + "ajaxhelper/?CISOROOT=icca&CISOPTR=" + picID
                    + "&action=2&DMSCALE=" + scale + "&DMWIDTH=" + width
                    + "&DMHEIGHT=" + height + "&DMX=0&DMY=0&DMTEXT=&DMROTATE=0";
        } else {
            String scale = "70";
            String width = "800";
            String height = "1500";
            pictureURL = "http://digital.library.illinoisstate.edu/utils/"
                    + "ajaxhelper/?CISOROOT=icca&CISOPTR=" + picID
                    + "&action=2&DMSCALE=" + scale + "&DMWIDTH=" + width
                    + "&DMHEIGHT=" + height + "&DMX=0&DMY=0&DMTEXT=&DMROTATE=0";
        }
        return pictureURL;
    }

    /*
     * Return the picture tag.
     *
     * @return The tag that was used to search for the current picture.
     */
    public String getPictureTag() {
        return (pictureTag);
    }

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }
}
