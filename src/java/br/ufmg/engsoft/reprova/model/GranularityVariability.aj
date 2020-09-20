package br.ufmg.engsoft.reprova.model;

import br.ufmg.engsoft.reprova.Configuration;
import br.ufmg.engsoft.reprova.model.variability.CoarseGrainedCourseFactory;
import br.ufmg.engsoft.reprova.model.variability.FineGrainedCourseFactory;

public aspect GranularityVariability {
    pointcut setCourseFactoryAsFineGrained() : call(CourseFactory CourseFactory.create(..)) && if (Configuration.isFineGrained());
    CourseFactory around(): setCourseFactoryAsFineGrained() {
        System.out.println("Configured fine granularity!");
        return new FineGrainedCourseFactory();
    }
    pointcut setCourseFactoryAsCoarseGrained() : call(CourseFactory CourseFactory.create(..)) && if (!Configuration.isFineGrained());
    CourseFactory around(): setCourseFactoryAsCoarseGrained() {
        System.out.println("Configured coarse granularity!");
        return new CoarseGrainedCourseFactory();
    }
}
