#!/bin/bash
FFMPEG=/home/pos/lbarion/bin/ffmpeg-GVC 
FFMPEG_LOG=$GVC_HOME/tmp/ffmpeg.log
GVC_TMP=$GVC_HOME/tmp

OPT1=$1
OPT2=$2
OPT3=$3
OPT4=$4
OPT5=$5
OPT6=$6
OPT7=$7
OPT8=$8
OPT9=$9
shift
OPTA0=$9
shift
OPTA1=$9
shift
OPTA2=$9
shift
OPTA3=$9
shift
INPUT=$9
shift
OUTPUT=$9
shift
TASK=$9
shift
QTDE_NO=$9
shift
ID_REQUEST=$9

echo "Executing ffmpeg"  
echo "INPUT FILE="$INPUT  
echo "OUTPUT FILE="$OUTPUT  
echo "TASK="$TASK  
echo "QTDE_NO="$QTDE_NO  
echo "==========================="
echo "-r="$OPT1  
echo "-vcode="$OPT2  
echo "-maxrate="$OPT3  
echo "-b="$OPT4  
echo "-qmin="$OPT5  
echo "-qmax="$OPT6  
echo "-bufsize="$OPT7  
echo "-g"=$OPT8  
echo "-acodec"=$OPT9  
echo "-ar"=$OPTA0  
echo "-ab"=$OPTA1  
echo "-s"=$OPTA2  
echo "-aspect"=$OPTA3  
echo "============================="

TOTAL_DUR=`mplayer $INPUT -frames 0 -identify 2>/dev/null | grep ID_LENGTH | cut -c 11-`
echo "TOTAL_DUR=$TOTAL_DUR" 
PARTE_DUR=`expr $TOTAL_DUR / $QTDE_NO`
echo "PARTE_DUR=$PARTE_DUR" 
SS=`expr $TASK "*" $PARTE_DUR`

if [ $TASK = `expr $QTDE_NO - 1` ]
then
  T=`expr $TOTAL_DUR - $SS`
else
  T=$PARTE_DUR
fi

echo "O valor do SS="$SS
echo "O valor do T="$T

if [ "$TASK" -lt "$QTDE_NO" ]
then

	TMPInput=$GVC_TMP/TMP-$ID_REQUEST-$TASK-`basename $INPUT`
	TMPOutput=$GVC_TMP/TMP-$ID_REQUEST-$TASK-`basename $OUTPUT`

       	$FFMPEG -i $INPUT -y -ss $SS -t $T $TMPInput 1>> $FFMPEG_LOG 2>> $FFMPEG_LOG &
        wait
	echo "Arquivo parcial $TMPInput gerado com sucesso"

   	$FFMPEG -i $TMPInput -y -r $OPT1 -vcodec $OPT2 -maxrate $OPT3 -b $OPT4 -qmin $OPT5 -qmax $OPT6 -bufsize $OPT7 -g $OPT8 -acodec $OPT9 -ar $OPTA0 -ab $OPTA1 -s $OPTA2 -aspect $OPTA3 $TMPOutput 1>> $FFMPEG_LOG 2>> $FFMPEG_LOG &
	wait 
	echo "Arquivo parcial convertido para $TMPOutput"


	touch $TMPOutput.ok
	echo "Sinalizacao realizada gerando o arquivo $TMPOutput.ok"

else

	echo "Espera $QTDE_NO NOS terminarem a execucao"
	NOS_CONVERTIDOS=0
	while [ $NOS_CONVERTIDOS -lt $QTDE_NO ]; do
		CONVERTIDO_OK=$GVC_TMP/TMP-$ID_REQUEST-$NOS_CONVERTIDOS-`basename $OUTPUT`.ok
		if [ -f $CONVERTIDO_OK ]
		then
			echo "O NO $NOS_CONVERTIDOS terminou com sucesso"
			LISTA_ARQUIVOS="$LISTA_ARQUIVOS  /tmp/TMP-$NOS_CONVERTIDOS-"`basename $OUTPUT`
			NOS_CONVERTIDOS=`expr $NOS_CONVERTIDOS + 1`
		else
			sleep 3
			echo "Esperando termino de execucao do no $NOS_CONVERTIDOS"
		fi
	done
	
	echo "Ira agora juntar os arquivos $LISTA_ARQUIVOS"
	mencoder -mc 0 -noskip -ovc copy -oac mp3lame $LISTA_ARQUIVOS -o $OUTPUT 1>> $FFMPEG_LOG 2>> $FFMPEG_LOG

	echo "Limpar os arquivos temporarios"
	NOS_CONVERTIDOS=0
	while [ $NOS_CONVERTIDOS -lt $QTDE_NO ]; do
		rm -rf "$GVC_TMP/TMP-$ID_REQUEST-$NOS_CONVERTIDOS-"`basename $OUTPUT`.ok
		rm -rf "$GVC_TMP/TMP-$ID_REQUEST-$NOS_CONVERTIDOS-"`basename $INPUT`
		rm -rf "$GVC_TMP/TMP-$ID_REQUEST-$NOS_CONVERTIDOS-"`basename $OUTPUT`
		NOS_CONVERTIDOS=`expr $NOS_CONVERTIDOS + 1`
	done
fi
