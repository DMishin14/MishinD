package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();


        service.saveUser("Алексей", "Гришин", (byte) 40);
        service.saveUser("Дмитрий", "Летов", (byte) 41);
        service.saveUser("Сергей", "Землев", (byte) 42);
        service.saveUser("Андрей", "Квадаратов", (byte) 43);

        service.removeUserById(1);

       System.out.println(service.getAllUsers());

        service.cleanUsersTable();

        service.dropUsersTable();


    }
}