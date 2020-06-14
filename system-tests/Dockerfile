FROM picoded/ubuntu-openjdk-8-jdk

COPY src src

COPY pom.xml /workspace/pom.xml

RUN rm src/test/resources/chromedriver

RUN apt-get update \
    && apt-get install -y software-properties-common

# Downloading and installing Maven
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downlaoding maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz http://us.mirrors.quenda.co/apache/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Downloading and installing Allure and other dependencies
RUN apt-add-repository ppa:qameta/allure \
    && apt-get update \
    && apt-get install -y \
     git \
     pgp \
     allure \
     wget \
     xvfb

# Downloading and installing Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm /etc/apt/sources.list.d/google.list \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

ARG CHROME_DRIVER_VERSION=83.0.4103.39

# Downloading and installing Chromedriver
RUN wget --no-verbose -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver.zip -d src/test/resources \
    && chmod 755 src/test/resources \
    && rm /tmp/chromedriver.zip

WORKDIR /workspace

CMD tail -f /dev/null
