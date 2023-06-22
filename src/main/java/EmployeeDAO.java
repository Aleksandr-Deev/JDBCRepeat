import java.util.List;

public interface EmployeeDAO {

    // Метод получения всех объектов
    List<Employee> getAllEmployee();

    // Метод получения объекта по id
    Employee getByIdEmployee(int id);

    // Метод добавления(создания) объектов
    void create(Employee employee);

    // Метод обновления данных в базе
    void updateEmployeeById(int id, int city_id);

    // Метод удаления данных из базы
    void deleteEmployeeById(int id);
}
