## Sobre
A aplicação "jwt" é uma solução Micronaut para manipulação e validação de tokens JWT.

### Requisitos para Execução
- JDK 17
- Maven


### VM Options:

``-Dmicronaut.environments=local``
### Para executar os testes de mutação utilzando PiTest e gerar relatórios de mutação, utilize o comando:

``
./mvnw org.pitest:pitest-maven:mutationCoverage
``

### Deploy Automatizado
O deploy é automatizado através do GitHub Actions, onde cada commit na branch master desencadeia um novo deploy no AWS EKS.

### Logs
Todos os erros são registrados automaticamente no Splunk, permitindo um monitoramento eficiente e uma rápida resposta a incidentes.

### Open API e Swagger
A aplicação oferece uma documentação a partir de openapi 3.0, acessível em /src/main/resources/swagger.yaml

### Configurações Personalizadas
``
JwtValidatorService.java
``
- Permite configurar as validações JWT. São elas:
    - O número máximo de caracteres para a claim "Name" (MAX_CHARACTERS_FOR_CLAIM_NAME).
    - Claims obrigatórias (REQUIRED_CLAIMS).

``
RoleEnum.java
``
- Configura se a claim "Role" do JWT é case sensitive (IS_CLAIM_ROLE_CASE_SENSITIVE).

### Problemas de autoscaling
Quando implantada, a aplicação é containerizada usando Docker e executada no AWS EKS em uma instância t3.micro (FREE), por conta disso, 
ela possui limite de 2 IP's limitações de IPs privados e é por isso que a configuração de réplicas no Kubernetes é 1, pois quando há 2 pods 
rodando e tenta-se fazer um deploy, não há nenhum IP disponível para a nova instância  



### Ferramentas utilizadas
- GitHub: https://github.com/LucasLimaL/jwt
- Trello: https://trello.com/b/fFY6WmJM/projeto-jwt-validator-itau
- Postman: collection está em /docs/jwt.postman_collection
- Java 17
- Micronaut
- openapi 3.0
- PiTest
- Splunk
- Docker
- Kubernetes
- k9s
- AWS EKS
- AWS ECR
- eksctl