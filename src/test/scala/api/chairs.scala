package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object chairs {
  def store(): ChainBuilder = {
    exec(
      http("store")
        .get(host + "/api/v1/store/DEFAULT")
    )
  }
  def count(): ChainBuilder = {
    exec(
      http("count")
        .get(host + "/api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(jsonPath("$.categories[*].id").findRandom.saveAs("random_chair"))
    )
  }
  def content(): ChainBuilder = {
    exec(
      http("content")
        .get(host + "/api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
    )
  }
  def param(): ChainBuilder = {
    exec(
      http("param")
        .get(host + "/api/v1/products/?&store=DEFAULT&lang=en&page=0")
        .queryParam("count", "15")
        .queryParam("category", "51")
    )
  }
  def price(): ChainBuilder = {
    exec(
      http("price")
        .post(host + "/api/v1/product/${chairNumber}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }
  def category(): ChainBuilder = {
    exec(
      http("category")
        .get(host + "/api/v1/category/51?store=DEFAULT&lang=en")
    )
  }
  def manufacturers(): ChainBuilder = {
    exec(
      http("manufacturers")
        .get(host + "/api/v1/category/51/manufacturers/?store=DEFAULT&lang=en")
    )
  }
  def products(): ChainBuilder = {
    exec(
      http("products")
        .get(host + "/api/v1/products/${random_chair}?lang=en&store=DEFAULT")
        .check(jsonPath("$.id").findRandom.saveAs("id_chair"))
        .check(jsonPath("$.sku").findRandom.saveAs("product_chair"))
    )
  }
  def review(): ChainBuilder = {
    exec(
      http("review")
        .get(host + "/api/v1/products/${random_chair}/reviews?store=DEFAULT")
    )
  }
  def chair_price(): ChainBuilder = {
    exec(
      http("chair_price")
        .post(host + "/api/v1/product/${random_chair}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }
  def cart(): ChainBuilder = {
    exec(
      http("cart")
        .post(host + "/api/v1/cart/?store=DEFAULT")
        .body(StringBody("""{"attributes":[{"id":${id_chair}}],"product":"${product_chair}","quantity":1}""")).asJson
        .check(jsonPath("$.code").findRandom.saveAs("code"))
    )
  }
  def cart_code(): ChainBuilder = {
    exec(
      http("cart_code")
        .get(host + "/api/v1/cart/${code}?lang=en")
        .check(jsonPath("$..name").exists)
    )
  }
  def product_in_cart(): ChainBuilder = {
    exec(
      http("product_in_cart")
        .get(host + "/api/v1/cart/${code}?store=DEFAULT")
        .check(jsonPath("$..name").exists)
    )
  }
}
