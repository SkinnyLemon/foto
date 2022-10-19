curl -fsSL https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo gpg --dearmor -o /etc/apt/keyrings/temurin.gpg --yes
echo "deb [signed-by=/etc/apt/keyrings/temurin.gpg] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
sudo apt-get update
sudo apt-get install temurin-11-jdk unzip -y

unzip fotos.zip
cp conf/application.conf fotos-1.0/conf/application.conf
cd fotos-1.0
mkdir resources
sudo mount -t auto /dev/vdb resources
cd ..
