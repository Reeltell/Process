import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class ProcessManager {

    public static void main(String[] args) {
        try {
            List<Process> processes = new ArrayList<>();

            // Ввод названий процессов
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (int i = 0; i < 3; i++) {
                System.out.print("Введите название процесса (например, notepad.exe): ");
                String processName = reader.readLine();
                ProcessBuilder processBuilder = new ProcessBuilder(processName);
                Process process = processBuilder.start();
                processes.add(process);
                System.out.println("Запущен процесс: " + processName + " с PID: " + process.pid());
            }

            // Задержка перед закрытием
            Thread.sleep(10000);

            // Ввод имени процесса для закрытия
            System.out.print("Введите название процесса для закрытия: ");
            String nameToClose = reader.readLine();
            closeProcessByName(processes, nameToClose);

            // Вывод списка всех процессов
            listAllProcesses();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void closeProcessByName(List<Process> processes, String name) {
        for (Process process : processes) {
            if (process.isAlive() && process.toString().contains(name)) {
                process.destroy();
                System.out.println("Процесс '" + name + "' закрыт.");
                return;
            }
        }
        System.out.println("Процесс '" + name + "' не найден.");
    }

    private static void listAllProcesses() {
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("\nСписок всех процессов:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}