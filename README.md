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


## Exemplos de cURL /orders 

### Request - GET 

```json
```

### Response - GET  

```json
```

---  

## Exemplos de cURL /history 

### Request - GET 

```json
```

### Response - GET  

```json
```

