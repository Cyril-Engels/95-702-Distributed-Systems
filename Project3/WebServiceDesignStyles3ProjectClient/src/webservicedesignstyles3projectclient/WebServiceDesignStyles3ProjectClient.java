/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles3projectclient;

/**
 * This class is the web client for adding, updating, deleting spy and getting
 * spy information.
 *
 * @author changyilong
 */
public class WebServiceDesignStyles3ProjectClient {

    /**
     * This main method tests the adding, updating, deleting spy and getting spy
     * information methods.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Begin main");
        Spy spy1 = new Spy("mikem", "spy", "Pittsburgh", "sesame");
        Spy spy2 = new Spy("joem", "spy", "Philadelphia", "obama");
        Spy spy3 = new Spy("seanb", "spy commander", "Adelaide", "pirates");
        Spy spy4 = new Spy("jamesb", "007", "Boston", "queen");
        System.out.println(doPut(spy1));// 201
        System.out.println(doPut(spy2));// 201
        System.out.println(doPut(spy3));// 201
        System.out.println(doPut(spy4));// 201

        System.out.println(doDelete("joem")); // 200 
        spy1.setPassword("Doris");
        System.out.println(doPost(spy1)); // 200

        System.out.println(doGetListAsXML()); // display xml 
        System.out.println(doGetListAsText()); // display text

        System.out.println(doGetSpyAsXML("mikem")); // display xml 
        System.out.println(doGetSpyAsText("joem")); // 404

        System.out.println(doGetSpyAsXML("mikem")); // display xml 
        System.out.println(doPut(spy2)); // 201 
        System.out.println(doGetSpyAsText("joem")); // display text 
        System.out.println("End main");
    }

    /**
     * This method uses the <code>doPut</code> method of Proxy Server to add a spy
     * to spy list.
     *
     * @param spy Spy to be added
     * @return HTTP response code
     */
    public static String doPut(Spy spy) {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doPut(spy);
    }

    /**
     * This method uses the <code>doDelete</code> method of Proxy Server to delete
     * a spy in spy list.
     *
     * @param name name of spy to be deleted
     * @return HTTP response code
     */
    public static String doDelete(String name) {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doDelete(name);
    }

    /**
     * This method uses the <code>doPost</code> method of Proxy Server to update a
     * spy in spy list.
     *
     * @param spy Spy to be updated
     * @return HTTP response code
     */
    public static String doPost(Spy spy) {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doPost(spy);
    }

    /**
     * This method uses the <code>doGet</code> method of Proxy Server to get the
     * XML representation of a spy in spy list.
     *
     * @param name name of Spy
     * @return XML representation of Spy or 404
     */
    public static String doGetSpyAsXML(String name) {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doGet("xml", name);
    }

    /**
     * This method uses the <code>doGet</code> method of Proxy Server to get the
     * text representation of a Spy in spy list.
     *
     * @param name name of Spy
     * @return text representation of Spy or 404
     */
    public static String doGetSpyAsText(String name) {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doGet("plain", name);
    }

    /**
     * This method uses the <code>doGet</code> method of Proxy Server to get the
     * XML representation of all Spies in spy list.
     *
     * @return XML representation of all Spies
     */
    public static String doGetListAsXML() {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doGet("xml", null);
    }

    /**
     * This method uses the <code>doGet</code> method of Proxy Server to get the
     * text representation of all Spies in spy list.
     *
     * @return text representation of Spy or 404
     */
    public static String doGetListAsText() {
        WebServiceDesignStyles3ProjectProxyServer spyService 
                = new WebServiceDesignStyles3ProjectProxyServer();
        return spyService.doGet("plain", null);
    }
}
