package tdt.micronaut.entity;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

  @Id @GeneratedValue private Long id;
  private String name;
  private String description;
  private Instant createdAt;
  private Instant updatedAt;

  public Product() {}

  public Product(Long id, String name) {
    this.id = id;
    this.name = name;
    this.createdAt = Instant.now();
  }

  public Product(String name, String description) {
    this.name = name;
    this.description = description;
    this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Product setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Product setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Product setDescription(String description) {
    this.description = description;
    return this;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Product setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Product setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }
}
