FROM mysql:8.0

EXPOSE 3306
# 設定ファイルをコンテナにコピー
COPY ./my.cnf /etc/mysql/my.cnf
# 設定ファイルの権限を変更
RUN chmod 644 /etc/mysql/my.cnf
# データの初期化を行うDDLをコンテナにコピー
COPY ./DDL /docker-entrypoint-initdb.d

FROM openjdk:11
RUN microdnf install findutils