package br.com.utfpr.avaliawifi.repository;

import br.com.utfpr.avaliawifi.entity.Comodo;
import br.com.utfpr.avaliawifi.entity.Residencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComodoRepository extends JpaRepository<Comodo, Long> {
    
    List<Comodo> findByResidencia(Residencia residencia);
    
    List<Comodo> findByResidenciaId(Long residenciaId);
    
    long countByResidenciaId(Long residenciaId);
    
}