package scenarios

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.feeder._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import scenarios.Feeders._

import scala.language.postfixOps


object Feeders {
  val feeder: BatchableFeederBuilder[String] = csv("feeders/productNumber.csv").queue.eager
  val feeder2: BatchableFeederBuilder[String] = csv("feeders/chairNumber.csv").queue.eager
}

object ShopizerScenario {
   def scnShopizerDemo: ScenarioBuilder = {
   scenario("Shopizer Online")
     .exec(flushHttpCache)
     .exec(flushCookieJar)
     .exitBlockOnFail(
        group("Home page") {
         exec(api.home.store())
           .exec(api.home.products())
           .exec(api.home.category())
           .exec(api.home.content())
           .repeat(4) {
             feed(feeder)
               .exec(api.home.price())
           }
           .exec(thinkTimer())
       }
          .group("Tables Tab") {
           exec(api.tables.store())
             .exec(api.tables.count())
             .exec(api.tables.content())
             .exec(api.tables.param())
             .exec(api.tables.price())
             .exec(api.tables.category())
             .exec(api.tables.manufacturers())
             .exec(thinkTimer())
         }
           .group("Table Card") {
             exec(api.table_card.store())
               .exec(api.table_card.products())
               .exec(api.table_card.review())
               .exec(api.table_card.category())
               .exec(api.table_card.content())
               .exec(api.table_card.price())
               .exec(api.table_card.cart())
               .exec(api.table_card.cart_code())
               .randomSwitch(
                 30.0 -> exec(api.chairs.product_in_cart())
               )
               .exec(thinkTimer())
           }
          .randomSwitch(
            50.0 -> group("Chairs") {
             exec(api.chairs.store())
               .exec(api.chairs.count())
              .exec(api.chairs.content())
              .exec(api.chairs.param())
              .repeat(3) {
                feed(feeder2)
                  .exec(api.chairs.price())
              }
              .exec(api.chairs.category())
              .exec(api.chairs.manufacturers())
              .exec(api.chairs.products())
              .exec(api.chairs.review())
              .exec(api.chairs.chair_price())
              .exec(api.chairs.cart())
              .exec(api.chairs.cart_code())
               .randomSwitch(
                 30.0 -> exec(api.chairs.product_in_cart())
               )
              .exec(thinkTimer())
          }
         )
     )
   }
}
