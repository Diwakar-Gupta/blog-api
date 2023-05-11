## Users
GET `/users/` return all users
POST `/users/` create new user
POST `/users/login?token=jwt/` get jwt auth token
POST `/users/login?token=auth_token/` get Server side auth token
GET `/users/{id}/` return User details

## Articles
GET `/articles?author={authod}&page={}&size={}/`  get Paginated Articles
POST `/articles/` create new Article
GET `/articles/{slug}/` get article with slug
PATCH `/articles/slug/` replace article with new one

## Comments

GET `/articles/{articleSlug}/comments/`  get comments for article
POST `/articles/{articleSlug}/comments/`  create new comments for article
GET `/comments/{commentId}/`  get comment info
DELETE `/comments/{commentId}/`  delete comment