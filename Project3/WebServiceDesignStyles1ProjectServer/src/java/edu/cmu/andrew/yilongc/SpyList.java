/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.yilongc;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class keeps a list of Spies.
 *
 * @author changyilong
 */
public class SpyList {

    // A TreeMap to keep spy information
    private Map<String, Spy> tree = new TreeMap<>();
    private static SpyList spyList = new SpyList();

    /**
     * Constructor.
     */
    private SpyList() {

    }

    /**
     * Method to get a SpyList instance.
     *
     * @return a SpyList instance.
     */
    public static SpyList getInstance() {
        return spyList;
    }

    /**
     * Add a Spy to the SpyList.
     *
     * @param s Spy to be added
     */
    public void add(Spy s) {
        tree.put(s.getName(), s);
    }

    /**
     * Delete a Spy from the SpyList.
     *
     * @param s Spy to be deleted
     * @return result of deleting
     */
    public Spy delete(Spy s) {
        return tree.remove(s.getName());
    }

    /**
     * Get a Spy from the SpyList.
     *
     * @param userID Spy name
     * @return Spy information
     */
    public Spy get(String userID) {
        return tree.get(userID);
    }

    /**
     * Get the Collection of all Spies.
     *
     * @return Collection of all Spies
     */
    public Collection<Spy> getList() {
        return tree.values();
    }

    /**
     * Override the toString method of class Object.
     *
     * @return text representation of all spies
     */
    @Override
    public String toString() {
        StringBuffer representation = new StringBuffer();
        Collection<Spy> c = getList();
        Iterator<Spy> sl = c.iterator();

        while (sl.hasNext()) {
            Spy spy = sl.next();
            representation.append("Name: " + spy.getName() + " Title: " + spy.getTitle()
                    + " Location: " + spy.getLocation() + ".\n");
        }
        if (representation.length() > 0) {
            return representation.toString().substring(0, representation.length() - 1);
        }
        return representation.toString();
    }

    /**
     * Get the XML representation of all spies.
     *
     * @return XML representation of all spies
     */
    public String toXML() {
        StringBuffer xml = new StringBuffer();
        xml.append("<spylist>\n");
        Collection<Spy> c = getList();
        Iterator<Spy> sl = c.iterator();
        while (sl.hasNext()) {
            Spy spy = sl.next();
            xml.append(spy.toXML()).append("\n");
        }
        // Now, close xml.append("</spylist>");
        xml.append("</spylist>");
        System.out.println("Spy list: " + xml.toString());
        return xml.toString();
    }
}