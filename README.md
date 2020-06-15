# Engenharia de Software II - Grupo 1

## Setup
Inicialmente, é necessário carregar as imagens das aplicações Java, que se encontram compactadas no diretório `docker-images/`. Para isso, deverá correr os seguintes comandos, a partir da raiz do projeto:

```sh
docker load -i docker-images/covid-evolution-diff.zip
docker load -i docker-images/covid-graph-spread.zip
docker load -i docker-images/covid-query.zip
docker load -i docker-images/covid-sci-discoveries.zip
docker load -i docker-images/system-tests.zip
```

## Start
Para correr o projeto, correr o seguinte comando a partir da raiz do projeto:
```sh
docker-compose up -d
```

Agora, poderá aceder à aplicação em [http://localhost:8080](http://localhost:8080) 

**Nota:** Apesar do docker-toolbox usar um IP específico ao invés de localhost, o endereço do wordpress em si está como localhost pelo que recomendamos correr numa máquina que suporte docker desktop, ou que corra linux ou mac, para que tudo funcione a 100%.

## Alternativa - setup via Jenkins
Em vez de fazer manualmente todos os passos demonstrados anteriormente, pode simplesmente configurar o Jenkins da seguinte forma:
![Jenkins Pipeline](https://i.ibb.co/ryMnzhH/Jenkins-WP-config.png)

Para correr tudo bem, certifique-se que não tem os containers docker a correr anteriormente e que usa a versão 3.6.3 do maven.

#### Para iniciar sessão no Wordpress pode criar uma conta ou iniciar com os perfis já criados:
**Administrator**
* **Username**: admin
* **Password**: admin

**Member**
* **Username**: member
* **Password**: Member

## Email do administrador do WP-CMS
* **email**: es2wpg1@gmail.com
* **password**: iscte2020

## Membros do Grupo e Requisitos

| N° Aluno | Nome             | Requisito |
| -------- | ---------------- | --------- |
| 82113    | Diego Souza      | 4         |
| 79879    | Franciele Faccin | 6         |
| 82695    | Hugo Barroca     | 3         |
| 82544    | João Louro       | 5         |
| 82454    | Lino Silva       | 1         |
| 81996    | Vanda Barata     | 2         |
