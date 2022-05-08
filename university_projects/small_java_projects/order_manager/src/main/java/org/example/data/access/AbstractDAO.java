package org.example.data.access;

import org.example.connection.ConnectionFactory;


import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AbstractDAO is used for implementing our DAOs
 * This class implements the methods used for working with the queries,
 * methods for creating the necessary queries in strings and methods that use these strings to communicate with
 * the database and acquire the wanted results or make the intended changes in the database.
 * This class works with generics that are given a type in the next classes which inherit this one.
 *
 *
 *
 * @param <T>
 */





public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    private String createInsertQuery(List<String> fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        sb.append(fields.get(0) +",");
        for( int i=1;i<fields.size()-1;i++){
            if(type.getName().equals("Product") && i == findAll().size()-2) {
                sb.append(""+fields.get(i) +",");
            }
            else sb.append("'"+fields.get(i) +"',");

        }
        if(type.getName().equals("Client")) {
            sb.append("'" + fields.get(fields.size() - 1) + "')");
        }
        else {
            sb.append("" + fields.get(fields.size() - 1) + ")");
        }
        return sb.toString();

    }
    private String createUpdateQuery(List<String> fields,List<String> fieldNames, String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        //sb.append(" "+fieldNames.get(0) +" = " + fields.get(0)+",");
        for( int i=1;i<fieldNames.size()-1;i++){
            sb.append(" "+fieldNames.get(i) +" = '" + fields.get(i-1)+"',");

        }
        sb.append(" "+fieldNames.get(fieldNames.size()-1) +" = " + fields.get(fields.size()-1));

        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    public ArrayList<T> findAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query=sb.toString();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return (ArrayList<T>) createObjects(resultSet);
        } catch (SQLException e) {
            System.out.println(query);
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }



    public T insert(T t)  {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fieldsArray =type.getDeclaredFields();

        List<String> fields= new ArrayList<>();
        for(Field field:fieldsArray){
            field.setAccessible(true);
            try {
                fields.add(field.get(t).toString());
            }
            catch (IllegalAccessException | IllegalArgumentException e){
                LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());

            }

        }
        String query = createInsertQuery(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.executeUpdate();

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;


    }

    public T update(T t,ArrayList<String> values,int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fieldsArray =type.getDeclaredFields();


        List<String> fieldNames= new ArrayList<>();

        for(Field field:fieldsArray){
            try {

                fieldNames.add(field.getName());
            }
            catch (IllegalArgumentException e){
                LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());

            }

        }
        String query = createUpdateQuery(values,fieldNames,"id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T delete(T t,int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;



        String query="DELETE FROM "+type.getSimpleName()+" WHERE id="+id;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T deleteAll(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;



        String query="DELETE FROM "+type.getSimpleName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }



    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                assert constructor != null;
                constructor.setAccessible(true);
                T instance = (T)constructor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Class<T> getType() {
        return type;

    }

}
