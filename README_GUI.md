# Interface Gráfica JavaFX - AvaliaWiFi

## 📋 Visão Geral

Esta é a interface gráfica do sistema AvaliaWiFi, desenvolvida em JavaFX. A interface fornece uma experiência visual moderna e intuitiva para gerenciar residências, cômodos e medições de WiFi.

## 🏗️ Arquitetura da Interface

### Estrutura de Diretórios
```
src/
├── view/
│   ├── AvaliaWifiApplication.java     # Aplicação principal JavaFX
│   └── controller/                    # Controllers das views
│       ├── MainViewController.java    # Controller principal
│       ├── ResidenciaDialogController.java
│       ├── ComodoDialogController.java
│       └── MedicaoDialogController.java
├── resources/                         # Arquivos FXML
│   ├── MainView.fxml                 # Interface principal
│   ├── ResidenciaDialog.fxml         # Diálogo de residência
│   ├── ComodoDialog.fxml             # Diálogo de cômodo
│   └── MedicaoDialog.fxml            # Diálogo de medição
```

## ✨ Funcionalidades

### 🏠 Aba Residências
- ➕ **Cadastrar nova residência** com nome, endereço e cliente
- ✏️ **Editar residências existentes**
- 🗑️ **Excluir residências** com confirmação
- 📋 **Visualizar lista completa** de residências cadastradas

### 🏠 Aba Cômodos
- 🔍 **Filtrar por residência** usando ComboBox
- ➕ **Adicionar cômodos** à residência selecionada
- ✏️ **Editar nomes de cômodos**
- 🗑️ **Remover cômodos** com confirmação

### 📡 Aba Medições
- 🔍 **Filtros duplos**: por residência e cômodo
- 📅 **Cadastro completo** com data/hora, sinal, velocidade, interferência e banda
- 📊 **Visualização em tabela** com todas as métricas
- ✏️ **Edição de medições existentes**
- 🗑️ **Exclusão de medições**

### 📊 Aba Relatórios
- 🔍 **Seleção por residência**
- 📄 **Geração automática** de relatórios detalhados
- 📤 **Exportação** (funcionalidade futura)

## 🔧 Princípios de Design

### ✅ Separation of Concerns
- **Views** são apenas responsáveis pela interface
- **Controllers** gerenciam eventos e validações de UI
- **Services** contêm toda a lógica de negócio
- **Repositories** lidam com persistência de dados

### ✅ Dependency Injection via Application
```java
// Controllers acessam services através da aplicação principal
ResidenciaService residenciaService = AvaliaWifiApplication.getResidenciaService();
```

### ✅ Event-Driven Architecture
- Uso de `@FXML` para bind de eventos
- Callbacks para atualização de dados entre telas
- Listeners para mudanças em ComboBoxes

### ✅ Validation & Error Handling
- Validação de campos obrigatórios
- Mensagens de erro específicas
- Confirmações para operações destrutivas

## 📋 Pré-requisitos

### ☑️ Software Necessário
- **Java JDK 11** ou superior
- **JavaFX SDK 17.0.2** ou superior
- **PostgreSQL** servidor rodando
- **Driver JDBC PostgreSQL** (incluído no projeto)

### 📁 Estrutura de Dependências
```
java-libs/
├── jdbc-connectors/
│   └── postgresql-42.7.8.jar
└── javafx/
    └── lib/
        ├── javafx.base.jar
        ├── javafx.controls.jar
        ├── javafx.fxml.jar
        └── ... (outros JARs do JavaFX)
```

## 🚀 Como Executar

### 1. **Baixar JavaFX SDK**
```bash
# Baixar de: https://openjfx.io/
# Extrair para: java-libs/javafx/
```

### 2. **Executar a Interface Gráfica**
```bash
# No Windows
.\executar_gui.bat

# Ou manualmente
java -cp "bin;java-libs\jdbc-connectors\postgresql-42.7.8.jar;java-libs\javafx\lib\*" \
    --module-path "java-libs\javafx\lib" \
    --add-modules javafx.controls,javafx.fxml \
    view.AvaliaWifiApplication
```

## 🎯 Fluxo de Uso Típico

### 1. **Cadastrar Residência**
   - Abrir aba "Residências"
   - Clicar em "Nova Residência"
   - Preencher nome, endereço e cliente
   - Salvar

### 2. **Adicionar Cômodos**
   - Abrir aba "Cômodos"
   - Selecionar residência no ComboBox
   - Clicar em "Novo Cômodo"
   - Informar nome do cômodo

### 3. **Registrar Medições**
   - Abrir aba "Medições"
   - Selecionar residência e cômodo
   - Clicar em "Nova Medição"
   - Preencher todos os dados da medição

### 4. **Gerar Relatórios**
   - Abrir aba "Relatórios"
   - Selecionar residência
   - Clicar em "Gerar Relatório"

## 🔒 Vantagens da Arquitetura

### ✅ **Reutilização de Lógica**
- Services são compartilhados entre interface terminal e gráfica
- Mesmas validações e regras de negócio

### ✅ **Manutenibilidade**
- Controllers focados apenas na UI
- Services isolados e testáveis
- Separação clara de responsabilidades

### ✅ **Escalabilidade**
- Fácil adição de novas telas
- Novos controllers seguem o mesmo padrão
- Services podem ser expandidos sem afetar UI

### ✅ **Testabilidade**
- Services podem ser testados independentemente
- Controllers podem ser testados com mocks
- Validações isoladas e verificáveis

## 🐛 Troubleshooting

### ❌ **Erro: "JavaFX não encontrado"**
```
Solução: Baixar JavaFX SDK e extrair em java-libs/javafx/
```

### ❌ **Erro: "Module javafx.controls not found"**
```
Solução: Verificar se --module-path aponta para java-libs/javafx/lib
```

### ❌ **Erro: "Cannot connect to database"**
```
Solução: Verificar se PostgreSQL está rodando e database.properties está correto
```

### ❌ **Tela não carrega**
```
Solução: Verificar se arquivos FXML estão em src/resources/ e foram copiados para bin/resources/
```

## 🎨 Customizações Futuras

### 🎨 **Estilo Visual**
- Adicionar CSS personalizado
- Temas claro/escuro
- Ícones e imagens

### 📊 **Gráficos e Visualizações**
- Charts de velocidade ao longo do tempo
- Mapas de calor de sinal
- Comparativos entre cômodos

### 🔧 **Funcionalidades Avançadas**
- Exportação de relatórios PDF/Excel
- Importação de dados CSV
- Configurações de usuário

---

> **Nota**: Esta interface foi projetada para funcionar em conjunto com seus services existentes, mantendo a mesma lógica de negócio e garantindo consistência entre as interfaces terminal e gráfica.