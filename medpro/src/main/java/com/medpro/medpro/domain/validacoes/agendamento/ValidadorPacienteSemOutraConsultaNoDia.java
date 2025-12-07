package com.medpro.medpro.domain.validacoes.agendamento;

import com.medpro.medpro.infra.exception.ValidacaoException;
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        
        // verifica explicitamente se existe consulta NÃO CANCELADA (IsNull)
        var pacientePossuiOutraConsultaAtivaNoDia = repository.existsByPacienteIdAndDataBetweenAndMotivoCancelamentoIsNull(
            dados.idPaciente(), 
            primeiroHorario, 
            ultimoHorario
        );

        if (pacientePossuiOutraConsultaAtivaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}