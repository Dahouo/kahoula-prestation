package com.afrologix.kahoula.service.mapper;

import com.afrologix.kahoula.domain.*;
import com.afrologix.kahoula.service.dto.UsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Users and its DTO UsersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {



    default Users fromId(String id) {
        if (id == null) {
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}
