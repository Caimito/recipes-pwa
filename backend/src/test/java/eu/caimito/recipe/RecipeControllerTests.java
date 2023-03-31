package eu.caimito.recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
// @DataMongoTest(excludeAutoConfiguration =
// EmbeddedMongoAutoConfiguration.class)
public class RecipeControllerTests {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2.22");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RecipeRepository recipeRepository;

  @AfterEach
  void cleanUp() {
    recipeRepository.deleteAll();
  }

  @Test
  void getRecipe() throws Exception {
    Recipe recipe = new Recipe("Pasta Carbonara", "A delicious pasta dish", List.of("SOmething"),
        List.of("instructions"));
    Recipe recipeSaved = recipeRepository.save(recipe);

    mockMvc.perform(get("/recipes/{id}", recipeSaved.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(recipeSaved.getId()))
        .andExpect(jsonPath("$.name").value("Pasta Carbonara"));
  }

  @Test
  void getRecipeNotFound() throws Exception {
    mockMvc.perform(get("/recipes/2"))
        .andExpect(status().isNotFound());
  }

  @Test
  void saveRecipe() throws Exception {
    mockMvc.perform(post("/recipes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\": \"Pasta Carbonara\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("Pasta Carbonara"));

    Recipe recipeFromDb = recipeRepository.findAll().get(0);
    assertThat(recipeFromDb).isNotNull();
    assertThat(recipeFromDb.getName()).isEqualTo("Pasta Carbonara");
  }

}
