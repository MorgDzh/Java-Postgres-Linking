import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "morg";
    private static final String DB_PASSWORD = "fight0206";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) throws Exception {
        // Создали подключение
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        // Создал обьект который считывает инфу с консоли
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            Пишу
            System.out.println("1: Open tasks-list");
            System.out.println("2: Update task");
            System.out.println("3: Create task");
            System.out.println("4: Exit");

            // считываем комманды пользователя
            int command = scanner.nextInt();

            if (command == 1) {
                // Обьект который отправляет запросы в бд
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASKS = "select * from task order by id desc";
                // Обьект который хранит результат выполнения запроса
                ResultSet result = statement.executeQuery(SQL_SELECT_TASKS);
                // Просматриваем все данные, которые вернулись из бд и выводим на экран
                while (result.next()) {
                    System.out.println(result.getInt("id") + " " + result.getString("name") + " " + result.getString("STATE"));
                }
            } else if (command == 2) {
                // Описываем запрос не зная какие параметры там будут
                String sql = "update task set state = 'Done' where id = ?";  // DROP TABLE username;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Enter task identificate");
                // Кладем параметр, который мы считали с консоли
                int taskId = scanner.nextInt();
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
            } else if (command == 3) {
                String sql = "insert into task (name, state) values (?, 'InProcess');";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Enter task name");
                scanner.nextLine();
                String taskName = scanner.nextLine();
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
            } else if (command == 4) {
                System.exit(0);
            } else {
                System.err.println("Command is not find");
            }
        }
    }
}
