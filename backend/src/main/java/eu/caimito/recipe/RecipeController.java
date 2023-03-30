package eu.caimito.recipe;

import java.net.URI;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

  @Autowired
  private RecipeRepository recipeRepository;

  @GetMapping("/{id}")
  public ResponseEntity<Recipe> getRecipe(@PathVariable("id") String id) {
    try {
      Recipe recipe = recipeRepository.findById(id).orElseThrow();

      return ResponseEntity.ok(recipe);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
    Recipe recipeSaved = recipeRepository.save(recipe);

    URI location = URI.create("/recipe/" + recipeSaved.getId());
    return ResponseEntity.created(location).body(recipeSaved);
  }

}
