package com.medpro.medpro.domain.validacoes.agendamento;

import com.medpro.medpro.infra.exception.ValidacaoException;
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var paciente = repository.getReferenceById(dados.idPaciente());
        // Assumindo que o atributo 'ativo' já existe na entidade Paciente (conforme migration V6)
        if (!paciente.getAtivo()) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}