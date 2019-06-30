package com.felipejansen.syslene.repository;

import com.felipejansen.syslene.domain.Localidade;
import org.springframework.data.jpa.repository.*;
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
