package br.ufmg.engsoft.reprova.tests.mime.json;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Course;
import br.ufmg.engsoft.reprova.model.Question;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue());
    }

    public static <K, U> Collector<Map.Entry<K, U>, ?, ConcurrentMap<K, U>> entriesToConcurrentMap() {
        return Collectors.toConcurrentMap((e) -> e.getKey(), (e) -> e.getValue());
    }

  /**
   * Rendering then parsing should produce an equivalent Question object.
   */
  @Test
  void testQuestionSerialization() {
    Course c1 =  new Course(2019, Course.Reference._1,"Software Reuse",50.0f);
    Course c2 =  new Course(2019, Course.Reference._1,"Design and Analysis of Algorithms",49.5f);
    Course c3 =  new Course(2020, Course.Reference._2,"Database",51.2f);
    Question question = new Question.Builder()
      .id("id")
      .theme("theme")
      .description("description")
      .statement("statement")
      .courses(Arrays.asList(c1,c2,c3))
      .isPrivate(false)
      .build();

    Json formatter = new Json();

    String json = formatter.render(question);

    Question questionCopy = formatter
      .parse(json, Question.Builder.class)
      .build();

    assertEquals(question,questionCopy);
  }

    /**
     * Rendering then parsing should produce an equivalent Course object.
     */
    @Test
    void testCourseSerialization() {
        Course course =  new Course(2019, Course.Reference._1,"Software Reuse",50.0f);

        Json formatter = new Json();

        String json = formatter.render(course);

        Course courseCopy = formatter
                .parse(json, Course.class);

        assertEquals(course,courseCopy);
    }
}
