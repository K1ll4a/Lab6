package org.example.Server.rulers;


import org.example.Server.tools.Parser;
import org.example.global.facility.Mclass;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс Руководителя коллекцией
 */

public class CollectionRuler {
    private DatabaseRuler databaseRuler;

    private LocalDateTime lastInitTime;
    private  LocalDateTime lastSaveTime;
    private  PriorityQueue<Mclass> collection = new PriorityQueue<>();

    public CollectionRuler(DatabaseRuler databaseRuler){
        this.databaseRuler = databaseRuler;
        this.lastInitTime=null;
        this.lastSaveTime=null;
    }

    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }

    public PriorityQueue<Mclass> getCollection(){
        return collection;
    }

    public synchronized  Mclass byId(Long id){
        for (Mclass element:collection){
            if(element.getId() == id) return element;
        }
        return null;
    }

    public boolean isContain(Mclass t){
        return t == null || byId(t.getId()) != null;
    }

    public void update() {
        List<Mclass> tempList = new ArrayList<>(collection);
        Collections.sort(tempList);
        collection = new PriorityQueue<>(tempList);
    }

    public boolean init() {
        collection.clear();
        collection = databaseRuler.loadCollection();
        lastInitTime = LocalDateTime.now();
        update();
        return true;
    }

    public boolean add(Mclass a){
        if(isContain(a)){
            return false;
        }
        collection.add(a);
        update();
        return true;
    }

    public synchronized int getUserid(String login) throws SQLException {
        return databaseRuler.getUserID(login);
    }

    public void insertMCLASS(Mclass mclass , String login) throws SQLException{
        databaseRuler.insertMclass(mclass , login);
    }

    public  Mclass getLastMclass() throws SQLException{
        return databaseRuler.getLastMclass();
    }

    public synchronized void addToCollection(Mclass mclass,String login) throws SQLException{
        insertMCLASS(mclass,login);
        add(getLastMclass());
    }

    public synchronized int isCorrectID(long id) throws SQLException{
        return  databaseRuler.isCorrectID(id);
    }

    public synchronized void updateMclassDB(Mclass mclass,long id) throws  SQLException{
        databaseRuler.updateMclass(mclass,id);
    }

    public synchronized void updateCollection(Mclass deletable, Mclass newMclass,long id, String login){
        newMclass.setId(id);
        remove(deletable);
        newMclass.setLogin(login);
        add(newMclass);
    }

    public synchronized void remove(Mclass m){
        collection.remove(m);
        update();
    }

    public synchronized void removeAll (int id) throws SQLException{
        databaseRuler.clear(id);
        collection = clearUsersMclass(id);
        update();
    }

    public synchronized void removeLast() throws SQLException{
        databaseRuler.removeLastMclass();
        collection.poll();
        update();
    }

    public synchronized void removeMclassByIdDB(long id) throws  SQLException{
        databaseRuler.removeMclassById(id);
    }

    public boolean collectionIsEmptu(){
        return collection.isEmpty();
    }

    public synchronized Mclass getFirtsMclassToRemove(){
        return collection.peek();
    }

    public PriorityQueue<Mclass> clearUsersMclass(int id){
        PriorityQueue<Mclass> filteredCollection = collection.stream()
                .filter(mclass -> mclass.getUser_id() != id)
                .collect(Collectors.toCollection(PriorityQueue::new));
        return filteredCollection;
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var Mclass : collection) {
            info.append(Mclass+"\n\n");
        }
        return info.toString().trim();
    }




}