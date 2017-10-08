package com.gmail.sync667.objects;

import com.gmail.sync667.COBuddy;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;

public class ElectroValve{

    private COBuddy app;

    private double state = 0.00;

    private GpioPinDigitalOutput openPin;
    private GpioPinDigitalOutput  closePin;
    private double moveTime = 0;

    public ElectroValve(COBuddy app, Pin openPin, Pin closePin, double moveTime) {
        this.app = app;

        this.openPin = app.getGpioController().provisionDigitalOutputPin(openPin);
        this.closePin = app.getGpioController().provisionDigitalOutputPin(closePin);
        this.moveTime = moveTime;


    }

    public void close()
    {
        stop();
        closePin.high();

        app.getLogger().info("ElectroValve is closing.");
    }

    public void open()
    {
        stop();
        openPin.high();

        app.getLogger().info("ElectroValve is opening.");
    }

    public void stop()
    {
        openPin.low();
        closePin.low();

        app.getLogger().info("ElectroValve all actions stopped.");
    }

    public void stoptest()
    {
        openPin.high();
        closePin.high();

        app.getLogger().info("ElectroValve all actions stopped.");
    }
    public double getState()
    {
        return this.state;
    }

    public boolean isOpening()
    {
        return openPin.getState().isHigh();
    }

    public boolean isClosing()
    {
        return closePin.getState().isHigh();
    }

    public void calibrate()
    {

    }

}
