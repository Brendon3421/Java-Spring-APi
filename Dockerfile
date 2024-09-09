# Etapa de build
FROM ubuntu:latest as build

# Atualizar pacotes e instalar JDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Copiar o código fonte para o contêiner
COPY . .

# Construir o projeto usando Maven
RUN mvn clean install

# Etapa final
FROM ubuntu:latest

# Instalar JDK no estágio final
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Copiar o .jar gerado no estágio de build para o estágio final
COPY --from=build /target/todolist-1.0.0.jar /app.jar

# Expor a porta 8080
EXPOSE 8080

# Definir o ponto de entrada para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
