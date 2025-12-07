# MedPro Mobile - Gestão de Clínica Médica 🏥

Aplicativo móvel desenvolvido para o gerenciamento integral de uma clínica médica, permitindo o cadastro de médicos e pacientes, além do agendamento e cancelamento de consultas. O sistema é composto por um aplicativo mobile (React Native) integrado a uma API REST robusta (Spring Boot).

## 📱 Funcionalidades

* **Gestão de Médicos:** Cadastro, listagem, edição, busca e desativação (exclusão lógica) de médicos.
* **Gestão de Pacientes:** Cadastro completo com endereço, edição e histórico.
* **Agendamento de Consultas:**
    * Agendamento com validações de regras de negócio (horário de funcionamento, conflito de horários, antecedência mínima).
    * Escolha de médico específico ou aleatório por especialidade.
* **Cancelamento:** Cancelamento de consultas com justificativa.
* **Busca:** Filtros dinâmicos por nome, CRM ou CPF.

## 🛠️ Tecnologias Utilizadas

### Mobile (Frontend)
* **React Native** com **Expo**
* **Axios** (Consumo de API)
* **React Navigation** (Navegação em Stack)
* **StyleSheet** (Estilização)

### Backend (API)
* **Java 21**
* **Spring Boot 3**
* **Spring Data JPA** & **Hibernate**
* **MySQL** (Banco de Dados)
* **Flyway** (Migração de Banco de Dados)
* **Lombok** (Boilerplate reduction)
* **Validation** (Bean Validation)

---

## 🚀 Como executar o projeto

Este repositório contém tanto o backend quanto o frontend. Siga os passos abaixo para rodar a aplicação completa.

### Pré-requisitos
* Node.js e npm instalados.
* JDK 21 instalado.
* MySQL rodando localmente.
* Celular com o app **Expo Go** instalado ou Emulador Android/iOS.

### 1. Configurando o Backend (`medpro`)

1.  Crie um banco de dados no MySQL com o nome `medpro_api`.
2.  Verifique as configurações de acesso no arquivo `medpro/src/main/resources/application.properties`. Por padrão está configurado para:
    * Usuário: `root`
    * Senha: `aluno`
    *(Altere se necessário)*.
3.  Abra o terminal na pasta `medpro` e execute:

```bash
cd medpro
./mvnw spring-boot:run
```
O Backend rodará na porta 8080.

### 2. Configurando o Frontend (App.clinica)

1. Antes de rodar, verifique o IP da sua API. Abra o arquivo App.clinica/src/Services/api.js.

2. Altere a baseURL para o endereço IPv4 da sua máquina (não use localhost se for testar no celular físico):

```JavaScript

// Exemplo
const api = axios.create({
  baseURL: "[http://192.168.1.](http://192.168.1.)XX:8080", 
});
```

### 3. Abra um novo terminal na pasta App.clinica e instale as dependências:

```Bash

cd App.clinica
npm install

```

### 4. Inicie o projeto Expo:

```Bash

npx expo start

````

### 5. Escaneie o QR Code com o app Expo Go no seu celular ou pressione a para abrir no emulador Android.

## 🧩 Arquitetura e Soluções Técnicas
### Estrutura
O projeto segue uma arquitetura Cliente-Servidor:

* Frontend: Responsável pela UI/UX, consome os endpoints REST e trata os dados para exibição.

* Backend: Responsável pela persistência, segurança e, principalmente, pelas Regras de Negócio (ex: validações complexas de agendamento).

### O Desafio do java.time 📅
Um dos desafios técnicos foi a integração entre o objeto Date do JavaScript e as classes LocalDate/LocalDateTime do Java.

* Solução: Implementamos a padronização via ISO-8601. O Frontend formata as datas para strings compatíveis antes de enviar o JSON, e o Backend utiliza anotações e conversores padrão do Spring para deserializar corretamente, garantindo integridade temporal sem erros de fuso horário.

## 👨‍💻 Autores
Desenvolvido como atividade da disciplina de Aplicações Mobile.

* Vinicius Augusto Ramos Bastos
* Pedro Henrique Lopes Martins
