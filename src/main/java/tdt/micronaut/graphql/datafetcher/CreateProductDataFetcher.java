package tdt.micronaut.graphql.datafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.common.States;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.input.CreateProductInput;
import tdt.micronaut.service.output.ProductOutput;

@Singleton
public class CreateProductDataFetcher implements DataFetcher<ProductOutput> {

  private final ObjectMapper objectMapper;
  private final ProductService productService;

  public CreateProductDataFetcher(ObjectMapper objectMapper,
      ProductService productService) {
    this.objectMapper = objectMapper;
    this.productService = productService;
  }

  @Override
  public ProductOutput get(DataFetchingEnvironment env) throws Exception {
    CreateProductInput input = objectMapper.convertValue(env.getArguments(), CreateProductInput.class);
    if (States.isNullOrBlank(input.getName())) {
      throw new RuntimeException("Product name must not be null or blank");
    }
//    String name = env.getArgument("name");
//    String description = env.getArgument("description");
//    CreateProductInput input = new CreateProductInput(name, description);
    return productService.createProduct(input);
  }
}
