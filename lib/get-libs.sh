#!/usr/bin/env bash

EXT_LIBS_FILENAME=external.zip
EXT_LIBS_FILENAME_URL=https://raw.githubusercontent.com/raulgonsales/chess_record_player/master/${EXT_LIBS_FILENAME}
DIR=`pwd`
IMAGES_FILENAME=images.zip
IMAGES_FOLDER="src/main/resources/images"

if [ "`basename ${DIR}`" != "lib" ]
then
	DIR="./lib"
else
	DIR="."
    IMAGES_FOLDER="../"${IMAGES_FOLDER}
fi

mkdir ${IMAGES_FOLDER}

wget --directory-prefix=${DIR} ${EXT_LIBS_FILENAME_URL}
unzip ${DIR}/${EXT_LIBS_FILENAME} -d ${DIR}
unzip ${DIR}/${IMAGES_FILENAME} -d ${IMAGES_FOLDER}
rm ${DIR}/${EXT_LIBS_FILENAME} ${DIR}/${IMAGES_FILENAME}