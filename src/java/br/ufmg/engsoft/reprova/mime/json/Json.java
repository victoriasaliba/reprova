package br.ufmg.engsoft.reprova.mime.json;

import java.lang.reflect.Type;

import br.ufmg.engsoft.reprova.model.*;
import com.google.gson.*;


/**
 * Json format for Reprova's types.
 */
public class Json {
  /**
   * Deserializer for Semester.
   */
  public static class CourserDeserializer implements JsonDeserializer<Course> {
    /**
     * The semester format is:
     * "year/ref"
     * Where ref is 1 or 2.
     */
    @Override
    public Course deserialize(
      JsonElement json,
      Type typeOfT,
      JsonDeserializationContext context
    ) {
      GsonBuilder parserBuilder = new GsonBuilder();

      return deserializeTo(json, parserBuilder,CoarseGrainedCourse.class);
    }

    private <T extends Course> Course deserializeTo(JsonElement json, GsonBuilder parserBuilder, Class<T> clazz) {
      System.out.println("Deserialized to type " + clazz.getName());
      return parserBuilder.create().fromJson(json.getAsJsonObject(), clazz);
    }
  }

  /**
   * Deserializer for Student.
   */
  public static class StudentDeserializer implements JsonDeserializer<Student> {
    @Override
    public Student deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
    ) {
      GsonBuilder parserBuilder = new GsonBuilder();

      return parserBuilder.create().fromJson(json.getAsJsonObject(), Student.class);
    }
  }

  /**
   * Deserializer for Question.Builder.
   */
  public static class QuestionBuilderDeserializer
    implements JsonDeserializer<Question.Builder>
  {
    @Override
    public Question.Builder deserialize(
      JsonElement json,
      Type typeOfT,
      JsonDeserializationContext context
    ) {
      GsonBuilder parserBuilder = new GsonBuilder();

      parserBuilder.registerTypeAdapter( // Question has a Course field.
        Course.class,
        new CourserDeserializer()
      );

      Question.Builder questionBuilder = parserBuilder
        .create()
        .fromJson(
          json.getAsJsonObject(),
          Question.Builder.class
        );

      // Mongo's id property doesn't match Question.id:
      JsonElement _id = json.getAsJsonObject().get("_id");

      if (_id != null){
        questionBuilder.id(
          _id.getAsJsonObject()
            .get("$oid")
            .getAsString()
        );
      }
      return questionBuilder;
    }
  }



  /**
   * The json formatter.
   */
  protected final Gson gson;



  /**
   * Instantiate the formatter for Reprova's types.
   * Currently, it supports only the Question type.
   */
  public Json() {
    GsonBuilder parserBuilder = new GsonBuilder();

    parserBuilder.registerTypeAdapter(
      Question.Builder.class,
      new QuestionBuilderDeserializer()
    );

    this.gson = parserBuilder.create();
  }



  /**
   * Parse an object in the given class.
   * @throws JsonSyntaxException  if json is not a valid representation for the given class
   */
  public <T> T parse(String json, Class<T> cls) {
    return this.gson.fromJson(json, cls);
  }


  /**
   * Render an object of the given class.
   */
  public <T> String render(T obj) {
    return this.gson.toJson(obj);
  }
}
