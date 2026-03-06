package by.strukov.springcourse.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Person {
    private Integer id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    private String name;

    @NotNull(message = "Поле не может быть пустым")
    @Positive(message = "Возраст не может быть отрицательным")
    private int age;

    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "Неправильный email")
    private String email;

    public Person() {
    }
}
