package ru.io.aimc;

import java.io.*;
import java.util.*;

public class RunnableFile implements Runnable {
    private String fileName;
    private Storage storage;
    private Map<String, BufferedWriter> writers = new HashMap<>();

    public RunnableFile(String fileName, Storage storage) {
        this.fileName = fileName;
        this.storage = storage;
    }

    @Override
    public void run() {
        read();
    }

    private void read() {
        try (
                InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), "Cp1251");
                BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String s = bufferedReader.readLine();
            String[] names = s.substring(0, s.length() - 1).split(";");
            createWriters(names);

            while ((s = bufferedReader.readLine()) != null) {
                String[] values = s.split(";");

                for (int i = 0; i < values.length; i++) {
                    if (storage.set(names[i], values[i])) {
                        BufferedWriter writer = writers.get(names[i]);
                        writer.write(values[i]);
                        writer.write(";");
                    }
                }
            }
            writers.forEach((key, value) -> {
                try {
                    value.close();
                } catch (IOException e) {
                    System.out.println("couldn't close BufferedWriter");
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Файл с именем " + fileName + " не найден");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так...");
        }
    }

    private void createWriters(String[] names) {
        Arrays.stream(names).forEach(name -> {
            String newFileName = getName(name);

            try {
                File file = new File(newFileName);
                file.createNewFile();

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                writers.put(name, writer);
            } catch (IOException e) {
                System.out.println("couldn't create writer");
            }
        });
    }

    private String getName(String s) {
        int lastSlash = fileName.lastIndexOf("\\");
        String result = fileName.substring(0, lastSlash + 1) + s + ".txt";

        return result;
    }
}
