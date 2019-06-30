package com.felipejansen.syslene.service.mapper;

import com.felipejansen.syslene.domain.*;
import com.felipejansen.syslene.service.dto.DomicilioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Domicilio} and its DTO {@link DomicilioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LocalidadeMapper.class})
public interface DomicilioMapper extends EntityMapper<DomicilioDTO, Domicilio> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "localidade.id", target = "localidadeId")
    @Mapping(source = "localidade.descricao", target = "localidadeDescricao")
    DomicilioDTO toDto(Domicilio domicilio);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "localidadeId", target = "localidade")
    Domicilio toEntity(DomicilioDTO domicilioDTO);

    default Domicilio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Domicilio domicilio = new Domicilio();
        domicilio.setId(id);
        return domicilio;
    }
}
