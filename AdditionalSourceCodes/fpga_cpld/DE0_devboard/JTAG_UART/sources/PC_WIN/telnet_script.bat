@echo off & setlocal
PAUSE
:: Startzeit (in Hundertstelsekunden) holen und speichern
call :GetTime
set "Startzeit=%t%"
set "Start=%TimeInHSec%"
echo %Startzeit%

:: Zum Testen...
cd "C:/Users/Patrick Schmitt/Downloads/tst10/"
TST10.exe/r:script.txt

:: Endzeit (in Hundertstelsekunden) holen und speichern
call :GetTime
set "Endzeit=%t%"
set "End=%TimeInHSec%"
echo %EndZeit%

:: Ermittlung und Zerlegung/Formatierung der Differenz
set /a Diff=%End%-%Start%
:: Tageswechsel beachten
if %Diff% lss 0 set /a Diff+=8640000

set DiffRem=%Diff%
:: Hundertstelsekunden holen
call :GetPart 100
set hs=%Part%
:: Sekunden holen
call :GetPart 60
set s=%Part%
:: Minuten holen
call :GetPart 60
set m=%Part%
:: Stunden bleiben als Rest
set h=%DiffRem%

::Ausgabe der Differenz
echo Dauer: %h%:%m%:%s%,%hs%
PAUSE
goto :eof

:GetTime
:: Aktuelle Zeit verwenden ...
set t=%time%
:: ... zerlegen ...
for /f "tokens=1-4 delims=:," %%i in ("%t%") do set "h=%%i" & set "m=%%j" & set "s=%%k" & set "hs=%%l"
:: ... Oktalzahlklippen umschiffen ...
if %m:~0,1%==0 set m=%m:~1%
if %s:~0,1%==0 set s=%s:~1%
if %hs:~0,1%==0 set hs=%hs:~1%
:: ... und in Hundertstelsekunden-Wert umrechnen.
set /a TimeInHSec=((%h%*60+%m%)*60+%s%)*100+%hs%
goto :eof

:GetPart
::Anhand des Aufrufparameters (60 oder 100) aufspalten ...
set /a Part=%DiffRem%%%%1
:: ... und mit fuehrender Null formatieren sowie ...
if %Part% lss 10 set Part=0%Part%
:: ... noch aufzuteilenden Rest der Differenz ermitteln.
set /a DiffRem=%DiffRem%/%1
goto :eof