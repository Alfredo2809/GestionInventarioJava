$ErrorActionPreference = 'Stop'
$raiz = Split-Path -Parent $PSScriptRoot
$jar = Join-Path $raiz 'build\libs\gestion-inventario-0.0.1-SNAPSHOT.jar'
$procfile = Join-Path $raiz 'deploy\aws-beanstalk\Procfile'
if (-not (Test-Path $jar)) { throw "No existe $jar. Ejecute .\gradlew.bat clean bootJar primero." }
$salida = Join-Path $raiz 'gestioninventario-beanstalk.zip'
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
if (Test-Path $salida) { Remove-Item $salida -Force }
$zip = [System.IO.Compression.ZipFile]::Open($salida, 'Create')
try {
    [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, $jar, 'app.jar') | Out-Null
    [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, $procfile, 'Procfile') | Out-Null
} finally { $zip.Dispose() }
Write-Host "Paquete creado: $salida (app.jar y Procfile en la raiz)."
