package br.ufmg.engsoft.reprova;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * TODO: SONIA CUIDE DISSO!
 */
public class Configuration {
    public enum Granularity { COARSE_GRAINED, FINE_GRAINED }
    public enum ScoreFileType {CSV, JSON}
    private static Granularity granularity = null;
    private static ScoreFileType scoreFileType = null;
    private static boolean isLoaded = false;
    public static void readConfiguration() {
        if (!isLoaded) {
            isLoaded = true;
            try {
                Properties prop = new Properties();
                InputStream is = ClassLoader.class.getResourceAsStream("/config.properties");
                prop.load(is);
                readGranularity(prop);
                readScoreFileType(prop);
            } catch (Exception e) {
                granularity = Granularity.COARSE_GRAINED;
                scoreFileType = ScoreFileType.CSV;

            }
        }
    }
    public static void readGranularity(Properties properties){
        try{
            granularity = Granularity.valueOf(properties.getProperty("Granularity"));
        }catch (Exception e){
            granularity = Granularity.COARSE_GRAINED;
        }
    }
    public static void readScoreFileType(Properties properties){
        try{
            scoreFileType = ScoreFileType.valueOf(properties.getProperty("ScoreFileType"));
        }catch (Exception e){
            scoreFileType = ScoreFileType.CSV;
        }
    }
    public static void setCoarseGrained() {
        if (Objects.isNull(scoreFileType)){
            readConfiguration();
        }
        else{
            isLoaded = true;
        }
        granularity = Granularity.COARSE_GRAINED;
    }
    public static void setFineGrained() {
        if (Objects.isNull(scoreFileType)){
            readConfiguration();
        }
        else{
            isLoaded = true;
        }
        granularity = Granularity.FINE_GRAINED;
    }
    public static boolean isFineGrained() {
        if (Objects.isNull(granularity)) {
            readConfiguration();
        }
        return granularity.equals(Granularity.FINE_GRAINED);
    }
    public static void setScoreFileCsv(){
        if (Objects.isNull(granularity)){
            readConfiguration();
        }
        else{
            isLoaded = true;
        }
        scoreFileType = ScoreFileType.CSV;
    }
    public static void setScoreFileJson(){
        if (Objects.isNull(granularity)){
            readConfiguration();
        }
        else{
            isLoaded = true;
        }
        scoreFileType = ScoreFileType.JSON;
    }
    public static boolean isCsv(){
        if (Objects.isNull(granularity)) {
            readConfiguration();
        }
        return scoreFileType.equals(ScoreFileType.CSV);
    }
    public static boolean isJson(){
        if (Objects.isNull(granularity)) {
            readConfiguration();
        }
        return scoreFileType.equals(ScoreFileType.JSON);
    }
}
