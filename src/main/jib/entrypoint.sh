#!/bin/bash
#echo "Current User:"$(whoami);
sed -i "s/{MODE}/${MODE}/g" app/resources/application.properties
sed -i "s,{DB_URL},${DB_URL},g" app/resources/application.properties
sed -i "s/{DB_USER}/${DB_USER}/g" app/resources/application.properties
sed -i "s/{DB_PWD}/${DB_PWD}/g" app/resources/application.properties
sed -i "s,{RSA_PRIVATE_KEY},${RSA_PRIVATE_KEY},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY},${RSA_PUBLIC_KEY},g" app/resources/application.properties
sed -i "s,{EVNID_API_LOGIN},${EVNID_API_LOGIN},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_EVN},${RSA_PUBLIC_KEY_EVN},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_GEN1},${RSA_PUBLIC_KEY_GEN1},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_GEN2},${RSA_PUBLIC_KEY_GEN2},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_GEN3},${RSA_PUBLIC_KEY_GEN3},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_HN},${RSA_PUBLIC_KEY_HN},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_NPC},${RSA_PUBLIC_KEY_NPC},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_CPC},${RSA_PUBLIC_KEY_CPC},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_SPC},${RSA_PUBLIC_KEY_SPC},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_HCM},${RSA_PUBLIC_KEY_HCM},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_NPT},${RSA_PUBLIC_KEY_NPT},g" app/resources/application.properties
sed -i "s,{RSA_PUBLIC_KEY_SYS},${RSA_PUBLIC_KEY_SYS},g" app/resources/application.properties
sed -i "s,{RSA_PRIVATE_KEY_SYS},${RSA_PRIVATE_KEY_SYS},g" app/resources/application.properties
sed -i "s,{ENABLE_SWAGGER},${ENABLE_SWAGGER},g" app/resources/application.properties
#exec java $JAVA_OPTS -cp $( cat /app/jib-classpath-file ) $( cat /app/jib-main-class-file )
cp -f java.security /opt/java/openjdk/conf/security/java.security
exec java $JAVA_OPTS -cp @/app/jib-classpath-file @/app/jib-main-class-file