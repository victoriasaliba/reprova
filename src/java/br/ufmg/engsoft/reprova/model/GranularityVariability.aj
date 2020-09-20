package br.ufmg.engsoft.reprova.model;

import br.ufmg.engsoft.reprova.Configuration;
import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.variability.CoarseGrainedCourseFactory;
import br.ufmg.engsoft.reprova.model.variability.FineGrainedCourseFactory;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

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
    pointcut deserializeToConfiguredGranularity(Json.CourserDeserializer cd, JsonElement json, GsonBuilder parserBuilder, Class<?> clazz) :
            call(Course Json.CourserDeserializer.deserializeTo(..)) && if (Configuration.isFineGrained()) && target(cd) && args(json,parserBuilder,clazz);
    Course around(Json.CourserDeserializer cd, JsonElement json, GsonBuilder parserBuilder, Class<?> clazz): deserializeToConfiguredGranularity(cd, json,parserBuilder,clazz) {
        return proceed(cd, json,parserBuilder,FineGrainedCourse.class);
    }
}
