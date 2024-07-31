### Setup infrastructure

- Setup database:
    + pull postgres image
    ```bash
    docker pull postgres:16-aline
    ```
    + then run cmd below:
    ```bash
    docker run --name yourname -p 5432:5432 -e POSTGRES_USER=yourusername-e POSTGRES_PASSWORD=yoursecretpassword-e POSTGRES_DB=food_delivery_identify -d postgres:16-alpine
    ```
    + and
    ```bash
    docker run --name yourname -p 5433:5432 -e POSTGRES_USER=yourusername-e POSTGRES_PASSWORD=yoursecretpassword-e POSTGRES_DB=food_delivery_profile -d postgres:16-alpine
    ```
