FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=gallery

# 設定ファイルをコンテナにコピー
#COPY my.cnf /etc/mysql/conf.d/my.cnf
# 設定ファイルの権限を変更
#RUN chmod 644 /etc/mysql/conf.d/my.cnf
# データの初期化を行うDDLをコンテナにコピー
COPY sql/init_data.sql /docker-entrypoint-initdb.d/

#FROM openjdk:15
#RUN microdnf install findutils
