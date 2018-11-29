## Docker Stack Configuration

Service | Image | Tag  | Image Size
--------|-------------|------------|----------------|--------------
db | [dbmi/i2b2transmart-db](https://hub.docker.com/r/dbmi/i2b2transmart-db/) | oracle.12.2.0.1-ee-i2b2.1.7.09-tm.release-18.1-v.1.4.1-nhanes  | 62.3GB
i2b2-wildfly | [dbmi/i2b2-wildfly](https://hub.docker.com/r/dbmi/i2b2-wildfly/) | 1.7.09c-18.1-beta-hotfix-sg.0.9-alpha | 335MB
irct | dbmi/irct | 1.4.2c |  698MB
irctdb | dbmi/irct-db  |  mysql.5.7.22-irct.1.4.2-i2b2-wildfly | 598MB
transmart | [dbmi/i2b2transmart](https://hub.docker.com/r/dbmi/i2b2transmart/) |  release-18.1-beta-16| 317MB
rserve | dbmi/rserve | 3.2.1-tm.release-18.1 | 584MB
solr | dbmi/solr  | 4.5.0-tm.release-18.1 | 259MB
fractalis | sherzinger/fractalis | 1.3.1 | 2.57GB
redis | redis | alpine| 40.9MB
rabbitmq |  [rabbitmq](https://hub.docker.com/_/rabbitmq/) | alpine | 46.8MB
nginx | [dbmi/nginx](https://hub.docker.com/r/dbmi/nginx/)  | i2b2tm.release-18.1 | 23MB
