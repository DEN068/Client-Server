import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234); // порт сервера

        Socket clientSocket = serverSocket.accept();
        System.out.println("Соединение установлено с клиентом");

        // Потоки для чтения/записи данных
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;

        // Запись истории чата в файл
        FileWriter fileWriter = new FileWriter("chat_history.txt", true);
        PrintWriter logWriter = new PrintWriter(fileWriter, true);

        // Чтение истории чата из файла и отправка клиенту при подключении
        BufferedReader historyReader = new BufferedReader(new FileReader("chat_history.txt"));
        String line;
        while ((line = historyReader.readLine()) != null) {
            out.println(line);
        }
        historyReader.close();

        // Основной цикл чтения/отправки сообщений
        while ((message = in.readLine()) != null) {
            System.out.println("Получено сообщение от клиента: " + message);
            out.println(message); // пересылка сообщения клиенту

            // Запись сообщения в файл
            logWriter.println(message);
        }

        // Закрытие соединения
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        logWriter.close();
    }
}