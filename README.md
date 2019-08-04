#Inter


///////////////////Subir o Projeto///////////////                                                                                                  
Instalar -> mvn clean install                                                                            
Rodar os testes(Junit) -> mvn clean test                                                                       
Rodar a aplicação -> mvn spring-boot:run                                                                                         
URL : http://localhost:8080/swagger-ui.html                                                                                


///////////////////Banco H2//////////////////////                                                                
URL Console H2 : http://localhost:8080/h2-console                                                                
Usuario H2 : adm                                                                        
Senha : adm                                                                                             
JDBC URL : jdbc:h2:mem:testdb                                                                                           


///////////////////INFOs/////////////////////////
-> Em /resources tem um arquivo chamado data.sql com 2 inserts para testes.
-> Caso você execute o postman_collection.json 2x seguidas a segunda vai dar erro , pelas as mudanças nos inserts da primeira execução.
-> As ações nos EndPoints são pelo ID Usuario o mesmo retorna no http://localhost:8080/usuario/listar-todos.
-> Criei um EndPoint para gerar uma public key, as keys são geradas na pasta temp.  

#MeContrata