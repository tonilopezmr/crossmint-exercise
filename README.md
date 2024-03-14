# crossmint-exercise

tonilopezmr.com Crossmint exercise

I have created an empty Spring boot project thinking I'd create an API, but finally I have created a simple App.

That's why to run the project you can use springBoot features.

### How to run it

Execute the TonilopezmrApplication:

 ```
./gradlew bootRun
```

### Code linter

Check code style:

```
./gradlew ktlintCheck
```

Check and solve the code styles problems that can be solved automatically:
```
./gradlew ktlintFormat
```

### Requests authentication

To be able to perform requests in the backend is mandatory to send in the header of the request:

```
Authorization: Bearer $token
```

where `$token` is the JWT token provided by the Strapi authentication endpoints.

#### Request authentication for testing endpoints

For the endpoints like `/prices` you need to use an user and password. This user and password are set in with the
application properties `test-endpoints.user` and `test-endpoints.password`


## Amazon Web Services

### Upload docker image to AWS ECR Repository

To upload a docker image of the backend to AWS just execute the following commands:

```
docker build -t Cactus-backend --build-arg PROFILE=dev .
aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 025093359923.dkr.ecr.eu-west-1.amazonaws.com
docker tag Cactus-backend:latest 025093359923.dkr.ecr.eu-west-1.amazonaws.com/Cactus-backend:latest
docker push 025093359923.dkr.ecr.eu-west-1.amazonaws.com/Cactus-backend:latest
```

### Deploy the image on Fargate

- AWS_ACCESS_KEY_ID
- AWS_DEFAULT_REGION
- AWS_SECRET_ACCESS_KEY

- DATABASE_CLIENT
- DATABASE_HOST
- DATABASE_USERNAME
- DATABASE_PASSWORD

- SECRET_KEY          (JWT)
- REPOSITORY_URL      (ECR endpoint)
- ENV_ECS_ARN_ROLE    (TASK ECS ARN ROLE)
- PROFILE             (springboot profile)
- SCHEDULERS_ACTIVE   (Activate internal workers)
- BLENDER_URL         (Blender full URL)
- KAFKA_BOOTSTRAP_SERVERS (Kafka endpoints and ports, comma separated)
- KAFKA_GROUP_ID      (Group ID)
- BLENDER_BUCKET      (Save blender results)

#### Deploying the backend for the first time

If you are deploying the backed for the first time, after pushing the image in the ECR Repository just follow
the next article from the section "_Deploying a Spring Boot Application on AWS Fargate_":

https://epsagon.com/development/deploying-java-spring-boot-on-aws-fargate/

#### Update the current deploy with a new image of the backend

If you have already created a cluster and just want only to update the docker image follow the next steps:

- Push the new image to the ECR Repository.
- Navigate to the Amazon ECS -> Task Definitions page.
- Select your task and click in create new revision, and click on create.
- Click in the Actions button and Update Service.
- Click on Skip to Review and Update Service.
- Wait until the task is updated, you can see the progress at the cluster page.
- If everything is working fine, you need to remove the previous task revision, go to Task Definitions
- Select the previous task revision and click on Actions -> Deregister

### CORS

You can configure CORS origins with the env variable `allowed.cors.origin` where you need to define all endpoints separated with commas.

Example:

```shell script
allowed.cors.origin=http://localhost:3000,https://backend.dev.Cactus.app
```