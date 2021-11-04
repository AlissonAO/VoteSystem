# VoteSystem
Desafio - Sistema de votação 
 # API para gerenciar as sessões de votação das assembleia
 ## Tech
 - Java 8;
 - Docker/docker-compose;
 - MySQL;
 - Apache Kafka;
 - Swagger
 
O Desafio foi desenvolvido em Java 8 usando Spring, o banco MySQL e o stream(Apache Kafka) com Docker 

A persistência foi realizada com JPA utilizando MySQL como banco de dados.

Os repositórios foram criados as interfaces extendendo JpaRepository como no padrão do Spring Data.

(Tarefa 1).
Foi criado um serviço para verificar o CPF do associado 

(Tarefa 2 e 3):
Endpoint recebe a requisição e envia para o  respectivo serviço;

E feita uma validação no serviços, e caso não haja erro é criada uma mensagem, contendo os valores e ação a ser realizada, enviando para o Kafka;

No consumer, é realizado novamente as validações, se caso não lançar exceção é persistido no banco de dados;

Os Endpoint recebem uma uri contendo o caminho para recuperar a entidade alterada;

(Tarefa 3)
Os endpoints com métodos são assíncronos, retornando o status 200 

(Tarefa 3)
Ao ultilizamos stream(Kafka), é possível escalonar facilmente a aplicação, já que o consumer tem o mesmo GROUP ID é feito um balanceamento no tópico.
O projeto foi desenvolvido para utilizar contêiners, permitindo um escalonamento horizontal.

(Tarefa 4)
Os serviços estão usando /api/v1/{endpoint} trocando o v1 para novas versões ex: v2. 

Foram criados testes unitários.

# Executando o projeto


Utilizando IDE

Execute o comando na pasta do projeto - docker-compose -f docker-compose-dependencies.yml up

O script levantará o MySQL e Apache Kafka

Abra o projeto na IDE de preferência e execulte o serviço "VoteSystem"

Para os EndPoits acesse o caminho http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/

- Associado /api/v1/associado/
- Ata /api/v1/votoata/
- Voto /api/v1/voto/
- Sessao /api/v1/sessao/



