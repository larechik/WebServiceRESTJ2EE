package com.makhnovetc.rest;

import javax.sql.DataSource;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    @Resource(lookup = "jdbc/__default")
    private DataSource dataSource;

    @GET
    public List<Person> getPerson(@QueryParam("id") String id,@QueryParam("name") String name,@QueryParam("middlename") String middlename,
                                  @QueryParam("surname") String surname,@QueryParam("dob") String dob,
                                  @QueryParam("sex") String sex) {
        List<Person> person = new PostgreSQLDAO(getConnection()).findPeople(id, name, middlename, surname, dob, sex);
        return person;
    }
    private Connection getConnection(){
        Connection dao = null;
        try{
            dao = dataSource.getConnection();
        } catch (SQLException ex){
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dao;
    }

}
