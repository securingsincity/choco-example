import org.chocosolver.solver._
import org.chocosolver.solver.constraints.Constraint
import org.chocosolver.solver.variables.IntVar
object OptionSolver {

  def getIncrementFunction(model: Model, option: IntVar, salary: Int, salaryIncrements: Array[Int], customIncrements: Array[Int], incrementType: String, increments: Int, fixedAmount:Int = 0): Constraint = incrementType match {
    case "salary" => {
      val amounts: Array[Int] = salaryIncrements.map(increment => increment * salary)
      model.member(option, amounts)
    }
    case "fixed" => model.arithm(option, "=", fixedAmount)
    case "custom" => model.member(option, customIncrements)
    case _ => model.mod(option, model.intVar(increments), model.intVar(0))
  }

  def possiblyIncludeAmount(model: Model, option: IntVar, approvedAmount: Int): Constraint = approvedAmount match {
    case 0 => model.falseConstraint()
    case _ => model.arithm(option, "=", approvedAmount)
  }

  def allowMinimum(model: Model, option: IntVar, incrementType: String, minimum: IntVar): Constraint = incrementType match {
    case "salary" | "custom" | "fixed" => model.falseConstraint()
    case _ => model.arithm(option, "=", minimum)
  }

  def getOptions(salary: Int, salaryIncrements: Array[Int], customIncrements: Array[Int], incrementType: String = "increments", increments: Int, minimumValue: Int, maximumValue: Int, fixedAmount:Int = 0, passiveCoverageAmount: Int = 0): Array[Int]= {

    val model = new Model("options calculation")
    val option: IntVar = model.intVar("option", 0, 999999, false)
    // this can be determined based on the minimum of a set of numbers (think m
    val maximum: IntVar = model.intVar("maximum", maximumValue)
    val minimum: IntVar = model.intVar("minimum", minimumValue)

    // constraints
    model.arithm(maximum, ">=", option).post()
    model.arithm(minimum, "<=", option).post()

    model.or(
      getIncrementFunction(model, option, salary, salaryIncrements, customIncrements, incrementType, increments, fixedAmount),
      allowMinimum(model, option, incrementType, minimum),
      possiblyIncludeAmount(model, option, passiveCoverageAmount)
    ).post()


    model.allDifferent(option).post()
    val solutions: java.util.List[Solution] = model.getSolver().findAllSolutions()
    val solutionsArray = solutions.toArray[Solution](Array[Solution]())
    solutionsArray.map(solution => solution.getIntVal(option)).sortBy(b => b)
  }


}
