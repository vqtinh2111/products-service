package tdt.micronaut.service.input;

public class UpdateProductInput {

  private String name;
  private String description;

  public String getName() {
    return name;
  }

  public UpdateProductInput setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public UpdateProductInput setDescription(String description) {
    this.description = description;
    return this;
  }
}
