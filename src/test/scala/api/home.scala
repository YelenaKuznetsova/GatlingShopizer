package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object home {
  def store(): ChainBuilder = {
    exec(
      http("store")
        .get(host + "/api/v1/store/DEFAULT")
        .header("Sec-Fetch-Mode", "cors")
        .check(regex("Default store"))
    )
  }
  def products(): ChainBuilder = {
    exec(
      http("products")
        .get(host + "/api/v1/products/group/FEATURED_ITEM?store=DEFAULT&lang=en")
        .check(regex("Genuine Chair"))
    )
  }
  def category(): ChainBuilder = {
    exec(
      http("category")
        .get(host + "/api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(regex("Default category"))
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
        .post(host + "/api/v1/product/${productNumber}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }

}
