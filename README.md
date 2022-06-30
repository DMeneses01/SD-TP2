# Sistemas Distribuídos - TP2
Trabalho 02 de Sistemas Distribuídos - LEI 2021/2022

##### Instruções para correr o projeto #####

Antes de mais, devem criar uma base de dados local para correr o nosso projeto. Recomendamos o uso do pgAdmin, visto ter sido essa a plataforma por nós utilizada. 
Essa base de dados deve ter o nome "Project_SD" num servidor chamado "PostgreSQL 13". Esse servidor deve estar conectado ao porto 5432, sendo o seu utilizador intitulado de "postgres" com a password "postgres".

Qualquer alteração efetuada a estes parâmetros deve ser igualmente alterada no ficheiro "application.properties" que está em: "Projeto2_v2\code\demoJPA+webservices\src\main\resources" nos parâmetros:

- spring.datasource.url=jdbc:postgresql://localhost:5432/Project_SD
- spring.datasource.username=postgres
- spring.datasource.password=postgres

Se se pretender criar as tabelas, o parâmetro no ficheiro já falado acima "application.properties", "spring.jpa.hibernate.ddl-auto", deve estar igualado a "create". Caso contrário deve estar como "update" para não se perder a informação armazenada na base de dados.

Desenvolvemos este projeto no IDE Intellij pelo que recomendamos que, para o compilar e executar, importem a pasta "code" fornecida exatamente como está para esse IDE. Assim, caso seja preciso instalar alguma dependência, o IDE fá-lo automaticamente e podem compilar e executar à vontade.

Na maioria das vezes, o Intellij deteta logo a main e fica pronto a correr. Caso isso não aconteça, devem ir a "demoJPA+webservices\src\main\java\com.example\demo\DemoApplication" e o Intellij reconhecerá como main.


##### Autores #####
  - Duarte Meneses 
  - Patrícia Costa 
