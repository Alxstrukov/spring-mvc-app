package by.strukov.springcourse.util;

import by.strukov.springcourse.dao.PersonDAO;
import by.strukov.springcourse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;


    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);//в методе supports указывается через equals класс,
        // экземпляры которого будут проверяться в методе validate
    }

    //метод проверяет БД на наличие человека с таким же email
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.show(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "Пользователь с таким email  уже существует");

    }
}
