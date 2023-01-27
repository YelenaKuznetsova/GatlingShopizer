package simulation

import config.BaseHelpers._
import io.gatling.core.Predef._
import scenarios.ShopizerScenario._

class PerfTestSimulation extends Simulation {

  //mvn gatling:test

  setUp(
    scnShopizerDemo.inject(atOnceUsers(1))
  ).protocols(httpProtocol)


}
