## Dataabase setup
### Postgres
1. Install Postgres and login
    ```bash
    brew install postgresql
    brew services restart postgresql@14
    psql postgres # for MacOS
    ```
2. Create Database User and password
    ```postgresql
    CREATE DATABASE blog;
    CREATE USER blog_user WITH ENCRYPTED PASSWORD 'blog_password';
    GRANT ALL PRIVILEGES ON DATABASE blog TO blog_user;
    ```
### Mysql
1. Install Postgres and login
    ```bash
    brew install mysql
    brew services start mysql
    mysql -u root -p
    ```
2. Create Database User and password
    ```postgresql
    CREATE DATABASE blog;
    CREATE USER 'blog_user'@'localhost' IDENTIFIED BY 'blog_password';
    GRANT ALL PRIVILEGES ON blog.* TO 'blog_user'@'localhost';
    ```
