<body>
    <h1>Brasil API CEP Management</h1>
    
   <h2>Overview</h2>
    <p>
        This application provides a RESTful API for managing addresses and interacting with the <a href="https://brasilapi.com.br/docs" target="_blank">Brasil API</a> for address-related information such as postal codes (CEP). 
    </p>
    
   <h2>Features</h2>
    <ul>
        <li><strong>Retrieve all addresses:</strong> Get all saved addresses from the database.</li>
        <li><strong>Search by CEP:</strong> Fetch address information from Brasil API using a CEP.</li>
        <li><strong>CRUD Operations:</strong> Create, retrieve, update, and delete address records.</li>
    </ul>
    
   <h2>Prerequisites</h2>
    <ul>
        <li>Java 21</li>
        <li>Maven</li>
        <li>Docker</li>
    </ul>
    
   <h2>Docker Compose Configuration</h2>
    <p>Ensure Docker is running and the following <code>docker-compose.yml</code> file is used to start the MySQL container:</p>
    <pre>
<code>
version: '3.8'

services:
  mysql:
    image: mysql:8.0.33
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: example
    ports:
      - "3307:3306"
    volumes:
      - devdojo-db:/var/lib/mysql

volumes:
  devdojo-db:
</code>
    </pre>
    <p>Start the container:</p>
    <pre><code>docker-compose up -d</code></pre>

   <h2>Configuration</h2>
    <p>Use the following environment variables in a YAML configuration file (<code>application.yml</code>):</p>
    <pre>
<code>
server:
  port: 7070
  error:
    include-stacktrace: NEVER
logging:
  level:
    org.springframework.security: DEBUG
spring:
  application:
    name: user-service
  datasource:
    url: jdbc:mysql://localhost:3307/user_service?useTimezone=true&serverTimezone=UTC&createDatabaseIfNotExist=true
    username: ${ROOT_USER:root}
    password: ${ROOT_PASSWORD:example}
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
brasil-api:
  base-url: ${BRASIL_API_BASE_URL:https://brasilapi.com.br/api}
  uri-cep: ${BRASIL_API_URI:/cep/v1/{cep}}
</code>
    </pre>

   <h2>Endpoints</h2>
    <p>All endpoints are prefixed with <code>/v1/address</code>.</p>
    
   <h3>1. Get All Addresses</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address</code></li>
        <li><strong>Method:</strong> GET</li>
        <li><strong>Response:</strong> Returns a list of all addresses in the database.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>GET:</strong> <code>http://localhost:7070/v1/address</code></li>
    </ul>
    
   <h3>2. Find Address by CEP</h3>
   <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address/search/{cep}</code></li>
        <li><strong>Method:</strong> GET</li>
        <li><strong>Path Variable:</strong> <code>cep</code> - The postal code (CEP) to search.</li>
        <li><strong>Response:</strong> Returns the address details for the given CEP.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>GET:</strong> <code>http://localhost:7070/v1/address/search/01001000</code></li>
    </ul>
    
   <h3>3. Find Address by ID</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address/{id}</code></li>
        <li><strong>Method:</strong> GET</li>
        <li><strong>Path Variable:</strong> <code>id</code> - The unique ID of the address.</li>
        <li><strong>Response:</strong> Returns the address details for the given ID.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>GET:</strong> <code>http://localhost:7070/v1/address/1</code></li>
    </ul>
    
   <h3>4. Save Address by CEP</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address/{cep}</code></li>
        <li><strong>Method:</strong> POST</li>
        <li><strong>Path Variable:</strong> <code>cep</code> - The postal code (CEP) to save.</li>
        <li><strong>Response:</strong> Returns the details of the saved address.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>POST:</strong> <code>http://localhost:7070/v1/address/01001000</code></li>
    </ul>
    
   <h3>5. Delete Address by ID</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address/{id}</code></li>
        <li><strong>Method:</strong> DELETE</li>
        <li><strong>Path Variable:</strong> <code>id</code> - The unique ID of the address to delete.</li>
        <li><strong>Response:</strong> Returns no content.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>DELETE:</strong> <code>http://localhost:7070/v1/address/1</code></li>
    </ul>
    
   <h3>6. Update Address</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/address</code></li>
        <li><strong>Method:</strong> PUT</li>
        <li><strong>Request Body:</strong> JSON representation of the address to update.</li>
        <li><strong>Response:</strong> Returns no content.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>PUT:</strong> <code>http://localhost:7070/v1/address</code></li>
        <li><strong>Body:</strong></li>
        <pre><code>{
    "id": 1,
    "street": "Updated Street",
    "city": "Updated City",
    "state": "Updated State"
}</code></pre>
    </ul>
    
   <h2>References</h2>
    <ul>
        <li><a href="https://brasilapi.com.br/docs" target="_blank">Brasil API Documentation</a></li>
    </ul>

   <p>Happy Coding! 🎉</p>
