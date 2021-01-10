/**
 * TTTWebService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ttt.james.server;

public class TTTWebService_ServiceLocator extends org.apache.axis.client.Service implements ttt.james.server.TTTWebService_Service {

    public TTTWebService_ServiceLocator() {
    }


    public TTTWebService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TTTWebService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TTTWebServicePort
    private java.lang.String TTTWebServicePort_address = "http://127.0.0.1/TTTWebService";

    public java.lang.String getTTTWebServicePortAddress() {
        return TTTWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TTTWebServicePortWSDDServiceName = "TTTWebServicePort";

    public java.lang.String getTTTWebServicePortWSDDServiceName() {
        return TTTWebServicePortWSDDServiceName;
    }

    public void setTTTWebServicePortWSDDServiceName(java.lang.String name) {
        TTTWebServicePortWSDDServiceName = name;
    }

    public ttt.james.server.TTTWebService_PortType getTTTWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TTTWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTTTWebServicePort(endpoint);
    }

    public ttt.james.server.TTTWebService_PortType getTTTWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ttt.james.server.TTTWebServicePortBindingStub _stub = new ttt.james.server.TTTWebServicePortBindingStub(portAddress, this);
            _stub.setPortName(getTTTWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTTTWebServicePortEndpointAddress(java.lang.String address) {
        TTTWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ttt.james.server.TTTWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ttt.james.server.TTTWebServicePortBindingStub _stub = new ttt.james.server.TTTWebServicePortBindingStub(new java.net.URL(TTTWebServicePort_address), this);
                _stub.setPortName(getTTTWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TTTWebServicePort".equals(inputPortName)) {
            return getTTTWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.james.ttt/", "TTTWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.james.ttt/", "TTTWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TTTWebServicePort".equals(portName)) {
            setTTTWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
