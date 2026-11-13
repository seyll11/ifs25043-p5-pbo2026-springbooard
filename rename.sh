#!/usr/bin/env bash
# Ganti placeholder "ifs00000" dengan username Anda. Pemakaian: ./rename.sh ifs18xyz
set -euo pipefail
NEW="${1:?Pemakaian: ./rename.sh <username>   contoh: ./rename.sh ifs18005}"
OLD="ifs00000"
[[ "$NEW" =~ ^[a-z][a-z0-9]*$ ]] || { echo "Username harus huruf kecil/angka, diawali huruf"; exit 1; }
grep -rIl "$OLD" . --exclude-dir=.git --exclude-dir=target --exclude=rename.sh --exclude=rename.ps1 \
  | xargs -r sed -i "s/$OLD/$NEW/g"
for base in src/main/java/org/delcom src/test/java/org/delcom; do
  [ -d "$base/${OLD}springbootcashflow" ] && mv "$base/${OLD}springbootcashflow" "$base/${NEW}springbootcashflow"
done
echo "Selesai. Username sekarang: ${NEW}"
