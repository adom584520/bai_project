/**
 * QueryContentService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.huawei.cimp;

public class QueryContentService_ServiceLocator extends org.apache.axis.client.Service implements com.huawei.cimp.QueryContentService_Service {

    public QueryContentService_ServiceLocator() {
    }


    public QueryContentService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public QueryContentService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for QueryContentService
    private java.lang.String QueryContentService_address = "http://127.0.0.1:8088/services/QueryContentService";

    public java.lang.String getQueryContentServiceAddress() {
        return QueryContentService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QueryContentServiceWSDDServiceName = "QueryContentService";

    public java.lang.String getQueryContentServiceWSDDServiceName() {
        return QueryContentServiceWSDDServiceName;
    }

    public void setQueryContentServiceWSDDServiceName(java.lang.String name) {
        QueryContentServiceWSDDServiceName = name;
    }

    public com.huawei.cimp.QueryContentService_PortType getQueryContentService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QueryContentService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQueryContentService(endpoint);
    }

    public com.huawei.cimp.QueryContentService_PortType getQueryContentService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.huawei.cimp.QueryContentServiceSoapBindingStub _stub = new com.huawei.cimp.QueryContentServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getQueryContentServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQueryContentServiceEndpointAddress(java.lang.String address) {
        QueryContentService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.huawei.cimp.QueryContentService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.huawei.cimp.QueryContentServiceSoapBindingStub _stub = new com.huawei.cimp.QueryContentServiceSoapBindingStub(new java.net.URL(QueryContentService_address), this);
                _stub.setPortName(getQueryContentServiceWSDDServiceName());
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
        if ("QueryContentService".equals(inputPortName)) {
            return getQueryContentService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://cimp.huawei.com", "QueryContentService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://cimp.huawei.com", "QueryContentService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("QueryContentService".equals(portName)) {
            setQueryContentServiceEndpointAddress(address);
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
