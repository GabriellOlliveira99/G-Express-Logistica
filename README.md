# 🚚 G-Express - Sistema de Gestão de Logística

Este projeto foi desenvolvido como parte dos meus estudos avançados em **Programação Orientada a Objetos (POO)** com Java. O objetivo é simular o controle de uma frota de veículos de uma empresa de logística, aplicando regras de negócio reais e segurança de tipos.

## 🚀 Conceitos de POO Aplicados:

- **Abstração:** Utilização da classe abstrata `br.com.gexpress.model.VeiculoStructure` como molde principal, garantindo que nenhum veículo seja instanciado sem as propriedades básicas.
- **Herança:** Implementação de classes especializadas (`br.com.gexpress.model.VeiculoPasseio` e `br.com.gexpress.model.VeiculoCarga`) que herdam e estendem o comportamento da classe base.
- **Polimorfismo:** Sobrescrita de métodos (`@Override`) para cálculos dinâmicos de aluguel e exibição customizada de dados.
- **Interfaces:** Uso da interface `br.com.gexpress.model.Rastreavel` para garantir que apenas veículos específicos possuam o contrato de conexão via satélite.
- **Encapsulamento & Enums:** Uso de `br.com.gexpress.model.TipoCombustivel` e `br.com.gexpress.model.StatusVeiculo` para garantir que o sistema mude de estado de forma segura, eliminando falhas de consistência e inputs inválidos.

## 🛡️ Tratamento de Erros e Segurança:

- **Custom Exceptions:** Criação da `br.com.gexpress.exception.ValorInvalidoException` para impedir que veículos nasçam com valores negativos/zerados ou com anos de fabricação incompatíveis.
- **Data Validation:** Uso de blocos `try-catch` na `Main` para capturar erros de entrada (`InputMismatchException`) e regras de negócio, mantendo o sistema estável mesmo após falhas do usuário.
- **Clean Code:** Organização rigorosa de métodos de acesso (Getters/Setters) agrupados por atributos e isolamento de métodos estáticos para o fluxo do menu.

## 💰 Regras de Negócio Implementadas:

1. **Carga Pesada:** Acréscimo automático de **20%** no aluguel para caminhões com capacidade superior a 10 toneladas.
2. **Sustentabilidade:** Desconto exclusivo de **10%** para veículos de passeio do tipo **ELÉTRICO**.
3. **Prevenção de Overbooking:** Bloqueio lógico de estados (`DISPONIVEL` e `ALOCADO`) que impede o aluguel duplicado de um veículo que já está em trânsito.
4. **Proteção de Frota Ativa:** Validação temporal que bloqueia o cadastro de veículos fabricados antes do ano 2000 ou com anos informados no futuro.
5. **Garantia de Diária Mínima:** Tratamento automatizado via Java Time API para contratos iniciados e encerrados no mesmo dia, assegurando a cobrança de pelo menos 1 diária cheia.

## 🛠️ Tecnologias Utilizadas:

- **Java 17+** (Utilizando recursos da **Java Time API**)
- **Java I/O** (Leitura e escrita estruturada com `BufferedReader` e `PrintWriter`)
- **Git & GitHub**
- **IntelliJ IDEA**

---
*Projeto em constante evolução. Próximo passo sugerido: Arquitetura em camadas (MVC) e persistência em Banco de Dados Relacional (SQL).*
