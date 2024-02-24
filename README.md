# PROJECT: 
FACEBOOK Clone (Simpler Version)
<hr>
<hr>

# General information
<hr>

### Architectural style: 
Microservices

### Project structure: 
Each service will be a module and is managed by parent folder (in parent pom file)

### Services:
| Service name         | Description | Port | Database | Database name |
|:---------------------|:------------|:----:|:--------:|:-------------:|
| gateway-service      |             | 8081 |          |               |
| discovery-service    |             | 8082 |          |               |
| user-service         |             | 8083 |          |               |
| post-service         |             | 8084 |          |               |
| notification-service |             | 8085 |          |               |
| auth-service         |             | 8086 |  MySQL   |   facebook    |
| common-service       |             | ---- |          |               |

### Set-up
#### Databases
    auth-service: 
        docker run -d -p 3306:3306 -v /etc/docker/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD= -e MYSQL_DATABASE=facebook -e MYSQL_USER= -e MYSQL_PASSWORD= --name mysql mysql