package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ItemEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {

}
