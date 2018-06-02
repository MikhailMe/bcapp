# Bet Blockchain System - graduation qualification work

-----------------------------------------------------------------------------------------------------

## How to deploy Iroha
  * docker network create iroha-network
  * docker run --name some-postgres \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=mysecretpassword \
    -p 5432:5432 \
    --network=iroha-network \
   -d postgres:9.5
  * docker volume create blockstore
  * git clone https://github.com/hyperledger/iroha
  * docker run -it --name iroha \
    -p 50051:50051 \
    -v $(pwd)/iroha/example:/opt/iroha_data \
    -v blockstore:/tmp/block_store \
    --network=iroha-network \
    --entrypoint=/bin/bash \
    hyperledger/iroha:develop
       
       
## Congratulations! Now you are ready to start system
      
## How to start system
* docker start iroha
* docker start some-postgres
* docker exec -it iroha /bin/bash
* irohad --config config.docker --genesis_block genesis.block --keypair_name node0
* set IP-address in the Constants.java
* run android application
* enjoy
