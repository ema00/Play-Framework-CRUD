Products CRUD - Play Framework 2.4
==================================

  Simple Play Framework 2.4 application to implement CRUD operations on a class Product.
  Shows how template composition is possible in Play Framework.
  Uses dependency injection to inject configuration into controllers.
  Implements Internationalization.
  Currently uses no database to store products, instead are stored in memory in a Set.
  Also uses a Controller to generate barcodes.
  Uses features from Bootstrap framework.
  Project generated using Lightbend Activator (now deprecated).


Controllers
-----------

- Application.scala:
  Handles HTTP requests to the application's home page.

- Products.scala:
  Controller for products HTTP interface. Implements CRUD operations for product list.

- Barcodes.scala:
  Generates barcodes for each product of the product list.


Intellectual property of other parties
--------------------------------------

  Based on the sample application from the book: Play for Scala - Francisco Canedo, Erik Bakker, Peter Hilton, 2013
  https://www.manning.com/books/play-for-scala
  But updated to work on Play Framework 2.4
