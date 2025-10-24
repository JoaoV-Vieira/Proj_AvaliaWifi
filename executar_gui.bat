@echo off
title Sistema AvaliaWiFi - Interface Gráfica
color 0F
cls

echo.
echo ==========================================
echo    SISTEMA AVALIAWIFI - INTERFACE GRAFICA
echo    Monitoramento de Redes Wi-Fi
echo ==========================================
echo.

echo [INFO] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java não está instalado ou não está no PATH.
    echo        Instale o Java JDK 11 ou superior.
    pause
    exit /b 1
)

echo [INFO] Compilando projeto...
cd /d "%~dp0"

rem Criar diretórios se não existirem
if not exist "bin" mkdir bin
if not exist "bin\view" mkdir bin\view
if not exist "bin\view\controller" mkdir bin\view\controller

rem Verificar se as dependências do JavaFX existem
if not exist "java-libs\javafx" (
    echo [ERRO] JavaFX não encontrado em java-libs\javafx
    echo        Baixe o JavaFX SDK e extraia na pasta java-libs\javafx
    echo        URL: https://openjfx.io/
    pause
    exit /b 1
)

rem Compilar com JavaFX no classpath
echo [INFO] Compilando classes Java...
javac -d bin ^
    -cp "java-libs\jdbc-connectors\postgresql-42.7.8.jar;java-libs\javafx\lib\*" ^
    --module-path "java-libs\javafx\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    src\model\*.java src\dto\*.java src\repository\*.java ^
    src\service\*.java src\controller\*.java src\util\*.java ^
    src\view\*.java src\view\controller\*.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilação.
    pause
    exit /b 1
)

echo [INFO] Copiando resources...
xcopy "src\resources" "bin\resources\" /E /I /Y >nul 2>&1
copy "src\database.properties" "bin\" >nul 2>&1

echo [INFO] Iniciando aplicação JavaFX...
echo.
java -cp "bin;java-libs\jdbc-connectors\postgresql-42.7.8.jar;java-libs\javafx\lib\*" ^
    --module-path "java-libs\javafx\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    view.AvaliaWifiApplication

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha ao executar a aplicação.
    echo        Verifique se o PostgreSQL está rodando e as configurações estão corretas.
)

echo.
echo Pressione qualquer tecla para continuar...
pause >nul