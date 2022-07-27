set foreign_key_checks = 0;

delete from city;
delete from cuisine;
delete from state;
delete from payment_method;
delete from `role`;
delete from role_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from `user`;
delete from user_role;
delete from restaurant_responsible_user;
delete from `order`;
delete from order_item;
delete from product_image;
delete from oauth_client_details;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table `role` auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table `user` auto_increment = 1;
alter table `order` auto_increment = 1;
alter table order_item auto_increment = 1;

insert into cuisine (id, name) values (1, 'Brasileira');
insert into cuisine (id, name) values (2, 'Italiana');
insert into cuisine (id, name) values (3, 'Japonesa');
insert into cuisine (id, name) values (4, 'Portuguesa');

insert into state (id, name) values (1, 'Rio de Janeiro');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Minas Gerais');

insert into city (id, name, state_id) values (1, 'Petrópolis', 1);
insert into city (id, name, state_id) values (2, 'Teresópolis', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Belo Horizonte', 3);

insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open, address_city_id, address_zip_code, address_street, address_number, address_district)
values (1, 'Churrascaria Majórica', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '25610-081', 'Rua do Imperador', '754', 'Centro');
insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open)
values (2, 'Tourinho', 7.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open)
values (3, 'Le Gioie - Cucina Italiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open)
values (4, 'Farfarello Restaurante', 12, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open)
values (5, 'Nikko Sushi', 0, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, delivery_fee, cuisine_id, registration_date, registration_update_date, active, open)
values (6, 'Lusitânia Culinária Portuguesa', 15, 4, utc_timestamp, utc_timestamp, true, true);

insert into payment_method (id, description, registration_update_date) values (1, 'Cartão de crédito', utc_timestamp);
insert into payment_method (id, description, registration_update_date) values (2, 'Cartão de débito', utc_timestamp);
insert into payment_method (id, description, registration_update_date) values (3, 'Pix', utc_timestamp);

insert into permission (id, name, description) values (1, 'EDIT_CUISINE', 'Allows you to create or edit cuisines');
insert into permission (id, name, description) values (2, 'EDIT_PAYMENT_METHODS', 'Allows you to create or edit payment methods');
insert into permission (id, name, description) values (3, 'EDIT_CITIES', 'Allows you to create or edit cities');
insert into permission (id, name, description) values (4, 'EDIT_STATES', 'Allows you to create or edit states');
insert into permission (id, name, description) values (5, 'CONSULT_USERS_ROLES_PERMISSIONS', 'Allows you to consult users, roles and permissions');
insert into permission (id, name, description) values (6, 'EDIT_USERS_ROLES_PERMISSIONS', 'Allows you to create or edit users, roles e permissions');
insert into permission (id, name, description) values (7, 'EDIT_RESTAURANTS', 'Allows you to create, edit or manage restaurants');
insert into permission (id, name, description) values (8, 'CONSULT_ORDERS', 'Allows you to consult orders');
insert into permission (id, name, description) values (9, 'MANAGE_ORDERS', 'Allows you to manage orders');
insert into permission (id, name, description) values (10, 'GENERATE_REPORTS', 'Allows you to generate reports');

insert into `role` (id, name) values (1, 'ADMIN'), (2, 'MANAGER'), (3, 'EMPLOYEE'), (4, 'CUSTOMER');

insert into role_permission (role_id, permission_id)
select 1, id from permission;

# Adiciona permissoes no grupo do vendedor
insert into role_permission (role_id, permission_id)
select 2, id from permission where name like 'CONSULT_%';

insert into role_permission (role_id, permission_id)
select 2, id from permission where name = 'EDIT_RESTAURANTS';

# Adiciona permissoes no grupo do auxiliar
insert into role_permission (role_id, permission_id)
select 3, id from permission where name like 'CONSULT_%';

insert into `user` (id, name, email, password, registration_date) values
(1, 'Renato Borges Viana', 'rviana@faeterj-petropolis.edu.br', '$2a$12$HL8GQYyCww2O6LB2LC/Ba.lFyINww7kkbltwm1gKPLla6SLk6HhoC', utc_timestamp),
(2, 'Carlos Augusto Cabral de Mello', 'carlos.manager@majórica.com','$2a$12$HL8GQYyCww2O6LB2LC/Ba.lFyINww7kkbltwm1gKPLla6SLk6HhoC', utc_timestamp),
(3, 'João Aguiar', 'joao.employee@majórica.com', '$2a$12$HL8GQYyCww2O6LB2LC/Ba.lFyINww7kkbltwm1gKPLla6SLk6HhoC', utc_timestamp),
(4, 'Pedro Neri', 'mealsdelivery.customer+pedro@gmail.com', '$2a$12$HL8GQYyCww2O6LB2LC/Ba.lFyINww7kkbltwm1gKPLla6SLk6HhoC', utc_timestamp),
(5, 'Ana Gouveia', 'mealsdelivery.customer+ana@gmail.com','$2a$12$HL8GQYyCww2O6LB2LC/Ba.lFyINww7kkbltwm1gKPLla6SLk6HhoC', utc_timestamp);

insert into user_role (user_id, role_id) values (1, 1), (2, 2), (3, 3), (4, 4), (5, 4);

insert into restaurant_responsible_user (restaurant_id, user_id) values (1, 2);

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Filé Mignon', 'Delicioso Corte na Brasa com aprox. 800g, acompanha arroz, farofa, batata frita e salada.', 171.10, 0, 1);
insert into product (name, description, price, active, restaurant_id) values ('Picanha', 'Delicioso Corte na Brasa com aprox. 900g, acompanha arroz, farofa, batata frita e salada.', 232.10, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Contra filé com fritas', 'Porção com 500g de Contra Filé, 800g de Fritas, acompanha arroz e salada.', 28.90, 1, 2);

insert into product (name, description, price, active, restaurant_id) values ('Macarrão de forno com calabresa', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 60.90, 1, 3);

insert into product (name, description, price, active, restaurant_id) values ('Pizza de frango com Catupiry', 'Deliciosa Pizza de frango com 40 cm', 57.90, 1, 4);

insert into product (name, description, price, active, restaurant_id) values ('Sushi de Salmão', '4 uramaki skin, 4 zequinha, 5 niguiri de salmão, 6 shakemaki com cebolinha.', 112.00, 1, 5);

insert into product (name, description, price, active, restaurant_id) values ('Bacalhau ao Brás', 'Bacalhau desfiado, refogado no azeite com cebola e alho, envoltos em ovos batidos e batata palha.', 140.00, 1, 6);

#Pedido efetuado no restaurante 1 pelo Pedro
insert into `order` (id, code, restaurant_id, user_customer_id, payment_method_id, address_city_id, address_zip_code,
                    address_street, address_number, address_complement, address_district,
                  status, creation_date, subtotal, delivery_fee, total_price)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 4, 1, 1, '25710-190', 'Rua A. Fonseca', '741', 'Perto da Igreja Batista', 'Brasil', 'CREATED', utc_timestamp, 574.30, 10, 584.30);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, note)
values (1, 1, 1, 2, 78.9, 342.20, null);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, note)
values (2, 1, 2, 1, 232.10, 232.10, 'Ao ponto');

#Pedido efetuado no restaurante 4 pelo Pedro
insert into `order` (id, code, restaurant_id, user_customer_id, payment_method_id, address_city_id, address_zip_code,
                    address_street, address_number, address_complement, address_district,
                  status, creation_date, subtotal, delivery_fee, total_price)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 4, 2, 1, '25710-190', 'Rua A. Fonseca', '741', 'Perto da Igreja Batista', 'Brasil', 'CONFIRMED', utc_timestamp, 28.90, 7.50, 36.40);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, note)
values (3, 2, 3, 1, 28.90, 28.90, 'Contra filé bem-passado');

#Pedido efetuado no restaurante 5 pela Ana
insert into `order` (id, code, restaurant_id, user_customer_id, payment_method_id, address_city_id, address_zip_code,
                    address_street, address_number, address_complement, address_district,
                  status, creation_date, confirmation_date, delivery_date, subtotal, delivery_fee, total_price)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 5, 5, 1, 1, '25720-066', 'Rua Adalberto Ponte Cordeiro', '900', null, 'Brasil',
        'DELIVERED', '2022-07-14 11:30:12', '2022-07-14 11:32:12', '2022-07-14 11:55:37', 112.00, 0, 112.00);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, note)
values (4, 3, 6, 1, 112.00, 112.00, null);

#Pedido efetuado no restaurante 6 pela Ana
insert into `order` (id, code, restaurant_id, user_customer_id, payment_method_id, address_city_id, address_zip_code,
                    address_street, address_number, address_complement, address_district,
                  status, creation_date, confirmation_date, delivery_date, subtotal, delivery_fee, total_price)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 6, 5, 1, 1, '25720-066', 'Rua Adalberto Ponte Cordeiro', '900', null, 'Brasil', 'CANCELLED', '2022-07-14 11:30:12', '2022-07-14 11:32:12', null, 140.00, 15, 155.00);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, note)
values (5, 4, 7, 1, 140.00, 140.00, null);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'mealsdelivery-web', null, '$2a$12$R3jWvM.uqUBkIwKJKA4mGekFwrP2EgNMXleYV626lvA9IzMpWd.8O',
  'READ,WRITE', 'password', null, null,
  304167, null, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'delivery-analysis', null, '$2a$12$R3jWvM.uqUBkIwKJKA4mGekFwrP2EgNMXleYV626lvA9IzMpWd.8O',
  'READ,WRITE', 'authorization_code', 'http://www.deliveryanalysis.local:8081', null,
  null, null, false
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'invoicing', null, '$2a$12$R3jWvM.uqUBkIwKJKA4mGekFwrP2EgNMXleYV626lvA9IzMpWd.8O',
  'READ,WRITE', 'client_credentials', null, 'CONSULT_ORDERS,GENERATE_REPORTS',
  null, null, null
);

