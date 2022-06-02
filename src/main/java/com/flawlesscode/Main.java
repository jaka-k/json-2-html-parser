package com.flawlesscode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File path = new File("json/");
        File[] files = path.listFiles();

        for (int i = 0; i < files.length; i++) {
            JsonNode json = mapper.readValue(readWithBufferedReader(files[i]).getBytes(), JsonNode.class);
            tagWriter writeHtml = new tagWriter();
            String result = writeHtml.recursiveTagWriter(json);
            writeWithBufferedReader(result, Integer.toString(i + 1));
        }
    }

    public static String readWithBufferedReader(File file) {
        String currentLine;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             currentLine = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentLine;
    }

    public static void writeWithBufferedReader(String source, String name) {
        File file = new File("output/result" + name + ".html");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

