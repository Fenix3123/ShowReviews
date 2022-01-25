package com.coderscampus.showreviews.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.showreviews.domain.Authorities;


public interface AuthoritiesRepository extends JpaRepository<Authorities, Long>{

}
