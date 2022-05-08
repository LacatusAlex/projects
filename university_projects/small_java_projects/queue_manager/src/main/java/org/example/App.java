package org.example;

import org.example.logic.SimulationManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        SimulationManager manager = new SimulationManager();
        Thread t= new Thread(manager);
        t.start();


    }
}
