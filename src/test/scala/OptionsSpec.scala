import org.scalatest.FunSpec

/**
  * Created by jameshrisho on 4/26/17.
  */
class OptionsSpec extends FunSpec {
  describe("OptionSolver") {
    it("should have increments") {
      val spouse = Dependent(Spouse,age = 29,zipCode = "02141")
      val user = User(
        userType = Employee,
        age = 30,
        zipCode = "02111",
        dependents = List(spouse),
        group = "Full-time",
        salary = 50000,
      )
      val product = ProductConfiguration(
        userConfig = UserProductConfiguration(maximumValue = 2000, minimumValue = 1000, increments = 200),
        spouseConfig = UserProductConfiguration(maximumValue = 1500, minimumValue = 500, increments = 200),
        childConfig = UserProductConfiguration(maximumValue = 0, minimumValue = 0)
      )
      val result = OptionsGenerator.getOptions(user, product)
      assert(result(0) === Array(1000, 1200, 1400, 1600, 1800, 2000))
      assert(result(1) === Array(500, 600, 800, 1000, 1200, 1400))
    }

    it("should have spouse increments driven by employee selection") {
      val spouse = Dependent(Spouse,age = 29,zipCode = "02141")
      val user = User(
        userType = Employee,
        age = 30,
        zipCode = "02111",
        dependents = List(spouse),
        group = "Full-time",
        salary = 50000,
      )
      val product = ProductConfiguration(
        userConfig = UserProductConfiguration(maximumValue = 2000, minimumValue = 1000, increments = 200),
        spouseConfig = UserProductConfiguration(maximumValue = 1500, minimumValue = 500, increments = 200, userMaximumPercentage = 50),
        childConfig = UserProductConfiguration(maximumValue = 0, minimumValue = 0)
      )
      val result = OptionsGenerator.getOptions(user, product, SelectedOptions(userCoverageAmount = 1600))
      assert(result(0) === Array(1000, 1200, 1400, 1600, 1800, 2000))
      assert(result(1) === Array(500, 600, 800))
    }
  }
}
