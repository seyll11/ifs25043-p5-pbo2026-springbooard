# Windows PowerShell. Pemakaian:  .\rename.ps1 ifs18xyz
param([Parameter(Mandatory=$true)][string]$New)
$Old = "ifs00000"
if ($New -notmatch '^[a-z][a-z0-9]*$') { Write-Error "Username harus huruf kecil/angka, diawali huruf"; exit 1 }
Get-ChildItem -Recurse -File | Where-Object {
  $_.FullName -notmatch '\\(\.git|target|\.mvn)\\' -and $_.Name -notin @('rename.ps1','rename.sh','icon.png')
} | ForEach-Object {
  $c = Get-Content $_.FullName -Raw -Encoding UTF8
  if ($c -and $c.Contains($Old)) { [IO.File]::WriteAllText($_.FullName, $c.Replace($Old,$New), (New-Object Text.UTF8Encoding $false)) }
}
foreach ($b in @("src\main\java\org\delcom","src\test\java\org\delcom")) {
  $p = Join-Path $b "${Old}springbootcashflow"
  if (Test-Path $p) { Rename-Item $p "${New}springbootcashflow" }
}
Write-Host "Selesai. Username sekarang: $New"
