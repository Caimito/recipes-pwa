package eu.caimito.recipe;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

  @Id
  private String id;

  private String name;
  private String description;
  private List<String> ingredients;
  private List<String> instructions;
  private LocalDateTime created;

  @SuppressWarnings("unused")
  private Recipe() {
  }

  @JsonCreator
  public Recipe(@JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("ingredients") List<String> ingredients,
      @JsonProperty("instructions") List<String> instructions) {
    this.name = name;
    this.description = description;
    this.ingredients = ingredients;
    this.instructions = instructions;
    this.created = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public List<String> getInstructions() {
    return instructions;
  }

  public LocalDateTime getCreated() {
    return created;
  }

}
