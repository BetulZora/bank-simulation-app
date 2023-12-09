package com.cydeo.mapper;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDTO convertToDTO(Account entity){
        //this method will accept Account entity and will convert it to DTO
        return modelMapper.map(entity,AccountDTO.class);
    }

    public Account convertToEntity(AccountDTO dto){
        //this method will accept dto and convert it to entity
        return modelMapper.map(dto, Account.class);
    }

}
