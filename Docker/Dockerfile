FROM mongo:latest
COPY --chown=999 --chmod=600 ./keyFile /etc/mongo/keyFile
RUN chmod 400 /etc/mongo/keyFile
COPY --chown=999 --chmod=600 ./mongo.conf /etc/mongod.conf