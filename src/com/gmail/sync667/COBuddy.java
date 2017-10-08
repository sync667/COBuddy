package com.gmail.sync667;

import com.gmail.sync667.objects.ElectroValve;
import com.gmail.sync667.objects.TemperatureSensor;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.awt.windows.ThemeReader;

public class COBuddy{

    private static COBuddy app;

    private static final Logger logger = LogManager.getLogger("CO-Buddy");
    private static final String VERSION = "0.1.0";
    private GpioController gpioController = null;

    public static void main(String[] args) {

        System.out.println("----- CO Buddy v." + VERSION + " ------");
        logger.info("Starting COBuddy server: " + VERSION);
        app = new COBuddy();

        app.startApp();

    }

    private void startApp()
    {
        logger.info("Setting up GPIO Controller!");
        setupGpio();

        logger.info("Setup ElectroValve object class");
        ElectroValve electroValve = new ElectroValve(app, RaspiPin.GPIO_09, RaspiPin.GPIO_10, 28);
        TemperatureSensor temperatureSensor1 = new TemperatureSensor("28-0316a112f7ff", 1);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor("28-0316b4a387ff", 2);
        TemperatureSensor temperatureSensor3 = new TemperatureSensor("28-0416a132e5ff", 3);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    electroValve.stoptest();
            logger.warn("CO Buddy is terminating!");
        })
        );

        while (true) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            electroValve.close();
            logger.info("Temperature 1 = " + temperatureSensor1.getTemperature());
            logger.info("Temperature 2 = " + temperatureSensor2.getTemperature());
            logger.info("Temperature 3 = " + temperatureSensor3.getTemperature());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            electroValve.open();
        }
    }

    private void setupGpio()
    {
        gpioController = GpioFactory.getInstance();
    }

    public GpioController getGpioController()
    {
        return this.gpioController;
    }

    public COBuddy getApp()
    {
        return this.app;
    }

    public Logger getLogger()
    {
        return logger;
    }
}
