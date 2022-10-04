package co.com.bancopichincha.retojava.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Super class person.
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    /**
     * Person's identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Person's name.
     */
    private String name;

    /**
     * Person's genre.
     */
    private String genre;

    /**
     * Person's age.
     */
    private int age;

    /**
     * Person's address.
     */
    private String address;

    /**
     * Person's phone number.
     */
    private String phone;

}
