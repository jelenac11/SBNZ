INSERT INTO authority (name) VALUES ('ROLE_REGISTERED_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_table("type", email, first_name, last_name, "password", username, last_password_reset_date) VALUES('AD', 'admin@gmail.com', 'Admin', 'Adminovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 'admin', 1608560339402);
insert into user_authority(user_id, authority_id) values(1,2);
INSERT INTO users_table("type", email, first_name, last_name, "password", username, last_password_reset_date) VALUES('RU', 'jelena@gmail.com', 'Jelena', 'Cupac', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 'jelenac11', 1608560339402);
insert into user_authority(user_id, authority_id) values(2,1);
