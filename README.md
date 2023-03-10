# MICROSERVIÇOS ORDER E HISTORY
  ## INFORMAÇÕES IMPORTANTES:
   - URI base microserviço order: http://localhost:8081/api 
   - URI base microserviço history: http://localhost:8082/api 
   - Ambos Microserviços podem ser executados individualmente, contanto que previamente seja inicializado o container com o Kafka.
   - Arquitetura Hexagonal


### Swagger

[Swagger - MS-History](https://github.com/Jonatas-Gomes/PB-SpringBoot-Final-Exam/blob/main/Ms-History/src/main/resources/static/openapi.yaml)  
[Swagger - MS-Order](https://github.com/Jonatas-Gomes/PB-SpringBoot-Final-Exam/blob/main/Ms-Order/src/main/resources/static/openapi.yaml)  

## TECNOLOGIAS UTILIZADAS NO PROJETO

- Kafka - Para trafegar informações de criação ou atualização de pedidos entre o microserviço order e o history
- OPEN-FEIGN - Para fazer comunicação com a api Via-Cep
- ModelMapper
- Lombok
- MongoDB
- MySQL
- Junit 5 
- Postman
- Spring Boot
- Docker - Containers contendo o kafka, o kafkadrop e o zookpeer


 ### Configuração Docker Compose
  ![KafkaContainerConfiguration](https://user-images.githubusercontent.com/57242457/215167206-2ef78767-37fe-40a1-b0df-69cfee53eef7.png)
 ### Inicialização dos containers
  ![ConteinerCodeExample](https://user-images.githubusercontent.com/57242457/215167967-29797542-f27d-418d-bfcd-3c622dc6c668.png)
  
 ## Endpoints do Microserviço Order
 

### GET - Request 

``` curl --location --request GET 'http://localhost:8082/api/historico' \ ```

![GetAllSemCPF](https://user-images.githubusercontent.com/57242457/215168574-501701b3-f25e-4012-9b02-a1bc89b1fbee.png)


### GET - Response  
 ![GetAllSemParamResponsePrint](https://user-images.githubusercontent.com/57242457/215168877-5a0a78f9-a41a-414b-a9fb-c21e49a4735f.png)

### POST - Request 

```   
   curl --location --request POST 'http://localhost:8081/api/pedidos' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "address": {
    "cep": "41200365",
    "number": "4143"
  },
  "cpf": "93267686512",
  "items": [
    {
      "name": "This is a name",
      "value": 25,
      "description": "this is a description",
      "expirationDate": "30-03-2023"
    }
  ]
}'

```

  
 ![POSTPedidosRequest2](https://user-images.githubusercontent.com/57242457/215169072-edc972a3-7ff5-4961-be4f-f96985780e59.png)

### POST - Response
 ![POSTPediosResponse](https://user-images.githubusercontent.com/57242457/215169908-2f42b8ea-580f-4741-b3c9-fd8827df4f05.png)

### PUT - Request

```
  curl --location --request PUT 'http://localhost:8081/api/pedidos/-72443552' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "address": {
    "cep": "41200365",
    "number": "4143"
  },
  "cpf": "93267686512"
}'

```

 ![Put](https://user-images.githubusercontent.com/57242457/215170180-37029400-6c74-4f9c-b901-490563c1004d.png)

### PUT - Response
  ![PUTRESPONSEPRINT](https://user-images.githubusercontent.com/57242457/215170497-f3710631-5aea-4b57-896e-74efb6e2391d.png)
  
### GET ID - Request
```
curl --location --request GET 'http://localhost:8081/api/pedidos/10' \
```

  ![GetId](https://user-images.githubusercontent.com/57242457/215171674-9f413cfe-f3cc-4d75-812e-0fe234269fe6.png)

### GET ID - Response

  
### PATCH - Request 

```
curl --location --request PATCH 'http://localhost:8081/api/pedidos/itens/-72443552' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "name": "This is a name",
  "value": 25,
  "description": "this is a description",
  "expirationDate": "30-03-2023"
}'

```

  ![PATCHRequest](https://user-images.githubusercontent.com/57242457/215172120-ea7890f9-2138-402b-97ec-cd7ae3a54076.png)
  
### PATCH - Response
  ![PatchResponse](https://user-images.githubusercontent.com/57242457/215172472-77b6069f-8cd7-4fef-958a-2e3b5183a591.png)

### DELETE - Request
  ```
  curl --location --request DELETE 'http://localhost:8081/api/pedidos/32' \
  
```
### DELETE - Response

  

## Endpoints do Microserviço History

### GET - Request

```
  curl --location --request GET 'http://localhost:8082/api/historico' \

```
![GetAllSemParam](https://user-images.githubusercontent.com/57242457/215183511-de13f9f3-afc6-405e-bbda-5591ba087c2e.png)


### GET - Response  

![GetAllSemParamResponsePrint](https://user-images.githubusercontent.com/57242457/215183763-38daecbd-2186-403a-ad13-d9715d5cb709.png)

## Testes Unitários
### Order
 ![OrderTests](https://user-images.githubusercontent.com/57242457/215184975-636065e1-ce80-4916-95cf-a504edafc7e8.png)
### History
![historyTestsCouveragePrintAll](https://user-images.githubusercontent.com/57242457/215185252-2dcd6a43-4780-4198-9e11-8aafa7054460.png)
