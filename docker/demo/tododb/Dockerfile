FROM golang:1.17.1 as build

RUN apt-get update && \
    apt-get install unzip -y

RUN wget https://github.com/progrium/entrykit/archive/refs/heads/master.zip \
    && echo testing the dir \
    && ls -a\
    && unzip master.zip \
    && rm master.zip \
    && cd entrykit-master/ \
    && make build \
    && mv build/Linux/entrykit /bin/entrykit


FROM mysql:8.0.33

COPY --from=build /bin/entrykit /bin/entrykit
RUN chmod +x /bin/entrykit && entrykit --symlink

COPY add-server-id.sh /usr/local/bin/
COPY etc/mysql/mysql.conf.d/mysqld.cnf /etc/mysql/mysql.conf.d/
COPY etc/mysql/conf.d/mysql.cnf /etc/mysql/conf.d/
COPY prepare.sh /docker-entrypoint-initdb.d
COPY init-data.sh /usr/local/bin/
COPY sql /sql

ENTRYPOINT [ \
  "prehook", \
    "add-server-id.sh", \
    "--", \
  "docker-entrypoint.sh" \
]

CMD ["mysqld"]
