import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws SQLException{

        final String user = "postgres";
        final String password = "G4#f^K@s7a";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            // Создаем объект класса BookDAOImpl
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

            // Создаем список наполняя его объектами, которые получаем
            // путем вызова метода для получения всех элементов таблицы
            List<Employee> list = new ArrayList<>(employeeDAO.getAllEmployee());

            // Выведем список в консоль
            for (Employee employees : list) {
                System.out.println(employees);
            }

            System.out.println();

            System.out.println(employeeDAO.getByIdEmployee(1));

            System.out.println();

            Employee employee1 = new Employee("Lora", "Soul", "woman", 32, 1);

            employeeDAO.create(employee1);

            employeeDAO.updateEmployeeById(4, 6);

            employeeDAO.deleteEmployeeById(8);

        }
    }
}
