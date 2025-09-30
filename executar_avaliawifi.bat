@echo off
title Sistema AvaliaWifi - Monitoramento de Redes Wi-Fi
color 0F
cls

echo.
echo ========================================
echo     SISTEMA AVALIAWIFI - v1.0
echo     Monitoramento de Redes Wi-Fi
echo ========================================
echo.

:: Verificar se Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java nao encontrado!
    echo Por favor, instale o Java JRE ou JDK versao 8 ou superior.
    echo Download: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

:: Verificar se o diretório bin existe
if not exist "bin" (
    echo [ERRO] Diretorio 'bin' nao encontrado!
    echo Por favor, compile o projeto antes de executar.
    echo Use o comando: javac -d bin -sourcepath src src/service/AvaliaWifi.java
    echo.
    pause
    exit /b 1
)

:: Verificar se o driver PostgreSQL existe
if not exist "java-libs\jdbc-connectors\postgresql-42.7.8.jar" (
    echo [ERRO] Driver PostgreSQL nao encontrado!
    echo Verifique se o arquivo postgresql-42.7.8.jar esta em:
    echo java-libs\jdbc-connectors\
    echo.
    pause
    exit /b 1
)

:: Verificar se o arquivo de propriedades existe
if not exist "src\database.properties" (
    echo [AVISO] Arquivo database.properties nao encontrado em src\
    echo Certifique-se de configurar a conexao com o banco de dados.
    echo.
)

echo [INFO] Iniciando Sistema AvaliaWifi...
echo [INFO] Conectando ao banco PostgreSQL...
echo.

:: Executar o programa
java -cp "bin;java-libs\jdbc-connectors\postgresql-42.7.8.jar" service.AvaliaWifi

:: Capturar código de saída
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Ocorreu um erro durante a execucao do sistema.
    echo Codigo de erro: %errorlevel%
    echo.
    echo Possiveis causas:
    echo - Banco de dados PostgreSQL nao esta rodando
    echo - Credenciais incorretas no database.properties
    echo - Classe principal nao encontrada
    echo.
) else (
    echo.
    echo [INFO] Sistema finalizado com sucesso.
)

echo.
echo Pressione qualquer tecla para sair...
pause >nul
