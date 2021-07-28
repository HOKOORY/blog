package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class Test {

    @RequestMapping("/test")
    public Object test(@RequestParam(name = "id") String id) {
        return new Response<>(id);
    }

    public static void main(String[] args) {
        try {
            InetAddress[] address= InetAddress.getAllByName("baidu.com");
            for (InetAddress a : address) {
                System.out.println(a);
            }
//            System.out.println(Arrays.toString(address));
//            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
