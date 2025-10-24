@echo off
title Instalacao Automatica do JavaFX
color 0F
cls

echo.
echo ==========================================
echo    INSTALACAO AUTOMATICA DO JAVAFX
echo    Para o Sistema AvaliaWiFi
echo ==========================================
echo.

echo [INFO] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java nao esta instalado ou nao esta no PATH.
    echo        Instale o Java JDK 11 ou superior primeiro.
    pause
    exit /b 1
)

echo [INFO] Java encontrado!
echo.

rem Definir diretório do projeto
set "PROJETO_DIR=%~dp0"
set "JAVAFX_DIR=%PROJETO_DIR%java-libs\javafx"
set "TEMP_DIR=%TEMP%\javafx_install"

echo [INFO] Verificando se JavaFX ja esta instalado...
if exist "%JAVAFX_DIR%\lib\javafx.controls.jar" (
    echo [INFO] JavaFX ja esta instalado em: %JAVAFX_DIR%
    echo [INFO] Deseja reinstalar? (S/N)
    set /p "resposta=Opcao: "
    if /i not "%resposta%"=="S" (
        echo [INFO] Instalacao cancelada.
        pause
        exit /b 0
    )
)

echo.
echo [INFO] Criando diretorios necessarios...
if not exist "%PROJETO_DIR%java-libs" mkdir "%PROJETO_DIR%java-libs"
if not exist "%JAVAFX_DIR%" mkdir "%JAVAFX_DIR%"
if not exist "%TEMP_DIR%" mkdir "%TEMP_DIR%"

echo [INFO] Baixando JavaFX 17.0.2...
echo        Origem: https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip

rem Usar PowerShell para baixar o arquivo
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip' -OutFile '%TEMP_DIR%\javafx.zip'}"

if not exist "%TEMP_DIR%\javafx.zip" (
    echo [ERRO] Falha ao baixar o JavaFX.
    echo        Verifique sua conexao com a internet.
    echo        Ou baixe manualmente de: https://openjfx.io/
    pause
    exit /b 1
)

echo [INFO] Arquivo baixado com sucesso!
echo [INFO] Extraindo JavaFX...

rem Extrair usando PowerShell
powershell -Command "& {Add-Type -AssemblyName System.IO.Compression.FileSystem; [System.IO.Compression.ZipFile]::ExtractToDirectory('%TEMP_DIR%\javafx.zip', '%TEMP_DIR%')}"

echo [INFO] Copiando arquivos para o projeto...

rem Encontrar a pasta extraída (javafx-sdk-17.0.2)
for /d %%i in ("%TEMP_DIR%\javafx-sdk-*") do (
    echo [INFO] Copiando de: %%i
    xcopy "%%i\*" "%JAVAFX_DIR%\" /E /I /Y >nul 2>&1
)

echo [INFO] Limpando arquivos temporarios...
rmdir /s /q "%TEMP_DIR%" >nul 2>&1

echo [INFO] Verificando instalacao...
if exist "%JAVAFX_DIR%\lib\javafx.controls.jar" (
    echo.
    echo ==========================================
    echo    INSTALACAO CONCLUIDA COM SUCESSO!
    echo ==========================================
    echo.
    echo JavaFX instalado em: %JAVAFX_DIR%
    echo.
    echo Arquivos encontrados:
    dir "%JAVAFX_DIR%\lib\javafx*.jar" /b
    echo.
    echo [INFO] Agora voce pode executar a interface grafica com:
    echo        .\executar_gui.bat
    echo.
) else (
    echo.
    echo [ERRO] Instalacao falhou!
    echo        Tente baixar manualmente de: https://openjfx.io/
    echo        E extrair para: %JAVAFX_DIR%
    echo.
)

echo Pressione qualquer tecla para continuar...
pause >nul