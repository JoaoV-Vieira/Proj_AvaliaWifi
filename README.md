# 📡 AvaliaWifi - Sistema de Monitoramento de Redes Wi-Fi

Sistema desenvolvido em Java para monitoramento e análise da qualidade de sinais Wi-Fi em residências, permitindo o cadastro de cômodos e medições detalhadas de conectividade.

## 🚀 Funcionalidades

- ✅ **Gerenciamento de Residências**: Cadastro completo com cliente e endereço
- ✅ **Controle de Cômodos**: Organização por ambiente da residência
- ✅ **Medições de Wi-Fi**: Registro de nível de sinal, velocidade e interferência
- ✅ **Suporte Multi-banda**: 2.4GHz e 5GHz
- ✅ **Relatórios Detalhados**: Análises estatísticas por cômodo e banda
- ✅ **Interface Amigável**: Menu interativo via terminal
- ✅ **Validação de Dados**: Verificações de entrada e formato de data brasileiro

## 🛠️ Tecnologias

- **Java 8+** - Linguagem principal
- **PostgreSQL** - Banco de dados
- **JDBC** - Conectividade com banco
- **Maven** (opcional) - Gerenciamento de dependências

## 📋 Pré-requisitos

1. **Java JDK 8+** instalado
2. **PostgreSQL** rodando na porta 5432
3. **Driver JDBC PostgreSQL** (incluído no projeto)

## 🚀 Como Executar

### Opção 1: Execução Rápida (Recomendada)
```bash
# 1. Compile o projeto
compilar.bat

# 2. Execute o sistema
executar_avaliawifi.bat
```

### Opção 2: Execução Manual
```bash
# 1. Compilar
javac -d bin -sourcepath src -cp "java-libs\jdbc-connectors\postgresql-42.7.8.jar" src\service\AvaliaWifi.java

# 2. Executar
java -cp "bin;java-libs\jdbc-connectors\postgresql-42.7.8.jar" service.AvaliaWifi
```

## ⚙️ Configuração do Banco

1. **Criar banco de dados**:
```sql
CREATE DATABASE avaliawifi;
```

2. **Executar script de criação**:
```bash
psql -U postgres -d avaliawifi -f criar_banco_avaliawifi.sql
```

3. **Configurar credenciais** em `src/database.properties`:
```properties
user=JoaoVieira
password=Naotemsenha
dburl=jdbc:postgresql://localhost:5432/avaliawifi
```

## 📊 Estrutura do Projeto

```
src/
├── controller/     # Controladores (API)
├── dto/           # Data Transfer Objects
├── model/         # Entidades do domínio
├── repository/    # Acesso a dados (DAO)
├── service/       # Lógica de negócio
├── test/          # Classes de teste
└── util/          # Utilitários e validações
```

## 🎯 Como Usar

1. **Cadastrar Residência**: Informe nome, endereço e cliente
2. **Selecionar Residência**: Escolha uma residência existente para gerenciar
3. **Cadastrar Cômodos**: Adicione ambientes à residência
4. **Registrar Medições**: Capture dados de Wi-Fi por cômodo
5. **Visualizar Relatórios**: Analise estatísticas e recomendações

## 📈 Exemplo de Medição

```
Data e Hora: 29/09/2025 14:30
Nível de Sinal: -65 dBm
Velocidade: 50.0 Mbps
Interferência: Baixa
Banda: 5GHz
Cômodo: Sala
```

## 🔧 Melhorias Sugeridas

- [ ] Implementar logs detalhados
- [ ] Adicionar backup automático
- [ ] Criar interface gráfica (JavaFX/Swing)
- [ ] Implementar exportação para Excel/PDF
- [ ] Adicionar gráficos de tendência
- [ ] Sistema de notificações por e-mail

## 🐛 Solução de Problemas

### Erro de Conexão
- Verifique se PostgreSQL está rodando
- Confirme credenciais no `database.properties`
- Teste conectividade: `psql -U JoaoVieira -h localhost avaliawifi`

### Erro de Compilação
- Verifique versão do Java: `java -version`
- Confirme se driver PostgreSQL está presente
- Execute `compilar.bat` para diagnóstico completo

## 👥 Autor

**João Vieira**  
Projeto Acadêmico - UTFPR 2025-2

---
*Sistema AvaliaWifi v1.0 - Monitoramento Profissional de Redes Wi-Fi*
