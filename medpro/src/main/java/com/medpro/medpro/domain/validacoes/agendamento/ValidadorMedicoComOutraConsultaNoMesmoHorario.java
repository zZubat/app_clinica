package com.medpro.medpro.domain.validacoes.agendamento;

import com.medpro.medpro.infra.exception.ValidacaoException;
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }
        
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}