package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ItemDTO;
import com.example.demo.entity.ItemEntity;

@Component
public class ItemConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ItemDTO convertToDto(ItemEntity entity) {
    	ItemDTO result = modelMapper.map(entity, ItemDTO.class);
        return result;
    }

    public ItemEntity convertToEntity(ItemDTO dto) {
    	ItemEntity result = modelMapper.map(dto, ItemEntity.class);
        return result;
    }
}
