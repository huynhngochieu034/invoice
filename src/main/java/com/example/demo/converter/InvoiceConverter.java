package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.InvoiceDTO;
import com.example.demo.entity.InvoiceEntity;

@Component
public class InvoiceConverter {

    @Autowired
    private ModelMapper modelMapper;

    public InvoiceDTO convertToDto(InvoiceEntity entity) {
    	InvoiceDTO result = modelMapper.map(entity, InvoiceDTO.class);
        return result;
    }

    public InvoiceEntity convertToEntity(InvoiceDTO dto) {
    	InvoiceEntity result = modelMapper.map(dto, InvoiceEntity.class);
        return result;
    }
}
