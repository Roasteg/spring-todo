FROM maven:3.9.3-sapmachine-17 AS dev

WORKDIR /todo-app/src

COPY . /todo-app/src

CMD [ "mvn", "spring-boot:run", "-DskipTests" ]


