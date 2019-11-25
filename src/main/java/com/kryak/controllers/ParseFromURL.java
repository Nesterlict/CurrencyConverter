package com.kryak.controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParseFromURL {

    String url = "http://www.cbr.ru/currency_base/daily/";
    public HashMap<String,Double> currencyValue = createMap();

    public Document getWebsite() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public ArrayList<String> getColumn(int col)  {

        ArrayList<String> list = new ArrayList<String>();
        Element table = getWebsite().select("table").get(0);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            list.add(cols.get(col).text());
        }
        return list;
    }
    public ArrayList<Double> getDoubleArray(ArrayList<String> stringArray) {
        ArrayList<Double> result = new ArrayList<>();
        for(String stringValue : stringArray) {
            try {
                result.add(Double.valueOf(stringValue.replace(",",".")));
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }
        return result;
    }


    public HashMap<String,Double> createMap() {
        ArrayList<String> names = getColumn(1);
        ArrayList<Double> valuesR = getDoubleArray(getColumn(2));
        ArrayList<Double> values = getDoubleArray(getColumn(4));
        HashMap<String,Double> value = new HashMap<>();
        for (int i = 0; i<names.size(); i++) {
            value.put(names.get(i), Controller.cutDouble(values.get(i)/valuesR.get(i)));
        }
        value.put("RUB",1.0);
        return value;
    }

    public String getAll()  {
        StringBuilder sb = new StringBuilder();
        try {
            for(Scanner sc = new Scanner(new URL(url).openStream()); sc.hasNext(); )
                sb.append(sc.nextLine()).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}