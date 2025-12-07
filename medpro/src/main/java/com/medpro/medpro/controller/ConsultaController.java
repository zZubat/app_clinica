package com.medpro.medpro.controller;

import com.medpro.medpro.domain.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import com.medpro.medpro.infra.exception.ValidacaoException;
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.model.dto.DadosCancelamentoConsulta;
import com.medpro.medpro.model.dto.DadosDetalhamentoConsulta;
import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.repository.ConsultaRepository;
import com.medpro.medpro.repository.MedicoRepository;
import com.medpro.medpro.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Spring injeta automaticamente todas as classes que implementam a interface
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        // Validação de existência (404 Not Found)
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            return ResponseEntity.notFound().build();
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            return ResponseEntity.notFound().build();
        }

        // Executar regras de negócio (Validações) - Lança 400 se falhar (via TratadorDeErros)
        validadores.forEach(v -> v.validar(dados));

        // Lógica de escolha de médico
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(Pageable paginacao) {
        // Define "Hoje" como o início do dia atual (00:00)
        // Isso garante que consultas de hoje (mesmo que cedo) apareçam, mas de ontem não.
        var dataAtual = LocalDateTime.now().with(LocalTime.MIN);

        // Busca apenas consultas ativas E a partir de hoje
        var page = consultaRepository.findAllByMotivoCancelamentoIsNullAndDataGreaterThanEqual(paginacao, dataAtual)
                .map(DadosDetalhamentoConsulta::new);
        
        return ResponseEntity.ok(page);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            return ResponseEntity.notFound().build();
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());

        return ResponseEntity.noContent().build();
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}