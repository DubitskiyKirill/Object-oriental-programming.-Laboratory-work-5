import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 9090; // Порт сервера

    public static void main(String[] args) throws IOException {
        // Создаем серверный сокет
        ServerSocket serverSocket = new ServerSocket(PORT);

        // Ждем подключения клиента
        Socket clientSocket = serverSocket.accept();

        // Получаем потоки ввода/вывода для обмена данными с клиентом
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Запрашиваем имя у клиента
        out.println("Ваше имя:");
        String name = in.readLine();

        // Приветствуем клиента
        out.println("WELCOME");

        // Создаем колоду карт
        List<Integer> deck = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            deck.add(i);
        }

        // Запускаем цикл игры
        int sum = 0;
        while (true) {
            // Перемешиваем колоду карт
            java.util.Collections.shuffle(deck);

            // Добавляем первую карту к сумме очков
            sum += deck.get(0);
            out.println(sum);

            // Если сумма очков равна 21, то игра закончена
            if (sum == 21) {
                out.println("GAME OVER");
                break;
            }

            // Если сумма очков больше 21, то игра закончена
            if (sum > 21) {
                out.println("GAME OVER");
                break;
            }

            // Ждем ответа от клиента
            String response = in.readLine();

            // Если клиент выбрал "HIT", то добавляем еще одну карту к сумме очков
            if ("HIT".equals(response)) {
                sum += deck.get(0);
                out.println(sum);
            }

            // Если клиент выбрал "STAND", то игра закончена
            if ("STAND".equals(response)) {
                out.println("GAME OVER");
                break;
            }
        }

        // Закрываем соединение
        clientSocket.close();
        serverSocket.close();
        System.exit(0);
    }
}