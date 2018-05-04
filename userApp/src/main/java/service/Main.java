package service;

import controller.HTTPServer;
import singletons.Logger;
import singletons.Mqtt;

public class Main {
    public static void main(String[] args) throws Exception {
        Mqtt.getInstance();
        HTTPServer.start(8003);
        Logger.getInstance().log("User App started successfully", null);
    }
}
