package br.com.votesystem.services.interfaces;

import br.com.votesystem.util.Action;

public interface ISendMessage {

    <T> void send(final Action<T> value) throws Exception;
}
