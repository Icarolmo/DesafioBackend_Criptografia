# DesafioBackend_Criptografia.
Este projeto foi feito para estudo e aperfeiçoamento das ferramentas Java, Spring e Criptografia de dados sensíveis. A ideia partiu como uma proposta de solução ao Desafio de Criptografia do repositório do Backend Brasil.

## Sobre o desafio.
Dado uma entidade que contém campos sensíveis deve ser implementado algum tipo de criptografia para proteção dos campos. Também, a criptografia deve ser transparente para as camadas de serviço da aplicação, ou seja, para as resquisições nas nossas API os campos devem ser entregues descriptografados e persistidos criptografados em nosso banco de dados. 
O desafio com maior detalhes você pode encontrar no reposítorio do [Backend Brasil](https://github.com/backend-br/desafios).

## Sobre o projeto.
Neste projeto foi criado uma aplicação de CRUD simples em Java/Spring seguindo o Padrão MVC (Model View Controller) com o desafio de criptografia de dados sensíveis, foi utilizado PostgreSQL como banco de dados e para criptografia é utilizado na aplicação o algoritmo AES existente na biblioteca do spring.security.

### Teste das API's, rotas, criptografia e persistência dos dados no banco de dados:


- Criação da entidade em "transfer/create":

![response_post-postman](https://github.com/Icarolmo/DesafioBackend_Criptografia/assets/72323389/fb082fd2-f861-495f-9759-ac2977290b8f)


- Entidade criada persistida no banco de dados com campos sensíveis criptografados:
  
![data_postgresql](https://github.com/Icarolmo/DesafioBackend_Criptografia/assets/72323389/367bb876-bf60-447e-92b6-1842ab67f9d8)



- Requisição de consulta da entidade no banco de dados, entidade retornada com campos sensíveis descriptografados:

![response_get-postaman](https://github.com/Icarolmo/DesafioBackend_Criptografia/assets/72323389/2a9e8dab-7634-4929-8375-f5ed0f10fd22)
