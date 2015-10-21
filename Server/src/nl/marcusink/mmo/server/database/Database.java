package nl.marcusink.mmo.server.database;

import nl.marcusink.mmo.server.database.table.Server;
import nl.marcusink.mmo.server.database.table.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Database {

    private static Database databaseInstance; // Singleton instance of the database object

    private final SessionFactory sessionFactory; // Creates the sessions

    public static Database getDatabaseInstance() { // getter of the database object
        if(databaseInstance == null) databaseInstance = new Database();
        return databaseInstance;
    }

    private Database(){ // Private constructor for the singleton
        Configuration configuration = new Configuration().configure(); // Create configuration object, and configure from "/src/hibernate.cfg.xml"

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build(); //

        this.sessionFactory = configuration // Create the session factory with all the tables
                .addAnnotatedClass(User.class) // Each anotated class can be added in a chain
                .addAnnotatedClass(Server.class)
                .buildSessionFactory(standardServiceRegistry); // Build the session factory
    }

    public SessionFactory getSessionFactory() { // Return the Factory object
        return sessionFactory;
    }

    public static class Queries {
        private Queries(){}


        public static boolean login(String username, String password) {
            Session session = Database.getDatabaseInstance().getSessionFactory().openSession();
            session.beginTransaction();

            User temporalUser = (User) session.get(username, User.class);

            session.close();

            return temporalUser != null && temporalUser.getPassword().equals(password);
        }
    }
}
