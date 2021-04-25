package tdt.micronaut.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class ProductController {

  @Get("/hello")
  public String hello() {
    return "Hello Micronaut";
  }

}
