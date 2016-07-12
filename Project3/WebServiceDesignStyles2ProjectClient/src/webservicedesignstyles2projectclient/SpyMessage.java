/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles2projectclient;

/**
 * This class is the pattern of message to be sent to server.
 *
 * @author changyilong
 */
public class SpyMessage {

    private Spy spy;
    private String operation;

    /**
     * Constructor.
     *
     * @param spy Spy
     * @param operation operation to be performed on server
     */
    public SpyMessage(Spy spy, String operation) {
        this.spy = spy;
        this.operation = operation;
    }

    /**
     * Return the XML presentation of SpyMessage.
     *
     * @return XML presentation of SpyMessage
     */
    public String toXML() {
        String xml;
        // If get List, no spy is passed
        if ("getList".equals(operation) || "getListAsXML".equals(operation)) {
            xml = String.format("<spyMessage>\n<operation>%s</operation>\n</spyMessage>", operation);
            return xml;
        } else {
            xml = String.format("<spyMessage>\n<operation>%s</operation>\n<spy>"
                    + "<name>%s</name><spyTitle>%s</spyTitle><location>%s</location><password>%s</password>\n"
                    + "</spy> </spyMessage>", operation, spy.getName(), spy.getTitle(), spy.getLocation(), spy.getPassword());
        }
        return xml;
    }
}
