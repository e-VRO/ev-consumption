# ECarConsumption 0.1
The main purpose of this project was to create a proof of concept to compute electrical consumption of an electrical vehicle.

## Requirements
+ Java 1.7
+ Maven 3
+ OSRM [back-end](https://github.com/Project-OSRM/osrm-backend) [(install)](https://github.com/Project-OSRM/osrm-backend/blob/master/README.md#quick-start)
+ Postgis database with elevation [(install)](#installation-of-postgis-database)

## How to

### Installation of postgis database with elevation
1. Install postgresql server [(install)](https://www.postgresql.org/download/)
2. Create database
3. Install ans enable postgis extension  on the database [(install)](http://postgis.net/install/)
4. Install osmosis [(install)](http://wiki.openstreetmap.org/wiki/Osmosis)
5. Install osmctools [(install)](https://github.com/ramunasd/osmctools)
6. Sample of script to get the last database of french territory elevation : (You have to replace with your values)

```bash
#!/bin/sh

###################################
######### CONFIGURATION ###########
###################################
URL_PBF="http://download.geofabrik.de/europe"
FILENAME="france-latest.osm.pbf"

OSMOSIS_DIR="/osmosis-latest"
DATA="/home/"
POSTGIS_IP="localhost"
POSTGIS_USERNAME=""
#POSTGIS_PASSWORD=""
POSTGIS_DATABASE="test"

TMP_O5M="tmp.o5m"
TMP_FILTERED="tmp_filtered.o5m"
OUT_FILTERED="out_filtered.osm.pbf"

CURRENT_DIR="$(pwd)"

###################################
######## END CONFIGURATION ########
###################################

cd $DATA

rm $FILENAME
wget $URL_PBF/$FILENAME

osmconvert $FILENAME -o=$TMP_O5M


osmfilter $TMP_O5M --parameter-file=$CURRENT_DIR/filter_parameters -o=$TMP_FILTERED
osmconvert $TMP_FILTERED -o=$OUT_FILTERED
rm $TMP_O5M
rm $TMP_FILTERED

#DB
dropdb -h $POSTGIS_IP -U $POSTGIS_USERNAME -W $POSTGIS_DATABASE
createdb -h $POSTGIS_IP -U $POSTGIS_USERNAME -W $POSTGIS_DATABASE
psql -h $POSTGIS_IP -U $POSTGIS_USERNAME -W -d $POSTGIS_DATABASE -c 'CREATE EXTENSION postgis;'
psql -h $POSTGIS_IP -U $POSTGIS_USERNAME -W -d $POSTGIS_DATABASE -c 'CREATE EXTENSION hstore;'
psql -h $POSTGIS_IP -U $POSTGIS_USERNAME -W -d $POSTGIS_DATABASE -f $OSMOSIS_DIR/script/pgsnapshot_schema_0.6.sql

#srtm

#direct to db
#maybe you have to add password=YOUR_PASS at the end
$OSMOSIS_DIR/bin/osmosis --read-pbf $OUT_FILTERED --write-srtm --write-pgsql host=$POSTGIS_IP user=$POSTGIS_USERNAME database=$POSTGIS_DATABASE


########two step if you have not enought RAM (remove the previous cmd)#######


#$OSMOSIS_DIR/bin/osmosis --read-pbf $OUT_FILTERED --write-srtm --write-pbf out_srtm.osm.pbf
#$OSMOSIS_DIR/bin/osmosis --read-pbf out_srtm.osm.pbf --write-pgsql host=$POSTGIS_IP user=$POSTGIS_USERNAME database=$POSTGIS_DATABASE


```

You have now a database with all elevation

### Set vehicle profile
When you have clone the repository (explanation [here](#get-sources)) You have to create your vehicle profile which will be use in computation,
for instance you can use the profile in the book quote at the end of this document which is about the nissan leaf. The format have to be like this :
```XMl
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
    <comment>Nissan Leaf configuration</comment>

    <entry key="ROLLING_RESISTANCE">0.132</entry>
    <entry key="AERODYNAMIC">0.000005</entry>
    <entry key="AUXILIARIES">0.183</entry>
    <entry key="POSITIVE_ELEVATION">0.00308</entry>
    <entry key="NEGATIVE_ELEVATION">-0.00254</entry>
</properties>
```
In order to work the webservice need the profile in the folder profile
### General configuration
When you have clone the repository (explanation [here](#get-sources)) you have to configure some point of the programme before use in the ```config.xml``` file :
```XMl
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
    <comment></comment>

    <entry key="DB_IP">{IP_POSGIS_DB}</entry>
    <entry key="DB_TABLE">{NAME_OF_TABLE}</entry>
    <entry key="DB_LOGIN">{LOGIN_OF_DB_USER}</entry>
    <entry key="DB_PASS">{PASSWORD_OF_DB_USER}</entry>


    <entry key="SERVER_OSRM_IP">{IP_OSRM_SERVER}</entry>
    <entry key="SERVER_OSRM_PORT">{PORT_OSRM_SERVER}</entry>

    <entry key="OPEN_WEATHER_API_KEY">{OPEN_WEATHER_API_KEY}</entry>

    <entry key="PROXY_HOSTNAME">{PROXY_HOSTNAME_IF_NEED}</entry>
    <entry key="PROXy_PORT">{PROXY_PORT_IF_NEED}</entry>
</properties>
```

You can get a free API key on [OpenWeatherMap](https://openweathermap.org/api). You need a 5 day / 3 hour forecast Key.

## Quick start

### Get sources
First of all you have to clone the repo :
```
git clone https://gitlab.com/helloworldFr/ECarConsumption.git
```

### Build dependencies
Run with maven the next command in the project folder to download all dependencies
```
mvn clean install
```

### Web service
To run the webservice after all the configuration are done use :
```
mvn clean compile spring-boot:run
```

By default server run on port 8080, to use the webService call :

```
http:\\{YOUR_IP}:8080\consumption?source={Longitute;Latitude}&destination={Longitute;Latitude}&profile{NAME_OF_PROFILE}
```

The profile is optional

The result is a JSON Object like :
```JSON
{
    "distance": 201077.203125 ,
    "time": 161.55667114257812 ,
    "positiveHeightDifference ": 546.6052367424022 ,
    "negativeHeightDifference ": -391.5529413856194 ,
    "consumption": 111.0530076118419
}
```

+ distance in meter
+ time in minute
+ positiveHeightDifference in meter
+ negativeHeightDifference in meter
+ consumption in kWh

### Matrix of consumption
You can use two mode from file or from inputs folder
For the two modes you need to compile the whole project with :
```
mvn clean compile
```
And the input format as to be like :
```JSON
[
    {
        "info": "info1",
        "longitude": 0.61156004923077,
        "latitude": 0.115694192
    },
...
]
```
#### Matrix from file
You need to give as parameters both profile.xml and input.json like this :

(You have to run this command in target folder)
```
java -cp . fr.univ_tours.polytech.di4.project.starter.WriteMatrix {PROFILE.XML} {INPUT.JSON}
```


The result will be write in a file name ```instance_{INPUT}.JSON``` and the format will be like :
```JSON
[
  [
    {
      "source": {
        "info": "info1",
        "longitude": 0.61156004923077,
        "latitude": 0.115694192
      },
      "destination": {
        "info": "info1",
        "longitude": 0.61156004923077,
        "latitude": 0.115694192
      },
      "distance": 0.0,
      "time": 0.0,
      "positiveHeightDifference": 0.0,
      "negativeHeightDifference": 0.0,
      "consumption": 0.0
    },
    ...
  ]
]
```


#### Matrix from folder
This only parameter is the profile so run the command like (in target folder) :
```
java -cp . fr.univ_tours.polytech.di4.project.starter.WriteMatrixFromFolder {PROFILE.XML}
```

The result of each file will be store in different file with the name ```instance_{INPUT}.JSON``` and the format will be like :
```JSON
[
    [
     {
       "source": {
         "info": "info1",
         "longitude": 0.61156004923077,
         "latitude": 0.115694192
       },
       "destination": {
         "info": "info1",
         "longitude": 0.61156004923077,
         "latitude": 0.115694192
       },
       "distance": 0.0,
       "time": 0.0,
       "positiveHeightDifference": 0.0,
       "negativeHeightDifference": 0.0,
       "consumption": 0.0
     },
     ...
    ]
]
```

## References in publications
When using the code in a (scientific) publication, please cite
```
@mastersthesis{
author={Archat, Mathieu and Gilbert, Théo and Ménard, Théo and Nibeaudeau, Timothy},
title={ECarConsumption},
type={Scolar Project},
school={Ecole Polytechnique de l’Université François Rabelais de Tours},
address={Tours, France},
year={2015-2016}
}
```


***

Base on part of [Cedric De Cauwer, Joeri Van Mierlo and Thierry Coosemans](http://www.mdpi.com/1996-1073/8/8/8573/pdf)
Implementation by Mahtieu Archat, Théo Gilbert, Théo Ménard, Timothy Nibeaudeau, Copyright (c) 2016-2017
