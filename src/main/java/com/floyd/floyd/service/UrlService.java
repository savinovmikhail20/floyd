package com.floyd.floyd.service;

import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

@Service
public class UrlService {

    public static  String getHtml(String url){
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return content;
    }


}
