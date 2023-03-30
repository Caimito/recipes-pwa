package eu.caimito.recipe;

import org.springframework.data.annotation.Id;

public class Recipe {

  @Id
  private String id;

  private String name;

  @SuppressWarnings("unused")
  private Recipe() {
  }

  public Recipe(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
