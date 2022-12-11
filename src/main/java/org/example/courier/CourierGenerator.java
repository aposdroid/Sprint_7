package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public Courier generic(){
        return new Courier("Loda", "1234", "EGM");
    }
    public Courier random(){
        return new Courier(RandomStringUtils.randomAlphanumeric(10),"1234", "EGM");
    }
    public Courier loginData(){
        return new Courier("Loda","1234");
    }
}