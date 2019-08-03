DROP TABLE IF EXISTS tb_usuario;

DROP TABLE IF EXISTS tb_digitos;


CREATE TABLE tb_usuario(
  usu_id INT AUTO_INCREMENT  PRIMARY KEY,
  usu_nome VARCHAR(250) NOT NULL,
  usu_email VARCHAR(250) NOT NULL
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
