package com.afrologix.kahoula.domain.Bill;

import com.afrologix.kahoula.domain.Bill.Bill;
import com.afrologix.kahoula.domain.Bill.BillDTO;

import com.afrologix.kahoula.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity Bill and its DTO BillDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillMapper extends EntityMapper<BillDTO, Bill> {



    default Bill fromId(String id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
