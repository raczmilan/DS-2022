package ro.tuc.ds2020.repositories;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByName(String name);
    @Query(value = "SELECT p " + "FROM Person p " + "WHERE p.name = :name " + "AND p.age >= 60  ")
    Optional<Person> findSeniorsByName(@Param("name") String name);

}
