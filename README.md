# 🚚 G-Express - Sistema de Gestão de Logística

Este projeto foi desenvolvido como parte dos meus estudos avançados em **Programação Orientada a Objetos (POO)** com Java. O objetivo é simular o controle de uma frota de veículos de uma empresa de logística, aplicando regras de negócio reais, segurança de tipos e persistência em banco de dados relacional.

## 🚀 Conceitos de POO Aplicados:

- **Abstração:** Utilização da classe abstrata `br.com.gexpress.model.VeiculoStructure` como molde principal, garantindo que nenhum veículo seja instanciado sem as propriedades básicas.
- **Herança:** Implementação de classes especializadas (`br.com.gexpress.model.VeiculoPasseio` e `br.com.gexpress.model.VeiculoCarga`) que herdam e estendem o comportamento da classe base.
- **Polimorfismo:** Sobrescrita de métodos (`@Override`) para cálculos dinâmicos de aluguel e exibição customizada de dados.
- **Interfaces:** Uso da interface `br.com.gexpress.model.Rastreavel` para garantir que apenas veículos específicos possuam o contrato de conexão via satélite.
- **Encapsulamento & Enums:** Uso de `br.com.gexpress.model.TipoCombustivel` e `br.com.gexpress.model.StatusVeiculo` para garantir que o sistema mude de estado de forma segura, eliminando falhas de consistência e inputs inválidos.

## 🛡️ Tratamento de Erros, Banco de Dados e Segurança:

- **Custom Exceptions:** Criação da `br.com.gexpress.exception.ValorInvalidoException` para impedir que veículos nasçam com valores negativos/zerados ou com anos de fabricação incompatíveis.
- **Persistência Robusta (SQL):** Integração total com o **PostgreSQL** via JDBC, abandonando arquivos locais (TXT) e garantindo persistência de dados em tempo real para Cadastros, Locações e Devoluções.
- **Trava de Duplicidade Suprema:** Validação na camada de banco de dados (`SELECT COUNT(*)`) com tratamento `case-insensitive` e remoção de espaços em branco, impedindo que o mesmo modelo seja cadastrado duas vezes, mesmo após reiniciar a aplicação.
- **Data Validation:** Uso de blocos `try-catch` na `Main` para capturar erros de entrada (`InputMismatchException`) e regras de negócio, mantendo o sistema estável mesmo após falhas do usuário.
- **Clean Code:** Organização rigorosa em camadas claras de responsabilidade (Model, Repository, Service e View/Main).

## 💰 Regras de Negócio Implementadas:

1. **Carga Pesada:** Acréscimo automático de **20%** no aluguel para caminhões com capacidade superior a 10 toneladas.
2. **Sustentabilidade:** Desconto exclusivo de **10%** para veículos de passeio do tipo **ELÉTRICO**.
3. **Prevenção de Overbooking:** Bloqueio lógico de estados que impede o aluguel duplicado de um veículo que já está em trânsito (atualizado diretamente no PostgreSQL).
4. **Proteção de Frota Ativa:** Validação temporal que bloqueia o cadastro de veículos fabricados antes do ano 2000 ou com anos informados no futuro.
5. **Garantia de Diária Mínima:** Tratamento automatizado via Java Time API para contratos iniciados e encerrados no mesmo dia, assegurando a cobrança de pelo menos 1 diária cheia.

## 🛠️ Tecnologias Utilizadas:

- **Java 17+** (Utilizando recursos da **Java Time API** e **Streams**)
- **PostgreSQL** (Persistência de dados relacional via JDBC)
- **Git & GitHub**
- **IntelliJ IDEA**

---
*Projeto em constante evolução. Próximo passo sugerido: Implementação de chaves primárias (IDs sequenciais) no banco de dados e criação de uma tabela separada para histórico de contratos de locação.*
