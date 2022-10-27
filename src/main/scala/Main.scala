import java.time.LocalDateTime
import java.time.ZonedDateTime

enum Name:
  case FirstName(name: String)
  case FullName(first: FirstName, last: String)
  case FullNameWithParent(fullName: FullName, parent: FirstName)

case class Kid(name: Name)

type Initials = String
case class Practitioner(initials: Initials)

type TimeStamp = Int

case class Entry(kid: Kid, practitioner: Practitioner, time: TimeStamp)

type Arrivals = Set[Entry]
type Departures = Set[Entry]

type SignOff = Option[Practitioner]

case class DailyRegistry(
  date: ZonedDateTime, 
  arrivals: Arrivals, 
  departures: Departures, 
  signOff: SignOff)
  
enum Amount:
  case GBP(pounds: Integer, pence: Integer)

case class Charge(kid: Kid, amount: Amount)
type Charges = Set[Charge]

type DailyRegistries = Set[DailyRegistry]

enum Month:
    case December; case January; case February
    case March; case April; case May
    case June; case July; case August
    case September; case October; case November

type CreateDailyRegistry = ZonedDateTime => DailyRegistry
type RegisterArrival = DailyRegistry => Practitioner => Kid => TimeStamp => DailyRegistry
type RegisterDeparture = DailyRegistry => Practitioner => Kid => TimeStamp => DailyRegistry
type SignOffDailyRegistry = DailyRegistry => Practitioner => DailyRegistry

type CalculateMonthlyCharges = DailyRegistries => Month => Charges

object Main extends App {

  val now = ZonedDateTime.now
  val arrivals = List(Entry(Kid(Name.FirstName("Nikoline")), Practitioner("ALM"), 6))
  val departures = List(Entry(Kid(Name.FirstName("Nikoline")), Practitioner("ALM"), 15))


  println("Hello, World!")
}