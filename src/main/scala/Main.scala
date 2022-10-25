import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
  * WHAT IS A DOMAIN MODEL
  *  * Is it the database?
  *  * Is it the UI?
  *  * Is it the use cases?
  * 
  * The answer is none of them and all of them.
  * 
  * A domain model is a abstraction that can map both to the business domain (the real world/UI) and the technical domain (databases and such).
  * It's in-between the technical infrastructure and the real world.
  * The domain model is expressed in code and doesn't have any specifics to either side.
  * It means that it doesn't contain any UI nor database code.
  */

/**
  * HOW TO FIND THE DOMAIN MODEL
  * 
  * There is no way around it - you have to talk to the customer.
  * Ask them about the stuff that happens when going about their business.
  * Cause things that happen will contain the data that we need to form
  * the domain model.
  */

/** THE BUSINESS
 * 
 * So we need a business. Here at Famly we work in the early years sector.
 * Inspired by that I'll use a kindergarten as the business.
 * What happens at a kindergarten?
 * Every morning parents will drop off their kids.
 * They will come into the kindergarten, go to take off their shoes and outerwear and head for the classroom.
 * If the parent has multiple kids, they are probably in different classrooms.
 * When they go into the classroom a practitioner will welcome the kid.
 * When the parent has gone, the practitioner registers the time of arrival of the kid.
 * 
 * Lets try to identify the domain model here. 
 * Not a lot happened - but we did managed to pickup some stuff.
 * A practitioner welcomes a kid and registers the time of arrival of the kid.
 * If we take a look at the register, we see that the practitioner refers to a kid
 * by first name - and also that they note down their own initials.
 * When asked about what they do with kids with the same name, they simply include
 * the last name. In rare occasions, a senior practitioner says, they have kids with
 * the same first and last name. In those cases they also note down one of the parents
 * name.
 */

/**
  * So we have an idea of a name, which is not just a string.
  * It's a choice between different ways of registering a name
  */
enum Name:
  case FirstName(name: String)
  case FullName(first: FirstName, last: String)
  case FullNameWithParent(fullName: FullName, parent: FirstName)

/**
  * We also know that a kid is identified by name - whatever version of the name that might be
  */
case class Kid(name: Name)

/**
  * There is also the initials that the practitioner uses to document
  * which employee welcomed they kid.
  */
type Initials = String
type Practitioner = Initials

/**
  * Finally there is the register - at the top of the paper it says "Daily register" and has the current date printed in the upper right corner.
  * You can also see that the sheet is used to register both arrivals and departures - to nearest hour.
  * You ask the practitioner where the sheet comes from. He says that it has been prepared by the manager in the morning
  * and is always conveniently putted on the desk at the entrance. 
  * When the last kid is picked up, the practitioner at work goes through all lines to make sure,
  * that everything is in order and signs it off with his initials.
  */
case class DailyRegister(date: ZonedDateTime, arrivals: Set[DailyRegister.Line], departures: Set[DailyRegister.Line], signedOffBy: Option[Practitioner])
object DailyRegister {
  type TimeStamp = Int

  case class Line(practitioner: Practitioner, kid: Kid, time: TimeStamp)
}

/**
  * Finally we have all we need to express the action of creating a daily register and registering arrivals and departures.
  */
type CreateDailyRegister = ZonedDateTime => DailyRegister
type RegisterArrival = DailyRegister => Practitioner => Kid => DailyRegister.TimeStamp => DailyRegister
type RegisterDeparture = DailyRegister => Practitioner => Kid => DailyRegister.TimeStamp => DailyRegister
type SignOffDailyRegister = DailyRegister => Practitioner => DailyRegister

/**
  * We can now describe the daily use case of kids being dropped off and picked up
  */

type DailyRegistering = CreateDailyRegister => Set[RegisterArrival] => Set[RegisterDeparture] => SignOffDailyRegister => DailyRegister

object Main extends App {
  println("Hello, World!")
}