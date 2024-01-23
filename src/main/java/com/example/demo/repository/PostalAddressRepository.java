package com.example.demo.repository;

import com.example.demo.models.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, Long> {
}
