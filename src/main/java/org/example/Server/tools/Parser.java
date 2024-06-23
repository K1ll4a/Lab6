package org.example.Server.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.PriorityQueue;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.example.global.facility.Mclass;
import org.example.global.tools.Console;

public class Parser {
    private final String filename;
    private final Console console;
    XmlMapper xmlMapper = new XmlMapper();

    public  Parser(String filename, Console console){
        this.filename = filename;
        this.console = console;
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.registerModule(new Jdk8Module());
        xmlMapper.registerModule(new JavaTimeModule());
    }

    public void saveCollectionxml(PriorityQueue<Mclass> mclasses){
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            writer.println(xmlMapper.writeValueAsString(mclasses));
            console.println("Collection saved to file " + filename);

        } catch (IOException e){
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    public PriorityQueue<Mclass> loadCollection(){
        PriorityQueue<Mclass> result = new PriorityQueue<>();
        if (filename != null && !filename.isEmpty()){
            File file = new File(filename);
            if (file.exists()){
                try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
                    result = xmlMapper.readValue(inputStream, xmlMapper.getTypeFactory().constructCollectionType(PriorityQueue.class, Mclass.class));
                }catch (FileNotFoundException exception) {
                    console.printError("Файл не найден!");
                } catch (IOException exception) {
                    console.printError("Ошибка чтения файла: " + exception.getMessage());
                } catch (Exception exception) {
                    console.printError("Ошибка загрузки коллекции!");
                }
            } else {
                console.println("Файл не найден!");
            }
        } else {
            console.println("Аргумент командной строки с загрузочнфм файлом не найден!");
        }
        PriorityQueue<Mclass> finalResult = new PriorityQueue<>();
        result.forEach(mclass -> {
            if (mclass != null){
                finalResult.add(mclass);
            }
        });
        return finalResult;
    }
}