# i2b2/tranSMART release-18.1 Quick-Start

_Database is pre-populated with the NHANES public dataset_

## Docker Host Hardware Requirements

-   8GB RAM
-   128GB Hard Drive
-   [Docker for Mac](https://docs.docker.com/docker-for-mac) and [Docker for Windows](https://docs.docker.com/docker-for-windows/) are not supported

## Docker Network Requirements

-   HTTP (80) and HTTPS (443) ports (default)
-   Edit `.env` file to assign different ports

```bash
HTTP_PORT=
HTTPS_PORT=
```

## Compatible Docker Versions

    Docker: 17.06.2+
    docker-compose: 1.14.0+

## Current Service Versions

    nginx:          i2b2tm.release-18.1
    i2b2transmart:  release-18.1-beta-5
    database:       oracle.12.2.0.1-ee-i2b2.1.7.09-tm.release-18.1-v.1.3-nhanes
    rserve:         3.2.1-tm.release-18.1
    solr:           4.5.0-tm.release-18.1
    i2b2-wildfly:   1.7.09c-18.1-beta-hotfix
    fractalis:      0.4.2
    irct:           1.4.2
    irct database:  mysql.5.7.22-irct.1.4.2-i2b2-nhanes

## Docker Machine Network Requirements
-   Exposed port 80
-   Exposed port 443

## Deploy

```bash
# images take several minutes to download
$ docker-compose pull

# NOTE: if you are running docker-compose version 1.21.0+
# and the pull command fails, try:
# $ docker-compose pull --no-parallel

# deploy database *first deploy only*
# database may hang here for a few minutes
$ export COMPOSE_HTTP_TIMEOUT=300
$ docker-compose up -d db

# verify database is up and running *first deploy only*
$ docker-compose logs -f db
# db_1            |#########################
# db_1            | DATABASE IS READY TO USE!
# db_1            | #########################

# deploy i2b2/tranSMART + fractalis
$ docker-compose up -d
```

## Test i2b2/tranSMART release-18.1

1.  Browse to your docker machine IP
2.  i2b2/tranSMART, by default, uses self-signed certificates. If the browser complains about security, 'click OK' to allow for security exception.
3.  Default username and passwords are used, e.g. admin/admin

## Troubleshoot

Biggest point of failure is deploying the database for the first time. Due to its size and resource requirements, your Docker client may timeout during first deployment of the database.

Docker is transferring the data from the Docker database image to the named volume `quickstart_i2b2transmart-db`. Notice, by running `docker system df` the total volume size increase:

```bash
TYPE          TOTAL         ACTIVE        SIZE          RECLAIMABLE
Local Volumes 11            2             25.24GB       25.24GB (100%)
```

If you run into a timeout error:

```bash
ERROR: for quickstart_db_1  HTTPSConnectionPool(host='xxxx', port=2376): Read timed out. (read timeout=60)
```

Wait for data transfer to complete and the client to re-sync with the docker machine (~100s). Resume the deployment by running `docker-compose up -d db` again.
