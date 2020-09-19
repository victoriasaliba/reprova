package br.ufmg.engsoft.reprova.tests.mime.json;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Question;
import br.ufmg.engsoft.reprova.model.Semester;


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
   * Rendering then parsing should produce an equivalent object.
   */
  @Test
  void question() {
    Question question = new Question.Builder()
      .id("id")
      .theme("theme")
      .description("description")
      .statement("statement")
      .record(
              (Map<Semester, Map<String, Float>>)Stream.of(
                      entry(
                              new Semester(2019, Semester.Reference._1),
                              (Map<String, Float>)Stream.of(
                                      entry("tw", 50.0f),
                                      entry("tz", 49.5f),
                                      entry("tx", 51.2f)
                              ).collect(entriesToMap())
                      ),
                      entry(
                              new Semester(2020, Semester.Reference._2),
                              Collections.emptyMap()
                      )
              ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
      )
      .pvt(false)
      .build();

    Json formatter = new Json();

    String json = formatter.render(question);

    Question questionCopy = formatter
      .parse(json, Question.Builder.class)
      .build();

    assertTrue(
      question.equals(questionCopy)
    );
  }
}
