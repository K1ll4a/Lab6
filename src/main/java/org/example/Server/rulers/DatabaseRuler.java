package org.example.Server.rulers;
import org.example.Server.tools.PasswordHashing;
import org.example.global.facility.*;
import  org.example.Server.managers.SocketServer;
import  org.example.global.facility.Mclass;


import  java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

import org.example.Server.managers.SocketServer;
import static  org.example.Server.managers.SocketServer.log;

public class DatabaseRuler {
    private Connection connection;
    private String DB_HOST = "pg";
    private String DB_NAME = "studs";
    private String DB_USER = "postgres";
    private String DB_PASSWORD = "26097234";
    private String URL = "jdbc:postgresql://pg:5432/studs";
    private final String ADD_MCLASS =
            "insert into mclass (name, price, x_coordinate , y_coordinate , unitOfMeasure, manufactureCost, " +
            "org_name, org_fullname , organizationType, user_id , login) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?);";

    private final String LAST_MCLASS = "SELECT * FROM mclass ORDER BY id DESC LIMIT 1;";
    private final String UPDATE_MCLASS = "update mclass set name = ?,price = ?, x_coordinate = ?,y_coordinate = ?,unitOfMeasure = ?,manufactureCost = ?, " +
            "org_name= ?,org_fullname = ?, organizationType = ?, where id = ?;";
    private final String CHECK_IF_USERID_IS_CORRECT = "select user_id from mclass where id=?;";
    private final String GET_USER_ID = "select id from users where login = ?;";
    private final String INSERT_USER = "insert into users(login , password_hash , salt) values(?,?,?);";
    private final String LOAD_DB = " select * from mclass;";
    private final String CHECK_USER = "select * from users where login = ?;";
    private final String CHECK_USER_SALT = "select salt from users where login = ?;";
    private final String REMOVE_LAST_MCLASS = "DELETE FROM mclass WHERE id = (SELECT MIN(id) FROM mclass);";
    private final String REMOVE_BY_ID = "delete from mclass where id = ?;";
    private final String CLEAR = "delete from mclass where user_id = ?;";

    public void connect(){
        try{
            connection = DriverManager.getConnection(URL);
            log.info("Подключение к базе данных успешно.");
        } catch (SQLException e){
            log.info("Ошибка подключения к базе данных.");
            System.exit(1);
        }
    }

    public PriorityQueue<Mclass> loadCollection(){
        PriorityQueue<Mclass> result = new PriorityQueue<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(LOAD_DB);
            while (resultSet.next()) {
                long id  =  resultSet.getLong("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                float x = resultSet.getFloat("x_coordinate");
                float y = resultSet.getFloat("y_coordinate");
                String creationDate = resultSet.getString("creation_date");
                Mclass.UnitOfMeasure unitOfMeasure = Mclass.UnitOfMeasure.valueOf(resultSet.getString("unitOfMeasure"));
                float manufactureCost = resultSet.getFloat("manufactureCost");
                int OrgId = resultSet.getInt("org_id");
                String org_name = resultSet.getString("org_name");
                String org_fullname = resultSet.getString("org_fullname");
                Mclass.OrganizationType organizationType = Mclass.OrganizationType.valueOf(resultSet.getString("organizationType"));
                int userId = resultSet.getInt("user_id");
                String login = resultSet.getString("login");
                result.add(new Mclass(id,name,price,new Mclass.Coordinates(x,y),creationDate,unitOfMeasure,manufactureCost, new Mclass.Organization(OrgId,org_name,org_fullname,organizationType),userId,login));


            }
        }catch (SQLException e){
            log.warn("Не удалось полностью загрузить коллекцию.");
            e.printStackTrace();
        }
        return result;
    }

    public  Mclass getLastMclass() throws  SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(LAST_MCLASS);
        if (resultSet.next()){
            long id  =  resultSet.getLong("id");
            String name = resultSet.getString("name");
            Double price = resultSet.getDouble("price");
            float x = resultSet.getFloat("x_coordinate");
            float y = resultSet.getFloat("y_coordinate");
            Mclass.UnitOfMeasure unitOfMeasure = Mclass.UnitOfMeasure.valueOf(resultSet.getString("unitOfMeasure"));
            float manufactureCost = resultSet.getFloat("manufactureCost");
            int OrgId = resultSet.getInt("org_id");
            String creationDate = resultSet.getString("creation_date");
            String org_name = resultSet.getString("org_name");
            String org_fullname = resultSet.getString("org_fullname");
            Mclass.OrganizationType organizationType = Mclass.OrganizationType.valueOf(resultSet.getString("organizationType"));
            int userId = resultSet.getInt("user_id");
            String login = resultSet.getString("login");

            return  new  Mclass(id,name,price,new Mclass.Coordinates(x,y),creationDate,unitOfMeasure,manufactureCost, new Mclass.Organization(OrgId,org_name,org_fullname,organizationType),userId,login);

        }
        return null;
    }

    public void insertUser(String login,String password , String salt) throws SQLException{
        PreparedStatement addUser = connection.prepareStatement(INSERT_USER);
        addUser.setString(1,login);
        addUser.setString(2,password);
        addUser.setString(3,salt);
        addUser.executeUpdate();
    }

    public boolean checkUser(String login,String password) throws SQLException{
        PreparedStatement checkStatement = connection.prepareStatement(CHECK_USER);
        checkStatement.setString(1,login);
        ResultSet resultSetUser = checkStatement.executeQuery();
        if (resultSetUser.next()){
            var passwordHash = resultSetUser.getString("password_hash");
            var salt = resultSetUser.getString("salt");
            var hashPasswordWithSalt = PasswordHashing.hashPasswordWithSalt(password, salt);
            var result = passwordHash.equals(hashPasswordWithSalt);
            return  result;

        }else {
            return false;
        }
    }
    public int getUserID(String login) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement(GET_USER_ID);
        checkStatement.setString(1,login);
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next()){
            var result = resultSet.getInt("id");
            return result;
        }else{
            return -1;
        }
    }

    public void insertMclass(Mclass mclass,String login) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MCLASS);
        String name = mclass.getName();
        float x = mclass.getCoordinates().getX();
        float y = mclass.getCoordinates().getY();
        Double price = mclass.getPrice();
        float manufactureCost = mclass.getManufactureCost();
        String unitOfMeasure =  String.valueOf(mclass.getUnitOfMeasure());
        String creationDate = mclass.getCreationDate().format(DateTimeFormatter.ISO_DATE_TIME);
        String org_name = mclass.getManufacturer().getOrgName();
        String org_fullname = mclass.getManufacturer().getFullName();
        String organizationType = String.valueOf(mclass.getManufacturer().getType());
        int userId = Math.toIntExact(mclass.getId());
        preparedStatement.setString(1,name);
        preparedStatement.setFloat(2,x);
        preparedStatement.setFloat(3,y);
        preparedStatement.setDouble(4,price);
        preparedStatement.setFloat(5,manufactureCost);
        preparedStatement.setString(6,unitOfMeasure);
        preparedStatement.setString(7,creationDate);
        preparedStatement.setString(8,org_name);
        preparedStatement.setString(9,org_fullname);
        preparedStatement.setString(10,organizationType);
        preparedStatement.setInt(11,userId);
        preparedStatement.setString(12,login);
        preparedStatement.executeUpdate();

    }

    public void updateMclass(Mclass mclass, long id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MCLASS);
        String name = mclass.getName();
        float x = mclass.getCoordinates().getX();
        float y = mclass.getCoordinates().getY();
        Double price = mclass.getPrice();
        float manufactureCost = mclass.getManufactureCost();
        String unitOfMeasure =  String.valueOf(mclass.getUnitOfMeasure());
        String org_name = mclass.getManufacturer().getOrgName();
        String org_fullname = mclass.getManufacturer().getFullName();
        String organizationType = String.valueOf(mclass.getManufacturer().getType());
        preparedStatement.setString(1,name);
        preparedStatement.setFloat(2,x);
        preparedStatement.setFloat(3,y);
        preparedStatement.setDouble(4,price);
        preparedStatement.setFloat(5,manufactureCost);
        preparedStatement.setString(6,unitOfMeasure);
        preparedStatement.setString(7,org_name);
        preparedStatement.setString(8,org_fullname);
        preparedStatement.setString(9,organizationType);
        preparedStatement.setLong(10,id);
        preparedStatement.executeUpdate();


    }
    public void removeMclassById(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public int isCorrectID(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_USERID_IS_CORRECT);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("user_id");
        } else {
            return -1;
        }
    }

    public void removeLastMclass() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(REMOVE_LAST_MCLASS);
    }

    public void clear(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CLEAR);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
