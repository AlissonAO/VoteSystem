package br.com.votesystem.services.interfaces;

import br.com.votesystem.enuns.CpfState;

public interface ICpfValidatorService {

    CpfState checkCpf(final String identification);

}
