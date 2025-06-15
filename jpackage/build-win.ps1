$stopwatch = [System.Diagnostics.Stopwatch]::StartNew()
$mavenOutput = $true
# IMPORTANT : Set Java Environment arguments here!!!
$JAVA_ENVIRONMENT_ARGS = "-Dsun.awt.noerasebackground=true --add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED"
$JAVA_ENVIRONMENT_MODULES = "--add-modules java.logging"
# TODO : App args?
# IMPORTANT : Set app arguments here!!!
#$APP_ARGS = ""

# FIXME @important : showing console

$failed = $false
$failedJDeps = $false
$failedJLink = $false
$failedWinMSI = $false
$failedWinPortable = $false
# FIXME : Only maven build errors are reported, need to check status on all build steps.
$sep = "--------------------------------------------------"
Write-Host $sep
Write-Host "Running Windows Build Process"
Write-Host $sep

Write-Host "Reading pom.xml... " -NoNewLine
$pomTime = Measure-Command {
    [xml]$pom = Get-Content pom.xml
    $APP_NAME = $pom.project.name
    $AUTHOR = $pom.project.properties.vendor
    $ARTIFACT_ID = $pom.project.artifactId
    $APP_VERSION = $pom.project.version
    $MAIN_CLASS = $pom.project.properties."main-class"
    $JAVA_VERSION = $pom.project.properties."java-version"
}
Write-Host "$( "{0:N3}" -f $pomTime.TotalSeconds )s"
Write-Host "`tJava     : $JAVA_VERSION"
Write-Host "`tApp      : $APP_NAME v$APP_VERSION"
Write-Host "`tAuthor   : $AUTHOR v$APP_VERSION"
Write-Host "`tArtifact : $ARTIFACT_ID"
Write-Host "`tMain     : $MAIN_CLASS"
if (!$APP_NAME)
{
    throw("App name is not set!")
}
if (!$AUTHOR)
{
    throw("Author/Publisher/Vendor is not set!")
}
if (!$APP_VERSION)
{
    throw("App version is not set!")
}
if (!$ARTIFACT_ID)
{
    throw("Artifact ID is not set!")
}
if (!$MAIN_CLASS)
{
    throw("Main class is not set!")
}
if (!$JAVA_VERSION)
{
    throw("Java version is not set!")
}

if ($mavenOutput)
{
    Write-Host "Building JAR... "
    $jarTime = Measure-Command { mvn clean compile assembly:single | Out-Default }
}
else
{
    Write-Host "Building JAR... " -NoNewLine
    $jarTime = Measure-Command { mvn clean compile assembly:single }
    Write-Host "$( "{0:N3}" -f $jarTime.TotalSeconds )s"
}
if ($LASTEXITCODE -ne 0)
{
    mvn clean
    if ($LASTEXITCODE -eq 0)
    {
        throw("Failed to build JAR, but project was successfully cleaned. Make sure the project builds locally before building for distribution.")
    }
    else
    {
        throw("Failed to clean project. Make sure no other programs are using the 'target' folder, then try again.")
    }
}

Write-Host "Running JDEPS... " -NoNewline
$jdepsTime = Measure-Command { $JDEPS = jdeps --print-module-deps --ignore-missing-deps target/jar/$ARTIFACT_ID.jar }
Write-Host "$( "{0:N3}" -f $jdepsTime.TotalSeconds )s"
Write-Host "Dependencies: $JDEPS"
if ($LASTEXITCODE -ne 0)
{
    throw("Failed to find dependencies using JDEPS!")
}

Write-Host "Building JRE... " -NoNewLine
$jlinkTime = Measure-Command {
    jlink `
    --output target/jre `
    --strip-native-commands `
    --strip-debug `
    --no-man-pages `
    --no-header-files `
    --add-modules $JDEPS `
    --add-modules java.logging
}
Write-Host "$( "{0:N2}" -f $jlinkTime.TotalSeconds )s"
if ($LASTEXITCODE -ne 0)
{
    throw("Failed to build JRE using jlink!")
}

Write-Host "Building Windows Portable... " -NoNewline
$portableTime = Measure-Command {
    jpackage --type app-image `
    --name "$APP_NAME" `
    --main-jar "$ARTIFACT_ID.jar" `
    --vendor $AUTHOR `
    --main-class $MAIN_CLASS `
    --icon 'jpackage/AppIcon.ico' `
    --java-options $JAVA_ENVIRONMENT_ARGS `
    --runtime-image target/jre `
    --input target/jar `
    --dest target/win-portable `
    --arguments '--distribution:win-portable' `
    --win-console
}
Write-Host "$( "{0:N2}" -f $portableTime.TotalSeconds )s"
if ($LASTEXITCODE -ne 0)
{
    Write-Host "Failed to build Windows Portable!"
    $failed = $true
    $failedWinPortable = $true
}
Write-Host "Zipping Windows Portable... " -NoNewLine
$portableCompressTime = Measure-Command {
    Compress-Archive "target/win-portable/$APP_NAME" "target/$ARTIFACT_ID-win-portable.zip"
}
Write-Host "$( "{0:N2}" -f $portableCompressTime.TotalSeconds )s"
if ($LASTEXITCODE -ne 0)
{
    Write-Host "Failed to zip Windows Portable!"
    $failed = $true
    $failedWinPortable = $true
}

Write-Host "Building Windows Installer... " -NoNewLine
# FIXME : Vendor name
$msiTime = Measure-Command {
    jpackage --type msi `
    --name "$APP_NAME" `
    --vendor $AUTHOR `
    --main-jar "$ARTIFACT_ID.jar" `
    --main-class $MAIN_CLASS `
    --icon 'jpackage/AppIcon.ico' `
    --java-options $JAVA_ENVIRONMENT_ARGS `
    --app-version $APP_VERSION `
    --runtime-image target/jre `
    --input target/jar `
    --dest target `
    --arguments '--distribution:win-msi' `
    --win-per-user-install `
    --win-shortcut-prompt `
    --win-shortcut `
    --win-menu
}
Write-Host "$( "{0:N2}" -f $msiTime.TotalSeconds )s"
if ($LASTEXITCODE -ne 0)
{
    Write-Host "Failed to build Windows Installer!"
    $failed = $true
    $failedWinMSI = $true
}

Write-Host "Building Windows Installer DEBUG... " -NoNewLine
# FIXME : Vendor name
$msiTime = Measure-Command {
    jpackage --type msi `
    --name "$APP_NAME-debug" `
    --vendor $AUTHOR `
    --main-jar "$ARTIFACT_ID.jar" `
    --main-class $MAIN_CLASS `
    --icon 'jpackage/AppIcon.ico' `
    --java-options $JAVA_ENVIRONMENT_ARGS `
    --app-version $APP_VERSION `
    --runtime-image target/jre `
    --input target/jar `
    --dest target `
    --arguments '--distribution:win-msi' `
    --win-per-user-install `
    --win-console `
    --win-shortcut-prompt `
    --win-shortcut `
    --win-menu
}
Write-Host "$( "{0:N2}" -f $msiTime.TotalSeconds )s"
if ($LASTEXITCODE -ne 0)
{
    Write-Host "Failed to build Windows Installer DEBUG!"
    $failed = $true
    $failedWinMSI = $true
}

$title
$successPrefix = "> "
$failPrefix = "[FAILURE] >>>>>>>>>>>>>>>>>>>> "
if ($failed)
{
    $title = "BUILD FAILED >:("
}
else
{
    $title = "Build Success :)"
}

Write-Host $sep
Write-Host $title
Write-Host $sep
# Hard coded success for steps what will intentionally crash early when failed.
Write-Host $successPrefix -NoNewLine
Write-Host "jar     : $( "{0:N2}" -f $jarTime.TotalSeconds )s"
Write-Host $successPrefix -NoNewLine
Write-Host "jdeps    : $( "{0:N2}" -f $jdepsTime.TotalSeconds )s"
Write-Host $successPrefix -NoNewLine
Write-Host "jre      : $( "{0:N2}" -f $jlinkTime.TotalSeconds )s"
if ($failedWinPortable)
{
    Write-Host $failPrefix -NoNewLine
}
else
{
    Write-Host $successPrefix -NoNewLine
}
Write-Host "portable : $( "{0:N2}" -f $portableTime.TotalSeconds )s"
if ($failedWinMSI)
{
    Write-Host $failPrefix -NoNewLine
}
else
{
    Write-Host $successPrefix -NoNewLine
}
Write-Host "msi      : $( "{0:N2}" -f $msiTime.TotalSeconds )s"
Write-Host $sep
Write-Host "Total  : $( "{0:N2}" -f $stopwatch.Elapsed.TotalSeconds )s"
Write-Host $sep
#Write-Host ""
Write-Host "You can close this window."