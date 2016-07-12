/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles2projectclient;

/**
 * This class is the web client for adding, updating, deleting spy and getting
 * spy information.
 *
 * @author changyilong
 */
public class WebServiceDesignStyles2ProjectClient {

    /**
     * This main method tests the adding, updating, deleting spy and getting spy
     * information methods.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Note: There is NO communication code or XML handling code in the main 
        // routine.
        String result = "";
        System.out.println("Adding spy jamesb");
        // create a spy
        Spy bond = new Spy("jamesb", "spy", "London", "james"); // create a message

        SpyMessage sb = new SpyMessage(bond, "addSpy");
        // make a call on the web service
        result = doSpyOperation(sb.toXML());
        System.out.println(result);
        System.out.println("Add myself.");
        SpyMessage me = new SpyMessage(bond, "addSpy");
        // make a call on the web service
        result = doSpyOperation(me.toXML());
        System.out.println(result);
        System.out.println("Adding spy seanb");
        Spy beggs = new Spy("seanb", "spy master", "Pittsburgh", "sean");
        System.out.println(beggs.toString());
        SpyMessage ss = new SpyMessage(beggs, "addSpy");
        result = doSpyOperation(ss.toXML());
        System.out.println(result);
        System.out.println("Adding spy joem");
        Spy mertz = new Spy("joem", "spy", "Los Angeles", "joe");
        SpyMessage sj = new SpyMessage(mertz, "addSpy");
        result = doSpyOperation(sj.toXML());
        System.out.println(result);
        System.out.println("Adding spy mikem");
        Spy mccarthy = new Spy("mikem", "spy", "Ocean City Maryland", "sesame");
        SpyMessage sm = new SpyMessage(mccarthy, "addSpy");
        result = doSpyOperation(sm.toXML());
        System.out.println(result);
        System.out.println("Displaying spy list");
        SpyMessage list = new SpyMessage(new Spy(), "getList");
        result = doSpyOperation(list.toXML());
        System.out.println(result);
        System.out.println("Displaying spy list as XML");
        SpyMessage listXML = new SpyMessage(new Spy(), "getListAsXML");
        result = doSpyOperation(listXML.toXML());
        System.out.println(result);
        System.out.println("Updating spy jamesb");
        Spy newJames = new Spy("jamesb", "Cool Spy", "New Jersey", "sesame");
        SpyMessage um = new SpyMessage(newJames, "updateSpy");
        result = doSpyOperation(um.toXML());
        System.out.println(result);
        System.out.println("Displaying spy list");
        list = new SpyMessage(new Spy(), "getList");
        result = doSpyOperation(list.toXML());
        System.out.println(result);
        System.out.println("Deleting spy jamesb");
        Spy james = new Spy("jamesb");
        SpyMessage dm = new SpyMessage(james, "deleteSpy");
        result = doSpyOperation(dm.toXML());
        System.out.println(result);
        System.out.println("Displaying spy list");
        list = new SpyMessage(new Spy(), "getList");
        result = doSpyOperation(list.toXML());
        System.out.println(result);
        System.out.println("Displaying spy list as XML");
        listXML = new SpyMessage(new Spy(), "getListAsXML");
        result = doSpyOperation(listXML.toXML());
        System.out.println(result);
        System.out.println("Deleting spy Amos");
        Spy amos = new Spy("amos");
        SpyMessage am = new SpyMessage(amos, "deleteSpy");
        result = doSpyOperation(am.toXML());
        System.out.println(result);
    }

    /**
     * This method uses the doSpyOperation method on server to perform Spy
     * operations.
     *
     * @param spyMessage SpyMessage
     * @return result of operation
     */
    private static String doSpyOperation(java.lang.String spyMessage) {
        edu.cmu.andrew.yilongc.SpyService_Service service
                = new edu.cmu.andrew.yilongc.SpyService_Service();
        edu.cmu.andrew.yilongc.SpyService port = service.getSpyServicePort();
        return port.doSpyOperation(spyMessage);
    }
}
