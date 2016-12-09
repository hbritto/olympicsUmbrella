/* Insert into Participante */
insert into Participante
  values ('AA121212', '1', 'Guilherme Momesso',
    to_date('01-09-1995', 'DD-MM-YYYY'), 'M');
  
insert into Participante
  values ('BB212121', '2', 'Henrique Bonini', 
    to_date('05-04-1996', 'DD-MM-YYYY'), 'M');
  
insert into Participante
  values ('CC323232', '3', 'Gustavo Lima',
    to_date('09-10-1996', 'DD-MM-YYYY'), 'M');

/* Insert into Atleta */
insert into Atleta 
  values ('AA121212', 70.5, 1.68);
  
insert into Atleta 
  values ('BB212121', 78.2, 1.70);
  
insert into Atleta 
  values ('CC323232', 65.0, 1.75);
 
/* Insert into Árbitro */ 
insert into Árbitro
  values ('AA121212');

insert into Árbitro
  values ('BB21212');
  
insert into Árbitro
  values ('CC323232');
  
/* Insert into Esporte */
insert into Esporte
  values ('Natação', 'T');

insert into Esporte
  values ('Atletismo', 'T');
  
insert into Esporte
  values ('Tiro ao Alvo', 'P');
  
/* Insert into Modalidade */
insert into Modalidade
  values ('200M Livre Masculino', 'Grupo', 'Natação', 10);

insert into Modalidade
  values ('200M Rasos Feminino', 'Grupo', 'Atletismo', 6);

insert into Modalidade
  values ('3000M Obstáculos Masculino', 'Grupo', 'Atletismo', 6);

/* Insert into Complexo Desportivo */
insert into ComplexoDesportivo
  values ('Ninho de Pássaro', 'Rua Osaka 123', 100000);
  
insert into ComplexoDesportivo
  values ('Capacete de Ciclista', 'Rua Hiroshima 456', 50000);
  
insert into ComplexoDesportivo
  values ('Templo dos Heróis', 'Rua Nagasaki 95', 60000);
  
/* Insert into Competição */

