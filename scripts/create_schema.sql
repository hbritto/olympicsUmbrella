create table país (
  nomePaís varchar2(50),
  letraHino varchar2(2000),
  continente varchar2(30),
  bandeira blob,
  constraint PK_país primary key (nomePaís)
);

create table participante (
  passaporte varchar2(13),
  inCode varchar2(10),
  nome varchar2(60),
  dtNasc date,
  sexo char(1),
  constraint PK_participante primary key (passaporte)
);

create table pertence (
  nomePaís varchar2(50),
  passaporte varchar2(13),
  representa number(1),
  constraint FK_pertencePaís foreign key (nomePaís)
    references país (nomePaís) on delete cascade,
  constraint FK_pertenceParticipante foreign key (passaporte)
    references participante (passaporte) on delete cascade,
  constraint PK_pertence primary key (nomePaís, passaporte)
);

create table atleta (
  passaporte varchar2(13),
  peso number(4, 1),
  altura number(3, 2),
  constraint FK_atletaParticipante foreign key (passaporte)
    references participante (passaporte) on delete cascade,
  constraint PK_atleta primary key (passaporte)
);

create table árbitro (
  passaporte varchar2(13),
  constraint FK_arbitroParticipante foreign key (passaporte)
    references participante (passaporte) on delete cascade,
  constraint PK_árbitro primary key (passaporte)
);

create table arbitra (
  passaporte varchar2(13),
  idCmp number(5),
  papel varchar2(20),
  constraint FK_arbitraÁrbitro foreign key (passaporte)
    references árbitro (passaporte) on delete cascade,
  constraint FK_arbitraCompetição foreign key (idCmp)
    references competição (idCmp) on delete cascade,
  constraint PK_arbitra primary key (passaporte, idCmp)
);

create table habilitado (
  passaporte varchar2(13),
  nomeEsporte varchar2(30),
  constraint FK_habilitadoÁrbitro foreign key (passaporte)
    references árbitro (passaporte) on delete cascade,
  constraint FK_habilitadoEsporte foreign key (nomeEsporte)
    references esporte (nomeEsporte) on delete cascade,
  constraint PK_habilitado primary key (passaporte, nomeEsporte)
);

create table esporte (
  nomeEsporte varchar2(30),
  unidPontuação char(1),
  constraint PK_esporte primary key (nomeEsporte)
);

create table representa (
  nomeMod varchar2(50),
  catMod varchar2(40),
  nomePaís varchar2(50),
  idEquipe number(5),
  constraint FK_representaModalidade foreign key (nomeMod, catMod)
    references modalidade (nomeMod, catMod) on delete cascade,
  constraint FK_representaPaís foreign key (nomePaís)
    references país (nomePaís) on delete cascade,
  constraint FK_representaEquipe foreign key (idEquipe)
    references equipe (idEquipe) on delete cascade,
  constraint PK_representa primary key (nomeMod, catMod, nomePaís, idEquipe)
);

create table modalidade (
  nomeMod varchar2(50),
  catMod varchar2(40),
  nomeEsporte varchar2(30),
  maxAtletas number(2),
  constraint FK_modalidadeEsporte foreign key (nomeEsporte)
    references esporte (nomeEsporte) on delete cascade,
  constraint PK_modalidade primary key (nomeMod, catMod)
);

create table equipe (
  numEquipe number(5),
  nomePaís varchar2(50),
  nomeMod varchar2(50),
  catMod varchar2(40),
  quantAtl number(2),
  idEquipe number(5),
  constraint FK_equipePaís foreign key (nomePaís)
    references país (nomePaís) on delete cascade,
  constraint FK_equipeModalidade foreign key (nomeMod, catMod)
    references modalidade (nomeMod, catMod) on delete cascade,
  constraint PK_equipe primary key (idEquipe)
);

create table integra (
  idAtleta varchar2(13),
  idEquipe number(5),
  constraint FK_integraAtleta foreign key (idAtleta)
    references atleta (passaporte) on delete cascade,
  constraint FK_integraEquipe foreign key (idEquipe)
    references equipe (idEquipe) on delete cascade,
  constraint PK_integra primary key (idAtleta, idEquipe)
);

create table participa (
  idEquipe number(5),
  idCmp number(5),
  tempoPontos varchar2(15),
  classificadoVencedor number(1),
  constraint FK_participaEquipe foreign key (idEquipe)
    references equipe (idEquipe) on delete cascade,
  constraint FK_participaCompetição foreign key (idCmp)
    references competição (idCmp) on delete cascade,
  constraint PK_participa primary key (idEquipe, idCmp)
);

create table competição (
  faseCmp varchar2(20),
  numCmp number(2),
  nomeMod varchar2(50),
  catMod varchar2(40),
  nomeCD varchar2(40),
  diaHorário date,
  obs varchar2(500),
  localização varchar2(100),
  grupo char(1),
  idCmp number(5),
  constraint FK_competiçãoModalidade foreign key (nomeMod, catMod)
    references modalidade (nomeMod, catMod) on delete cascade,
  constraint FK_competiçãoCD foreign key (nomeCD)
    references complexoDesportivo (nomeCD) on delete cascade,
  constraint PK_competição primary key (idCmp)
);

create table complexoDesportivo (
  nomeCD varchar2(40),
  endereçoCD varchar2(100),
  capPúblico number(6),
  constraint PK_CD primary key (nomeCD)
);

create table suporta (
  nomeEsporte varchar2(30),
  nomeCD varchar2(40),
  constraint FK_suportaEsporte foreign key (nomeEsporte)
    references esporte (nomeEsporte),
  constraint FK_suportaCD foreign key (nomeCD)
    references complexoDesportivo (nomeCD),
  constraint PK_suporta primary key (nomeEsporte, nomeCD)
);
  
  