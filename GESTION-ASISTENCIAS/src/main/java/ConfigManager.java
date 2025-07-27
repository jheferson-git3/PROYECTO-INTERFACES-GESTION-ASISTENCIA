package com.instituto.util;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";
    private static final String KEY_LAST_WEEK = "lastWeek";

    public static int leerUltimaSemana() {
        Properties props = new Properties();
        File f = new File(CONFIG_FILE);
        if (!f.exists()) return 1;
        try (FileInputStream in = new FileInputStream(f)) {
            props.load(in);
            return Integer.parseInt(props.getProperty(KEY_LAST_WEEK, "1"));
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static void guardarUltimaSemana(int semana) {
        Properties props = new Properties();
        props.setProperty(KEY_LAST_WEEK, String.valueOf(semana));
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Configuración aplicación");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
