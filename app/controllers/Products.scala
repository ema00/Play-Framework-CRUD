package controllers

import play.api.mvc._
import play.api.mvc.{Flash, Action, Controller}

import play.api.data.Form
import play.api.data.Forms._

import play.api.Configuration
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi, Messages}

import models.Product


/**
 * Controller for products HTTP interface.
 * Injecting configuration:
 * http://stackoverflow.com/questions/36955237/play-2-5-x-method-current-in-object-play-is-deprecated-this-is-a-static-refere
 * Messages and internationalization:
 * https://www.playframework.com/documentation/2.5.x/ScalaI18N
 */

class Products @Inject() (val messagesApi: MessagesApi, implicit val config: Configuration) extends Controller with I18nSupport {

  /* FORM FOR A PRODUCT */
  private val productForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )

  /* FORM FOR A PRODUCT, TO USE ONLY WHEN CREATING A NEW PRODUCT */
  private val productNewForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber.verifying("validation.ean.duplicate", Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )

  /* RENDER PRODUCT List IN list OBJECT, LOCATED IN FOLDER wiews\products */
  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.products.list(products))
  }

  /* SHOW DETAILS OF A PRODUCT IN details OBJECT IN FOLDER wiews\products */
  def show(ean: Long) = Action { implicit request =>
    Product.findByEan(ean) match {
      case None => BadRequest(Messages("products.edit.notfound"))   //NotFound
      case some: Option[Product] => Ok(views.html.products.details(some.get))
    }
  }

  /* SHOW FORM FOR ADDING A NEW PRODUCT */
  def newProduct = Action { implicit request =>
    val form =
      if (request.flash.get("error").isDefined)
        productForm.bind(request.flash.data)
      else
        productForm
    Ok(views.html.products.newProduct(form))
  }

  /* SAVING A NEW PRODUCT, AND VALIDATION OF USER INPUT IN NEW PRODUCT FORM */
  def save = Action { implicit request =>
    val newProductForm = productNewForm.bindFromRequest()
    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct()).flashing(Flash(form.data) +
          ("error" -> Messages("validation.errors")))
      },
      success = { newProduct =>
        Product.add(newProduct)
        val message = Messages("products.new.success", newProduct.name)
        Redirect(routes.Products.show(newProduct.ean)).flashing("success" -> message)
      }
    )
  }

  /* SHOW FORM FOR EDITING AN EXISTING PRODUCT */
  def editProduct(ean: Long) = Action { implicit request =>
    Product.findByEan(ean) match {
      case None => BadRequest(Messages("products.edit.notfound"))
      case some =>
        val product = some.get
        val form = productForm.fill(product)
        Ok(views.html.products.editProduct(form))
    }
  }

  /* SAVING AN UPDATED PRODUCT, AND VALIDATION OF USER INPUT IN FORM */
  def update(ean: Long) = Action { implicit request =>
    val updatedProductForm = productForm.bindFromRequest()
    updatedProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.editProduct(ean)).flashing(Flash(form.data) +
          ("error" -> Messages("validation.errors")))
      },
      success = { updatedProduct =>
        Product.findByEan(ean) match {
          case None => BadRequest(Messages("products.edit.notfound"))
          case some =>
            Product.remove(some.get)
            Product.add(updatedProduct)
            val message = Messages("products.edit.success", updatedProduct.name)
            Redirect(routes.Products.show(updatedProduct.ean)).flashing("success" -> message)
        }
      }
    )
  }

  /* REMOVING AN EXISTING PRODUCT */
  def remove(ean: Long) = Action { implicit request =>
    Product.findByEan(ean) match {
      case None => BadRequest(Messages("products.edit.notfound"))
      case some =>
        val product = some.get
        Product.remove(product)
        val message = Messages("products.remove.success", product.name)
        Redirect(routes.Products.list()).flashing("success" -> message)
    }
  }

}
