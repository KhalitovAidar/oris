package com.context.person;

import com.context.EmtyPersonException;
import com.context.annotations.Component;
import com.context.annotations.Inject;

import java.util.Optional;

@Component
public class PersonService {

    public PersonService() {
    }

    @Inject
    private PersonRepository repository;


    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> findById(Integer id) {
        return repository.findById(id);
    }

    public void add(Person person) throws EmtyPersonException {
        repository.add(person);
    }
}
