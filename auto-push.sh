git add .
echo -n "[+] Commit message: "
read msg
git commit -m "$msg"
git push -u origin master
