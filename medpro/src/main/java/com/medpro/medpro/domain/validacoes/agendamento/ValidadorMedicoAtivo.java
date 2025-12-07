package com.medpro.medpro.domain.validacoes.agendamento;

import com.medpro.medpro.infra.exception.ValidacaoException;
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Escolha opcional de médico
        if (dados.idMedico() == null) {
            return;
        }

        var medico = repository.getReferenceById(dados.idMedico());
        if (!medico.getAtivo()) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}