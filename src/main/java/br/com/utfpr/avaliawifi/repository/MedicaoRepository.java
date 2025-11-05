package br.com.utfpr.avaliawifi.repository;

import br.com.utfpr.avaliawifi.entity.Medicao;
import br.com.utfpr.avaliawifi.entity.Comodo;
import br.com.utfpr.avaliawifi.entity.Residencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    
    List<Medicao> findByResidencia(Residencia residencia);
    
    List<Medicao> findByComodo(Comodo comodo);
    
    List<Medicao> findByResidenciaId(Long residenciaId);
    
    List<Medicao> findByComodoId(Long comodoId);
    
    long countByResidenciaId(Long residenciaId);
    
    long countByComodoId(Long comodoId);
    
    @Query("SELECT COUNT(m) FROM Medicao m WHERE m.intensidadeDbm >= -50")
    long countSinalForte();
    
    @Query("SELECT COUNT(m) FROM Medicao m WHERE m.intensidadeDbm < -50 AND m.intensidadeDbm >= -70")
    long countSinalMedio();
    
    @Query("SELECT COUNT(m) FROM Medicao m WHERE m.intensidadeDbm < -70")
    long countSinalFraco();
    
}