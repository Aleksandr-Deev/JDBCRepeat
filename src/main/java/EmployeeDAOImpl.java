import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl  implements EmployeeDAO {

    private final Connection connection;

    public EmployeeDAOImpl(Connection connection)  {
        this.connection = connection;
    }
    // Метод получения всех объектов
    @Override
    public List<Employee> getAllEmployee() {

        // Создаем список, в который будем укладывать объекты
        List<Employee> employeeList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM employee")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int id = Integer.parseInt(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = Integer.parseInt(resultSet.getString("age"));
                int cityId = Integer.parseInt(String.valueOf(resultSet.getInt("city_id")));


                // Создаем объекты на основе полученных данных
                // и укладываем их в итоговый список
                employeeList.add(new Employee(id, first_name, last_name, gender, age, cityId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    // Метод получения объекта по id
    @Override
    public Employee getByIdEmployee(int id) {

        Employee employee = new Employee();
        // Формируем запрос к базе с помощью PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM employee WHERE id = (?)")) {

            // Подставляем значение вместо wildcard
            statement.setInt(1, id);

            // Делаем запрос к базе и результат кладем в ResultSet
            ResultSet resultSet = statement.executeQuery();

            // Методом next проверяем есть ли следующий элемент в resultSet
            // и одновременно переходим к нему, если таковой есть
            while(resultSet.next()) {

                // С помощью методов getInt и getString получаем данные из resultSet
                // и присваиваем их полям объекта
                employee.setId(Integer.parseInt(resultSet.getString("id")));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(Integer.parseInt(resultSet.getString("age")));
                employee.setCityId(Integer.parseInt(resultSet.getString("city_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    // Метод добавления(создания) объектов
    @Override
    public void create(Employee employee) {

        // Формируем запрос к базе с помощью PreparedStatement
        try(PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO employee (first_name, last_name, gender, age, city_id) VALUES ((?), (?), (?), (?), (?))")) {

            // Подставляем значение вместо wildcard
            // первым параметром указываем порядковый номер wildcard
            // вторым параметром передаем значение
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCityId());

            // С помощью метода executeQuery отправляем запрос к базе
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод обновления данных в базе
    @Override
    public void updateEmployeeById(int id, int city_id) {

        try(PreparedStatement statement = connection.prepareStatement(
                "UPDATE employee SET city_id=(?) WHERE id=(?)")) {

            statement.setInt(1, city_id);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод удаления данных из базы
    @Override
    public void deleteEmployeeById(int id) {

        try(PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM employee WHERE id=(?)")) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
