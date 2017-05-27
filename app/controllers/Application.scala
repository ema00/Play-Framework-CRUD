package controllers

import play.api._
import play.api.mvc._

import javax.inject.Inject
import play.api.Configuration



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class Application @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    // REDIRECT THE INDEX TO /products ROUTE
    Redirect(routes.Products.list())
  }

}
