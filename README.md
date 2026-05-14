# 🚚 G-Express - Sistema de Gestão de Logística

Este projeto foi desenvolvido como parte dos meus estudos avançados em **Programação Orientada a Objetos (POO)** com Java. O objetivo é simular o controle de uma frota de veículos de uma empresa de logística, aplicando regras de negócio reais e segurança de tipos.

## 🚀 Conceitos de POO Aplicados:

- **Abstração:** Utilização da classe abstrata `VeiculoStructure` como molde principal, garantindo que nenhum veículo seja instanciado sem as propriedades básicas.
- **Herança:** Implementação de classes especializadas (`VeiculoPasseio` e `VeiculoCarga`) que herdam e estendem o comportamento da classe base.
- **Polimorfismo:** Sobrescrita de métodos (`@Override`) para cálculos dinâmicos de aluguel.
- **Interfaces:** Uso da interface `Rastreavel` para garantir que apenas veículos específicos possuam o contrato de conexão via satélite.
- **Encapsulamento & Enums:** Uso de `TipoCombustivel` (Enum) para garantir que o sistema aceite apenas categorias válidas, eliminando erros de digitação e aumentando a segurança de tipos.

## 🛡️ Tratamento de Erros e Segurança:

- **Custom Exceptions:** Criação da `ValorInvalidoException` para impedir que veículos nasçam com valores negativos ou zerados.
- **Data Validation:** Uso de blocos `try-catch` na `Main` para capturar erros de entrada (`InputMismatchException`) e regras de negócio, mantendo o sistema rodando mesmo após falhas do usuário.
- **Clean Code:** Implementação de métodos auxiliares estáticos para organizar o fluxo de entrada de dados no menu interativo.

## 💰 Regras de Negócio Implementadas:

1. **Carga Pesada:** Acréscimo automático de **20%** no aluguel para caminhões com capacidade superior a 10 toneladas.
2. **Sustentabilidade:** Desconto exclusivo de **10%** para veículos de passeio do tipo **ELÉTRICO**.

## 🛠️ Tecnologias Utilizadas:

- **Java 17+**
- **Git & GitHub**
- **IntelliJ IDEA**

---
*Projeto em constante evolução. Próximo passo sugerido: Persistência de dados ou Collections avançadas.*
