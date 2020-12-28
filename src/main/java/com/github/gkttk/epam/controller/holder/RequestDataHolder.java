package com.github.gkttk.epam.controller.holder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestDataHolder {


    private final Map<String, String[]> requestParameters = new HashMap<>();
    private final Map<String, Object> requestAttributes = new HashMap<>();
    private final Map<String, Object> sessionAttributes = new HashMap<>();
    private boolean isSessionValid;


    public RequestDataHolder(HttpServletRequest request) {
        isSessionValid = true;
        extractRequestParameters(request);
        extractRequestAttributes(request);
        extractSessionAttribute(request);
    }

    public void invalidateSession() {
        this.isSessionValid = false;
    }

    public void removeSessionAttribute(String key){
        sessionAttributes.remove(key);
    }


    public boolean isSessionValid() {
        return isSessionValid;
    }

    public void fillRequest(HttpServletRequest request) { //todo don't fill parameters
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach((key, value) -> request.getSession().setAttribute(key, value));

    }


    public String[] getRequestParameters(String key) {
        return requestParameters.get(key);
    }

    public String getRequestParameter(String key) {
        if(requestParameters.get(key) != null){
            return requestParameters.get(key)[0];
        }
        return null;
    }



    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void putRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }


    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void putSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }


    private void extractRequestParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        requestParameters.putAll(parameterMap);
    }


    private void extractRequestAttributes(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            Object parameter = request.getAttribute(name);
            this.requestAttributes.put(name, parameter);
        }
    }

    private void extractSessionAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttrNames = session.getAttributeNames();
        while (sessionAttrNames.hasMoreElements()) {
            String name = sessionAttrNames.nextElement();
            Object parameter = session.getAttribute(name);
            this.sessionAttributes.put(name, parameter);
        }
    }
}
