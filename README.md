Reprova
===
[![Build Status](https://travis-ci.com/victorgveloso/reprova.svg?branch=master)](https://travis-ci.com/victorgveloso/reprova)
[![Coverage Status](https://coveralls.io/repos/github/victorgveloso/reprova/badge.svg)](https://coveralls.io/github/victorgveloso/reprova)

---

Um sistema gerenciador de questões e respostas para exames.

Objetivos gerais
---

- Dar suporte ao professor na criação de exames;
- Auxiliar alunos na preparação para exames;
- Facilitar controle e visualização de rendimento por questões e turmas.


Objetivos específicos
---

- [X] Armazenamento de notas individuais
- [X] Importação de notas por arquivo
- [ ] Geração automática de prova
- [ ] Tempo estimado para questão 
- [ ] Arquivo de resposta
- [ ] Dificuldade com base em notas 
- [ ] Ordenação e filtro por tempo de questão

### Requisitos do professor:

- Adicionar questões (atualmente de extensão `.doc`)
- Definir acesso público ou privado às questões
  - Apenas professores podem ver questões privadas
- Associar questões a tópicos
- Atribuir questões a turmas
- Determinar desempenho de turma por questão

### Requisitos de alunos e professores

- Buscar questões por tópico

Composição de um arquivo de questões
---

Um único arquivo `.doc` possui várias questões relacionadas ao mesmo tópico. Cada questão é dividida em três partes:

- Textos e figuras da questão (formatados)
- Lista de turmas em que questão foi usada
- Média do desempenho de alunos por questão por turma

# Como usar?

## 1º Faça clone do projeto

```shell script
$ git clone https://github.com/victorgveloso/reprova
$ cd reprova
```

## 2ª Configure as features

No arquivo localizado em `./src/resources/config.properties`, defina as configurações de...

1. Granularidade:
    
    1. Granularidade fina (notas por turma):
        ```properties
        Granularity=FINE_GRAINED
        ```
    1. Granularidade grossa (notas individuais):
        ```properties
        Granularity=COARSE_GRAINED
        ```
       
1. Formato do arquivo de notas:
    
    1. CSV:
        ```properties
        ScoreFileType=CSV
        ```
    1. JSON:
        ```properties
        ScoreFileType=JSON
        ```

## 3º Compile

```shell script
$ mvn install 
$ mv target/reprova-0.1.jar target/reprova.jar
```

## 4º Execute

### a) Via docker-compose (recomendado)

Configure as portas no arquivo `.docker-compose.yml`:
```yaml
version: '2.1'


services:

  reprova:
    container_name: reprova
    build: .
    image: gahag/reprova:v0.1
    environment:
      - PORT=8080
      - REPROVA_TOKEN=d2fad245dd1d8a4f863e3f1c32bdada723361e6f63cfddf56663e516e47347bb
      - REPROVA_MONGO=mongodb://mongo:27017/?connectTimeoutMS=5000
    ports:
    - 8080:8080 <--- troque 8080 pela porta que deseja disponibilizar o serviço web
    depends_on:
    - mongo

  mongo:
    container_name: mongo
    image: mvertes/alpine-mongo
    ports:
    - 27017:27017 <--- troque 27017 pela porta que deseja disponibilizar o banco de dados
    volumes:
    - ./db:/data/db

```

Agora basta executar o seguinte comando:

```shell script
$ docker-compose up --build
```

### b) Via Docker cli

Execute os seguintes comandos, substituindo "PORTA_DO_SERVIDOR" pelo valor desejado (por exemplo 8080)

```shell script
$ docker build .
$ docker network create mongo-net
$ docker run mvertes/alpine-mongo -p 27017:27017 -v ./db:/data/db --name mongo --network=mongo-net
$ docker run gahag/reprova:v0.1 -e PORT=8080 -e REPROVA_TOKEN='d2fad245dd1d8a4f863e3f1c32bdada723361e6f63cfddf56663e516e47347bb' -e REPROVA_MONGO='mongodb://mongo:27017/?connectTimeoutMS=5000' -p PORTA_DO_SERVIDOR:8080 --name reprova  --network=mongo-net 
```

### c) Usando o jar

Primeiramente, certifique-se de ter um banco de dados MongoDB (versão recomendada: 4.0) em alguma máquina cujo IP é conhecido.

Execute o seguinte comando, substituindo "PORTA_DO_SERVIDOR" e "IP_DO_MONGODB" pelos valores desejados (por exemplo 8080 e localhost).

Atenção: IP_DO_MONGODB deve ser o IP real da máquina que esteja executando o banco de dados na porta 27017 e o banco deve ter o sistema de autenticação desativado (por isso recomendamos o uso de docker)!

```shell script
$ PORT=PORTA_DO_SERVIDOR REPROVA_TOKEN=d2fad245dd1d8a4f863e3f1c32bdada723361e6f63cfddf56663e516e47347bb REPROVA_MONGO=mongodb://IP_DO_MONGODB:27017/?connectTimeoutMS=5000 java -jar target/reprova.jar
```