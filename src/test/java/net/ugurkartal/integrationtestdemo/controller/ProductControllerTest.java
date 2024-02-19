package net.ugurkartal.integrationtestdemo.controller;

import net.ugurkartal.integrationtestdemo.model.Product;
import net.ugurkartal.integrationtestdemo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllProducts_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Product product = new Product("1", "Computer");
        productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                    [
                                      {
                                        "id" : "1",
                                        "name" : "Computer"
                                      }
                                    ]
                                    """
                ));
    }

    @Test
    void getByIdProduct_shouldReturnOneObject_whenIdValid() throws Exception {
        Product product = new Product("1", "Computer");
        productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                      {
                                        "id" : "1",
                                        "name" : "Computer"
                                      }
                                    """
                ));
    }


    @Test
    void addProduct_shouldReturnCreatedProduct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "id" : "1",
                                  "name" : "Computer"
                                }
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                          "id" : "1",
                          "name" : "Computer"
                        }
                        """
                ));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        Product product = new Product("1", "Computer");
        productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.put("/api/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "id" : "1",
                                  "name" : "Keyboard"
                                }
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                          "id" : "1",
                          "name" : "Keyboard"
                        }
                        """
                ));
    }

    @Test
    void deleteCharacter_shouldDeleteChar_whenGivenValidId() throws Exception {
        //GIVEN
        Product product = new Product("1", "Computer");
        productRepository.save(product);
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.delete("/api/products/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }
}