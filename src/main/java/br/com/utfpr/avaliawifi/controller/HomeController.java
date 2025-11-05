package br.com.utfpr.avaliawifi.controller;

import br.com.utfpr.avaliawifi.entity.Residencia;
import br.com.utfpr.avaliawifi.entity.Comodo;
import br.com.utfpr.avaliawifi.entity.Medicao;
import br.com.utfpr.avaliawifi.repository.ResidenciaRepository;
import br.com.utfpr.avaliawifi.repository.ComodoRepository;
import br.com.utfpr.avaliawifi.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

@Controller
public class HomeController {
    
    @Autowired
    private ResidenciaRepository residenciaRepository;
    
    @Autowired
    private ComodoRepository comodoRepository;
    
    @Autowired
    private MedicaoRepository medicaoRepository;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "AvaliaWiFi - Sistema de Avaliação de Rede WiFi");
        return "index";
    }
    
    @GetMapping("/residencias")
    public String residencias(Model model) {
        model.addAttribute("titulo", "Gerenciar Residências");
        model.addAttribute("residencias", residenciaRepository.findAll());
        model.addAttribute("novaResidencia", new Residencia());
        return "residencias";
    }
    
    @PostMapping("/residencias")
    public String salvarResidencia(@ModelAttribute Residencia residencia, RedirectAttributes redirectAttributes) {
        try {
            residenciaRepository.save(residencia);
            redirectAttributes.addFlashAttribute("sucesso", "Residência cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar residência: " + e.getMessage());
        }
        return "redirect:/residencias";
    }
    
    @PostMapping("/residencias/editar/{id}")
    public String editarResidencia(@PathVariable Long id, @ModelAttribute Residencia residencia, RedirectAttributes redirectAttributes) {
        try {
            residencia.setId(id);
            residenciaRepository.save(residencia);
            redirectAttributes.addFlashAttribute("sucesso", "Residência atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar residência: " + e.getMessage());
        }
        return "redirect:/residencias";
    }
    
    @GetMapping("/comodos")
    public String comodos(@RequestParam(required = false) Long residenciaId, Model model) {
        model.addAttribute("titulo", "Gerenciar Cômodos");
        
        List<Comodo> comodos;
        if (residenciaId != null) {
            comodos = comodoRepository.findByResidenciaId(residenciaId);
            model.addAttribute("residenciaSelecionada", residenciaId);
        } else {
            comodos = comodoRepository.findAll();
        }
        
        model.addAttribute("comodos", comodos);
        model.addAttribute("residencias", residenciaRepository.findAll());
        model.addAttribute("novoComodo", new Comodo());
        return "comodos";
    }
    
    @PostMapping("/comodos")
    public String salvarComodo(@ModelAttribute Comodo comodo, @RequestParam Long residenciaId, RedirectAttributes redirectAttributes) {
        try {
            Residencia residencia = residenciaRepository.findById(residenciaId).orElse(null);
            if (residencia != null) {
                comodo.setResidencia(residencia);
                comodoRepository.save(comodo);
                redirectAttributes.addFlashAttribute("sucesso", "Cômodo cadastrado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Residência não encontrada!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar cômodo: " + e.getMessage());
        }
        return "redirect:/comodos";
    }
    
    @PostMapping("/comodos/editar/{id}")
    public String editarComodo(@PathVariable Long id, @ModelAttribute Comodo comodo, @RequestParam Long residenciaId, RedirectAttributes redirectAttributes) {
        try {
            Residencia residencia = residenciaRepository.findById(residenciaId).orElse(null);
            if (residencia != null) {
                comodo.setId(id);
                comodo.setResidencia(residencia);
                comodoRepository.save(comodo);
                redirectAttributes.addFlashAttribute("sucesso", "Cômodo atualizado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Residência não encontrada!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar cômodo: " + e.getMessage());
        }
        return "redirect:/comodos";
    }
    
    @GetMapping("/medicoes")
    public String medicoes(@RequestParam(required = false) Long residenciaId, 
                          @RequestParam(required = false) Long comodoId, Model model) {
        model.addAttribute("titulo", "Realizar Medições");
        
        List<Medicao> medicoes;
        List<Comodo> comodos;
        
        if (comodoId != null) {
            // Filtrar por cômodo específico
            medicoes = medicaoRepository.findByComodoId(comodoId);
            comodos = comodoRepository.findAll();
            model.addAttribute("comodoSelecionado", comodoId);
            
            // Se há um cômodo selecionado, também precisamos da residência
            Comodo comodo = comodoRepository.findById(comodoId).orElse(null);
            if (comodo != null) {
                model.addAttribute("residenciaSelecionada", comodo.getResidencia().getId());
            }
        } else if (residenciaId != null) {
            // Filtrar por residência
            medicoes = medicaoRepository.findByResidenciaId(residenciaId);
            comodos = comodoRepository.findByResidenciaId(residenciaId);
            model.addAttribute("residenciaSelecionada", residenciaId);
        } else {
            // Mostrar todas as medições
            medicoes = medicaoRepository.findAll();
            comodos = comodoRepository.findAll();
        }
        
        model.addAttribute("medicoes", medicoes);
        model.addAttribute("residencias", residenciaRepository.findAll());
        model.addAttribute("comodos", comodos);
        model.addAttribute("novaMedicao", new Medicao());
        
        // Estatísticas do sinal (baseadas no filtro aplicado)
        long sinaisFortes = medicoes.stream().mapToLong(m -> m.getIntensidadeDbm() >= -50 ? 1 : 0).sum();
        long sinaisMedios = medicoes.stream().mapToLong(m -> (m.getIntensidadeDbm() < -50 && m.getIntensidadeDbm() >= -70) ? 1 : 0).sum();
        long sinaisFracos = medicoes.stream().mapToLong(m -> m.getIntensidadeDbm() < -70 ? 1 : 0).sum();
        
        // Calcular média das intensidades
        double mediaIntensidade = medicoes.isEmpty() ? 0.0 : 
            medicoes.stream().mapToDouble(Medicao::getIntensidadeDbm).average().orElse(0.0);
        
        model.addAttribute("sinaisFortes", sinaisFortes);
        model.addAttribute("sinaisMedios", sinaisMedios);
        model.addAttribute("sinalsFracos", sinaisFracos);
        model.addAttribute("mediaIntensidade", String.format("%.2f", mediaIntensidade));
        
        return "medicoes";
    }
    
    @PostMapping("/medicoes")
    public String salvarMedicao(@ModelAttribute Medicao medicao, @RequestParam Long residenciaId, 
                               @RequestParam Long comodoId, RedirectAttributes redirectAttributes) {
        try {
            Residencia residencia = residenciaRepository.findById(residenciaId).orElse(null);
            Comodo comodo = comodoRepository.findById(comodoId).orElse(null);
            
            if (residencia != null && comodo != null) {
                medicao.setResidencia(residencia);
                medicao.setComodo(comodo);
                medicao.setDataHora(LocalDateTime.now());
                medicaoRepository.save(medicao);
                redirectAttributes.addFlashAttribute("sucesso", "Medição registrada com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Residência ou cômodo não encontrado!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao registrar medição: " + e.getMessage());
        }
        return "redirect:/medicoes";
    }
    
    @GetMapping("/api/comodos/{residenciaId}")
    @ResponseBody
    public List<Comodo> getComodosByResidencia(@PathVariable Long residenciaId) {
        return comodoRepository.findByResidenciaId(residenciaId);
    }
    
    @GetMapping("/api/medicoes/estatisticas/{comodoId}")
    @ResponseBody
    public java.util.Map<String, Object> getEstatisticasByComodo(@PathVariable Long comodoId) {
        List<Medicao> medicoes = medicaoRepository.findByComodoId(comodoId);
        
        java.util.Map<String, Object> estatisticas = new java.util.HashMap<>();
        estatisticas.put("totalMedicoes", medicoes.size());
        
        if (!medicoes.isEmpty()) {
            double media = medicoes.stream().mapToDouble(Medicao::getIntensidadeDbm).average().orElse(0.0);
            long sinalForte = medicoes.stream().mapToLong(m -> m.getIntensidadeDbm() >= -50 ? 1 : 0).sum();
            long sinalMedio = medicoes.stream().mapToLong(m -> (m.getIntensidadeDbm() < -50 && m.getIntensidadeDbm() >= -70) ? 1 : 0).sum();
            long sinalFraco = medicoes.stream().mapToLong(m -> m.getIntensidadeDbm() < -70 ? 1 : 0).sum();
            
            estatisticas.put("mediaIntensidade", media);
            estatisticas.put("sinalForte", sinalForte);
            estatisticas.put("sinalMedio", sinalMedio);
            estatisticas.put("sinalFraco", sinalFraco);
        } else {
            estatisticas.put("mediaIntensidade", 0.0);
            estatisticas.put("sinalForte", 0);
            estatisticas.put("sinalMedio", 0);
            estatisticas.put("sinalFraco", 0);
        }
        
        return estatisticas;
    }
    
    @GetMapping("/api/medicoes/grafico")
    @ResponseBody
    public List<java.util.Map<String, Object>> getDadosGrafico(@RequestParam(required = false) Long comodoId) {
        List<Medicao> medicoes;
        
        // Se comodoId for fornecido, filtra por cômodo, senão retorna todas
        if (comodoId != null) {
            medicoes = medicaoRepository.findByComodoId(comodoId);
        } else {
            medicoes = medicaoRepository.findAll();
        }
        
        return medicoes.stream()
            .sorted((m1, m2) -> m1.getDataHora().compareTo(m2.getDataHora()))
            .map(medicao -> {
                java.util.Map<String, Object> ponto = new java.util.HashMap<>();
                ponto.put("dataHora", medicao.getDataHora().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                ponto.put("intensidade", medicao.getIntensidadeDbm());
                ponto.put("comodo", medicao.getComodo().getNome());
                return ponto;
            })
            .collect(java.util.stream.Collectors.toList());
    }
    
    @PostMapping("/medicoes/editar/{id}")
    public String editarMedicao(@PathVariable Long id, @ModelAttribute Medicao medicao, 
                               @RequestParam Long residenciaId, @RequestParam Long comodoId, 
                               RedirectAttributes redirectAttributes) {
        try {
            Residencia residencia = residenciaRepository.findById(residenciaId).orElse(null);
            Comodo comodo = comodoRepository.findById(comodoId).orElse(null);
            
            if (residencia != null && comodo != null) {
                medicao.setId(id);
                medicao.setResidencia(residencia);
                medicao.setComodo(comodo);
                medicao.setDataHora(LocalDateTime.now());
                medicaoRepository.save(medicao);
                redirectAttributes.addFlashAttribute("sucesso", "Medição atualizada com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Residência ou cômodo não encontrado!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar medição: " + e.getMessage());
        }
        return "redirect:/medicoes";
    }
    
    // Métodos de exclusão com confirmação
    @PostMapping("/residencias/deletar/{id}")
    public String deletarResidencia(@PathVariable Long id, 
                                  @RequestParam(required = false, defaultValue = "false") String confirmar,
                                  RedirectAttributes redirectAttributes) {
        try {
            Residencia residencia = residenciaRepository.findById(id).orElse(null);
            if (residencia == null) {
                redirectAttributes.addFlashAttribute("erro", "Residência não encontrada!");
                return "redirect:/residencias";
            }
            
            // Contar quantos registros filhos serão excluídos
            long comodos = comodoRepository.countByResidenciaId(id);
            long medicoes = medicaoRepository.countByResidenciaId(id);
            
            if (!"true".equals(confirmar)) {
                // Primeira chamada - solicitar confirmação
                String mensagem = String.format(
                    "ATENÇÃO: Ao excluir a residência '%s' serão também excluídos:\n" +
                    "• %d cômodo(s)\n" +
                    "• %d medição(ões)\n\n" +
                    "Esta ação NÃO PODE SER DESFEITA!\n\n" +
                    "Tem certeza que deseja continuar?",
                    residencia.getNome(), comodos, medicoes
                );
                redirectAttributes.addFlashAttribute("confirmacao", mensagem);
                redirectAttributes.addFlashAttribute("confirmarUrl", "/residencias/deletar/" + id + "?confirmar=true");
                return "redirect:/residencias";
            }
            
            // Confirmação recebida - prosseguir com exclusão
            residenciaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", 
                String.format("Residência '%s' e todos os dados relacionados (%d cômodos, %d medições) foram excluídos com sucesso!", 
                            residencia.getNome(), comodos, medicoes));
                            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir residência: " + e.getMessage());
        }
        return "redirect:/residencias";
    }
    
    @PostMapping("/comodos/deletar/{id}")
    public String deletarComodo(@PathVariable Long id, 
                               @RequestParam(required = false, defaultValue = "false") String confirmar,
                               RedirectAttributes redirectAttributes) {
        try {
            Comodo comodo = comodoRepository.findById(id).orElse(null);
            if (comodo == null) {
                redirectAttributes.addFlashAttribute("erro", "Cômodo não encontrado!");
                return "redirect:/comodos";
            }
            
            // Contar quantas medições serão excluídas
            long medicoes = medicaoRepository.countByComodoId(id);
            
            if (!"true".equals(confirmar)) {
                // Primeira chamada - solicitar confirmação
                String mensagem = String.format(
                    "ATENÇÃO: Ao excluir o cômodo '%s' serão também excluídas:\n" +
                    "• %d medição(ões)\n\n" +
                    "Esta ação NÃO PODE SER DESFEITA!\n\n" +
                    "Tem certeza que deseja continuar?",
                    comodo.getNome(), medicoes
                );
                redirectAttributes.addFlashAttribute("confirmacao", mensagem);
                redirectAttributes.addFlashAttribute("confirmarUrl", "/comodos/deletar/" + id + "?confirmar=true");
                return "redirect:/comodos";
            }
            
            // Confirmação recebida - prosseguir com exclusão
            comodoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", 
                String.format("Cômodo '%s' e %d medição(ões) foram excluídos com sucesso!", 
                            comodo.getNome(), medicoes));
                            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir cômodo: " + e.getMessage());
        }
        return "redirect:/comodos";
    }
    
    @PostMapping("/medicoes/deletar/{id}")
    public String deletarMedicao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            medicaoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", "Medição excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir medição: " + e.getMessage());
        }
        return "redirect:/medicoes";
    }
}