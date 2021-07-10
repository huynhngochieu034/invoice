package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, UUID> {

}
