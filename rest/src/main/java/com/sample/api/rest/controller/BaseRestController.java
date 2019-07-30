package com.sample.api.rest.controller;


import com.sample.api.model.ResponseError;
import com.sample.exception.CheckedException;
import com.sample.exception.EntityNotFound;
import com.sample.exception.ForbiddenException;
import com.sample.exception.InternalCheckedException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Component
public class BaseRestController {

    private static final Logger logger = LoggerFactory.getLogger(BaseRestController.class);



    public String logRequestData(HttpServletRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append("\n\n ===========================   Request begin  =========================== \n ");
        builder.append("\n URI             : " + request.getRequestURI());
        builder.append("\n PATH_INFO       : " + request.getPathInfo());
        builder.append("\n Method          : " + request.getMethod());
        builder.append("\n CONTENT_TYPE    : " + request.getContentType());
        builder.append("\n CONTENT_LENGTH  : " + request.getContentLength());
        builder.append("\n HEADERS         : ");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            builder.append(headerName + " = " + request.getHeader(headerName) + "\n");
        }
        Map<String, String[]> reqParams = getRequestParameters(request);
        if (!reqParams.isEmpty()) {
            builder.append("\n REQUEST PARAMS         ");
            for (String paramName : reqParams.keySet()) {
                builder.append("\n PARAM " + paramName + " = " + Arrays.asList(reqParams.get(paramName)));
            }
        }
//        try {
//            builder.append("Request body: "+getRequestBody(request));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        builder.append("\n ==========================  Request end  =============================== \n");

        return builder.toString();
    }

    @ExceptionHandler(Exception.class)
    public ResponseError handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        if (true) {
            logger.error(getLogRequestData(request, e));
        }

        logger.error(" \n >>>>>>>>> " + e.getClass().getName() + " <<<<<<<<< \n");
        logger.error("\n RunTime Exception ", e);
        //e.printStackTrace();

        if (e instanceof CheckedException) {
            if (e instanceof InternalCheckedException) {
                return new ResponseError(e.getMessage(), response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return new ResponseError(e.getMessage(), response, HttpServletResponse.SC_BAD_REQUEST);
        } else if(e instanceof TypeMismatchException) {
            return new ResponseError("main.invalid.params", response, HttpServletResponse.SC_NOT_FOUND);
        } else if(e instanceof EntityNotFound) {
            return new ResponseError(e.getMessage(), response, HttpServletResponse.SC_NOT_FOUND);
        } else if(e instanceof ForbiddenException) {
            return new ResponseError(e.getMessage(), response, HttpServletResponse.SC_FORBIDDEN);
        }

        logger.error(" \n >>>>>>>>>  " + e.getClass().getName() + "  <<<<<<<<< \n");

        return new ResponseError("main.error", response);
    }


    private String getLogRequestData(HttpServletRequest request, Exception e) {

        StringBuilder builder = new StringBuilder();
        builder.append("\n\n ===========================   " + e.getClass().getName() + " request begin  ============================================= \n ");
        builder.append("\n URI             : " + request.getRequestURI());
        builder.append("\n PATH_INFO       : " + request.getPathInfo());
        builder.append("\n Method          : " + request.getMethod());
        builder.append("\n CONTENT_TYPE    : " + request.getContentType());
        builder.append("\n CONTENT_LENGTH  : " + request.getContentLength());
        builder.append("\n HEADERS         : ");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            builder.append(headerName + " = " + request.getHeader(headerName) + "\n");
        }
        Map<String, String[]> reqParams = getRequestParameters(request);
        if (!reqParams.isEmpty()) {
            builder.append("\n REQUEST PARAMS         ");
            for (String paramName : reqParams.keySet()) {
                builder.append("\n PARAM " + paramName + " = " + Arrays.asList(reqParams.get(paramName)));
            }
        }
//        try {
//            builder.append("Request body: "+getRequestBody(request));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        builder.append("\n ==========================   " + e.getClass().getName() + " request end  ================================================ \n");

        return builder.toString();
    }


    public void logRequest(HttpServletRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append("\n\n ===========================   Logging request! request begin  ============================================= \n ");
        builder.append("\n URI             : " + request.getRequestURI());
        builder.append("\n PATH_INFO       : " + request.getPathInfo());
        builder.append("\n Method          : " + request.getMethod());
        builder.append("\n CONTENT_TYPE    : " + request.getContentType());
        builder.append("\n CONTENT_LENGTH  : " + request.getContentLength());
        builder.append("\n HEADERS         : ");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            builder.append(headerName + " = " + request.getHeader(headerName) + "\n");
        }
        Map<String, String[]> reqParams = getRequestParameters(request);
        if (!reqParams.isEmpty()) {
            builder.append("\n REQUEST PARAMS         ");
            for (String paramName : reqParams.keySet()) {
                builder.append("\n PARAM " + paramName + " = " + Arrays.asList(reqParams.get(paramName)));
            }
        }
//        try {
//            builder.append("Request body: "+getRequestBody(request));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        builder.append("\n ==============================  request end  ================================================ \n");

        System.out.println( builder.toString() );

    }

    /**
     * Get locale from Request
     *
     * @param request
     * @return
     */
    protected Locale getLocale(HttpServletRequest request) {
        String language = request.getHeader("lang");
        if (language != null) {
            return new Locale(language);
        }
        return null;
    }


    public static Map<String, String[]> getRequestParameters(HttpServletRequest request) {
        Map<String, String[]> queryParameters = new HashMap<>();
        String queryString = request.getQueryString();

        if (StringUtils.isEmpty(queryString)) {
            return queryParameters;
        }

        String[] parameters = queryString.split("&");

        for (String parameter : parameters) {
            String[] keyValuePair = parameter.split("=");
            String[] values = queryParameters.get(keyValuePair[0]);
            values = ArrayUtils.add(values, keyValuePair.length == 1 ? "" : keyValuePair[1]); //length is one if no value is available.
            queryParameters.put(keyValuePair[0], values);
        }
        return queryParameters;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
//        if ( request.getMethod().equals("POST") ) {
            StringBuffer sb = new StringBuffer();
            BufferedReader bufferedReader = null;
            String content = "";

            try {
                //InputStream inputStream = request.getInputStream();
                //inputStream.available();
                //if (inputStream != null) {
                bufferedReader =  request.getReader() ; //new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
                    sb.append(charBuffer, 0, bytesRead);
                }
                //} else {
                //        sb.append("");
                //}

            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw ex;
                    }
                }
            }

            content += sb.toString();
//        }
        return content;
    }
}
