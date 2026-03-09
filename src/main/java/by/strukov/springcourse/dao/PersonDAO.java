package by.strukov.springcourse.dao;


import by.strukov.springcourse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
        String sql = "INSERT INTO person (name, age, email) VALUES (?,?,?)";
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

    /*
     * тестируем пакетное обновление данных (Batch Update)*/

    public void testMultipleUpdate() {
        List<Person> people = create1000People();

        Long before = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)",
                    person.getId(),
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }

        Long after = System.currentTimeMillis();

        System.out.println("Time multipleUpdate() = " + (after - before));

    }


    public void testBatchUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("Time testBatchUpdate() = " + (after - before));
    }


    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(i, new Person(i, "Name-" + i, 30, "test-" + i + "@mail.com"));
        }
        return people;
    }


}
