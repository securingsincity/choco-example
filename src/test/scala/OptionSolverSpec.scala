import org.scalatest.{FunSpec}
class OptionSolverSpec extends FunSpec {
  describe("OptionSolver") {
    it("should have increments") {
      val result = OptionSolver.getOptions(
        salary = 0,
        salaryIncrements = Array[Int](),
        customIncrements = Array[Int](),
        maximumValue = 50000,
        minimumValue = 20000,
        increments = 5000
      )
      assert(result === Array[Int](20000,25000, 30000, 35000, 40000, 45000,50000))
    }

    it("should have increments and include the minimum if its not in the list ") {
      val result = OptionSolver.getOptions(
        salary = 0,
        salaryIncrements = Array[Int](),
        customIncrements = Array[Int](),
        maximumValue = 50000,
        minimumValue = 19000,
        increments = 5000
      )
      assert(result === Array[Int](19000, 20000,25000, 30000, 35000, 40000, 45000,50000))
    }


    it("should have a fixed coverage amount") {
      val result = OptionSolver.getOptions(
        salary = 0,
        salaryIncrements = Array[Int](),
        customIncrements = Array[Int](),
        incrementType = "fixed",
        maximumValue = 50000,
        minimumValue = 4000,
        increments = 5000,
        fixedAmount = 7000

      )
      assert(result === Array[Int](7000))
    }


    it("should have no options if fixed amount is less than minimum amount") {
      val result = OptionSolver.getOptions(
        salary = 0,
        salaryIncrements = Array[Int](),
        customIncrements = Array[Int](),
        incrementType = "fixed",
        maximumValue = 50000,
        minimumValue = 9000,
        increments = 5000,
        fixedAmount = 7000

      )
      assert(result === Array[Int]())
    }

    it("should have increments and include the minimum and past coverage amount if its not in the list ") {
      val result = OptionSolver.getOptions(
        salary = 0,
        salaryIncrements = Array[Int](),
        customIncrements = Array[Int](),
        maximumValue = 50000,
        minimumValue = 19000,
        increments = 5000,
        passiveCoverageAmount = 21000
      )
      assert(result === Array[Int](19000, 20000,21000, 25000, 30000, 35000, 40000, 45000,50000))
    }

    it("should support salary incrememnts") {
      val result = OptionSolver.getOptions(
        salary = 50000,
        salaryIncrements = Array[Int](1, 2, 3, 4),
        customIncrements = Array[Int](),
        incrementType = "salary",
        maximumValue = 180000,
        minimumValue = 20000,
        increments = 5000
      )
      assert(result === Array[Int](50000,100000, 150000))
    }

    it("should support custom incrememnts") {
      val result = OptionSolver.getOptions(
        salary = 50000,
        salaryIncrements = Array[Int](1, 2, 3, 4),
        customIncrements = Array[Int](1999, 2000, 3000, 5000),
        incrementType = "custom",
        maximumValue = 180000,
        minimumValue = 0,
        increments = 5000
      )
      assert(result === Array[Int](1999,2000,3000, 5000))
    }


    it("should support return an empty array when there are no possible custom increments") {
      val result = OptionSolver.getOptions(
        salary = 50000,
        salaryIncrements = Array[Int](1, 2, 3, 4),
        customIncrements = Array[Int](1999, 2000, 3000, 5000),
        incrementType = "custom",
        maximumValue = 180000,
        minimumValue = 7000,
        increments = 5000
      )
      assert(result === Array[Int]())
    }


    it("should support return an empty array when there are no possible salary increments") {
      val result = OptionSolver.getOptions(
        salary = 100,
        salaryIncrements = Array[Int](1, 2, 3, 4),
        customIncrements = Array[Int](1999, 2000, 3000, 5000),
        incrementType = "custom",
        maximumValue = 180000,
        minimumValue = 7000,
        increments = 5000
      )
      assert(result === Array[Int]())
    }
  }
}
