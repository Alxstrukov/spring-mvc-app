package by.strukov.springcourse.dao;


import by.strukov.springcourse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));


    }

    public Person show(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        List<Person> personList = jdbcTemplate.query(sql,
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
        return personList.stream().findAny().orElse(null);
    }

    public void save(Person person) {
        String sql = "INSERT INTO person VALUES (1,?,?,?)";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), id);

    }

    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
