package com.utilities;

public class Env {

    private static final int TCP_PORT = 54587, UDP_PORT = 54787;
    public static int getTcpPort() {
        return TCP_PORT;
    }
    public static int getUdpPort() {
        return UDP_PORT;
    }

}
