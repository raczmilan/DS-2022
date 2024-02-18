package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDeleteDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.dtos.builders.PersonBuilder;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findAll();
        return personList.stream()
                .map(PersonBuilder::toPersonDTO)
                .collect(Collectors.toList());
    }

    public PersonDetailsDTO findPersonById(Integer id) {
        Optional<Person> prosumerOptional = personRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return PersonBuilder.toPersonDetailsDTO(prosumerOptional.get());
    }

    public Integer insert(PersonDetailsDTO personDTO) {
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);
        LOGGER.debug("Person with id {} was inserted in db", person.getId());
        return person.getId();
    }

    public Integer delete(PersonDeleteDTO personDTO){
        Integer id = personDTO.getId();
        personRepository.deleteById(id);
        LOGGER.debug("Person with id {} was deleted from db", id);
        return id;
    }

    public Integer update(PersonDTO personDTO){
        Person person = PersonBuilder.toEntity(personDTO);
        if(person.getId() == null){
            return -1;
        }
        Person personBeforeUpdate = personRepository.findById(person.getId()).orElse(null);
        if(personBeforeUpdate == null) {
            LOGGER.debug("Person with id {} was not updated", person.getId());
            return -1;
        }
        if(person.getName().equals("")){
            person.setName(personBeforeUpdate.getName());
        }
        if(person.getAddress().equals("")){
            person.setAddress(personBeforeUpdate.getAddress());
        }
        if(person.getAge() == 0){
            person.setAge(personBeforeUpdate.getAge());
        }
        personRepository.save(person);
        LOGGER.debug("Person with id {} was updated in db", person.getId());
        return person.getId();
    }

}
