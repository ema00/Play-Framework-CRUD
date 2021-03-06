package models


/**
  * case class Product
  * A simple model of a Product.
  */
case class Product(ean: Long, name: String, description: String)


/**
  * object Product
  * Companion object for the Product case class.
  */
object Product {

  /**
  * Set containing the instances of Product.
  */
  var products = Set(
    Product(5010255079763L, "Paperclips Large",
        "Large Plain Pack of 1000"),
    Product(5018206244666L, "Giant Paperclips",
        "Giant Plain 51mm 100 pack"),
    Product(5018306332812L, "Paperclip Giant Plain",
        "Giant Plain Pack of 10000"),
    Product(5018306312913L, "No Tear Paper Clip",
        "No Tear Extra Large Pack of 1000"),
    Product(5018206244611L, "Zebra Paperclips",
        "Zebra Length 28mm Assorted 150 Pack")
  )

  /**
    * findAll: Returns all the products.
    */
  def findAll = products.toList.sortBy(_.ean)

  /**
    * findByEan: Returns an Option[Product] containing the product with the ean
    * specified in the parameter.
    */
  def findByEan(ean: Long) = products.find(p => p.ean == ean)

  /**
    * add: Adds a product to products.
    */
  def add(product: Product) = { products = products + product }

  /**
    * remove: Removes a product from products.
    */
  def remove(product: Product) = { products = products - product }

  /**
    * update: Updates product.
    * Postcondition: Returns an Option, Some[Product] updated product in case
    * product exists, or None in case product doesn't exist. Doesn't create a new
    * product if product doesn't exist previously.
    */
  def update(product: Product) = {
    products.find(p => p.ean == product.ean) match {
      case None => None
      case some =>
        val current = some.get
        products = products - current + product
        Some(product)
    }
  }

}
