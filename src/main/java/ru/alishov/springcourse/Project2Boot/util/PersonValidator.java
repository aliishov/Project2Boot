package ru.alishov.springcourse.Project2Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishov.springcourse.Project2Boot.models.Person;
import ru.alishov.springcourse.Project2Boot.services.PeopleServices;


@Component
public class PersonValidator implements Validator
{
    private final PeopleServices peopleServices;

    @Autowired
    public PersonValidator(PeopleServices peopleServices)
    { this.peopleServices = peopleServices; }

    @Override
    public boolean supports(Class<?> aClass)
    { return Person.class.equals(aClass); }

    @Override
    public void validate(Object o, Errors errors)
    {
        Person person = (Person) o;

        if (peopleServices.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
    }
}