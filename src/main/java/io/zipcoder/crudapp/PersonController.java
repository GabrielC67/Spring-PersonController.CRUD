package io.zipcoder.crudapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.zipcoder.crudapp.Person;
import io.zipcoder.crudapp.PersonRepository;

@RestController
@RequestMapping
public class PersonController{

    @Autowired
        private PersonRepository personRepository;

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person p){
        return new ResponseEntity<>(personRepository.save(p), HttpStatus.CREATED);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id){
        Person person = personRepository.findOne(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404 Not found
        }
        return new ResponseEntity<>(person, HttpStatus.OK); //200 Ok
    }

    @GetMapping("/people")
    public ResponseEntity<Iterable<Person>> getPersonList(){
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person p, @PathVariable int id){
        Person person = personRepository.findOne(id);
        person.setId(p.getId());
        person.setFirstName(p.getFirstName());
        person.setLastName(p.getLastName());
        if(person == null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(personRepository.save(person), HttpStatus.OK);
        }
    }

    @DeleteMapping("/people/{id}")
    ResponseEntity<Person> deletePerson(@PathVariable int id){
        Person person = personRepository.findOne(id);
        if (person != null) {
            personRepository.delete(person);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

}
