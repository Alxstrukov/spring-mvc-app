package by.strukov.springcourse.dao;


import by.strukov.springcourse.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT = 0;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Katy", 27, "katrinv99@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Alex", 32, "alxstrukov@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 45, "bob@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Ivan", 42, "ivan@tut.by"));
        people.add(new Person(++PEOPLE_COUNT, "Tom", 39, "tomikGomik@gmail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream()
                .filter(person -> person.getId() == id)
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        people.add(new Person(++PEOPLE_COUNT,
                person.getName(),
                person.getAge(),
                person.getEmail()));
    }

    public void update(int id, Person personUpdated) {
        Person updateablePerson = show(id);
        updateablePerson.setName(personUpdated.getName());
        updateablePerson.setAge(personUpdated.getAge());
        updateablePerson.setEmail(personUpdated.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
