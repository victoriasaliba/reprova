package br.ufmg.engsoft.reprova.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;


/**
 * A semester class.
 * The semester is composed of an year and a reference (1 or 2).
 */
public abstract class Course {
  /**
   * The reference part of a semester.
   * Either 1 or 2.
   */
  public enum Reference {
    _1(1),
    _2(2);

    public final int value;
    Reference(int i) {
      this.value = i;
    }

    /**
     * The mapping of integers to Semester.Reference.
     */
    protected static final Map<Integer, Reference> valueMap =
      new HashMap<>(); static {
      for (Reference ref : Reference.values())
        valueMap.put(ref.value, ref);
    }

    /**
     * Convert a int to a Semester.Reference.
     */
    public static Reference fromInt(int i) {
      Reference ref = valueMap.get(Integer.valueOf(i));

      if (ref == null)
        throw new IllegalArgumentException();

      return ref;
    }
  }


  /**
   * The year of the semester.
   */
  public final int year;
  /**
   * The reference of the semester.
   */
  public final Reference ref;
  /**
   * TODO: SONIA CUIDE DISSO!
   */
  public final String courseName;


  /**
   * Construct a Semester.
   * @param year  the year
   * @param ref   the reference
   * @throws IllegalArgumentException  if any parameter is null
   */
  public Course(int year, Reference ref, String courseName) {
    if (ref == null)
      throw new IllegalArgumentException("ref mustn't be null");

    this.year = year;
    this.ref = ref;
    this.courseName = courseName;
  }

  public abstract float getScore();


  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Course))
      return false;

    Course course = (Course) obj;

    return this.year == course.year
        && this.ref == course.ref;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.year, this.ref);
  }

  /**
   * Convert a Semester to String for visualization purposes.
   */
  @Override
  public String toString() {
    return String.format(
      "%d/%d",
      this.year,
      this.ref.value);
  }
}
