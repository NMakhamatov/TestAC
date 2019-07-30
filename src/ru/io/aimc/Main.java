package ru.io.aimc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();
        String[] fileNames = getFileNames();
        Arrays.stream(fileNames)
                .forEach(
                        it -> new Thread(new RunnableFile(it, storage)).start()
                );
        Thread.sleep(1000);
    }

    private static String[] getFileNames() {
        String[] result = null;
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String string = bufferedReader.readLine();
            result = string.split(";");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так...  :(");
        }

        return result;
    }
}
