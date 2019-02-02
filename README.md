<<<<<<< HEAD
## Pet Store Spring REST API

* ## Projeto
  * ### Especificações técnicas
    * **Linguagem de programação:** Java - jdk1.8.0_152 ou superior
    * **Gerenciador de dependências:** apache-maven-3.0.4
    * **Spring Boot:** 2.1.2
    * **Banco de dados:** H2 database - http://localhost:8080/h2
    * **Testes unitários:** Mockito + JUnit
    * **Swagger**: [petstore.yaml](etc/petstore.yaml)
    * **Postman**: [PetStore.postman_collection.json](etc/PetStore.postman_collection.json)

=======
## PetStore Spring REST API

```

```
>>>>>>> 18463ed6365d77bbe08cc4ac6bd141a6a8b13b33
  * ### Modelagem
    ![modelagem](etc/modelagem.png)

  * ### Representações

      Os modelos de entrada e saída são representados no formato JSON

      * **Cliente**

        *ClienteRequestDTO*
        ```json
        {
          "nome": "Pedro"
        }
        ```

        *ClienteResponseDTO*
        ```json
        {
          "id": 1,
          "nome": "Pedro"
        }
        ```
      * **Espécie**

        *EspecieRequestDTO*
        ```json
        {
          "descricao": "Cachorro"
        }
        ```

        *EspecieResponseDTO*
        ```json
        {
          "id": 1,
          "descricao": "Cachorro"
        }
        ```
      * **Pet**

        *PetRequestDTO*
        ```json
        {
          "nome": "Rex",
          "dataNascimento": "01/01/2019",
          "idCliente": 1,
          "idEspecie": 1
        }
        ```

        *PetResponseDTO*
        ```json
        {
          "id": 1,
          "nome": "Rex",
          "dataNascimento": "01/01/2019",
          "cliente": {
            "id": 1,
            "nome": "Pedro"
          },
          "especie": {
            "id": 1,
            "descricao": "Cachorro"
          }
        }
        ```
      * **Serviço**

        *ServicoRequestDTO*
        ```json
        {
          "observacao": "Consulta",
          "idTipoServico": 1,
          "valor": 80.00,
          "idPet": 1
        }
        ```

        *ServicoResponseDTO*
        ```json
        {
          "id": 1,
          "observacao": "Consulta",
          "dataHora": "11/01/2019 12:38:17",
          "tipoServico": "Consulta",
          "valor": 80.00,
          "pet": {
            "id": 1,
            "nome": "Rex",
            "dataNascimento": "01/01/2019",
            "cliente": {
              "id": 1,
              "nome": "Pedro"
            },
            "especie": {
              "id": 1,
              "descricao": "Cachorro"
            }
          }
        }
        ```

  * ### Requisições

      * **Cliente**

      Método | URL                                             | Entrada             | Saída
      ------ | ----------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/clientes           | *ClienteRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/clientes           |                     | 200 (OK) Lista *ClienteResponseDTO*
      GET    | http://localhost:8080/api/v1/clientes/{id}      |                     | 200 (OK) *ClienteResponseDTO*
      GET    | http://localhost:8080/api/v1/clientes/{id}/pets |                     | 200 (OK) Lista *PetResponseDTO*
      PUT    | http://localhost:8080/api/v1/clientes/{id}      | *ClienteRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/clientes/{id}      |                     | 204 (No Content)

      * **Espécie**

      Método | URL                                             | Entrada             | Saída
      ------ | ----------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/especies           | *EspecieRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/especies           |                     | 200 (OK) Lista *EspecieResponseDTO*
      GET    | http://localhost:8080/api/v1/especies/{id}      |                     | 200 (OK) *EspecieResponseDTO*
      GET    | http://localhost:8080/api/v1/especies/{id}/pets |                     | 200 (OK) Lista *PetResponseDTO*
      PUT    | http://localhost:8080/api/v1/especies/{id}      | *EspecieRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/especies/{id}      |                     | 204 (No Content)

      * **Pet**

      Método | URL                                             | Entrada          | Saída
      ------ | ----------------------------------------------- | ---------------- | ------ |
      POST   | http://localhost:8080/api/v1/pets               | *PetRequestDTO*  | 201 (Created)
      GET    | http://localhost:8080/api/v1/pets               |                  | 200 (OK) Lista *PetResponseDTO*
      GET    | http://localhost:8080/api/v1/pets/{id}          |                  | 200 (OK) *PetResponseDTO*
      GET    | http://localhost:8080/api/v1/pets/{id}/servicos |                  | 200 (OK) Lista *ServicoResponseDTO*
      PUT    | http://localhost:8080/api/v1/pets/{id}          | *PetRequestDTO*  | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/pets/{id}          |                  | 204 (No Content)

      * **Serviço**

      Método | URL                                             | Entrada             | Saída
      ------ | ----------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/servicos      	   | *ServicoRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/servicos      	   |                     | 200 (OK) Lista *ServicoResponseDTO*
      GET    | http://localhost:8080/api/v1/servicos/{id} 	   |                     | 200 (OK) *ServicoResponseDTO*
      GET    | http://localhost:8080/api/v1/servicos/buscaPorData?dataInicial=dd/MM/yyyy&dataFinal=dd/MM/yyyy |                     | 200 (OK) Lista *ServicoResponseDTO*
      PUT    | http://localhost:8080/api/v1/servicos/{id} 	   | *ServicoRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/servicos/{id} 	   |                     | 204 (No Content)
