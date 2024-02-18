package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDeleteDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.services.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        for (PersonDTO dto : dtos) {
            Link personLink = linkTo(methodOn(PersonController.class)
                    .getPerson(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Integer> insertPerson(@Valid @RequestBody PersonDetailsDTO personDTO) {
        Integer personID = personService.insert(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Integer> deletePerson(@Valid @RequestBody PersonDeleteDTO personDTO) {
        Integer personID = personService.delete(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> updatePerson(@Valid @RequestBody PersonDTO personDTO) {
        Integer personID = personService.update(personDTO);
        if(personID == -1){
            return new ResponseEntity<>(personID, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(personID, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDetailsDTO> getPerson(@PathVariable("id") Integer personId) {
        PersonDetailsDTO dto = personService.findPersonById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
