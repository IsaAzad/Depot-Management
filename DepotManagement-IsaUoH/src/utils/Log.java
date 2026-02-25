package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static Log instance;
    private StringBuffer logBuffer;
    private final String LOG_FILE = System.getProperty("user.dir") + "/log.txt";

    private Log() {
        logBuffer = new StringBuffer();
    }

    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public synchronized void logEvent(String message) {
        String timestamp = getCurrentTimestamp();
        logBuffer.append(timestamp).append(" - EVENT: ").append(message).append("\n");
    }

    public synchronized void logError(String errorMessage) {
        String timestamp = getCurrentTimestamp();
        logBuffer.append(timestamp).append(" - ERROR: ").append(errorMessage).append("\n");
    }

    public synchronized void writeLogToFile() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) file.createNewFile();
            if (!file.canWrite()) throw new IOException("No write permissions for: " + LOG_FILE);

            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE))) {
                writer.write(logBuffer.toString());
            }
        } catch (IOException e) {
            System.err.println("Failed to write log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void writeImmediateLog(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = getCurrentTimestamp();
            writer.println(timestamp + " - IMMEDIATE: " + message);
        } catch (IOException e) {
            System.err.println("Failed to write immediate log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Log.getInstance().writeLogToFile();
            System.out.println("Logs have been written to: " + System.getProperty("user.dir") + "/app_log.txt");
        }));
    }
}