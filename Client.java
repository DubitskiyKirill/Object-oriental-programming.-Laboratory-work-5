import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
    private static final String SERVER_IP = "127.0.0.1"; // IP-адрес сервера
    private static final int SERVER_PORT = 9090; // Порт сервера

    public static void main(String[] args) throws IOException {
        // Создаем сокет для подключения к серверу
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        // Получаем потоки ввода/вывода для обмена данными с сервером
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Запрашиваем у пользователя имя
        String name = JOptionPane.showInputDialog("Ваше имя:");

        // Отправляем имя на сервер
        out.println(name);

        // Ждем ответа от сервера
        String response = in.readLine();

        // Если сервер прислал сообщение "WELCOME", значит мы успешно подключились
        if ("WELCOME".equals(response)) {
            // Запускаем цикл игры
            while (true) {
                // Получаем текущую сумму очков
                response = in.readLine();
                int sum = Integer.parseInt(response);

                // Если сумма очков равна 21, то мы выиграли
                if (sum == 21) {
                    JOptionPane.showMessageDialog(null, "You win!");
                    break;
                }

                // Если сумма очков больше 21, то мы проиграли
                if (sum > 21) {
                    JOptionPane.showMessageDialog(null, "You lose!");
                    break;
                }

                // Запрашиваем у пользователя, хочет ли он взять еще одну карту
                int choice = JOptionPane.showConfirmDialog(null, "Current sum: " + sum + "\\nDo you want another card?",
                        "Choose", JOptionPane.YES_NO_OPTION);

                // Отправляем ответ на сервер
                out.println(choice == JOptionPane.YES_OPTION ? "HIT" : "STAND");
            }
        }

        // Закрываем соединение
        socket.close();
        System.exit(0);
    }
}
