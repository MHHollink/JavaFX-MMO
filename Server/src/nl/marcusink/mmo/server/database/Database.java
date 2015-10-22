package nl.marcusink.mmo.server.database;

import com.google.gson.Gson;
import nl.marcusink.mmo.server.database.table.Avatar;
import nl.marcusink.mmo.server.database.table.Server;
import nl.marcusink.mmo.server.database.table.User;
import nl.marcusink.mmo.server.database.table.UserOwnsAvatar;
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
                .addAnnotatedClass(Avatar.class)
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

            User temporalUser = session.get(User.class, username);

            session.close();

            return temporalUser != null && temporalUser.getPassword().equals(password);
        }

        public static boolean register(String username, String password, String email, String firstName, String lastName, String birthDate) {

            Session session = Database.getDatabaseInstance().getSessionFactory().openSession();
            session.beginTransaction();

            User temporalUser = session.get(User.class, username);

            if(temporalUser != null) return false;

            User newlyRegisterdUser = new User();
            newlyRegisterdUser.setUsername(username);
            newlyRegisterdUser.setPassword(password);
            newlyRegisterdUser.setEmail(email);
            newlyRegisterdUser.setfName(firstName);
            newlyRegisterdUser.setlName(lastName);
            newlyRegisterdUser.setBDay(birthDate);

            session.save(newlyRegisterdUser);
            session.getTransaction().commit();
            session.close();

            return true;
        }

        public static String profileRequest(String username) {

            Session session = Database.getDatabaseInstance().getSessionFactory().openSession();
            session.beginTransaction();

            User temporalUser = session.get(User.class, username);

            session.close();

            return new Gson().toJson(temporalUser);
        }

        public static boolean createAvatar(String username, String name, String gender, String profession, String race){

            Session session = Database.getDatabaseInstance().getSessionFactory().openSession();
            session.beginTransaction();

            User temporalUser = session.get(User.class, username);
            if(temporalUser == null) return false;
            Avatar temporalAvatar = session.get(Avatar.class, name);
            if(temporalAvatar != null) return false;

            Avatar newAvatar = new Avatar();
            newAvatar.setCharacterName(name);
            newAvatar.setGender(gender);
            newAvatar.setProfession(profession);
            newAvatar.setRace(race);

            session.save(newAvatar);

            UserOwnsAvatar owns = new UserOwnsAvatar();
            owns.setUser(temporalUser);
            owns.setAvatar(newAvatar);

            session.save(owns);

            session.getTransaction().commit();
            session.close();

            return true;
        }
    }
}
