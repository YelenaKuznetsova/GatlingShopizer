package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object tables {

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
        .queryParam("category", "50")
    )
  }
    def price(): ChainBuilder = {
    exec(
      http("price")
        .post(host + "/api/v1/product/1/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }
  def category(): ChainBuilder = {
    exec(
      http("category")
        .get(host + "/api/v1/category/50?store=DEFAULT&lang=en")
    )
  }
  def manufacturers(): ChainBuilder = {
    exec(
      http("manufacturers")
        .get(host + "/api/v1/category/50/manufacturers/?store=DEFAULT&lang=en")
    )
  }
}
