FROM node

WORKDIR /todoweb
COPY . /todoweb

RUN npm update
RUN npm install -g vue-cli@2.9.3
RUN npm install
RUN npm run build

ENV HOST 0.0.0.0

CMD ["npm", "run", "start"]

EXPOSE 3000
