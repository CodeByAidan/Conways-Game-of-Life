package com.gameoflife.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to configure the logger.
 * @see com.gameoflife.model.GameOfLife#main
 */
public class LogConfiguration {

    /**
     * This constructor is private to prevent instantiation.
     * @throws AssertionError
     *        If this constructor is called
     *        it throws an {@code AssertionError}
     *        because this constructor should never be called.
     *        This class should only be used statically.
     */
    private LogConfiguration() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * This method configures the logger.
     * @see com.gameoflife.util.CustomFormatter com.gameoflife.util.CustomFormatter
     * @see java.util.logging.Logger java.util.logging.Logger
     * @see java.util.logging.ConsoleHandler java.util.logging.ConsoleHandler
     */
    public static void configure() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new CustomFormatter());

        rootLogger.addHandler(consoleHandler);
    }
}
