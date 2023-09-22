package com.service.client.service;

import com.service.client.withoutswagger.resttemplate.GetTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    GetTestService getTestService;
    public String callTest(){
        return getTestService.callTest();
    }
}
