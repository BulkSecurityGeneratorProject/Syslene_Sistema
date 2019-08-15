package com.felipejansen.syslene.repository;

import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Localidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalidadeRepository extends JpaRepository<Localidade, Long>, JpaSpecificationExecutor<Localidade> {

    @Query("select localidade from Localidade localidade where localidade.user.login = ?#{principal.username}")
    List<Localidade> findByUserIsCurrentUser();
}
