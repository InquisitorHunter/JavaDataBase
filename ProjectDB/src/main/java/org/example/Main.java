package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper();
        connectionWrapper.openConnection();
    }
}