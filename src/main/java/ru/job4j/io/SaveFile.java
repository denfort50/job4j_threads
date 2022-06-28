package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {

    public final String name;

    public SaveFile(String name) {
        this.name = name;
    }

    public void saveContent(String content) throws IOException {
        try (FileOutputStream out = new FileOutputStream(name)) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\projects\\job4j_threads\\ООД.txt");
        ReadFile reader = new ReadFile(file);
        SaveFile saver = new SaveFile("ООД_modified.txt");
        String data = reader.getContent(character -> character < 0x80);
        saver.saveContent(data);
    }

}
