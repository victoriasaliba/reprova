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