package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User Ivan = new User("Ivan", "Ivanov", "ivanov@mail.ru");
      User Ilia = new User("Ilia", "Stepanov", "stepanov@mail.ru");
      User Alex =new User("Alex", "Sidorov", "sidorov@mail.ru");
      User Anna = new User("Anna", "Karenina", "Karenina@mail.ru");

      Car BMW = new Car("BMW", 5);
      Car Audi = new Car("Audi", 4);
      Car VAZ = new Car("VAZ", 99);
      Car Opel = new Car("Opel", 3);
      Car BYD = new Car("BYD", 3);

      userService.add(Ivan.setCar(BMW).setUser(Ivan));
      userService.add(Ilia.setCar(Audi).setUser(Ilia));
      userService.add(Alex.setCar(VAZ).setUser(Alex));
      userService.add(Anna.setCar(Opel).setUser(Anna));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      User user = userService.getUserByCar("BMW", 5);
      System.out.println(user.toString());

      try {
         System.out.println(userService.getUserByCar("Audi", 6));
      } catch (NoResultException e) {
         System.out.println("Пользователь с авто" + BYD + "не найден");
      }

      context.close();
   }
}
