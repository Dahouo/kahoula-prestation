package com.afrologix.kahoula.resources.Bill;

import com.afrologix.kahoula.resources.EntityMapper;
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
