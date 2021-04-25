package tdt.micronaut.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
import tdt.micronaut.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
