package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object table_card {
  def store(): ChainBuilder = {
    exec(
      http("store")
        .get(host + "/api/v1/store/DEFAULT")
    )
  }
  def products(): ChainBuilder = {
    exec(
      http("products")
        .get(host + "/api/v1/products/1?lang=en&store=DEFAULT")
        .check(jsonPath("$.id").findRandom.saveAs("id_table"))
        .check(jsonPath("$.sku").findRandom.saveAs("product_table"))
        .check(jsonPath("$..name").findRandom.saveAs("name"))
    )
  }
  def review(): ChainBuilder = {
    exec(
      http("review")
        .get(host + "/api/v1/products/1/reviews?store=DEFAULT")
    )
  }
  def category(): ChainBuilder = {
    exec(
      http("category")
        .get(host + "/api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
    )
  }
  def content(): ChainBuilder = {
    exec(
      http("content")
        .get(host + "/api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
    )
  }
  def price(): ChainBuilder = {
    exec(
      http("price")
        .post(host + "/api/v1/product/${id_table}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }
  def cart(): ChainBuilder = {
    exec(
      http("cart")
        .post(host + "/api/v1/cart/?store=DEFAULT")
        .body(StringBody("""{"attributes":[{"id":${id_table}}],"product":"${product_table}","quantity":1}""")).asJson
        .check(jsonPath("$.code").findRandom.saveAs("code"))
    )
  }
  def cart_code(): ChainBuilder ={
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
