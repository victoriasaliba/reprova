package br.ufmg.engsoft.reprova.model.variability;

import br.ufmg.engsoft.reprova.model.ScoreFile;
import br.ufmg.engsoft.reprova.model.ScoreFileFactory;
import br.ufmg.engsoft.reprova.model.ScoreJson;

public class JsonScoreFactory extends ScoreFileFactory {
    @Override
    public ScoreFile createScoreFile(String submittedFileName) {
        ScoreJson scoreFile = new ScoreJson();
        if (!scoreFile.validateFile(submittedFileName)){
            throw new IllegalArgumentException("Score file must be .json");
        }
        return scoreFile;
    }
}
