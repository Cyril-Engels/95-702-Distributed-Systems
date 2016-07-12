/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.yilongc;

/**
 * This class represents a spy.
 *
 * @author changyilong
 */
public class Spy {

    // instance data for spies 
    private String name;
    private String title;
    private String location;
    private String password;

    /**
     * Constructor with Spy name, title, location and password.
     *
     * @param name Spy name
     * @param title Spy title
     * @param location Spy location
     * @param password Spy password
     */
    public Spy(String name, String title, String location, String password) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = password;
    }

    /**
     * Constructor with Spy name, title and location.
     *
     * @param name Spy name
     * @param title Spy title
     * @param location Spy location
     */
    public Spy(String name, String title, String location) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = "";
    }

    /**
     * Constructor with Spy name.
     *
     * @param name Spy name.
     */
    public Spy(String name) {
        this.name = name;
        this.title = "";
        this.location = "";
        this.password = "";
    }

    /**
     * Default Constructor.
     */
    public Spy() {
        this.name = "";
        this.title = "";
        this.location = "";
        this.password = "";
    }

    /**
     * This method generates the XML representation of a Spy object.
     *
     * @return XML representation of a Spy object
     */
    public String toXML() {
        StringBuffer xml = new StringBuffer();
        xml.append("<spy>\n");
        xml.append("<name>" + name + "</name>\n");
        xml.append("<spyTitle>" + title + "</spyTitle>\n");
        xml.append("<location>" + location + "</location>\n");
        xml.append("<password>" + password + "</password>\n");
        xml.append("</spy>");
        return xml.toString();
    }

    /**
     * This method overrides the toString method of class Object.
     *
     * @return text representation of a spy
     */
    @Override
    public String toString() {
        return String.format("Name: %s  Title: %s Location: %s.", name, title, location);
    }

    /**
     * Getter of password.
     *
     * @return Spy password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of password.
     *
     * @param password Spy password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of location.
     *
     * @return Spy location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter of name.
     *
     * @return Spy name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter of title.
     *
     * @return Spy title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of location.
     *
     * @param location Spy location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter of name.
     *
     * @param name Spy name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter of title.
     *
     * @param title Spy title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
