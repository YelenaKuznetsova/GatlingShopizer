package simulation

import config.BaseHelpers._
import io.gatling.core.Predef._
import scenarios.ShopizerScenario._

class PerfTestSimulation extends Simulation {

  //mvn gatling:test

  setUp(
    scnShopizerDemo.inject(rampConcurrentUsers(1).to(5).during(600))
  ).protocols(httpProtocol)


}
