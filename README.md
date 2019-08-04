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
-> As ações nos EndPoints são pelo ID Usuario o mesmo retorna pelo swagger no listar-todos ou direto na url em http://localhost:8080/usuario/listar-todos.
                                                                                                    
-> Como que tinha que ter um EndPoint que recebe uma PublicKey eu criei um EndPoint para gerar uma public key, e outras para criptografar e descriptografar.                                      
-> Eu salvei as Keys na pasta temp mesmo, achei mais pratico para o teste.                                                         

-> Tecnicamente fiz tudo o que foi pedido, qualquer erro que a aplicação apresentar, pode entrar em contato lucaspestanaa@gmail.com


#MeContrata