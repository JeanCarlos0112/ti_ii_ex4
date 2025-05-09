# Projeto TI II EXERCICIO 4

## Pré-requisitos

* Java Development Kit (JDK) versão 24 ou superior.
* Apache Maven 3.6.0 ou superior.

## Estrutura de diretórios do projeto

* Nas pastas `Produto_1` e `Produto_2` estarão as imagens dos topicos 1 e 2 requisitados, na pasta code contém todo o codigo que faz a interação com a LLM, segue a estrutura abaixo:

```text
.
├── Produto_1/
│   └── Produto_1_homepage.png
├── Produto_2/
│   ├── Produto_2_account.png
│   ├── Produto_2_db.png
│   └── Produto_2_url.png
└── code/
    ├── pom.xml
    ├── README.md
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/ti2/ex4/
    │   │           └── App.java
    │   └── test/
    │       └── java/
    │           └── com/ti2/ex4/
    │               └── AppTest.java
    |
```

## Dependências Principais

As dependências são gerenciadas pelo Maven e estão listadas no arquivo `pom.xml`. As principais incluem:

* `com.azure:azure-ai-inference`: Para interagir com serviços de inferência de IA da Azure.
* `com.azure:azure-identity`: Para autenticação com serviços Azure.
* `com.azure:azure-core`: Biblioteca principal para SDKs Azure.
* `com.azure:azure-json`: Para manipulação de JSON.
* `com.azure:azure-core-http-netty`: Cliente HTTP Netty para Azure Core.

## Como Compilar e Executar

1. Abra um terminal na pasta `code`:

   ```bash
   cd code
   ```

2. Compile o projeto e gere o JAR:

   ```bash
   mvn clean package
   ```

3. Execute a aplicação Java:

   ```bash
   mvn exec:java -Dexec.mainClass="com.ti2.ex4.App"
   ```

   ou diretamente:

   ```bash
   java -cp target/code-1.0-SNAPSHOT.jar com.ti2.ex4.App
   ```

> **Observação:** Ajuste `com.ti2.ex4.App` se alterar o nome da classe principal.

## Saída do Programa

* A resposta gerada pelo modelo de inferência será impressa no console.
* Opcionalmente, ela será salva em `code/output/chat_output.txt`. A pasta `output` será criada automaticamente se não existir.

## Configurações Adicionais

* Verifique as variáveis de ambiente ou o arquivo de configuração para definir corretamente o **endpoint** e a **chave** do serviço Azure AI Inference.
* Caso utilize autenticação via `DefaultAzureCredential`, configure também `AZURE_TENANT_ID`, `AZURE_CLIENT_ID` e `AZURE_CLIENT_SECRET`.

---

© 2025 — Projeto TI II EXERCÍCIO 4
