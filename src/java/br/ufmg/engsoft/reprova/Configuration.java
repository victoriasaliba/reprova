package br.ufmg.engsoft.reprova;

import org.omg.CORBA.Object;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Configuration {
    public enum Granularity { COARSE_GRAINED, FINE_GRAINED }
    private static Granularity granularity = null;
    private static boolean isLoaded = false;
    public static void readGranularity() {
        if (!isLoaded) {
            isLoaded = true;
            try {
                Properties prop = new Properties();
                InputStream is = ClassLoader.class.getResourceAsStream("/config.properties");
                prop.load(is);
                granularity = Granularity.valueOf(prop.getProperty("Granularity"));
            } catch (Exception e) {
                granularity = Granularity.COARSE_GRAINED;
            }
        }
    }
    public static boolean isFineGrained() {
        if (Objects.isNull(granularity)) {
            readGranularity();
        }
        return granularity.equals(Granularity.FINE_GRAINED);
    }
}
