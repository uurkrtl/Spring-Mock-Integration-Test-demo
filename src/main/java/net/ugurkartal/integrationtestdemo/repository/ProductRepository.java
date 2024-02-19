package net.ugurkartal.integrationtestdemo.repository;

import net.ugurkartal.integrationtestdemo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>{
}
