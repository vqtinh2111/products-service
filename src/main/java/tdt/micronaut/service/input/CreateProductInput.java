package tdt.micronaut.service.input;

import tdt.micronaut.entity.Product;

public class CreateProductInput {

  private String name;
  private String description;

  public CreateProductInput() {}

  public CreateProductInput(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Product toEntity() {
    return new Product(name, description);
  }

  public String getName() {
    return name;
  }

  public CreateProductInput setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public CreateProductInput setDescription(String description) {
    this.description = description;
    return this;
  }
}
