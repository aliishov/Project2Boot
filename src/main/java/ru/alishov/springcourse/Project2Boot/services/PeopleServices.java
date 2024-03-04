package ru.alishov.springcourse.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishov.springcourse.Project2Boot.models.Book;
import ru.alishov.springcourse.Project2Boot.models.Person;
import ru.alishov.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleServices
{
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServices(PeopleRepository peopleRepository)
    { this.peopleRepository = peopleRepository; }

    public List<Person> findAll()
    { return peopleRepository.findAll(); }

    public Person findById(int id)
    {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person)
    { peopleRepository.save(person); }

    @Transactional
    public void update(int id, Person updatedPerson)
    {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void deleteById(int id)
    { peopleRepository.deleteById(id); }

    public Optional<Person> getPersonByFullName(String fullName)
    { return peopleRepository.findByFullName(fullName); }

    public List<Book> getBooksByPersonId(int id)
    {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent())
        {
            Hibernate.initialize(person.get().getBooks());

            return person.get().getBooks();
        }
        else
            return Collections.emptyList();
    }
}
