# 🚚 G-Express - Sistema de Gestão de Logística

Este projeto foi desenvolvido como parte dos meus estudos avançados em **Programação Orientada a Objetos (POO)** com Java. O objetivo é simular o controle de uma frota de veículos de uma empresa de logística, aplicando regras de negócio reais e segurança de tipos.

## 🚀 Conceitos de POO Aplicados:

- **Abstração:** Utilização da classe abstrata `VeiculoStructure` como molde principal, garantindo que nenhum veículo seja instanciado sem as propriedades básicas de modelo e valor de diária.
- **Herança:** Implementação de classes especializadas (`VeiculoPasseio` e `VeiculoCarga`) que herdam e estendem o comportamento da classe base.
- **Polimorfismo:** Sobrescrita de métodos (`@Override`) para permitir comportamentos diferentes no cálculo do aluguel. Por exemplo, veículos de carga com mais de 10 toneladas recebem automaticamente um acréscimo de 20% no valor final.
- **Interfaces:** Uso da interface `Rastreavel` para garantir que apenas veículos específicos (como os de carga) possuam o contrato de conexão via satélite, separando responsabilidades de forma limpa.
- **Encapsulamento:** Proteção de atributos sensíveis e uso de `BigDecimal` para garantir precisão absoluta nos cálculos financeiros.

## 🛠️ Tecnologias Utilizadas:

- **Java 17+** (Utilizando as últimas funcionalidades da linguagem).
- **Git & GitHub:** Para controle de versão e versionamento semântico.
- **IntelliJ IDEA:** Como ambiente de desenvolvimento principal.

---
*Projeto em constante evolução. Próximo passo: Implementação de Tratamento de Exceções (Custom Exceptions).*
