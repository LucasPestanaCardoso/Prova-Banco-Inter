DROP TABLE IF EXISTS tb_usuario;

DROP TABLE IF EXISTS tb_digitos;


CREATE TABLE tb_usuario(
  
usu_id INT AUTO_INCREMENT  PRIMARY KEY,
 
usu_nome VARCHAR(250) NOT NULL,

usu_email VARCHAR(250) NOT NULL,
usu_private_key VARCHAR(3000) NULL,
usu_public_key VARCHAR(3000) NULL
);


CREATE TABLE tb_digitos(

digi_id INT AUTO_INCREMENT  PRIMARY KEY,
  
digi_entrada_k INT NOT NULL,
  
digi_entrada_n INT NOT NULL, 
  
digi_resultado INT NOT NULL,
  
usu_id INT,
  
foreign key (usu_id) references tb_usuario(usu_id)

);




insert into tb_usuario (usu_id , usu_nome , usu_email) values (1 ,'Lucas' , 'lucaspestanaa@gmail.com');

insert into tb_digitos (usu_id ,digi_entrada_n , digi_entrada_k , digi_resultado) values ( 1 ,9875 , 4 , 6);


insert into tb_usuario (usu_id , usu_nome , usu_email , usu_private_key, usu_public_key) 
values (2 ,'Arthur' , 'arthur@gmail.com' , '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' );

