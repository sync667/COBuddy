package com.gmail.sync667.objects;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;


public class TemperatureSensor{

    private String uuid = null;
    private double temperature = 0;
    private int room = 0;

    private String device;

    public TemperatureSensor(String uuid, int room){
        this.uuid = uuid;
        this.room = room;

        parseFile();
    }

    public String getUuid()
    {
        return this.uuid;
    }

    public int getRoom()
    {
        return this.room;
    }

    public double getTemperature()
    {
        parseFile();
        return this.temperature;
    }

    private void parseFile()
    {
        try(FileInputStream inputStream = new FileInputStream("/sys/bus/w1/devices/" + uuid + "/w1_slave")) {
            this.device = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        temperature = (Integer.parseInt(device.split("t=")[1].trim()) / 1000);
    }
}
