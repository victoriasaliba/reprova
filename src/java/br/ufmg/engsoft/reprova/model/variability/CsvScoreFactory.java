package br.ufmg.engsoft.reprova.model.variability;

import br.ufmg.engsoft.reprova.model.ScoreCsv;
import br.ufmg.engsoft.reprova.model.ScoreFile;
import br.ufmg.engsoft.reprova.model.ScoreFileFactory;

public class CsvScoreFactory extends ScoreFileFactory {
    @Override
    public ScoreFile createScoreFile(String submittedFileName) {
        ScoreCsv scoreFile = new ScoreCsv();
        if (!scoreFile.validateFile(submittedFileName)){
            throw new IllegalArgumentException("Score file must be .csv");
        }
        return scoreFile;
    }
}
