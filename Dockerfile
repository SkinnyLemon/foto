FROM hseeberger/scala-sbt:11.0.14.1_1.6.2_2.13.8 as builder
COPY . .
RUN apt-get install unzip -y
RUN sbt dist
RUN unzip target/universal/fotos-1.0.zip -d build


FROM azul/zulu-openjdk-alpine:11 as packager
RUN { \
        java --version ; \
        echo "jlink version:" && \
        jlink --version ; \
    }
ENV JAVA_MINIMAL=/opt/jre
RUN jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.unsupported \
        # java.naming - javax/naming/NamingException
        # java.desktop - java/beans/PropertyEditorSupport
        # java.management - javax/management/MBeanServer
        # java.security.jgss - org/ietf/jgss/GSSException
        # java.instrument - java/lang/instrument/IllegalClassFormatException
    --compress 2 \
    --strip-debug \
    --no-header-files \
    --no-man-pages \
    --output "$JAVA_MINIMAL"


FROM alpine
RUN apk update
RUN apk upgrade
RUN apk add bash
ENV JAVA_MINIMAL=/opt/jre
ENV PATH="$PATH:$JAVA_MINIMAL/bin"
ENV GOOGLE_APPLICATION_CREDENTIALS=/fotos/credentials
COPY --from=packager "$JAVA_MINIMAL" "$JAVA_MINIMAL"
COPY --from=builder "/root/build/fotos-1.0" "/fotos"
COPY conf/application.conf /fotos/conf/application.conf
COPY config/credentials /fotos/credentials
WORKDIR /fotos
RUN ls
ENTRYPOINT bash bin/fotos
