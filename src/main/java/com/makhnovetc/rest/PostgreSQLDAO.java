package com.makhnovetc.rest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PostgreSQLDAO {
    private Connection connection;
    private String query = "select * from persons";

    public PostgreSQLDAO(){};

    public PostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Person> getPersons() {
        List<Person> persons = getPerson(query);
        return persons;
    }

    public List<Person> getPerson(String query) {
        List<Person> persons = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id=rs.getInt("id");
                String name = rs.getString("name");
                String middlename = rs.getString("middle_name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                String sex=rs.getString("sex");
                Person person = new Person(id,name,middlename, surname, dob, sex);
                persons.add(person);
                System.out.println("get person");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;
    }

    public List<Person> findPeople(String id, String name, String middlename, String surname, String dob, String sex){
        ArrayList<String> query_where = new ArrayList<String>();
        if (!id.isEmpty()) query_where.add("id='"+id+"'");
        if (!name.isEmpty()) query_where.add("name='"+name+"'");
        if (!middlename.isEmpty()) query_where.add("middle_name='"+middlename+"'");
        if (!surname.isEmpty()) query_where.add("surname='"+surname+"'");
        if (!dob.isEmpty()) query_where.add("dob='"+dob+"'");
        if (!sex.isEmpty()) query_where.add("sex='"+sex+"'");
        String query = query_where.size() >0 ? this.query +
                " where " + String.join(" and ",query_where)
                : this.query;

        List<Person> persons = getPerson(query);
        return persons;
    }

}
