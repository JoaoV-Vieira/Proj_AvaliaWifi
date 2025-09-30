@echo off
title Compilar AvaliaWifi
color 0B
cls

echo.
echo ========================================
echo      COMPILADOR AVALIAWIFI
echo ========================================
echo.

:: Verificar se Java está instalado
javac -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] JavaC (compilador Java) nao encontrado!
    echo Por favor, instale o Java JDK versao 8 ou superior.
    echo Download: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

:: Criar diretório bin se não existir
if not exist "bin" (
    echo [INFO] Criando diretorio bin...
    mkdir bin
)

:: Verificar se o driver PostgreSQL existe
if not exist "java-libs\jdbc-connectors\postgresql-42.7.8.jar" (
    echo [ERRO] Driver PostgreSQL nao encontrado!
    echo Baixe o driver em: https://jdbc.postgresql.org/
    echo E coloque em: java-libs\jdbc-connectors\postgresql-42.7.8.jar
    echo.
    pause
    exit /b 1
)

echo [INFO] Compilando codigo fonte...
echo.

:: Compilar todas as classes Java
javac -d bin -sourcepath src -cp "java-libs\jdbc-connectors\postgresql-42.7.8.jar" src\**\*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    echo Verifique os erros acima e corrija o codigo fonte.
    echo.
    pause
    exit /b 1
)

:: Copiar arquivo de propriedades para bin
if exist "src\database.properties" (
    echo [INFO] Copiando database.properties...
    copy "src\database.properties" "bin\" >nul
)

echo [SUCESSO] Projeto compilado com sucesso!
echo.
echo Arquivos gerados no diretorio 'bin\'
echo Para executar o sistema, rode: executar_avaliawifi.bat
echo.
pause
