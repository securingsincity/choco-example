import org.chocosolver.solver._
import org.chocosolver.solver.constraints.Constraint
import org.chocosolver.solver.variables.IntVar
object Main extends App {

  def getIncrementFunction(model: Model, option: IntVar, salary: Int, salaryIncrements: Array[Int], customIncrements: Array[Int], incrementType: String, increments: Int): Constraint = incrementType match {
    case "salary" => {
      val amounts: Array[Int] = salaryIncrements.map(increment => increment * salary)
      model.member(option, amounts)
    }
    case "custom" => {
      model.member(option, customIncrements)
    }
    case _ => model.mod(option, model.intVar(increments), model.intVar(0))
  }

  def allowMinimum(model: Model, option: IntVar, incrementType: String, minimum: IntVar): Constraint = incrementType match {
    case "salary" => model.falseConstraint()
    case "custom" => model.falseConstraint()
    case _ => model.arithm(option, "=", minimum)
  }

  def getOptions(salary: Int, salaryIncrements: Array[Int], customIncrements: Array[Int], incrementType: String, increments: Int): Array[Int]= {

    val model = new Model("options calculation")
    val option: IntVar = model.intVar("option", 0, 999999, false)
    // this can be determined based on the minimum of a set of numbers (think m
    val maximum: IntVar = model.intVar("maximum", maximumValue)
    val minimum: IntVar = model.intVar("minimum", minimumValue)
    // constraints
    model.arithm(maximum, ">=", option).post()
    model.arithm(minimum, "<=", option).post()

    model.or(
      getIncrementFunction(model, option, salary, salaryIncrements, customIncrements, incrementType, increments),
      allowMinimum(model, option, incrementType, minimum)
    ).post()


    model.allDifferent(option).post()
    val solutions: java.util.List[Solution] = model.getSolver().findAllSolutions()
    val solutionsArray = solutions.toArray[Solution](Array[Solution]())
    solutionsArray.map(solution => solution.getIntVal(option)).sortBy(b => b)
  }

  var increments = 4500
  var customIncrements: Array[Int] = Array[Int](1000, 2000, 5000, 7000, 10000)
  var salaryIncrements: Array[Int] = Array[Int](1, 3, 4, 6, 10)
  var salary = 50000
  var incrementType = "increments"
  var maximumValue = 300000
  var minimumValue = 1000
  // variables


  val options = getOptions(salary, salaryIncrements, customIncrements, incrementType, increments)
  options.foreach(b => println(b))

}
