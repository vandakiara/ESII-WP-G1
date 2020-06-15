# Engenharia de Software II - Grupo 1

## Setup

Inicialmente, é necessário carregar as imagens das aplicações Java, que se encontram compactadas no diretório `docker-images/`. Para isso, deverá correr os seguintes comandos, a partir da raiz do projeto:

```sh
docker load -i docker-images/covid-evolution-diff.zip
docker load -i docker-images/covid-graph-spread.zip
docker load -i docker-images/covid-sci-discoveries.zip
docker load -i docker-images/covid-query.zip
```

## Start

Para correr o projeto, correr o seguinte comando a partir da raiz do projeto:

```sh
docker-compose up -d
```

E agora, poderá aceder a aplicação em [http://localhost:8080](http://localhost:8080)

Para iniciar sessão no Wordpress pode criar uma conta ou iniciar com os perfis já criados:

Administrator
User: Administrador
Pass: Administrador

Member
User: Member
Pass: Member

## Email do admin

email: es2wpg1@gmail.com
pass: iscte2020

## Membros do Grupo e Requisitos

| N° Aluno | Nome             | Requisito |
| -------- | ---------------- | --------- |
| 82113    | Diego Souza      | 4         |
| 79879    | Franciele Faccin | 6         |
| 82695    | Hugo Barroca     | 3         |
| 82544    | João Louro       | 5         |
| 82454    | Lino Silva       | 1         |
| 81996    | Vanda Barata     | 2         |
