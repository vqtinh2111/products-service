package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.input.UpdateProductInput;

@Singleton
public class UpdateProductDataFetcher implements DataFetcher<Boolean> {

  private final ProductService productService;

  public UpdateProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Boolean get(DataFetchingEnvironment env) throws Exception {
    try {
      Long id = Long.valueOf(env.getArgument("id"));
      String name = env.getArgument("name");
      String description = env.getArgument("description");
      UpdateProductInput input = new UpdateProductInput();
      input.setName(name);
      input.setDescription(description);
      productService.updateProduct(id, input);
      return Boolean.TRUE;
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }
}
