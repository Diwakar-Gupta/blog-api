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
    ```mysql
    CREATE DATABASE blog;
    CREATE USER 'blog_user'@'localhost' IDENTIFIED BY 'blog_password';
    GRANT ALL PRIVILEGES ON blog.* TO 'blog_user'@'localhost';
    ```
## Test
- **@DataJpaTest**: this is used to test only DB layer not controller and service
- **@SpringBootTest**: this can be used to test complete app very slow

There are 3 ways of testing
real > fake > mock
- mock: return values are hard coded
- fake: input data is fake
- real: input data taken from read site traffic. Say last 10 hours traffic.

## Authentication
### Crypto Graphy
```ags
encrypt(data, sign) -> encrypted
decrypt(encrypted, sign) -> data
```
```
sign(data) = encrypt(hash(data), key) -> signature
# hash is used to make data size small
```
1. one-way (cannot be reversed)
2. will be exactly same for same data (+ same key)
3. will be different if data changes (or key changes)
4. if someone changes data, they cannot generate 'signature' unless they know key

### JWT
https://jwt.io
- use atob(encrypt) to decrypt data
- use btoa(data) to encrypt data 

Key learnings:-
1. Server don't require to store token in db. 
2. Server can validate using its private key if token is valid or not. 
3. Hijacked token can compromise only that person not all users in site.
   - to prevent hijack use ssl
   - use invalidating token

### Crypto Token vs Server side token
```
Crypto Token                     |  Server side token
---------------------------------------------------------------------
1. Storage required is very low  |  1. storage required is high
2. CPU high, db query low        |  2. CPU low, db query
3. No Server side invalidation   |  3. yes server side invalidation
4. Stateless                     |  4. StateFull
5. Can't see details of logged   |  5. Can store details in db
   in users                      |     can see logged in users
6. Cannot limit simultaneous     |  6. Can limit number of logins
   login
```

### Implement Hash+Salt
```java
 @Bean
 public PasswordEncoder passwordEncoder(){
     // Hash + salt( cant remove it once mixed )
     // save password()
     // 			salt = "x length random string"
     // 			db.save( hash( password + salt) + salt )
     // verify password()
     // 			salt = "x sized suffix from DB"
     //			hash( password + salt ) == hashFromDB
     return new BCryptPasswordEncoder(); // one of the most popular encoder
 }
 ```

### Validating Token
There are multiple ways of authenticating
- token
- cookie
- ip in datacenter itself

we need to check them one by one even before controllers.
This checks are called by different names like:-
- guards
- filters: spring calls this
- middleware: mostly used

#### Implementation
Filter consists of 2 things
1. `AuthenticationConverter`: finds token or other think
2. `AuthenticationManager`: consumes token and converts to user

First sprint tries to convert( find token ), 2 things can happen
- converted(found token): Returns `Authentication(token)` which is consumed by `AuthenticationManager`.
  - `AuthenticationManager` consumes the token 2 think can happed
    - Valid token: set user or userid in `Authentication(token)` object.
    - Invalid token: returns `403 Not Authenticated`
- not converted(not found): `return null` then spring tries to find another converter(like cookie) and it goes on.

### OAuth

- Identification: Who you are; I am Tim
- Authentication: Can you prove you are Tim; Here see my ID card
- Authorization: Do you have permission to do this.

Status Code
1. 401( UnAuthorized ) : This actually means not authentication or needs authenticating
2. 403 ( forbidden ): Authenticated but dont have persmission

OAuth -> Authentication using other social media sites like google auth, facebook apple, github etc.

Client, Server, AuthServer(third party)

1. client -> server//login
2. client -> continue with authserver
3. Two ways to proceed
	1. Implicit Flow
		1. Authserver sends `auth_token` to client
		2. client fetches user details from authserver
		3. server not involved
		4. insecure
	2. Explicit Flow
		1. auth server sends `grant_code` to client
		2. client send `grant_code` to server
		3. server can request [user details or auth_token] Authserver will accept request from server+`grant_code` only no 
other person can use `grant_code`
		4. server involved
		5. secure

Both Usescases
1. Implicit
	1. no-server / server is only hosting files
	2. Generally service providing apps
	3. client is running on android or web app
2. Explicit
	1. server is full fledged
	2. mostly product app's

## Docker
Steps
1. Create `Dockerfile`
2. Build `docker build -t blog .`
3. Run `docker run -p 8080:8888 blog`