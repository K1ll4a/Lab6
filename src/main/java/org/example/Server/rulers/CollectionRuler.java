package org.example.Server.rulers;


import org.example.Server.tools.Parser;
import org.example.global.facility.Mclass;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Класс Руководителя коллекцией
 */

public class CollectionRuler {
    private long currentId = 1;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final Parser parser;
    private PriorityQueue<Mclass> collection = new PriorityQueue<>();

    public CollectionRuler(Parser parser){
        this.parser = parser;
        this.lastInitTime=null;
        this.lastSaveTime=null;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }


    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }


    public PriorityQueue<Mclass> getCollection() {
        return collection;
    }


    /**
     * Ищет элемент коллекции по id
     */
    public Mclass byId(Long id){
        for (Mclass element:collection){
            if (element.getId().equals(id)) return element;
        }
        return null;
    }

    /**
     * Проверяет содержит ли коллекция элемент
     *
     * @return возвращает true если элемент существует в коллекции
     */
    public boolean isContain(Mclass m){
        return m == null || byId(m.getId()) != null;
    }

    /**
     * Получить свободный id
     */
    public long getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }
    /**
     * Сортировка коллекции
     */
    public void update() {
        List<Mclass> tempList = new ArrayList<>(collection);
        Collections.sort(tempList);
        collection = new PriorityQueue<>(tempList);
    }
    /**
     * Инициализация коллекции из файла
     *
     *@return возвращает сообщение о  успешности выполнения метода
     */

    public boolean init() {
        collection.clear();;
        collection = parser.loadCollection();
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (byId(e.getId()) == null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
            }
        update();
        return true;
    }
    /**
     * Сохранение коллекции в файл
     */

    public void saveCollection() {
        parser.saveCollectionxml(collection);
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * Добавление элемента в коллекцию
     *
     * @return возвращает сообщение о  успешности выполнения метода
     */
    public boolean add(Mclass a){
        if(isContain(a)){
            return false;
        }
        collection.add(a);
        update();
        return true;
    }
    /**
     * Удаляет элемент из коллекции
     */
    public void remove(Mclass t){
        collection.remove(t);
    }
    /**
     * Удаляет все элементы в коллекции
     */
    public void removeAll(){
        collection.clear();
    }
    /**
     * удаляет первый элемент коллекции
     */


    public void removeLast(){
        if (!collection.isEmpty()){
            collection.poll();
        }
    }





    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var Ticket : collection) {
            info.append(Ticket+"\n\n");
        }
        return info.toString().trim();
    }



}