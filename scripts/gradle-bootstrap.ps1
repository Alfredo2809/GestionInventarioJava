# Lanzador autonomo de Gradle. No es el Wrapper oficial: descarga la distribucion
# Gradle 9.2.1 la primera vez; no requiere instalar Gradle globalmente.
$ErrorActionPreference = 'Stop'
$version = '9.2.1'
$project = Split-Path -Parent $PSScriptRoot
$base = Join-Path $project '.gradle-local'
$gradleHome = Join-Path $base "gradle-$version"
$gradle = Join-Path $gradleHome 'bin\gradle.bat'
if (-not (Test-Path $gradle)) {
    New-Item -ItemType Directory -Force -Path $base | Out-Null
    $file = Join-Path $base "gradle-$version-bin.zip"
    $uri = "https://services.gradle.org/distributions/gradle-$version-bin.zip"
    Write-Host "Descargando Gradle $version (primera ejecucion)..."
    Invoke-WebRequest -Uri $uri -OutFile $file -UseBasicParsing
    Expand-Archive -Path $file -DestinationPath $base -Force
    Remove-Item $file -Force
}
& $gradle @args
exit $LASTEXITCODE
