import br.ufmg.engsoft.reprova.model.variability.CsvScoreFactory;
import br.ufmg.engsoft.reprova.model.variability.JsonScoreFactory;
import br.ufmg.engsoft.reprova.Configuration;
import br.ufmg.engsoft.reprova.model.ScoreFileFactory;

public aspect ScoreFileTypeVariability {
    pointcut setScoreFileFactoryAsCsv(): call(ScoreFileFactory ScoreFileFactory.create(..)) && if (Configuration.isCsv());
    ScoreFileFactory around(): setScoreFileFactoryAsCsv() {
            return new CsvScoreFactory();
        }
    pointcut setScoreFileFactoryAsJson(): call(ScoreFileFactory ScoreFileFactory.create(..)) && if (Configuration.isJson());
        ScoreFileFactory around(): setScoreFileFactoryAsCsv() {
                return new JsonScoreFactory();
            }
}