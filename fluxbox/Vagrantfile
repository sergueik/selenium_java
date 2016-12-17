provision_selenium = ENV.fetch('PROVISION_SELENIUM', '')
selenium_version = ENV.fetch('SELENIUM_VERSION', '')
chromedriver_version = ENV.fetch('CHROMEDRIVER_VERSION', '')
firefox_version = ENV.fetch('FIREFOX_VERSION', '')
geckodriver_version = ENV.fetch('GECKODRIVER_VERSION', '')
chrome_version = ENV.fetch('CHROME_VERSION', '')
use_oracle_java = ENV.fetch('USE_ORACLE_JAVA', '')

# check if requested Chrome version is available on http://www.slimjetbrowser.com/chrome/
available_chrome_versions = %w|
    54.0.2840.71
    53.0.2785.116
    52.0.2743.116
    51.0.2704.84
    50.0.2661.75
    49.0.2623.75
    48.0.2564.109
    |

unless chrome_version.empty? or chrome_version =~ /(?:beta|stable|unstable)/ or available_chrome_versions.include?(chrome_version)
  puts 'CHROME_VERSION shoukd be set to "stable", "unstable" or "beta"'
  puts "Alternatively, few specific old Chrome versions available :\n" + available_chrome_versions.join("\n")
  exit
end

VAGRANTFILE_API_VERSION = '2'
basedir = ENV.fetch('HOME','') || ENV.fetch('USERPROFILE', '')
box_memory = ENV.fetch('BOX_MEMORY', '2048').to_i
basedir = basedir.gsub('\\', '/')
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = 'ubuntu/trusty64'
  # Localy cached vagrant box images from http://www.vagrantbox.es/ and  http://dev.modern.ie/tools/vms/linux/
  config_vm_box_name = 'trusty-server-amd64-vagrant-selenium.box'
  # config_vm_box_name = 'ubuntu-16.04-2.3.1.virtualbox.box'
  config.vm.box_url = "file://#{basedir}/Downloads/#{config_vm_box_name}"
  config.vm.network :forwarded_port, guest:4444, host:4444
  config.vm.network :private_network, ip: '192.168.33.10'
  # Configure common synced folder
  config.vm.synced_folder './' , '/vagrant'
  config.vm.provision 'shell', inline: <<-END_OF_PROVISION
#!/bin/bash
# set -x

#=========================================================
echo Install the packages
sudo apt-get -qq update
sudo apt-get -qqy install fluxbox xorg unzip vim default-jre rungetty wget
#=========================================================
echo Install the OpenJDK 8 backport for trusty
if false ; then
  # installing the oracle 8 JDK from ppa:webupd8team/java still stops on Oracle Licence Agreement prompt
  # for alternative install set USE_ORACLE_JAVA
  sudo add-apt-repository ppa:webupd8team/java -y
  sudo apt-get -qq update
  sudo apt-get -qqy install oracle-java8-installer
  sudo apt-get -qqy install oracle-java8-set-default
fi
sudo add-apt-repository -y ppa:openjdk-r/ppa
sudo apt-get -qqy update
sudo apt-get install -qqy openjdk-8-jdk
update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
#=========================================================
echo Set autologin for the Vagrant user
sudo sed -i '$ d' /etc/init/tty1.conf
sudo echo 'exec /sbin/rungetty --autologin vagrant tty1' >> /etc/init/tty1.conf
#=========================================================
cat <<EOF>> .profile
if [ ! -e '/tmp/.X0-lock' ] ; then
  echo -n Start X on login
  startx
else
  echo X is alredy running..
fi
EOF
#=========================================================
PROVISION_SELENIUM='#{provision_selenium}'
if [[ $PROVISION_SELENIUM ]]
then
  echo Updating Selenium app stack
  FIREFOX_VERSION='#{firefox_version}'
  if [[ $FIREFOX_VERSION ]]
  then
    echo Install Firefox version ${FIREFOX_VERSION}
    cd /vagrant
    PACKAGE_ARCHIVE="firefox-${FIREFOX_VERSION}.tar.bz2"
    if [ ! -e $PACKAGE_ARCHIVE ]
    then
      URL="https://ftp.mozilla.org/pub/firefox/releases/${FIREFOX_VERSION}/linux-x86_64/en-US/${PACKAGE_ARCHIVE}"
      wget -O $PACKAGE_ARCHIVE -nv $URL
    else
      echo Using already downloaded archive $PACKAGE_ARCHIVE
    fi
    mkdir -p /home/vagrant/firefox
    cd /home/vagrant
    tar xjf "/vagrant/${PACKAGE_ARCHIVE}"
    sudo cp -R firefox /usr/lib
  else
    echo Install the latest Firefox
    sudo apt-get -qqy install firefox
  fi
  #=========================================================
  SELENIUM_VERSION='#{selenium_version}'
  if [[ $SELENIUM_VERSION ]]
  then
    echo Download Selenium version $SELENIUM_VERSION
    SELENIUM_RELEASE=$(curl -# "https://selenium-release.storage.googleapis.com/" | sed -n "s/.*<Key>\\\\(${SELENIUM_VERSION}\\\\/selenium-server-standalone[^<][^>]*\\\\)<\\\\/Key>.*/\\\\1/p")
  else
    echo Download latest Selenium Server
    SELENIUM_RELEASE=$(curl -# https://selenium-release.storage.googleapis.com/ | sed -n 's/.*<Key>\\([^>][^>]*selenium-server-standalone[^<][^<]*\\)<\\/Key>.*/\\1/p')
    SELENIUM_VERSION=$(echo $SELENIUM_RELEASE | sed -n 's/.*selenium-server-standalone-\\([0-9][0-9.]*\\).jar/\\1/p')
  fi
  PACKAGE_ARCHIVE="selenium-server-standalone-${SELENIUM_VERSION}.jar"
  cd /vagrant
  if [ ! -e $PACKAGE_ARCHIVE ]
  then
    URL="https://selenium-release.storage.googleapis.com/${SELENIUM_RELEASE}"
    echo Downloading Selenium $PACKAGE_ARCHIVE from $URL
    wget -O $PACKAGE_ARCHIVE -nv $URL
  else
    echo Using already downloaded $PACKAGE_ARCHIVE
  fi
  cp $PACKAGE_ARCHIVE /home/vagrant/selenium-server-standalone.jar
  cd /home/vagrant
  chown vagrant:vagrant selenium-server-standalone.jar
  #=========================================================
  CHROME_VERSION='#{chrome_version}'
  if [[ $CHROME_VERSION ]]
  then
    case $CHROME_VERSION in
      beta|stable|unstable)
        wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
        apt-add-repository http://dl.google.com/linux/chrome/deb/
        apt-get -qq update
        apt-get install google-chrome-${CHROME_VERSION}
      ;;
      *)
        sudo apt-get remove -qqy -f google-chrome-stable
        echo Installing Chrome version $CHROME_VERSION
        export URL="http://www.slimjetbrowser.com/chrome/lnx/chrome64_${CHROME_VERSION}.deb"
        cd /vagrant
        PACKAGE_ARCHIVE="chrome64_${CHROME_VERSION}.deb"
        if [ ! -e $PACKAGE_ARCHIVE ]
        then
          echo Downloading Chrome from $URL
          wget -nv $URL
        else
          echo Using already downloaded archive $PACKAGE_ARCHIVE
        fi
        sudo apt-get install -qqy libxss1 libappindicator1 libindicator7
        sudo dpkg -i "chrome64_${CHROME_VERSION}.deb"
        # sudo rm "chrome64_${CHROME_VERSION}.deb"
        cd /home/vagrant
      ;;
    esac
  else
    echo Download the latest Chrome
    # http://askubuntu.com/questions/79280/how-to-install-chrome-browser-properly-via-command-line
    cd /tmp
    wget -nv "https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"
    sudo apt-get install -qqy libxss1 libappindicator1 libindicator7
    sudo dpkg -i google-chrome-stable_current_amd64.deb
    sudo rm google-chrome-stable_current_amd64.deb
    sudo apt-get install -qqy -f google-chrome-stable
    cd -
  fi
  #=========================================================
  CHROMEDRIVER_VERSION='#{chromedriver_version}'
  if [[ $CHROMEDRIVER_VERSION ]]
  then
    echo Download Chrome Driver version $CHROMEDRIVER_VERSION
  else
    echo Download latest Chrome Driver
    CHROMEDRIVER_VERSION=$(curl -# "http://chromedriver.storage.googleapis.com/LATEST_RELEASE")
  fi
  PACKAGE_ARCHIVE='chromedriver_linux64.zip'
  cd /vagrant
  if [ ! -e $PACKAGE_ARCHIVE ]; then
    URL="http://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip"
    wget -O $PACKAGE_ARCHIVE -nv $URL
  fi
  cd /home/vagrant
  unzip -o "/vagrant/${PACKAGE_ARCHIVE}"
  chown vagrant:vagrant chromedriver
  echo Installed Chrome Driver version $(./chromedriver --version| head -1 | cut -f 2 -d ' ')
  echo Done
  #=========================================================
  GECKODRIVER_VERSION='#{geckodriver_version}'
  if [[ $GECKODRIVER_VERSION ]]
  then
    echo Download the latest Gecko Driver version $GECKODRIVER_VERSION
  else
    echo Determine the latest version of Gecko Driver
    GECKODRIVER_VERSION=$(curl -insecure -L -# https://github.com/mozilla/geckodriver/releases | sed -n 's/.*<a href="\\/mozilla\\/geckodriver\\/releases\\/download\\/v\\([0-9.][0-9.]*\\)\\/geckodriver-.*-linux64.*/\\1/p' | head -1)
  fi
  URL="https://github.com/mozilla/geckodriver/releases/download/v${GECKODRIVER_VERSION}/geckodriver-v${GECKODRIVER_VERSION}-linux64.tar.gz"
  ARCHIVE='/var/tmp/geckodriver_linux64.tar.gz'
  echo Downloading GECKODRIVER version $GECKODRIVER_VERSION from $URL
  sudo wget -O $ARCHIVE -nv $URL
  cd /home/vagrant
  tar -xzf $ARCHIVE
  chown vagrant:vagrant geckodriver
  echo Installed Gecko Driver version $(./geckodriver --version | head -1 | cut -f 2 -d ' ')
  # TODO: geckodriver ERROR Address in use (os error 98)
  #=========================================================
  USE_ORACLE_JAVA='#{use_oracle_java}'
  if [[ $USE_ORACLE_JAVA ]]
  then
    echo Downloading Oracle JDK
    # https://www.digitalocean.com/community/tutorials/how-to-manually-install-oracle-java-on-a-debian-or-ubuntu-vps
    pushd /vagrant
    PACKAGE_ARCHIVE=jdk-linux-x64.tar.gz
    URL="http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
    sudo wget -O $PACKAGE_ARCHIVE --header "Cookie: oraclelicense=accept-securebackup-cookie" -nv $URL
    mkdir /opt/oracle-jdk 2>/dev/null
    tar -zxf $PACKAGE_ARCHIVE -C /opt/oracle-jdk
    update-alternatives --install /usr/bin/java java /opt/oracle-jdk/jdk1.8.0_05/bin/java 100
    update-alternatives --set java /opt/oracle-jdk/jdk1.8.0_05/bin/java 100
    update-alternatives --install /usr/bin/javac javac /opt/oracle-jdk/jdk1.8.0_05/bin/javac 100
    update-alternatives --set javac /opt/oracle-jdk/jdk1.8.0_05/bin/javac 100
    popd
  fi
fi
#=========================================================
echo Set screen resolution
cat <<EOF>> /home/vagrant/.fluxbox/startup
xrandr -s 1280x800
EOF
#=========================================================
echo Install tmux scripts
cat <<EOF> tmux.sh
#!/bin/sh
tmux start-server

tmux new-session -d -s chrome-driver
tmux send-keys -t chrome-driver:0 'export DISPLAY=:0' C-m
tmux send-keys -t chrome-driver:0 './chromedriver' C-m

tmux new-session -d -s selenium
tmux send-keys -t selenium:0 'export DISPLAY=:0' C-m

# NOTE options for java runtime.
tmux send-keys -t selenium:0 'java -Xmn512M -Xms1G -Xmx1G -jar selenium-server-standalone.jar' C-m
tmux send-keys -t selenium:0 'for cnt in {0..10}; do wget -O- http://127.0.0.1:4444/wd/hub; sleep 120; done' C-m

EOF
chmod +x tmux.sh
chown vagrant:vagrant tmux.sh
#=========================================================
echo Install startup scripts
cat <<EOF> /etc/X11/Xsession.d/9999-common_start
#!/bin/sh
/home/vagrant/tmux.sh &
xterm -fa fixed &
EOF
chmod +x /etc/X11/Xsession.d/9999-common_start
#=========================================================
echo -n 'Add host alias'
echo '192.168.33.1 host'| tee /etc/hosts
#=========================================================
echo Reboot the VM
sudo reboot

  END_OF_PROVISION

  config.vm.provider :virtualbox do |v|
    v.gui = true
    v.name = 'Selenium Fluxbox'
    v.customize ['modifyvm', :id, '--memory', box_memory ]
    v.customize ['modifyvm', :id, '--vram', '16']
    v.customize ['modifyvm', :id, '--clipboard', 'bidirectional']
    v.customize ['setextradata', 'global', 'GUI/MaxGuestResolution', 'any']
    v.customize ['setextradata', :id, 'CustomVideoMode1', '1280x900x32']
  end
end
