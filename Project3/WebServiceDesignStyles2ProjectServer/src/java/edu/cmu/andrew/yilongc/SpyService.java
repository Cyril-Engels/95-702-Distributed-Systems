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
 * This class is the web server providing a function for adding, updating,
 * deleting spy and getting spy information.
 *
 * @author changyilong
 */
@WebService(serviceName = "SpyService")
public class SpyService {

    private SpyList spyList = SpyList.getInstance();

    /**
     * Web service operation for adding, updating, deleting spy and getting spy
     * information.
     *
     * @param spyMessage client-sent XML string of Spy operation and/or Spy
     * @return operation result
     */
    @WebMethod(operationName = "doSpyOperation")
    public String doSpyOperation(
            @WebParam(name = "spyMessage") String spyMessage) {
        SpyMessage sm = new SpyMessage(spyMessage);
        String operation = sm.getOperation();
        String response = "";
        Spy spy = null;
        switch (operation) {
            case "addSpy":
                spy = sm.getSpy();
                if (spyList.get(spy.getName()) != null) {
                    response = "Spy already exists. No update made.";
                } else {
                    response = spyList.add(spy).toString();
                }
                break;
            case "updateSpy":
                spy = sm.getSpy();
                if (spyList.get(spy.getName()) == null) {
                    response = "Spy doesn't exist. No update made.";
                } else {
                    response = spyList.add(spy).toString();
                }
                break;
            case "getSpyAsXML":
                spy = sm.getSpy();
                if (spyList.get(spy.getName()) == null) {
                    response = "No such spy.";
                } else {
                    response = spyList.get(spy.getName()).toXML();
                }
                break;
            case "deleteSpy":
                spy = sm.getSpy();
                if (spyList.get(spy.getName()) == null) {
                    response = "Spy doesn't exist. No Spy deletion.";
                } else {
                    spyList.delete(spy);
                    response = String.format("Spy <%s> was deleted from the list.", spy.getName());
                }
                break;
            case "getList":
                response = spyList.toString();
                break;
            case "getListAsXML":
                response = spyList.toXML();
                break;
        }
        return response;
    }
}
