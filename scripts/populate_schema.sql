/* Insert into País */
insert into País (nomePaís, letraHino, continente)
  values ('Alemanha', 'Alemanha, Alemanha acima de tudo
    Acima de tudo no mundo
    Quando sempre, na defesa e resistência
    Fica unida fraternalmente
    Do Maas ao Memel
    Do Etsch ao Belt
    
    Alemanha, Alemanha acima de tudo
    Acima de tudo no mundo
    Alemanha, Alemanha acima de tudo
    Acima de tudo no mundo
    
    Mulheres alemãs, fidelidade alemã
    Vinho alemão e canto alemão
    Devem manter no mundo
    O seu velho e belo som
    Inspira-nos para ato nobre
    Durante toda a nossa vida
    
    Mulheres alemãs, fidelidade alemã
    Vinho alemão e canto alemão
    Mulheres alemãs, fidelidade alemã
    Vinho alemão e canto alemão
    
    Unidade e justiça e liberdade
    Para a pátria alemã
    Zelaremos todos para isso
    Fraternalmente com coração e mão!
    Unidade e justiça e liberdade
    São a garantia da felicidade
    
    Floresce no brilho da tua felicidade
    Floresce a pátria alemã!
    Floresce no brilho da tua felicidade
    Floresce a pátria alemã!','Europa');
    
insert into País (nomePaís, letraHino, continente)
  values('Brasil','I
    
    Ouviram do Ipiranga as margens plácidas
    De um povo heroico o brado retumbante
    E o sol da liberdade, em raios fúlgidos
    Brilhou no céu da pátria nesse instante
    
    Se o penhor dessa igualdade
    Conseguimos conquistar com braço forte
    Em teu seio, ó liberdade
    Desafia o nosso peito a própria morte!
    
    Ó pátria amada
    Idolatrada
    Salve! Salve!
    
    Brasil, um sonho intenso, um raio vívido
    De amor e de esperança à terra desce
    Se em teu formoso céu, risonho e límpido
    A imagem do cruzeiro resplandece
    
    Gigante pela própria natureza
    És belo, és forte, impávido colosso
    E o teu futuro espelha essa grandeza
    
    Terra adorada
    Entre outras mil
    És tu, Brasil
    Ó pátria amada!
    Dos filhos deste solo és mãe gentil
    Pátria amada
    Brasil!
    
    II
    
    Deitado eternamente em berço esplêndido
    Ao som do mar e à luz do céu profundo
    Fulguras, ó Brasil, florão da América
    Iluminado ao sol do novo mundo!
    
    Do que a terra mais garrida
    Teus risonhos, lindos campos têm mais flores
    "Nossos bosques têm mais vida"
    "Nossa vida" no teu seio "mais amores"
    
    Ó pátria amada
    Idolatrada
    Salve! Salve!
    
    Brasil, de amor eterno seja símbolo
    O lábaro que ostentas estrelado
    E diga o verde-louro dessa flâmula
    Paz no futuro e glória no passado
    
    Mas, se ergues da justiça a clava forte
    Verás que um filho teu não foge à luta
    Nem teme, quem te adora, a própria morte
    
    Terra adorada
    Entre outras mil
    És tu, Brasil
    Ó pátria amada!
    
    Dos filhos deste solo és mãe gentil
    Pátria amada
    Brasil!', 'América do Sul');
    
insert into País (nomePaís, letraHino, continente)
  values ('Canadá', 'Ó, Canadá!
    Nossa casa e terra nativa!
    Verdadeiro amor patriota, em vossos filhos comanda
    
    Com corações em brilho nós vemos vós ascender
    O Norte Verdadeiro forte e livre!
    
    De longe e largo, ó, Canada
    Ó, Canadá, nós ficamos de guarda para vós!
    
    Deus guarde nossa terra gloriosa e livre!
    Ó, Canadá, nós ficamos de guarda para vós!
    Ó, Canadá, nós ficamos de guarda para vós!', 'América do Norte');
    
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
    
/* Insert into Pertence  */
insert into Pertence
  values ('Alemanha', 'BB212121', 1);
  
insert into Pertence
  values('Canadá', 'CC323232', 1);

insert into Pertence
  values ('Brasil', 'AA121212', 1);

/* Insert into Atleta */
insert into Atleta 
  values ('AA121212', 68.0, 1.68);
  
insert into Atleta 
  values ('BB212121', 70.2, 1.70);
  
insert into Atleta 
  values ('CC323232', 65.0, 1.75);
 
/* Insert into Árbitro */ 
insert into Árbitro
  values ('AA121212');

insert into Árbitro
  values ('BB212121');
  
insert into Árbitro
  values ('CC323232');
  
/* Insert into Esporte */
insert into Esporte
  values ('Natação', 'T');

insert into Esporte
  values ('Atletismo', 'T');
  
insert into Esporte
  values ('Tiro ao Alvo', 'P');
  
/* Insert into Habilitado */
insert into Habilitado
  values ('AA121212', 'Natação');
  
insert into Habilitado
  values ('BB212121', 'Atletismo');
  
insert into Habilitado
  values ('CC323232', 'Tiro ao Alvo');
  
/* Insert into Modalidade */
insert into Modalidade
  values ('200M Livre Masculino', 'Grupo', 'Natação', 10);

insert into Modalidade
  values ('200M Rasos Feminino', 'Grupo', 'Atletismo', 6);

insert into Modalidade
  values ('3000M Obstáculos Masculino', 'Grupo', 'Atletismo', 6);
  
/* Insert into Equipe */
insert into Equipe
  values (12121, 'Alemanha', '200M Rasos Feminino', 'Grupo', 8, 00001);
  
insert into Equipe
  values (21212, 'Canadá', '3000M Obstáculos Masculino', 'Grupo', 8, 00002);
  
insert into Equipe
  values (32323, 'Brasil', '200M Livre Masculino', 'Grupo', 8, 00003);
  
/* Insert into Representa */
insert into Representa
  values ('200M Rasos Feminino', 'Grupo', 'Alemanha', 00001);
  
insert into Representa
  values ('3000M Obstáculos Masculino', 'Grupo', 'Canadá', 00002);
  
insert into Representa
  values ('200M Livre Masculino', 'Grupo', 'Brasil', 00003);

/* Insert into Integra */
insert into Integra
  values ('AA121212', 00001);
  
insert into Integra
  values ('BB212121', 00002);
  
insert into Integra
  values ('CC323232', 00003);

/* Insert into Complexo Desportivo */
insert into ComplexoDesportivo
  values ('Ninho de Pássaro', 'Rua Osaka 123', 100000);
  
insert into ComplexoDesportivo
  values ('Capacete de Ciclista', 'Rua Hiroshima 456', 50000);
  
insert into ComplexoDesportivo
  values ('Templo dos Heróis', 'Rua Nagasaki 95', 60000);
  
/* Insert into Competição */
insert into Competição
  values ('Oitavas', 01, '200M Rasos Feminino', 'Grupo', 'Ninho de Pássaro', to_date('17-12-2016', 'DD-MM-YYYY'),
    'Competição de corrida feminina de 200m rasos', 'Rua Osaka 123', 'A', 00001);
    
insert into Competição
  values ('Oitavas', 02, '3000M Obstáculos Masculino', 'Grupo', 'Capacete de Ciclista', to_date('17-12-2016', 'DD-MM-YYYY'),
    'Competição de natação feminina de 200m rasos', 'Rua Hiroshima 456', 'A', 00002);
    
insert into Competição
  values ('Oitavas', 03, '200M Livre Masculino', 'Grupo', 'Templo dos Heróis', to_date('17-12-2016', 'DD-MM-YYYY'),
    'Competição de corrida masculina de 200m rasos', 'Rua Nagasaki 95', 'A', 00003);
    
/* Insert into Suporta */
insert into Suporta
  values ('Natação', 'Ninho de Pássaro');
  
insert into Suporta
  values ('Atletismo', 'Templo dos Heróis');

insert into Suporta
  values ('Tiro ao Alvo', 'Capacete de Ciclista');
  
/* Insert into Arbitra */
insert into Arbitra
  values ('AA121212', 00001, 'Árbitro');
  
insert into Arbitra
  values ('BB212121', 00002, 'Árbitro');
  
insert into Arbitra
  values ('CC323232', 00003, 'Árbitro');
  
/* Insert into Participa */
insert into Participa
  values (00001, 00001, '10.00', 1);

insert into Participa
  values (00001, 00002, '10.00', 1);

insert into Participa
  values (00001, 00003, '10.00', 1);

insert into Participa
  values (00002, 00002, '10.00', 1);
  
insert into Participa
  values (00003, 00003, '10.00', 1);

