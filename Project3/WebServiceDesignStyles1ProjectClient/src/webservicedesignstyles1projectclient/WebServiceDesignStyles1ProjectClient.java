/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles1projectclient;

/**
 * This class is the web client for adding, updating, deleting spy and getting
 * spy information.
 *
 * @author changyilong
 */
public class WebServiceDesignStyles1ProjectClient {

    /**
     * This main method tests the adding, updating, deleting spy and getting spy
     * information methods.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Test Web Service. */
        System.out.println(getList());
        System.out.println(getListAsXML());
        System.out.println(addSpy("mikem", "spy", "Pittsburgh", "sesame"));
        addSpy("joem", "spy", "North Hills", "xyz");
        addSpy("seanb", "spy commander", "South Hills", "abcdefg");
        addSpy("jamesb", "spy", "Adelaide", "sydney");
        addSpy("adekunle", "spy", "Pittsburgh", "secret");
        // Myself
        addSpy("yilongc", "spy", "Montreal", "123456");
        System.out.println(getList());
        System.out.println(getListAsXML());
        updateSpy("mikem", "super spy", "Pittsburgh", "sesame");
        System.out.println(getListAsXML());
        String result = getSpy("jamesb");
        System.out.println(result);
        deleteSpy("jamesb");
        result = getSpy("jamesb");
        System.out.println(result);
    }

    /**
     * This method uses the addSpy method on Web Server to add a spy to spy
     * list.
     *
     * @param name name of spy to be added
     * @param title title of spy to be added
     * @param location location of spy to be added
     * @param password password of spy to be added
     * @return result of adding
     */
    private static String addSpy(java.lang.String name,
            java.lang.String title,
            java.lang.String location,
            java.lang.String password) {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.addSpy(name, title, location, password);
    }

    /**
     * This method uses the updateSpy method on Web Server to update a spy in
     * spy list.
     *
     * @param name name of spy to be updated
     * @param title title of spy to be updated
     * @param location location of spy to be updated
     * @param password password of spy to be updated
     * @return result of updating
     */
    private static String updateSpy(java.lang.String name,
            java.lang.String title,
            java.lang.String location,
            java.lang.String password) {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.updateSpy(name, title, location, password);
    }

    /**
     * This method uses the getSpy method on Web Server to get information of a
     * spy in spy list.
     *
     * @param name name of spy to be gotten
     * @return spy info
     */
    private static String getSpy(java.lang.String name) {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.getSpy(name);
    }

    /**
     * This method uses the deleteSpy method on Web Server to delete a spy in
     * spy list.
     *
     * @param name name of spy to be deleted
     * @return result of deleting
     */
    private static String deleteSpy(java.lang.String name) {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.deleteSpy(name);
    }

    /**
     * This method uses the getList method on Web Server to get the text
     * representation of all spies in spy list.
     *
     * @return text representation of all spies
     */
    private static String getList() {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.getList();
    }

    /**
     * This method uses the getListAsXML method on Web Server to get the XML
     * representation of all spies in spy list.
     *
     * @return XML representation of all spies
     */
    private static String getListAsXML() {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.getListAsXML();
    }
}
