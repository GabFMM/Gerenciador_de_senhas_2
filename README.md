# Gerenciador de Senhas 2 — Aplicação Desktop com JavaFX e Spring Boot

Esse projeto possui a finalidade de ser um cofre seguro para senhas.

Abaixo é mostrado, em seções e respectivamente, requisitos para executar, como executar, principais funcionalidades, arquitetura, justificativas e situação.

## Requisitos para executar
* JDK 21
* Spring boot 4.0.1
* JavaFX 21

## Como executar
1. Clone o repositório
2. Crie uma variável de ambiente com o nome SECURITY_CRYPTO_KEY. Ela deve ter 256 bits (32 bytes) de tamanho no formato hexadecimal.
2. Inicie o back-end Spring Boot
3. Execute a aplicação JavaFX

## Principais funcionalidades
* Multiusuário
* Para desktop
* Uso de criptografia para armazenamento seguro de dados sensíveis.
* Uso de função hash para proteção de senhas de autenticação
* Criação de inúmeras contas, isto é, estruturas que armazenam um título, uma descrição e uma senha, para cada usuário
* Gerador personalizável e automático de senhas

## Arquitetura
O projeto utiliza uma arquitetura em camadas, separando:
* Front-end (JavaFX)
* Back-end (Spring Boot – REST API)
* Persistência de dados (Spring Data JPA + H2)

## Justificativa
Esse gerenciador foi criado com a intenção de melhorar as habilidades na área de desenvolvimento de sistemas.

Ele também é uma continuação de outro repositório semelhante ([clique aqui para ver mais](https://github.com/GabFMM/Gerenciador_de_senhas)), porém esse possui mais aprofundamento em tópicos como camadas de desenvolvimento (front-end e back-end) via REST API.

## Situação
Atualmente, o projeto é seguro e funcional, porém com alguns bugs visuais.

Para saber quais serão os próximos passos a serem realizados, vá à aba Issues do repositório ([ou clique aqui](https://github.com/GabFMM/Gerenciador_de_senhas_2/issues)).
