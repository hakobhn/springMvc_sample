package com.sample.api.rest.controller;


import com.sample.ClassA;
import com.sample.api.model.ResponseStatus;
import com.sample.api.model.ResponseSuccess;
import com.sample.ClassB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleRestController extends BaseRestController {

    @Autowired
    ClassA classA;

    @Autowired
    ClassB classB;


    /**
     * Call method a
     *
     * @return
     */
    @RequestMapping(value = "call_a", method = RequestMethod.GET)
    public ResponseStatus callA(final HttpServletRequest request) {

        ResponseSuccess success = new ResponseSuccess();
        success.setRspObject(classA.methodA());
        return success;

    }

    /**
     * Call method b
     *
     * @return
     */
    @RequestMapping(value = "call_b", method = RequestMethod.GET)
    public ResponseStatus callB(final HttpServletRequest request) throws Exception {

        ResponseSuccess success = new ResponseSuccess();
        success.setRspObject(classB.methodB());
        return success;

    }
}
