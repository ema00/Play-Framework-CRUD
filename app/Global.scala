/**
  * This file defines the Global object whose onStart method will be called on
  * Play startup. The method creates a database adapter.
  * The database used in this example is the H2 SQL Database Engine in in-memory
  * mode: http://www.h2database.com/html/main.html
  */

import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, SessionFactory}
import play.api.db.DB
import play.api.{Application, GlobalSettings}



object Global extends GlobalSettings {

  /**
    * The onStart method will be called by Play on startup.
    * A Squeryl session is an SQL connection to talk to a database using the
    * implementation of the Squeryl database adapter for that specific database.
    */
	override def onStart(app: Application) {
    /**
      * Give Squeryl SessionFactory a function to create a session wrapped in a
      * Some. This function just calls Session.create(...)
      */
		SessionFactory.concreteFactory = Some( () =>
			Session.create(DB.getConnection()(app), new H2Adapter)
		)
	}

}
