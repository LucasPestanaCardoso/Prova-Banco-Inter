**Subir o Projeto**

Instalar(Raiz do Projeto) -> mvn clean install                                                                            
Rodar os testes(Junit) -> mvn clean test                                                                       
Rodar a aplicação -> mvn spring-boot:run                                                                                         
URL : http://localhost:8080/swagger-ui.html                                                                                


**Banco H2**

URL Console H2 : http://localhost:8080/h2-console                                                                
Usuario H2 : adm                                                                        
Senha : adm                                                                                             
JDBC URL : jdbc:h2:mem:testdb    

**Criptografia**

- A chave publica e passada para  `http://localhost:8080/cripto/criptografar` onde retorna os dados do Usuario Criptografados

- O após isso o método `http://localhost:8080/cripto/descriptografar?textoCriptografado?idUsuario`  retorna os dados Descriptografados


**Info's**

- Em /resources tem um arquivo chamado data.sql com 2 inserts para testes.                                                 
- Caso você execute o postman_collection.json 2x seguidas a segunda vai dar erro , pelas as mudanças nos inserts da primeira execução.                   
- As ações nos EndPoints são pelo ID Usuario o mesmo retorna pelo swagger no listar ou direto na url em `http://localhost:8080/usuario/listar`.          

 Qualquer erro que a aplicação apresentar, pode entrar em contato lucaspestanaa@gmail.com

Obrigado !


