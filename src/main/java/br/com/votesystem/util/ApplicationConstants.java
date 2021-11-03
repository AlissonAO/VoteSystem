package br.com.votesystem.util;

public class ApplicationConstants {

    public static final String ENDPOINT_ROOT_V1 = "/api/v1";
    public static final String ENDPOINT_ASSOCIADO_V1 = ENDPOINT_ROOT_V1 + "/associado";
    public static final String ENDPOINT_VOTO_ATA_V1 = ENDPOINT_ROOT_V1 + "/votoata";
    public static final String ENDPOINT_VOTO_SESSAO_V1 = ENDPOINT_ROOT_V1 + "/sessao";
    public static final String ENDPOINT_VOTO_V1 = ENDPOINT_ROOT_V1 + "/voto";

    public static final  String TOPIC_ASSOCIADO= "votacao-associado";
    public static final  String TOPIC_VOTO_ATA = "votacao-minute";
    public static final  String TOPIC_VOTO_SESSAO = "votacao-poll";
    public static final  String TOPIC_VOTE = "votacao-vote";
    public static final  String GROUP_ID = "votacao";

}

