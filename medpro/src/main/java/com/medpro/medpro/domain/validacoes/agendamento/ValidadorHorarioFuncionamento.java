package com.medpro.medpro.domain.validacoes.agendamento;

import com.medpro.medpro.infra.exception.ValidacaoException; // Crie essa RuntimeException
import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = dataConsulta.getHour() < 7;
        var depoisDoFechamento = dataConsulta.getHour() > 18; // Encerra as 19h, ultima consulta as 18h

        if (domingo || antesDaAbertura || depoisDoFechamento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento");
        }
    }
}