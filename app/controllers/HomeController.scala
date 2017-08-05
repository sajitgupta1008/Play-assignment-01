package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController extends Controller {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def setSessionAction(name: String) = Action { request: Request[AnyContent] =>

    Ok("<h1>Session created<h1>").as(HTML).withSession("user" -> name)
  }

  def getSessionAction = Action { request: Request[AnyContent] =>

    val userOption = request.session.get("user")

    userOption match {
      case Some(data) => Redirect(routes.HomeController.flashData()).flashing("message" -> s"$data has sucessfully logged in.")
      case None => Redirect(routes.HomeController.flashData()).flashing("message" -> "Failure")
    }
  }

  def flashData() = Action { request: Request[AnyContent] => Ok(request.flash.get("message").fold("No flash data")(x => x)).as(HTML) }

}

