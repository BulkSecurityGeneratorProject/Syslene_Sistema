package com.felipejansen.syslene.service.mapper;

import com.felipejansen.syslene.domain.*;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Localidade} and its DTO {@link LocalidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CidadeMapper.class})
public interface LocalidadeMapper extends EntityMapper<LocalidadeDTO, Localidade> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "cidade.id", target = "cidadeId")
    @Mapping(source = "cidade.descricao", target = "cidadeDescricao")
    LocalidadeDTO toDto(Localidade localidade);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "cidadeId", target = "cidade")
    Localidade toEntity(LocalidadeDTO localidadeDTO);

    default Localidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Localidade localidade = new Localidade();
        localidade.setId(id);
        return localidade;
    }
}
