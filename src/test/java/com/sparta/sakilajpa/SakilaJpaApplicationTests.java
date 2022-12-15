package com.sparta.sakilajpa;

import com.sparta.sakilajpa.entities.Actor;
import com.sparta.sakilajpa.entities.Address;
import com.sparta.sakilajpa.entities.Staff;
import com.sparta.sakilajpa.repositories.ActorRepository;
import com.sparta.sakilajpa.repositories.StaffRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class SakilaJpaApplicationTests {

    @Autowired
    private ActorRepository repository;

    @Autowired
    private StaffRepository staffRepo;

    @Test
    void testStaffRelationship(){
        Optional<Staff> mikeOpt = staffRepo.findById((short) 1);
        Staff mike = mikeOpt.get();
        Address mikesAddress = mike.getAddress();
        System.out.println(mikesAddress);
        String district = mikesAddress.getDistrict();
        Assertions.assertEquals("Alberta", district);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void findById() {

        Optional<Actor> result = repository.findById(15);
        if (result.isPresent()){
            // we do have actor
            Actor cuba = result.get();
            System.out.println(cuba);
            Assertions.assertEquals("CUBA", cuba.getFirstName());
            Assertions.assertEquals("OLIVIER", cuba.getLastName());
        } else {
            Assertions.fail();
        }

    }

    @Test
    void testCreateActor(){
        Actor newActor = new Actor();
        newActor.setId(0);
        newActor.setFirstName("Hannibal");
        newActor.setLastName("Brhanu");
        newActor.setLastUpdate(Instant.now());
        System.out.println(newActor);
        // save it to DB
        Actor result = repository.save(newActor);
        System.out.println(result);
        // retrieve it
        Optional<Actor> found = repository.findById(result.getId());
        Actor foundActor = found.get();
        // check that we got back what we created
        System.out.println(foundActor);
        Assertions.assertEquals("Hannibal", foundActor.getFirstName());
    }

    @Test
    void testUpdateActor(){
        Optional<Actor> result = repository.findById(70);
        if (result.isPresent()){
            Actor actor = result.get();
            actor.setFirstName("Matthew");
            System.out.println(actor);
            repository.save(actor);
        }
        Optional<Actor> resultAfterUpdate = repository.findById(70);
        if (resultAfterUpdate.isPresent()){
            Actor actor = resultAfterUpdate.get();
            Assertions.assertEquals("Matthew", actor.getFirstName());
        }
    }

    @Test
    void testDeleteActor(){
        Optional<Actor> resultBeforeDelete =  repository.findById(209);
        if (resultBeforeDelete.isEmpty()) Assertions.fail();
        repository.deleteById(209);
        Optional<Actor> resultAfterDelete =  repository.findById(209);
        Assertions.assertFalse(resultAfterDelete.isPresent());
    }

    @Test
    void testDeleteActor2(){
        Optional<Actor> resultBeforeDelete =  repository.findById(2);
        if (resultBeforeDelete.isEmpty()) Assertions.fail();
        repository.deleteById(2);
        Optional<Actor> resultAfterDelete =  repository.findById(2);
        Assertions.assertFalse(resultAfterDelete.isPresent());
    }

    @Test
    void testFindByLastName(){
        List<Actor> result = repository.findByLastName("NICHOLSON");
        System.out.println(result);
        Assertions.assertTrue(result.size() >= 1);
    }

}
