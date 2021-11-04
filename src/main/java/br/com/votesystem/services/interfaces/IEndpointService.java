package br.com.votesystem.services.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface IEndpointService {
    String process( String endpoint, String id,  HttpServletRequest request);
}
