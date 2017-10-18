package com.coinbase.exchange.api.accounts;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Imagine you are a peer of the developer who committed this (syntactically correct) Java code and asked you to review
 * their pull request. You work on the same product but are not familiar with this piece of work or its associated
 * requirements.
 * Please use Java comments for your review feedback, putting them on separate lines around the code. Do not modify the
 * code itself.
 **/
public class CodeReviewTest {

    // this local variable - if its needed outside the scope of this class fine but there's no getter for it and
    // it only has package access scope
    // if its not needed anywhere else, it would be better to have a getTotalAge() method that computes this since
    // its likely to be different every time.
    volatile Integer totalAge = 0;

    // name of the variable is not clear, what is a personPerson database? how about databaseOfPeople?
    CodeReviewTest(PersonDatabase<Person> personPersonDatabase) {

        Person[] persons = null;

        try {
            persons = personPersonDatabase.getAllPersons();
        } catch (IOException e) {
            // Exception caught but nothing done with it here - throw exception instead? Log some output?
        }

        List<Person> personsList = new LinkedList(); // why a linked list? Better performing lists are available.

        // This style of for loop is not recommended due to off by one errors being more likely
        // use instead:
        // for (Person p : persons) { personsList.add(p); }
        for (int i = 0; i <= persons.length; i++) {
            personsList.add(persons[i]);
        }

        personsList.parallelStream().forEach(person -> {
            // this lambda can be on one line.
            totalAge += person.getAge();
        });


        // This list could be generated using a filter on a stream which would be cleaner.
        List<Person> males = new LinkedList<>();
        for (Person person : personsList) {
            // might be better to implement this as an If statement given the choice is binary.
            switch (person.gender) {
                case "Female": personsList.remove(person); // no break?
                case "Male" : males.add(person);
            }
        }

        System.out.println("Total age =" + totalAge);  // use logging framework
        System.out.println("Total number of females =" + personsList.size());
        System.out.println("Total number of males =" + males.size());
    }

}

// scoping implied again - was this intended?
class Person {

    private int age; // since this appears to be coming from a database it might be better to use the boxed reference type here
    private String firstName;
    private String lastName;

    // different scope to the other variabls - because its used in the for loop up above. It would be better
    // to provide a getter for this really.
    // Could implement this as an enum rather than a String which isn't necessarily the right data type.
    String gender;

    public Person(int age, String firstName, String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {
        // where are the rest of the getters/setters?
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        // could be implemented better - no type safety check before the cast.
        // you must also override hashCode if you override Equals due to the implied contract
        // between equals and hashcode stating that if 2 objects are equal, their hashcode should be equal
        // as well.
        return this.lastName == ((Person)obj).lastName;
    }
}

// enum not used .. see next comment below.
interface PersonDatabase<E> {
    // returns a person - looks like this was supposed to be: E[] getAll() throws IOException; also package scoped implicitly
     Person[] getAllPersons() throws IOException;
}