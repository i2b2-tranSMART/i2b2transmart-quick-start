# i2b2/tranSMART release-18.1 Quick-Start, and PIC-SURE HPDS UI

_Database is pre-populated with the CDC NHANES public dataset (41,474 patients with 1,300 variables per patient)_

## Docker Host Hardware Requirements

-   4 cpu cores
-   8GB RAM
-   128GB of free Hard Drive space
-   [Docker for Mac](https://docs.docker.com/docker-for-mac) and [Docker for Windows](https://docs.docker.com/docker-for-windows/) are not supported unless you use docker-machine to create a VM that meets the requirements for RAM and Hard Drive space.

## Docker Network Requirements

-   HTTP (80) and HTTPS (443) ports (default)
-   Edit `.env` file to assign different ports

```bash
HTTP_PORT=
HTTPS_PORT=
```

## Compatible Docker Versions

    Docker: 17.06.2+
    docker-compose: 1.21.0+

## Deploy

```bash
$ cd deployments/i2b2transmart/release-18.1/quickstart

# start by running the new PIC-SURE HPDS UI, it will give you something to do while the other images download.
$ docker-compose up -d nhaneshpds

# The i2b2/tranSMART images will take several minutes to become available, while you wait you should check out the new PIC-SURE HPDS UI which will be the landing page of your stack. Browse to your docker-machine IP to try it out as you wait for the remaining steps of the setup to complete. You will still need to check on each of the following steps as they will run into intermittent network failures while you download about 40GB of compressed data. To get to i2b2/tranSMART once the following steps have completed successfully, click the Advanced Phenotype Search button in the PIC-SURE UI.

Note: when you run this it will download about 40GB of data from the internet. Please be mindful of this if you are charged for data transfer.

# images take several minutes to download, start by just downloading the DB image
$ docker-compose pull db

# NOTE: if you are running docker-compose version 1.21.0+
# and the pull command fails, try:
# $ docker-compose pull --no-parallel

# deploy database *first deploy only*
# database may hang here for a few minutes
$ export COMPOSE_HTTP_TIMEOUT=300
$ docker-compose up -d db

#download all other images while the DB starts up
$ docker-compose pull

# deploy i2b2/tranSMART + fractalis
$ docker-compose up -d
```

### To stop and remove the stack

```bash
# -v will remove any associated persistent volumes, including the database, with the stack
$ docker-compose down -v
```

## Test i2b2/tranSMART release-18.1

1.  Browse to your docker machine IP and click the Advanced Phenotype Search link.

## Troubleshoot

If you get "socket read timeout" errors while pulling the images, just retry the pull. Eventually it will succeed.

Biggest point of failure is deploying the database for the first time. Due to its size and resource requirements, your Docker client may timeout during first deployment of the database.

## Open discussion forum
https://discuss.i2b2transmart.org

## Docker-machine installation - MAC/Windows
https://tinyurl.com/docker-VM
