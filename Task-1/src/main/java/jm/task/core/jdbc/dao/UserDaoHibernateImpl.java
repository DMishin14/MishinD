package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Properties;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    List<User> users;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users"
                + "(id SERIAL PRIMARY KEY,"
                + "name VARCHAR(50) NOT NULL,"
                + "lastname VARCHAR(50) NOT NULL,"
                + "age INTEGER NOT NULL);";
        session.createSQLQuery(createUserTableQuery).executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        String dropUsersTableQuery = "DROP TABLE IF EXISTS users";
        session.createSQLQuery(dropUsersTableQuery).executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        User removedUser = session.get(User.class, id);
        session.delete(removedUser);
        transaction.commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        users = (List<User>) session.createCriteria(User.class).list();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = hibernateConnection();
        Transaction transaction = session.beginTransaction();
        List<User> cleanedUserList = (List<User>) session.createCriteria(User.class).list();
        for (User u : cleanedUserList) {
            session.delete(u);
        }
        transaction.commit();
        session.close();
    }

    private Session hibernateConnection() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        properties.setProperty("dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        properties.setProperty("hibernate.connection.username", "postgres");
        properties.setProperty("hibernate.connection.password", "postgres");
        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("show_sql", String.valueOf(true));
        SessionFactory sessionFactory = new Configuration().addProperties(properties)
                .addAnnotatedClass(User.class).buildSessionFactory();
        return sessionFactory.openSession();
    }
}