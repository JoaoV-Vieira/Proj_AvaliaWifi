# 📥 Guia de Instalação do JavaFX

## 🎯 Objetivo

Este guia te ajudará a configurar o JavaFX para executar a interface gráfica do AvaliaWiFi.

## 📋 Passo a Passo

### 1. **Baixar o JavaFX SDK**

1. Acesse: https://openjfx.io/
2. Vá para a seção "Download"
3. Baixe a versão **17.0.2** ou superior
4. Escolha a versão adequada para seu sistema:
   - **Windows**: JavaFX 17.0.2 Windows x64 SDK
   - **Linux**: JavaFX 17.0.2 Linux x64 SDK
   - **macOS**: JavaFX 17.0.2 macOS x64 SDK

### 2. **Extrair os Arquivos**

1. Crie a pasta `java-libs\javafx` no seu projeto:
   ```
   c:\Users\j_vie\OneDrive\UTFPR\UTFPR\2025-2\Projeto\avaliaWiFi\java-libs\javafx\
   ```

2. Extraia o arquivo baixado (javafx-sdk-17.0.2.zip) para esta pasta

3. A estrutura final deve ficar assim:
   ```
   java-libs/
   ├── jdbc-connectors/
   │   └── postgresql-42.7.8.jar
   └── javafx/
       ├── bin/
       ├── legal/
       └── lib/          ← Esta pasta é essencial!
           ├── javafx.base.jar
           ├── javafx.controls.jar
           ├── javafx.fxml.jar
           ├── javafx.graphics.jar
           ├── javafx.media.jar
           ├── javafx.swing.jar
           └── javafx.web.jar
   ```

### 3. **Verificar a Instalação**

Execute o script de teste:
```cmd
.\executar_gui.bat
```

Se aparecer a mensagem:
```
[ERRO] JavaFX não encontrado em java-libs\javafx
```

Significa que a pasta não foi criada corretamente. Verifique o caminho.

### 4. **Instalação Alternativa (Manual)**

Se preferir instalar globalmente no sistema:

1. Extraia o JavaFX em `C:\javafx-17.0.2\`
2. Adicione ao PATH do sistema
3. Modifique o script `executar_gui.bat`:
   ```batch
   --module-path "C:\javafx-17.0.2\lib"
   ```

## 🔍 Verificação Final

Quando tudo estiver correto, você verá:

```
==========================================
    SISTEMA AVALIAWIFI - INTERFACE GRAFICA
    Monitoramento de Redes Wi-Fi
==========================================

[INFO] Verificando Java...
[INFO] Compilando projeto...
[INFO] Compilando classes Java...
[INFO] Copiando resources...
[INFO] Iniciando aplicacao JavaFX...
```

E a interface gráfica será aberta automaticamente!

## ❓ Problemas Comuns

### ❌ Erro: "Module not found"
**Causa**: JavaFX não está no local correto
**Solução**: Verificar se a pasta `java-libs\javafx\lib\` contém os arquivos .jar

### ❌ Erro: "UnsupportedClassVersionError"
**Causa**: Versão do Java incompatível
**Solução**: Instalar Java JDK 11 ou superior

### ❌ Interface não abre
**Causa**: Arquivos FXML não encontrados
**Solução**: Verificar se `src\resources\*.fxml` existem

## 📞 Suporte

Se ainda houver problemas:

1. Verifique se Java 11+ está instalado: `java -version`
2. Confirme a estrutura de pastas conforme mostrado acima
3. Execute o terminal como administrador (se necessário)
4. Verifique se o antivírus não está bloqueando os arquivos

---

> **Importante**: O JavaFX foi removido do JDK a partir da versão 11, por isso é necessário baixá-lo separadamente!