package com.challengeliteratura.challengeliteratura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challengeliteratura.challengeliteratura.entity.AutorEntity;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    @Query("SELECT a FROM AutorEntity a WHERE :ano between a.dataNascimento AND a.dataFalecimento")
    List<AutorEntity> findForYear(int ano);

}
