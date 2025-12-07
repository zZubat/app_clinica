package com.medpro.medpro.repository;

import com.medpro.medpro.model.entity.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Validação de Médico: verifica se está ativo (não cancelado)
    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

    // Validação de Paciente: ATUALIZADO para ignorar canceladas
    // checa se existe alguma ATIVA no intervalo.
    boolean existsByPacienteIdAndDataBetweenAndMotivoCancelamentoIsNull(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    // Listagem: Busca ativas (sem motivo) E que sejam futuras (data >= hoje)
    Page<Consulta> findAllByMotivoCancelamentoIsNullAndDataGreaterThanEqual(Pageable paginacao, LocalDateTime data);
}