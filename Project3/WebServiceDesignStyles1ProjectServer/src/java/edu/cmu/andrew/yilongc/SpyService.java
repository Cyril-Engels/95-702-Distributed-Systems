/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.yilongc;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * This class is the web server providing functions for adding, updating,
 * deleting spy and getting spy information.
 *
 * @author changyilong
 */
@WebService(serviceName = "SpyService")
public class SpyService {

    // Spy list
    SpyList spyList = SpyList.getInstance();

    /**
     * Web service operation to add spy to the spy list.
     *
     * @param name name of spy to be added
     * @param title title of spy to be added
     * @param location location of spy to be added
     * @param password password of spy to be added
     * @return result of adding
     */
    @WebMethod(operationName = "addSpy")
    public String addSpy(@WebParam(name = "name") String name,
            @WebParam(name = "title") String title,
            @WebParam(name = "location") String location,
            @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        if (spyList.get(name) != null) {
            return "Spy already exists. No update made.";
        }
        Spy spy = new Spy(name, title, location, password);
        spyList.add(spy);
        return spy.toString();
    }

    /**
     * Web service operation to update a spy in spy list.
     *
     * @param name name of spy to be updated
     * @param title title of spy to be updated
     * @param location location of spy to be updated
     * @param password password of spy to be updated
     * @return result of updating
     */
    @WebMethod(operationName = "updateSpy")
    public String updateSpy(@WebParam(name = "name") String name,
            @WebParam(name = "title") String title,
            @WebParam(name = "location") String location,
            @WebParam(name = "password") String password) {
        if (spyList.get(name) == null) {
            return "Spy doesn't exist. No update made.";
        }

        Spy spy = new Spy(name, title, location, password);
        spyList.add(spy);
        return spy.toString();
    }

    /**
     * Web service operation to get information of a spy in spy list.
     *
     * @param name name of spy to be gotten
     * @return spy info
     */
    @WebMethod(operationName = "getSpy")
    public String getSpy(@WebParam(name = "name") String name) {
        if (spyList.get(name) == null) {
            return "No such spy.";
        }
        return spyList.get(name).toString();
    }

    /**
     * Web service operation to delete a spy in spy list.
     *
     * @param name name of spy to be deleted
     * @return result of deleting
     */
    @WebMethod(operationName = "deleteSpy")
    public String deleteSpy(@WebParam(name = "name") String name) {
        Spy spy;
        if ((spy = spyList.get(name)) == null) {
            return "Spy doesn't exist. No deletion made.";
        }
        spyList.delete(spy);
        return String.format("Spy <%s> was deleted from the list.", name);
    }

    /**
     * Web service operation to get the text representation of all spies in spy
     * list.
     *
     * @return text representation of all spies
     */
    @WebMethod(operationName = "getList")
    public String getList() {
        return spyList.toString();
    }

    /**
     * Web service operation to get the XML representation of all spies in spy
     * list.
     *
     * @return XML representation of all spies
     */
    @WebMethod(operationName = "getListAsXML")
    public String getListAsXML() {
        return spyList.toXML();
    }
}
