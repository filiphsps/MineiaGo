package com.filiphsandstrom.mineiago;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logging {
    private File loggingFile;
    PrintWriter printer;

    public Logging() {
        MineiaGo instance = MineiaGo.getInstance();

        // Create plugin folder if it doesn't exist
        if (!instance.getDataFolder().exists())
            instance.getDataFolder().mkdir();

        loggingFile = new File(instance.getDataFolder(), "MineiaGo.log");
        if (!loggingFile.exists()) {
            try {
                loggingFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            printer = new PrintWriter(new FileWriter(loggingFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Save () {
        printer.close();
    }

    private void Print (String msg) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        
        String output = "[" + dateFormat.format(date) + "]" + msg + "\n";
        printer.print(output);
    }

    public void Info(String msg) {
        Print("[INFO]: " + msg);
    }

    public void Warning(String msg) {
        Print("[WARNING]: " + msg);
    }

    public void Error(String msg) {
        Print("[ERROR]: " + msg);
    }

    public void Fatal(String msg) {
        Print("[FATAL]: " + msg);
    }
}
