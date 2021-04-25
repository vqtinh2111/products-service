package tdt.micronaut.service.output;

import tdt.micronaut.entity.Product;

public class ProductOutput {

  private Long id;
  private String name;
  private String description;

  public ProductOutput() {}

  public ProductOutput(Product product) {
    this.setId(product.getId());
    this.setName(product.getName());
    this.setDescription(product.getDescription());
  }

  public Long getId() {
    return id;
  }

  public ProductOutput setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public ProductOutput setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ProductOutput setDescription(String description) {
    this.description = description;
    return this;
  }
}
