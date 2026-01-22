# Sistema Mineradora - Microserviços com Apache Kafka

Projeto de arquitetura de microserviços desenvolvido com **Quarkus Framework** e **Apache Kafka** para um sistema de gestão mineradora com cotações, propostas e relatórios.

---

## 🔗 Repositórios dos Microsserviços

- **[Gateway BFF](https://github.com/phedrohenrick/gateway-bff_microservice_quarkus)** - Camada de autenticação e roteamento
- **[Cotação](https://github.com/phedrohenrick/cotacao_microservice_quarkus)** - Serviço de conversão de moedas
- **[Propostas](https://github.com/phedrohenrick/proposal_microservice_quarkus)** - Gerenciamento de propostas comerciais
- **[Relatórios](https://github.com/phedrohenrick/reporte_microsservice)** - Agregação e geração de relatórios

---

## 📋 Visão Geral dos Microsserviços

### **Gateway BFF (Backend for Frontend)**
- **Localização:** `gatewayBff_microservice_quarkus/`
- **Responsabilidade:** Microservice API responsável por aplicar autenticação implementada com Keycloak no padrão BFF. Atua como ponto de entrada único para todas as requisições, realizando roteamento e orquestração de chamadas aos microsserviços de negócio com validação de segurança.
- **Porta Padrão:** 8080

### **Microsserviço de Cotação (Quotation)**
- **Localização:** `cotacao_microservice_quarkus/`
- **Responsabilidade:** API responsável por consumir uma API externa de conversão de moedas, permitindo a conversão de Real para Dólar. Com base na cotação atual do dólar, facilita parcerias de compra e otimiza negociações. Publica os dados de cotação para o tópico Kafka, permitindo que outros microsserviços tenham acesso às taxas de câmbio atualizadas.
- **Banco de Dados:** `quotationdb` (PostgreSQL)
- **Porta Padrão:** 8082

### **Microsserviço de Propostas (Proposal)**
- **Localização:** `proposal_microservice_quarkus/`
- **Responsabilidade:** Microservice API responsável por receber propostas de compra de minério de clientes e enviar dados para um tópico Kafka. Consome eventos de cotação do Kafka para facilitar parcerias de compra baseadas na cotação atual do dólar, otimizando negociações e permitindo que dados de propostas sejam compartilhados entre os microsserviços.
- **Banco de Dados:** `proposaldb` (PostgreSQL)
- **Porta Padrão:** 8083

### **Microsserviço de Relatórios (Report)**
- **Localização:** `reporte_microsservice/reporte/`
- **Responsabilidade:** Microsserviço responsável por reunir dados de propostas e cotações, gerenciando o valor das propostas conforme a mudança do dólar. Consome eventos de cotações e propostas do Kafka, agrega dados e gera relatórios dos resultados para a empresa, permitindo análise de impacto das variações cambiais nas negociações.
- **Banco de Dados:** `reportdb` (PostgreSQL)
- **Porta Padrão:** 8084

---

## 🛠 Tecnologias Utilizadas

- **Java 11+** (JDK)
- **Quarkus Framework** - Framework reactive Java
- **Apache Kafka** - Message broker para comunicação assíncrona entre microsserviços
- **PostgreSQL** - Banco de dados relacional
- **Docker & Docker Compose** - Containerização
- **Maven** - Gerenciador de dependências e build
- **Keycloak 26.2.5** - Servidor de autenticação e autorização (OpenID Connect/OAuth2)
- **Conduktor** - Interface visual para gerenciar Kafka

---

## 📦 Pré-requisitos

### **Instalações Obrigatórias**

#### **Java 11 ou superior (JDK)**
- **Windows:** https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA
- **Linux:** https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8
- **Mac:** https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE

#### **Maven**
https://maven.apache.org/download.cgi

https://maven.apache.org/install.html

#### **Docker**
- **Windows:** https://docs.docker.com/desktop/windows/install/
- **Linux:** https://docs.docker.com/desktop/linux/install/
- **Mac:** https://docs.docker.com/desktop/mac/install/

#### **PostgreSQL**
https://www.postgresql.org/download/

Ou utilize via Docker (recomendado).

#### **Apache Kafka (Conduktor)**
https://www.conduktor.io/download

#### **IDE (Opcional mas Recomendado)**
- **IntelliJ IDEA:** https://www.jetbrains.com/idea/download
- **Eclipse:** https://www.eclipse.org/downloads/

---

## 🚀 Como Rodar o Projeto

### **Passo 1: Iniciar o Keycloak**

Execute uma instância do Keycloak com Docker (porta 8081):

```bash
docker run -p 8081:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.2.5 start-dev
```

**Acessar Keycloak:**
- URL: http://localhost:8081
- Usuário: `admin`
- Senha: `admin`

**Configurações Necessárias no Keycloak:**

1. **Criar um Realm**
   - Importe o arquivo de configuração do Quarkus disponível na pasta de curso

2. **Criar Usuários**
   - Operadores
   - Gerentes
   - Clientes

3. **Criar Roles**
   - `user` - Usuário padrão
   - `manager` - Gerenciador
   - `proposal-customer` - Cliente de propostas

4. **Associar Roles aos Usuários**
   - Registre cada usuário com suas respectivas roles

---

### **Passo 2: Iniciar PostgreSQL**

**Opção 1: Via Docker (Recomendado)**

```bash
docker run -d --name postgres_mineradora \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5433:5432 \
  postgres:latest
```

**Opção 2: Instalação Local**

Se já tiver PostgreSQL instalado, certifique-se de que está rodando na porta 5433.

**Criar Bancos de Dados:**

Conecte ao PostgreSQL e execute:

```sql
CREATE DATABASE quotationdb;
CREATE DATABASE proposaldb;
CREATE DATABASE reportdb;
```

---

### **Passo 3: Iniciar o Apache Kafka**

1. **Abra o Conduktor**
   - Faça download em: https://www.conduktor.io/download
   - Configure a conexão com seu cluster Kafka
   - Crie os tópicos necessários (se não forem criados automaticamente pelos microsserviços)

---

### **Passo 4: Iniciar os Microsserviços**

Cada microsserviço deve ser iniciado em um terminal separado. A partir da raiz de cada projeto:

**Gateway BFF:**
```bash
cd gatewayBff_microservice_quarkus
mvn quarkus:dev
```

**Microsserviço de Cotação:**
```bash
cd cotacao_microservice_quarkus
mvn quarkus:dev
```

**Microsserviço de Propostas:**
```bash
cd proposal_microservice_quarkus
mvn quarkus:dev
```

**Microsserviço de Relatórios:**
```bash
cd reporte_microsservice/reporte
mvn quarkus:dev
```

---

### **Verificação de Saúde**

Após todos os serviços estarem rodando, você pode verificar se estão operacionais:

- **Gateway BFF:** http://localhost:8080/health
- **Cotação:** http://localhost:8082/health
- **Propostas:** http://localhost:8083/health
- **Relatórios:** http://localhost:8084/health

---

## 🔐 Fluxo de Autenticação

O sistema utiliza **Keycloak** como servidor de autenticação:

1. Usuários autenticam no Keycloak
2. Keycloak emite um token JWT
3. Cliente envia o token nas requisições ao Gateway BFF
4. Gateway valida o token e roteia para o microsserviço apropriado
5. Cada microsserviço também valida a autorização do usuário

<!-- ---

## 📊 Fluxo de Dados com Kafka

```
Cotação Microservice → Publica Eventos (tópico: quotations)
                              ↓
                    Proposal & Report Microservices
                              ↓
                    Processam e Armazenam em BDs
```

Os microsserviços de Proposta e Relatório **consomem** eventos publicados pelo microsserviço de Cotação, permitindo comunicação assíncrona e desacoplamento entre serviços. -->

---

## 🐛 Troubleshooting

### **Conexão PostgreSQL Recusada**
- Verifique se PostgreSQL está rodando na porta 5433
- Verifique as credenciais em `application.properties` de cada microsserviço

### **Erro ao conectar ao Kafka**
- Verifique se o Kafka está rodando
- Abra o Conduktor e confirme a conectividade

### **Erro de Autenticação**
- Verifique se o Keycloak está rodando
- Confirme que o usuário foi criado e associado às roles corretas

### **Porta em Uso**
- Se uma porta estiver em uso, você pode mudar a porta de cada microsserviço no arquivo `application.properties` ou passando `-Dquarkus.http.port=XXXX` ao comando maven

---

## 📝 Estrutura de Configuração

Cada microsserviço possui um arquivo `src/main/resources/application.properties` que contém:
- Configurações de conexão ao PostgreSQL
- Configurações de conexão ao Kafka
- Configurações do Keycloak

---

## 🤝 Contribuições

Este é um projeto de implementação representa o resultado de estudo e implementação pessoal das tecnologias e padões de projeto mais usadas do mercado.

---

## 📚 Recursos Adicionais

- **Documentação Quarkus:** https://quarkus.io/guides/
- **Documentação Kafka:** https://kafka.apache.org/documentation/
- **Documentação Keycloak:** https://www.keycloak.org/documentation
- **Documentação PostgreSQL:** https://www.postgresql.org/docs/

---

**Última Atualização:** Janeiro 2026
