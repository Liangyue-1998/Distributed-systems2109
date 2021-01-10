/**
 * TTTWebService_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ttt.james.server;

public interface TTTWebService_Service extends javax.xml.rpc.Service {
    public java.lang.String getTTTWebServicePortAddress();

    public ttt.james.server.TTTWebService_PortType getTTTWebServicePort() throws javax.xml.rpc.ServiceException;

    public ttt.james.server.TTTWebService_PortType getTTTWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
