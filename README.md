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

## Fractalis

To use Fractalis, your users must have emails associated with them.

1.  Login as admin
2.  Select the `Admin` Menu
3.  Select `User List`
4.  For each user that will use Fractalis
    1.  Select `Show`
    2.  Select `Edit`
    3.  Add an email in the `Email` field
    4.  Take note of the `Roles` for the user [existing issue #1](existing-issues)
    5.  Select `Update`
    6.  Select `Edit`
    7.  Re-apply its `Roles`
5.  Select `Utilities`
6.  Select `Log Out`

Once you log back in, Fractalis will be available.

## Existing issues

1.  Updating any user removes its `Roles`. You neeed to re-edit the user to re-apply its `Roles`.
