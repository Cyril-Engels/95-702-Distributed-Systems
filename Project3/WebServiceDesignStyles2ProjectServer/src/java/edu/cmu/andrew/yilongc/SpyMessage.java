/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.yilongc;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * This class parses the request string from clients.
 *
 * @author changyilong
 */
public class SpyMessage {

    private String operation;
    private Spy spy;

    /**
     * Constructor.
     *
     * @param xmlString XML representation of client sent Spy operation and/or
     * Spy information
     */
    public SpyMessage(String xmlString) {
        parseXML(getDocument(xmlString));
    }

    /**
     * Parses the DOM representation of client-sent string to operation and/or
     * spy.
     *
     * @param document DOM representation of the client-sent string
     */
    private void parseXML(Document document) {
        document.getDocumentElement().normalize();
        NodeList nodeOp = document.getElementsByTagName("operation");
        operation = nodeOp.item(0).getTextContent();
        NodeList nodeSpies = document.getElementsByTagName("spy");
        Node nodeSpy;
        // See if there is a Spy
        if ((nodeSpy = nodeSpies.item(0)) != null) {
            NodeList nodeListSpy = nodeSpy.getChildNodes();
            String name = nodeListSpy.item(0).getTextContent();
            String title = nodeListSpy.item(1).getTextContent();
            String location = nodeListSpy.item(2).getTextContent();
            String password = nodeListSpy.item(3).getTextContent();
            spy = new Spy(name, title, location, password);
        }
    }

    /**
     * Get the DOM representation of the client-sent string.
     *
     * @param xmlString client-sent XML string
     * @return DOM representation of the client-sent string
     */
    private Document getDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document spyDoc = null;
        try {
            builder = factory.newDocumentBuilder();
            spyDoc = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spyDoc;
    }

    /**
     * Getter of operation.
     *
     * @return operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Getter of Spy.
     *
     * @return Spy
     */
    public Spy getSpy() {
        return spy;
    }
}
