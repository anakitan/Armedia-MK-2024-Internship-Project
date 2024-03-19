package com.example.demo.repository.dao;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.web.PersonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Person> createPerson(Person person) {
        try {
            entityManager.persist(person);
            return Optional.of(person);
        } catch (PersistenceException ex) {
            throw new UserAlreadyExistsException("Duplicate entry for contact method or postal address.");
        }
    }

    public List<Person> listAllPersons() {
        return entityManager.createNamedQuery("Person.findAll", Person.class).getResultList();
    }

    public Person getPersonById(Long id) {
        return entityManager.createNamedQuery("Person.findById", Person.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<Person> findByEmail(String email) {
        Person person = entityManager.createNamedQuery("Person.findByEmail", Person.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> findByStreetAddress(String streetAddress) {
        Person person = entityManager.createNamedQuery("Person.findByStreetAddress", Person.class)
                .setParameter("streetAddress", streetAddress)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> getPersonDetails(Long personId) {
        Person person = entityManager.createNamedQuery("Person.findById", Person.class)
                .setParameter("id", personId)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();
            person.getPostalAddresses().add(newAddress);
            entityManager.persist(person);
            return Optional.of(person);
        } catch (NoResultException e) {
            logger.error("Exception at addPersonAddress method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at addPersonAddress method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for street address.");
        }
    }

//    public Optional<Person> editPersonAddress(Long personId, Long addressId, PostalAddress editedAddress) {
//        TypedQuery<Person> queryById = entityManager.createNamedQuery(
//                "Person.findById", Person.class).setParameter("id", personId);
//        try {
//            Person person = queryById.getSingleResult();
//
//            PostalAddress existingAddress = findPostalAddressById(person, addressId);
//            if (existingAddress != null) {
//                if (editedAddress.getStreetAddress() != null) {
//                    existingAddress.setStreetAddress(editedAddress.getStreetAddress());
//                }
//                if (editedAddress.getCity() != null) {
//                    existingAddress.setCity(editedAddress.getCity());
//                }
//                if (editedAddress.getZip() != null) {
//                    existingAddress.setZip(editedAddress.getZip());
//                }
//                if (editedAddress.getCountry() != null) {
//                    existingAddress.setCountry(editedAddress.getCountry());
//                }
//
//                entityManager.persist(person);
//                return Optional.of(person);
//            } else {
//                logger.error("Postal address not found for editing.");
//                throw new PersonNotFoundException(String.format("Postal address with id: %d was not found.", addressId));
//            }
//        } catch (NoResultException e) {
//            logger.error("Exception at editPersonAddress method for not found entry.");
//            return Optional.empty();
//        } catch (PersistenceException e) {
//            logger.error("Exception at editPersonAddress method for duplicate entry.");
//            throw new UserAlreadyExistsException("Duplicate entry for street address.");
//        }
//    }
//    private PostalAddress findPostalAddressById(Person person, Long addressId) {
//        for (PostalAddress existingAddress : person.getPostalAddresses()) {
//            if (existingAddress.getId().equals(addressId)) {
//                return existingAddress;
//            }
//        }
//        return null;
//    }

    public Optional<Person> editPersonAddress(Long personId, PostalAddress editedAddress) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();

            PostalAddress existingAddress = person.getPostalAddresses().get(0);

            if (existingAddress != null) {
                if (editedAddress.getStreetAddress() != null) {
                    existingAddress.setStreetAddress(editedAddress.getStreetAddress());
                }
                if (editedAddress.getCity() != null) {
                    existingAddress.setCity(editedAddress.getCity());
                }
                if (editedAddress.getZip() != null) {
                    existingAddress.setZip(editedAddress.getZip());
                }
                if (editedAddress.getCountry() != null) {
                    existingAddress.setCountry(editedAddress.getCountry());
                }

                entityManager.persist(person);
                return Optional.of(person);
            } else {
                logger.error("Postal address not found for editing.");
                throw new PersonNotFoundException(String.format("Postal address not found for person with id: %d", personId));
            }
        } catch (NoResultException e) {
            logger.error("Exception at editPersonAddress method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at editPersonAddress method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for street address.");
        }
    }

    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();
            person.getContactMethods().add(contactMethod);
            entityManager.persist(person);
            return Optional.of(person);
        } catch (NoResultException e) {
            logger.error("Exception at addPersonContactMethod method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at addPersonContactMethod method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for contact method.");
        }
    }

    public Optional<Person> editPersonContact(Long personId, ContactMethod editedContact) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();

            ContactMethod existingContact = person.getContactMethods().get(0);

            if (existingContact != null) {
                if (editedContact.getType() != null) {
                    existingContact.setType(editedContact.getType());
                }
                if (editedContact.getValue() != null) {
                    existingContact.setValue(editedContact.getValue());
                }
                if (editedContact.getDescription() != null) {
                    existingContact.setDescription(editedContact.getDescription());
                }

                entityManager.persist(person);
                return Optional.of(person);
            } else {
                logger.error("Contact method not found for editing.");
                throw new PersonNotFoundException(String.format("Contact method not found for person with id: %d", personId));
            }
        } catch (NoResultException e) {
            logger.error("Exception at editPersonContact method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at editPersonContact method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for value.");
        }
    }

    public void deletePostalAddress(Long personId, Long addressId) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();

            PostalAddress addressToDelete = null;
            for (PostalAddress address : person.getPostalAddresses()) {
                if (address.getId().equals(addressId)) {
                    addressToDelete = address;
                    break;
                }
            }
            if (addressToDelete != null) {
                person.getPostalAddresses().remove(addressToDelete);
                entityManager.remove(addressToDelete);
                entityManager.persist(person);
            } else {
                logger.error("Postal address not found for deletion.");
                throw new PersonNotFoundException(String.format("Postal address with id: %d was not found.", addressId));
            }
        } catch (NoResultException e) {
            logger.error("Exception at deletePostalAddress method for not found entry.");
            throw new PersonNotFoundException("Person with id: " + personId + " not found.");
        } catch (PersistenceException e) {
            logger.error("Exception at deletePostalAddress method for persistence error.");
            throw e;
        }
//        TypedQuery<Person> queryPersonById = entityManager.createNamedQuery(
//                "Person.findById", Person.class).setParameter("id", personId);
//        TypedQuery<PostalAddress> queryAddressById = entityManager.createNamedQuery(
//                "PostalAddress.findById", PostalAddress.class).setParameter("id", postalAddress.getId());
//        try {
//            Person person = queryPersonById.getSingleResult();
//            PostalAddress address = queryAddressById.getSingleResult();
//            person.getPostalAddresses().remove(address);
//            entityManager.remove(address);
//            return Optional.of(person);
//        } catch (NoResultException e) {
//            return Optional.empty();
//        } catch (PersistenceException e) {
//            logger.error(e.getMessage());
//            throw new UserAlreadyExistsException("Duplicate entry for street address.");
//        }
    }

    public void deleteContactMethod(Long personId, Long contactId) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery(
                "Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();

            ContactMethod contactToDelete = null;
            for (ContactMethod contact : person.getContactMethods()) {
                if (contact.getId().equals(contactId)) {
                    contactToDelete = contact;
                    break;
                }
            }
            if (contactToDelete != null) {
                person.getContactMethods().remove(contactToDelete);
                entityManager.remove(contactToDelete);
                entityManager.persist(person);
            } else {
                logger.error("Contact method not found for deletion.");
                throw new PersonNotFoundException(String.format("Contact method with id: %d was not found.", contactId));
            }
        } catch (NoResultException e) {
            logger.error("Exception at deleteContactMethod method for not found entry.");
            throw new PersonNotFoundException("Person with id: " + personId + " not found.");
        } catch (PersistenceException e) {
            logger.error("Exception at deleteContactMethod method for persistence error.");
            throw e;
        }
    }
}
