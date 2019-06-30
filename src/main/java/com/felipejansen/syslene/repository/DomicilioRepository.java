package com.felipejansen.syslene.repository;

import com.felipejansen.syslene.domain.Domicilio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Domicilio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long>, JpaSpecificationExecutor<Domicilio> {

    @Query("select domicilio from Domicilio domicilio where domicilio.user.login = ?#{principal.username}")
    List<Domicilio> findByUserIsCurrentUser();

}
