# MICROSERVIÇOS ORDER E PEDIDOS  
  ## INFORMAÇÕES IMPORTANTES:
   - URI base microserviço order: http://localhost:8081/api 
   - URI base microserviço history: http://localhost:8082/api 
   - Ambos Microserviços podem ser executados individualmente, porém antes necessário inicializar o docker compose contendo o kafka.
   - Arquitetura Hexagonal


### Swagger

[Swagger - MS-History]()  
[Swagger - MS-Order]()  

## TECNOLOGIAS USADAS NO PROJETO

- Kafka - Para trafegar informações de novos pedido entre o microserviço order e o history
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
 
![GetAllSemCPF](https://user-images.githubusercontent.com/57242457/215168574-501701b3-f25e-4012-9b02-a1bc89b1fbee.png)


### GET - Response  
 ![GetAllSemParamResponsePrint](https://user-images.githubusercontent.com/57242457/215168877-5a0a78f9-a41a-414b-a9fb-c21e49a4735f.png)

### POST - Request 
 ![POSTPedidosRequest2](https://user-images.githubusercontent.com/57242457/215169072-edc972a3-7ff5-4961-be4f-f96985780e59.png)

### POST - Response
 ![POSTPediosResponse](https://user-images.githubusercontent.com/57242457/215169908-2f42b8ea-580f-4741-b3c9-fd8827df4f05.png)

### PUT - Request
 ![Put](https://user-images.githubusercontent.com/57242457/215170180-37029400-6c74-4f9c-b901-490563c1004d.png)

### PUT - Response
  ![PUTRESPONSEPRINT](https://user-images.githubusercontent.com/57242457/215170497-f3710631-5aea-4b57-896e-74efb6e2391d.png)
  


## Exemplos de cURL /history 

### Request - GET 

```json
```

### Response - GET  

```json
```

