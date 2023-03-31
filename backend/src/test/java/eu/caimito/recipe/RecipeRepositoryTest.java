package eu.caimito.recipe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class RecipeRepositoryTest {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2.22");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Autowired
  private RecipeRepository recipeRepository;

  @Test
  public void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe("Pasta Carbonara", "A delicious pasta dish", null, null);
    Recipe recipeSaved = recipeRepository.save(recipe);

    Recipe recipeFromDb = recipeRepository.findById(recipeSaved.getId()).orElseThrow();

    assertThat(recipeFromDb).isNotNull();
    assertThat(recipeFromDb.getId()).isEqualTo(recipeSaved.getId());
    assertThat(recipeFromDb.getName()).isEqualTo("Pasta Carbonara");
  }
}
