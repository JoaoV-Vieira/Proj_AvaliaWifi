package br.com.utfpr.avaliawifi.config;

import br.com.utfpr.avaliawifi.entity.Residencia;
import br.com.utfpr.avaliawifi.entity.Comodo;
import br.com.utfpr.avaliawifi.entity.Medicao;
import br.com.utfpr.avaliawifi.repository.ResidenciaRepository;
import br.com.utfpr.avaliawifi.repository.ComodoRepository;
import br.com.utfpr.avaliawifi.repository.MedicaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Autowired
    private ComodoRepository comodoRepository;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem dados no banco
        if (residenciaRepository.count() > 0) {
            logger.info("Dados já existem no banco. Pulando inicialização.");
            return;
        }

        logger.info("Inicializando dados do banco de dados...");
        
        try {
            // Criar residências
            List<Residencia> residencias = criarResidencias();
            
            // Criar cômodos
            List<Comodo> comodos = criarComodos(residencias);
            
            // Criar medições
            criarMedicoes(residencias, comodos);
            
            logger.info("Dados inicializados com sucesso!");
            logger.info("Total de residências: {}", residenciaRepository.count());
            logger.info("Total de cômodos: {}", comodoRepository.count());
            logger.info("Total de medições: {}", medicaoRepository.count());
            
        } catch (Exception e) {
            logger.error("Erro ao inicializar dados: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<Residencia> criarResidencias() {
        logger.info("Criando residências...");
        
        Residencia casa1 = new Residencia("Casa do João", "Rua das Flores, 123", "João Silva");
        Residencia apartamento = new Residencia("Apartamento Centro", "Av. Brasil, 456 - Apto 302", "Maria Santos");
        Residencia casa2 = new Residencia("Casa dos Pais", "Rua Principal, 789", "José Oliveira");
        Residencia residenciaThiago = new Residencia("Residencia de Thiago", "Rua dos Projetos, 456 - Bairro Universitário", "Thiago");

        return residenciaRepository.saveAll(List.of(casa1, apartamento, casa2, residenciaThiago));
    }

    private List<Comodo> criarComodos(List<Residencia> residencias) {
        logger.info("Criando cômodos...");
        
        Residencia casa1 = residencias.get(0);
        Residencia apartamento = residencias.get(1);
        Residencia casa2 = residencias.get(2);
        Residencia residenciaThiago = residencias.get(3);

        // Cômodos da Casa do João
        Comodo salaEstar = new Comodo("Sala de Estar", 4.5, 6.0, casa1);
        Comodo quartoPrincipal = new Comodo("Quarto Principal", 3.5, 4.0, casa1);
        Comodo cozinha = new Comodo("Cozinha", 2.5, 3.0, casa1);

        // Cômodos do Apartamento
        Comodo sala = new Comodo("Sala", 5.0, 7.0, apartamento);
        Comodo quarto = new Comodo("Quarto", 3.0, 4.5, apartamento);

        // Cômodos da Casa dos Pais
        Comodo homeOffice = new Comodo("Home Office", 3.0, 3.5, casa2);

        // Cômodos da Residência do Thiago
        Comodo escritorio = new Comodo("Escritório", 3.0, 4.0, residenciaThiago);
        Comodo quarto1 = new Comodo("Quarto 1", 3.5, 4.0, residenciaThiago);
        Comodo quarto2 = new Comodo("Quarto 2", 3.0, 3.5, residenciaThiago);
        Comodo copa = new Comodo("Copa", 2.5, 3.0, residenciaThiago);
        Comodo salaGame = new Comodo("Sala Game", 4.0, 5.0, residenciaThiago);
        Comodo banheiro = new Comodo("Banheiro", 2.0, 2.5, residenciaThiago);
        Comodo cozinhaThiago = new Comodo("Cozinha Thiago", 3.0, 4.0, residenciaThiago);
        Comodo salaTv = new Comodo("Sala de TV", 4.0, 5.0, residenciaThiago);
        Comodo quarto3 = new Comodo("Quarto 3", 3.0, 3.5, residenciaThiago);
        Comodo varanda = new Comodo("Varanda", 2.0, 8.0, residenciaThiago);
        Comodo bar = new Comodo("Bar", 2.5, 3.0, residenciaThiago);
        Comodo banheiro2 = new Comodo("Banheiro 2", 1.8, 2.0, residenciaThiago);
        Comodo areaExterna = new Comodo("Área Externa", 5.0, 10.0, residenciaThiago);
        Comodo edicula = new Comodo("Edícula", 3.0, 4.0, residenciaThiago);
        Comodo banheiro3 = new Comodo("Banheiro 3", 1.5, 2.0, residenciaThiago);

        return comodoRepository.saveAll(List.of(
            salaEstar, quartoPrincipal, cozinha, sala, quarto, homeOffice,
            escritorio, quarto1, quarto2, copa, salaGame, banheiro, cozinhaThiago,
            salaTv, quarto3, varanda, bar, banheiro2, areaExterna, edicula, banheiro3
        ));
    }

    private void criarMedicoes(List<Residencia> residencias, List<Comodo> comodos) {
        logger.info("Criando medições...");
        
        Residencia casa1 = residencias.get(0);
        Residencia apartamento = residencias.get(1);
        Residencia casa2 = residencias.get(2);
        Residencia residenciaThiago = residencias.get(3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Medições de exemplo para outras residências
        Medicao med1 = new Medicao(2.5, 3.0, -45.5, LocalDateTime.now(), comodos.get(0), casa1);
        Medicao med2 = new Medicao(1.5, 2.0, -52.1, LocalDateTime.now(), comodos.get(1), casa1);
        Medicao med3 = new Medicao(1.2, 1.5, -58.7, LocalDateTime.now(), comodos.get(2), casa1);
        Medicao med4 = new Medicao(3.0, 4.0, -42.3, LocalDateTime.now(), comodos.get(3), apartamento);
        Medicao med5 = new Medicao(2.0, 3.5, -48.9, LocalDateTime.now(), comodos.get(4), apartamento);
        Medicao med6 = new Medicao(1.5, 1.8, -55.4, LocalDateTime.now(), comodos.get(5), casa2);

        // Medições do projeto do Thiago - Sábado 14h
        Medicao[] medicoesThiagoSabado = {
            new Medicao(1.5, 2.0, -54, LocalDateTime.parse("2024-11-02 14:00:00", formatter), comodos.get(6), residenciaThiago),
            new Medicao(1.75, 2.0, -47, LocalDateTime.parse("2024-11-02 14:05:00", formatter), comodos.get(7), residenciaThiago),
            new Medicao(1.5, 1.75, -44, LocalDateTime.parse("2024-11-02 14:10:00", formatter), comodos.get(8), residenciaThiago),
            new Medicao(1.25, 1.5, -41, LocalDateTime.parse("2024-11-02 14:15:00", formatter), comodos.get(9), residenciaThiago),
            new Medicao(2.0, 2.5, -62, LocalDateTime.parse("2024-11-02 14:20:00", formatter), comodos.get(10), residenciaThiago),
            new Medicao(1.0, 1.25, -62, LocalDateTime.parse("2024-11-02 14:25:00", formatter), comodos.get(11), residenciaThiago),
            new Medicao(1.5, 2.0, -55, LocalDateTime.parse("2024-11-02 14:30:00", formatter), comodos.get(12), residenciaThiago),
            new Medicao(2.0, 2.5, -66, LocalDateTime.parse("2024-11-02 14:35:00", formatter), comodos.get(13), residenciaThiago),
            new Medicao(1.5, 1.75, -70, LocalDateTime.parse("2024-11-02 14:40:00", formatter), comodos.get(14), residenciaThiago),
            new Medicao(1.0, 4.0, -72, LocalDateTime.parse("2024-11-02 14:45:00", formatter), comodos.get(15), residenciaThiago),
            new Medicao(1.25, 1.5, -74, LocalDateTime.parse("2024-11-02 14:50:00", formatter), comodos.get(16), residenciaThiago),
            new Medicao(0.9, 1.0, -74, LocalDateTime.parse("2024-11-02 14:55:00", formatter), comodos.get(17), residenciaThiago),
            new Medicao(2.5, 5.0, -83, LocalDateTime.parse("2024-11-02 15:00:00", formatter), comodos.get(18), residenciaThiago),
            new Medicao(1.5, 2.0, -84, LocalDateTime.parse("2024-11-02 15:05:00", formatter), comodos.get(19), residenciaThiago),
            new Medicao(0.75, 1.0, -90, LocalDateTime.parse("2024-11-02 15:10:00", formatter), comodos.get(20), residenciaThiago)
        };

        // Medições do projeto do Thiago - Domingo 11h
        Medicao[] medicoesThiagoDomingo = {
            new Medicao(1.5, 2.0, -56, LocalDateTime.parse("2024-11-03 11:00:00", formatter), comodos.get(6), residenciaThiago),
            new Medicao(1.75, 2.0, -49, LocalDateTime.parse("2024-11-03 11:05:00", formatter), comodos.get(7), residenciaThiago),
            new Medicao(1.5, 1.75, -42, LocalDateTime.parse("2024-11-03 11:10:00", formatter), comodos.get(8), residenciaThiago),
            new Medicao(1.25, 1.5, -47, LocalDateTime.parse("2024-11-03 11:15:00", formatter), comodos.get(9), residenciaThiago),
            new Medicao(2.0, 2.5, -63, LocalDateTime.parse("2024-11-03 11:20:00", formatter), comodos.get(10), residenciaThiago),
            new Medicao(1.0, 1.25, -61, LocalDateTime.parse("2024-11-03 11:25:00", formatter), comodos.get(11), residenciaThiago),
            new Medicao(1.5, 2.0, -56, LocalDateTime.parse("2024-11-03 11:30:00", formatter), comodos.get(12), residenciaThiago),
            new Medicao(2.0, 2.5, -63, LocalDateTime.parse("2024-11-03 11:35:00", formatter), comodos.get(13), residenciaThiago),
            new Medicao(1.5, 1.75, -69, LocalDateTime.parse("2024-11-03 11:40:00", formatter), comodos.get(14), residenciaThiago),
            new Medicao(1.0, 4.0, -73, LocalDateTime.parse("2024-11-03 11:45:00", formatter), comodos.get(15), residenciaThiago),
            new Medicao(1.25, 1.5, -72, LocalDateTime.parse("2024-11-03 11:50:00", formatter), comodos.get(16), residenciaThiago),
            new Medicao(0.9, 1.0, -74, LocalDateTime.parse("2024-11-03 11:55:00", formatter), comodos.get(17), residenciaThiago),
            new Medicao(2.5, 5.0, -82, LocalDateTime.parse("2024-11-03 12:00:00", formatter), comodos.get(18), residenciaThiago),
            new Medicao(1.5, 2.0, -86, LocalDateTime.parse("2024-11-03 12:05:00", formatter), comodos.get(19), residenciaThiago),
            new Medicao(0.75, 1.0, -90, LocalDateTime.parse("2024-11-03 12:10:00", formatter), comodos.get(20), residenciaThiago)
        };

        // Medições do projeto do Thiago - Segunda 20h
        Medicao[] medicoesThiagoSegunda = {
            new Medicao(1.5, 2.0, -54, LocalDateTime.parse("2024-11-04 20:00:00", formatter), comodos.get(6), residenciaThiago),
            new Medicao(1.75, 2.0, -47, LocalDateTime.parse("2024-11-04 20:05:00", formatter), comodos.get(7), residenciaThiago),
            new Medicao(1.5, 1.75, -42, LocalDateTime.parse("2024-11-04 20:10:00", formatter), comodos.get(8), residenciaThiago),
            new Medicao(1.25, 1.5, -42, LocalDateTime.parse("2024-11-04 20:15:00", formatter), comodos.get(9), residenciaThiago),
            new Medicao(2.0, 2.5, -61, LocalDateTime.parse("2024-11-04 20:20:00", formatter), comodos.get(10), residenciaThiago),
            new Medicao(1.0, 1.25, -60, LocalDateTime.parse("2024-11-04 20:25:00", formatter), comodos.get(11), residenciaThiago),
            new Medicao(1.5, 2.0, -51, LocalDateTime.parse("2024-11-04 20:30:00", formatter), comodos.get(12), residenciaThiago),
            new Medicao(2.0, 2.5, -67, LocalDateTime.parse("2024-11-04 20:35:00", formatter), comodos.get(13), residenciaThiago),
            new Medicao(1.5, 1.75, -70, LocalDateTime.parse("2024-11-04 20:40:00", formatter), comodos.get(14), residenciaThiago),
            new Medicao(1.0, 4.0, -73, LocalDateTime.parse("2024-11-04 20:45:00", formatter), comodos.get(15), residenciaThiago),
            new Medicao(1.25, 1.5, -75, LocalDateTime.parse("2024-11-04 20:50:00", formatter), comodos.get(16), residenciaThiago),
            new Medicao(0.9, 1.0, -76, LocalDateTime.parse("2024-11-04 20:55:00", formatter), comodos.get(17), residenciaThiago),
            new Medicao(2.5, 5.0, -83, LocalDateTime.parse("2024-11-04 21:00:00", formatter), comodos.get(18), residenciaThiago),
            new Medicao(1.5, 2.0, -87, LocalDateTime.parse("2024-11-04 21:05:00", formatter), comodos.get(19), residenciaThiago),
            new Medicao(0.75, 1.0, -90, LocalDateTime.parse("2024-11-04 21:10:00", formatter), comodos.get(20), residenciaThiago)
        };

        // Salvar todas as medições
        medicaoRepository.saveAll(List.of(med1, med2, med3, med4, med5, med6));
        medicaoRepository.saveAll(List.of(medicoesThiagoSabado));
        medicaoRepository.saveAll(List.of(medicoesThiagoDomingo));
        medicaoRepository.saveAll(List.of(medicoesThiagoSegunda));
    }
}