# Cara push ke GitHub SAYA (bukan GitHub teman)

Folder ini TIDAK punya folder `.git`, jadi tidak terhubung ke repo siapa pun.

## 1. (Opsional) Ganti placeholder ke username Anda
    .\rename.ps1 usernameanda      (Windows)
    ./rename.sh usernameanda       (Linux/Mac/Git Bash)

## 2. Buat repo KOSONG di akun GitHub Anda
github.com → New repository → jangan centang README/.gitignore/license.

## 3. Pastikan identitas git memakai akun Anda
    git config --global user.name  "Nama Anda"
    git config --global user.email "email-github-anda@contoh.com"
(cek: `git config user.name` dan `git config user.email`)

## 4. Init & push
    git init
    git add .
    git commit -m "Initial commit"
    git branch -M main
    git remote add origin https://github.com/USERNAME_ANDA/NAMA_REPO.git
    git push -u origin main

Cek remote: `git remote -v` -> harus URL akun Anda.
Jika diminta login dan yang muncul akun teman: hapus kredensial lama di
Windows "Credential Manager" -> "Windows Credentials" -> hapus entri `git:https://github.com`.
