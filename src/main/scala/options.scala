import java.util.logging.Logger

/**
  * Created by jameshrisho on 4/26/17.
  */
object OptionsGenerator {
  def getOptions(user: User, product: ProductConfiguration): Array[Array[Int]] = {
    val employeeOptions = OptionSolver.getOptions(
      salary = user.salary,
      salaryIncrements = product.userConfig.salaryIncrements,
      customIncrements = product.userConfig.customIncrements,
      incrementType = product.userConfig.incrementType,
      minimumValue = product.userConfig.minimumValue,
      maximumValue = product.userConfig.maximumValue,
      increments = product.userConfig.increments
    )

    val spouseOptions = OptionSolver.getOptions(
      salary = user.salary,
      salaryIncrements = product.spouseConfig.salaryIncrements,
      customIncrements = product.spouseConfig.customIncrements,
      incrementType = product.spouseConfig.incrementType,
      minimumValue = product.spouseConfig.minimumValue,
      maximumValue = product.spouseConfig.maximumValue,
      increments = product.spouseConfig.increments
    )
    Array(employeeOptions, spouseOptions)
  }

  def getOptions(user: User, product: ProductConfiguration, options: SelectedOptions): Array[Array[Int]] = {
    val employeeOptions = OptionSolver.getOptions(
      salary = user.salary,
      salaryIncrements = product.userConfig.salaryIncrements,
      customIncrements = product.userConfig.customIncrements,
      incrementType = product.userConfig.incrementType,
      minimumValue = product.userConfig.minimumValue,
      maximumValue = product.userConfig.maximumValue,
      increments = product.userConfig.increments
    )
    val maximumValue = product.spouseConfig.maximumValue.min((options.userCoverageAmount  *  product.spouseConfig.userMaximumPercentage) / 100)
    val spouseOptions = OptionSolver.getOptions(
      salary = user.salary,
      salaryIncrements = product.spouseConfig.salaryIncrements,
      customIncrements = product.spouseConfig.customIncrements,
      incrementType = product.spouseConfig.incrementType,
      minimumValue = product.spouseConfig.minimumValue,
      maximumValue = maximumValue,
      increments = product.spouseConfig.increments
    )
    Array(employeeOptions, spouseOptions)
  }
}

sealed trait UserType
case object Employee extends UserType
case object Spouse extends UserType
case object Child extends UserType

final case class User(userType: UserType, group: String, age: Int, zipCode: String, salary: Int, dependents: List[Dependent])
final case class Dependent(userType: UserType, age: Int, zipCode: String)
final case class UserProductConfiguration(
                                           salaryIncrements: Array[Int] = Array(),
                                           customIncrements: Array[Int] = Array(),
                                           incrementType: String = "increments",
                                           minimumValue: Int,
                                           increments: Int = 0,
                                           maximumValue: Int,
                                          userMaximumPercentage:Int = 100
                                         )
final case class SelectedOptions(
                                userCoverageAmount: Int = 0,
                                spouseCoverageAmount: Int = 0,
                                childCoverageAmount: Int = 0
                                )
final case class ProductConfiguration(userConfig: UserProductConfiguration, spouseConfig: UserProductConfiguration, childConfig: UserProductConfiguration)



//salary: Int, salaryIncrements: Array[Int], customIncrements: Array[Int], incrementType: String, increments: Int, minimumValue: Int, maximumValue: Int, fixedAmount:Int = 0