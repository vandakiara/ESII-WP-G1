# Engenharia de Software II - Grupo 1

## Setup

Inicialmente, é necessário carregar as imagens das aplicações Java, que se encontram compactadas no diretório `docker-images/`. Para isso, deverá correr os seguintes comandos, a partir da raiz do projeto:

```sh
docker load -i docker-images/covid-evolution-diff.zip
docker load -i docker-images/covid-graph-spread.zip
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

---

## Instruções para criar o JAR e o Docker image

### Preparar o JAR

Se tiver ficheiros de referência que são necessários para executar a aplicação (como os templates `html`), criar uma pasta na raiz do projeto e mover essas dependências para lá.

No `pom.xml`, adicionar as seguintes linhas, substituir o que está entre `{}`, incluindo as `{}`:

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <application.mainclass>{PACKAGE.NOME_DA_CLASSE_COM_O_MAIN}</application.mainclass>
    <application.dependencies>lib</application.dependencies>
    <application.workdir>application</application.workdir>
</properties>

<build>
    <plugins>
        <plugin>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>3.0.1</version>
            <executions>
                <execution>
                    <phase>prepare-package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <includeScope>runtime</includeScope>
                        <outputDirectory>${project.build.directory}/${application.dependencies}</outputDirectory>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <plugin>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.0.2</version>
            <configuration>
                <archive>
                    <manifest>
                        <addClasspath>true</addClasspath>
                        <classpathPrefix>${application.dependencies}</classpathPrefix>
                        <mainClass>${application.mainclass}</mainClass>
                        <!-- match with base version string naming of Maven Dependency Plugin -->
                        <useUniqueVersions>false</useUniqueVersions>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
        <plugin>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.0.2</version>
            <executions>
                <!-- Build Dockerfile -->
                <execution>
                    <id>resources-docker</id>
                    <phase>generate-resources</phase>
                    <goals>
                        <goal>copy-resources</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>${project.build.directory}</outputDirectory>
                        <resources>
                            <resource>
                                <directory>src/main/docker</directory>
                                <filtering>true</filtering>
                            </resource>
                        </resources>
                    </configuration>
                </execution>
                <!-- SE TIVER FICHEIROS DE DEPENDÊNCIA QUE COLOCOU NUMA PASTA ESPECÍFICA, CONFIGURAR ESSA SESSÃO TBM. SENÃO, DELETAR ESSA SESSÃO <execution> -->
                <execution>
                    <id>resources-html</id>
                    <phase>generate-resources</phase>
                    <goals>
                        <goal>copy-resources</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>${project.build.directory}/{O_NOME_DA_PASTA_DAS_DEPENDENCIAS}</outputDirectory>
                        <resources>
                            <resource>
                                <directory>assets</directory>
                                <filtering>true</filtering>
                            </resource>
                        </resources>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

No caminho `src/main/docker`, criar um ficheiro `Dockerfile` com o seguinte conteúdo:

```docker
FROM openjdk:8-jre-alpine

WORKDIR ${application.workdir}
COPY ${application.dependencies} ${application.dependencies}
COPY ${project.build.finalName}.jar ${project.build.finalName}.jar
# SE TIVER DEPENDENCIAS, COLOCAR AQUI O COPY DELAS, COMO O EXEMPLO ABAIXO
COPY assets/diff-template.html assets/diff-template.html

ENTRYPOINT ["/usr/bin/java", "-jar", "${project.build.finalName}.jar"]
# COLOCAR O PORTO DO SEU PROJETO
EXPOSE 300X
```

Vamos agora construir o ficheiro `.jar`. No terminal, vá para a raiz do seu projeto e execute:

```sh
mvn package
```

> Nota: Se precisarem correr `mvn package` novamente, antes corram `mvn clean`.

Pode verificar se o jar está a correr bem:

```sh
java -jar target/nome_do_seu_jar.jar
```

ex: `java -jar target/covid-evolution-diff-0.0.1-SNAPSHOT.jar`

Ainda na raiz do seu projeto, agora vamos criar a imagem docker criando uma tag:

```sh
docker build -t {NOME_DO_SEU_PROJETO}:0.0.1 target/
```

ex: `docker build -t covid-evolution-diff:0.0.1 target/`

Podes correr a sua image para ver se funcionou, ex:

```sh
docker run -p 3000:3000 covid-evolution-diff:0.0.1
```

Vamos agora configurar o `docker-compose.yml` para rodar o seu projeto. Para isso, adicione nos `services` RESPEITANDO A IDENTAÇÃO DO FICHEIRO:

```yml
{o nome que queira dar para o servico}:
    container_name: {o nome que deu para o serviço}
    image: {NOME_DO_SEU_PROJETO_QDO_FEZ_DOCKER_BUILD}
    ports:
      - {seu porto}:{seu porto}
    networks:
      - wp_network
```

ex:

```yml
java-covid-evolution-diff:
  container_name: java-covid-evolution-diff
  image: covid-evolution-diff:0.0.1
  ports:
    - 3000:3000
  networks:
    - wp_network
```

Vá para a raiz do repositório e rode o docker-compose

```sh
docker-compose up -d
```

Pode testar a aplicação pelo wordpress em [http://localhost:8080](http://localhost:8080) ou diretamente no seu porto.

Tudo funcionando, então podemos salvar o `.zip` da imagem. Ainda na raiz do repo:

```sh
docker save -o docker-images/{NOME DO PROJETO}.zip {TAG DA IMAGEM DOCKER}
```

ex: `docker save -o docker-images/covid-evolution-diff.zip covid-evolution-diff:0.0.1`

Profit!
