# i2b2/tranSMART release-18.1 Quick-Start

## Docker Machine Hardware Requirements

-   8GB RAM
-   80GB Hard Drive

## Deploy

```bash
$ cd deployments/i2b2transmart/release-18.1/quick-start

# images take several minutes to download
$ docker-compose pull

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
2.  i2b2/tranSMART, by default, uses self-signed certificates. If broswer complains about security, 'click OK' allow for security exception.
3.  Default username and passwords are used, e.g. admin/admin

## Troubleshoot

Biggest point of failure is deploying the database for the first time. Due to its size and resource requirements, your Docker client may timeout during first deployment of the database.

If you run into a timeout error:

```bash
ERROR: for quickstart_db_1  HTTPSConnectionPool(host='xxxx', port=2376): Read timed out. (read timeout=60)
```

Wait for the client to re-sync with the docker machine (~100s), and resume the deployment by running `docker-compose up -d db` again.
