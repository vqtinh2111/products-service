package tdt.micronaut;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import javax.inject.Singleton;
import tdt.micronaut.repository.ProductRepository;

// This class just for test
@Singleton
public class DefaultProductDataLoader implements ApplicationEventListener<ApplicationStartupEvent> {

  private final ProductRepository productRepository;

  public DefaultProductDataLoader(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void onApplicationEvent(ApplicationStartupEvent event) {}

  @Override
  public boolean supports(ApplicationStartupEvent event) {
    return false;
  }
}
