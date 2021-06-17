INSERT INTO authority (name) VALUES ('ROLE_REGISTERED_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_table("type", email, first_name, last_name, "password", username, last_password_reset_date, allowed_to_login) VALUES('AD', 'admin@gmail.com', 'Admin', 'Adminovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 'admin', 1608560339402, true);
insert into user_authority(user_id, authority_id) values(1,2);
INSERT INTO users_table("type", email, first_name, last_name, "password", username, last_password_reset_date, allowed_to_login, age, height, weight, bmi, activity_count, score, allowed_to_eat, sex, body_type, activity_level, diet, category, previous_category, age_category) VALUES('RU', 'jelena@gmail.com', 'Jelena', 'Cupac', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 'jelenac11', 1608560339402, true, 0, 0, 0, 0, 0, 0, true, 'MALE', 'ECTOMORPH', 'INACTIVE', 'OMNIVORE', 'BEGINNER', 'BEGINNER', 'CHILD');
insert into user_authority(user_id, authority_id) values(2,1);
INSERT INTO users_table("type", email, first_name, last_name, "password", username, last_password_reset_date, allowed_to_login, age, height, weight, bmi, activity_count, score, allowed_to_eat, sex, body_type, activity_level, diet, category, previous_category, age_category) VALUES('RU', 'aleksa@gmail.com', 'Aleksa', 'Goljovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 'aleksag12', 1608560339402, true, 22, 186, 85, 23.5, 1, 255, true, 'MALE', 'MESOMORPH', 'INACTIVE', 'OMNIVORE', 'INTERMEDIATE', 'BEGINNER', 'YOUNG_ADULT');
insert into user_authority(user_id, authority_id) values(3,1);

insert into groceries(name, diet, calories, carbs, proteins, fats) values('Chicken Breast', 'OMNIVORE', 110, 0, 23.1, 1.2);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Beef', 'OMNIVORE', 272, 0, 27, 17.4);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Bacon', 'OMNIVORE', 540, 0, 37, 41.8);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Ham', 'OMNIVORE', 163, 3.8, 16.6, 8.6);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Tuna', 'OMNIVORE', 116, 0, 25.5, 0.8);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Egg', 'VEGETARIAN', 155, 1.1, 12.6, 10.6);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Milk', 'VEGETARIAN', 62, 5.4, 3.3, 3.4);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Potato', 'VEGAN', 86, 20, 1.7, 0.1);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Tomato', 'VEGAN', 18, 3.9, 0.9, 0.2);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Iceberg Lettuce', 'VEGAN', 14, 3, 0.9, 0.1);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Banana', 'VEGAN', 89, 22.8, 1.1, 0.3);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Apple', 'VEGAN', 52, 13.8, 0.3, 0.2);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Peanuts', 'VEGAN', 566, 16.1, 25.8, 49.2);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Avocado', 'VEGAN', 160, 8.5, 2, 14.7);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Beans', 'VEGAN', 143, 26.2, 9, 0.7);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Rice', 'VEGAN', 130, 28.2, 2.7, 0.3);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Muesli', 'VEGAN', 406, 65.6, 12.5, 10.9);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Pork', 'OMNIVORE', 117, 0, 22.4, 2.3);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Almonds', 'VEGAN', 577, 19.7, 21.3, 50.6);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Ketchup', 'VEGAN', 97, 25.2, 1.7, 0.3);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Sour cream', 'VEGETARIAN', 214, 4.3, 3.2, 21);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Chocolate', 'VEGETARIAN', 534, 59.4, 7.7, 29.7);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Honey', 'VEGETARIAN', 304, 82.4, 0.3, 0);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Sugar', 'VEGAN', 389, 99.6, 0, 0.2);
insert into groceries(name, diet, calories, carbs, proteins, fats) values('Bread', 'VEGAN', 265, 49.1, 9.2, 3.2);

insert into allergens(name) values ('Chicken Breast');
insert into allergens(name) values ('Beef');
insert into allergens(name) values ('Bacon');
insert into allergens(name) values ('Ham');
insert into allergens(name) values ('Tuna');
insert into allergens(name) values ('Egg');
insert into allergens(name) values ('Milk');
insert into allergens(name) values ('Potato');
insert into allergens(name) values ('Tomato');
insert into allergens(name) values ('Iceberg Lettuce');
insert into allergens(name) values ('Banana');
insert into allergens(name) values ('Apple');
insert into allergens(name) values ('Peanuts');
insert into allergens(name) values ('Avocado');
insert into allergens(name) values ('Beans');
insert into allergens(name) values ('Rice');
insert into allergens(name) values ('Muesli');
insert into allergens(name) values ('Pork');
insert into allergens(name) values ('Almonds');
insert into allergens(name) values ('Ketchup');
insert into allergens(name) values ('Sour cream');
insert into allergens(name) values ('Chocolate');
insert into allergens(name) values ('Honey');
insert into allergens(name) values ('Sugar');
insert into allergens(name) values ('Bread');

insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Tuna Salad', 254, 9.3, 24.3, 4.4, 10, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Caesar Salad', 187, 4.1, 18.5, 1.1, 15, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('French Omlette', 360, 7.7, 13.2, 12.9, 20, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Fruit Salad', 96, 11.3, 4.3, 0.3, 15, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Rice Cake', 121, 38.7, 18, 4.2, 35, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Chicken with Rice', 275, 14.1, 27.9, 8.5, 45, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Beef with Beans', 340, 6.5, 23.5, 17.8, 50, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Avocado Salad', 110, 11.4, 11, 0.9, 10, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Milkshake', 84, 28.3, 9.8, 1.2, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);
insert into meals(name, calories, carbs, proteins, fats, "time", description, average_grade) values ('Greek Moussaka', 305, 4.5, 12.1, 16.5, 60, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 0);

-- For Tuna Salad
insert into ingredients(grams, grocery_id) values(80, 5); -- Tuna
insert into ingredients(grams, grocery_id) values(50, 9); -- Tomato
insert into ingredients(grams, grocery_id) values(35, 10); -- Iceberg Lettuce
insert into meals_ingredients(meal_id, ingredient_id) values(1, 1);
insert into meals_ingredients(meal_id, ingredient_id) values(1, 2);
insert into meals_ingredients(meal_id, ingredient_id) values(1, 3);

-- For Caesar Salad
insert into ingredients(grams, grocery_id) values(120, 1); -- Chicken Breast
insert into ingredients(grams, grocery_id) values(50, 9); -- Tomato
insert into ingredients(grams, grocery_id) values(45, 10); -- Iceberg Lettuce
insert into ingredients(grams, grocery_id) values(30, 25); -- Bread
insert into meals_ingredients(meal_id, ingredient_id) values(2, 4);
insert into meals_ingredients(meal_id, ingredient_id) values(2, 5);
insert into meals_ingredients(meal_id, ingredient_id) values(2, 6);
insert into meals_ingredients(meal_id, ingredient_id) values(2, 7);

-- For French Omlette
insert into ingredients(grams, grocery_id) values(100, 6); -- Egg
insert into ingredients(grams, grocery_id) values(70, 4); -- Ham
insert into ingredients(grams, grocery_id) values(35, 9); -- Tomato
insert into meals_ingredients(meal_id, ingredient_id) values(3, 8);
insert into meals_ingredients(meal_id, ingredient_id) values(3, 9);
insert into meals_ingredients(meal_id, ingredient_id) values(3, 10);

-- For Fruit Salad
insert into ingredients(grams, grocery_id) values(120, 12); -- Apple
insert into ingredients(grams, grocery_id) values(110, 11); -- Banana
insert into ingredients(grams, grocery_id) values(35, 19); -- Almonds
insert into meals_ingredients(meal_id, ingredient_id) values(4, 11);
insert into meals_ingredients(meal_id, ingredient_id) values(4, 12);
insert into meals_ingredients(meal_id, ingredient_id) values(4, 13);

-- For Rice Cake
insert into ingredients(grams, grocery_id) values(250, 16); -- Rice
insert into ingredients(grams, grocery_id) values(80, 22); -- Chocolate
insert into ingredients(grams, grocery_id) values(30, 13); -- Peanuts
insert into meals_ingredients(meal_id, ingredient_id) values(5, 14);
insert into meals_ingredients(meal_id, ingredient_id) values(5, 15);
insert into meals_ingredients(meal_id, ingredient_id) values(5, 16);

-- For Chicken With Rice
insert into ingredients(grams, grocery_id) values(250, 16); -- Rice
insert into ingredients(grams, grocery_id) values(150, 1); -- Chicken Breast
insert into meals_ingredients(meal_id, ingredient_id) values(6, 17);
insert into meals_ingredients(meal_id, ingredient_id) values(6, 18);

-- For Beef With Beans
insert into ingredients(grams, grocery_id) values(250, 2); -- Beef
insert into ingredients(grams, grocery_id) values(250, 15); -- Beans
insert into meals_ingredients(meal_id, ingredient_id) values(7, 19);
insert into meals_ingredients(meal_id, ingredient_id) values(7, 20);

-- For Avocado Salad
insert into ingredients(grams, grocery_id) values(150, 14); -- Avocado
insert into ingredients(grams, grocery_id) values(100, 11); -- Banana
insert into ingredients(grams, grocery_id) values(50, 13); -- Peanuts
insert into meals_ingredients(meal_id, ingredient_id) values(8, 21);
insert into meals_ingredients(meal_id, ingredient_id) values(8, 22);
insert into meals_ingredients(meal_id, ingredient_id) values(8, 23);

-- For Milkshake
insert into ingredients(grams, grocery_id) values(250, 7); -- Milk
insert into ingredients(grams, grocery_id) values(40, 22); -- Chocolate
insert into ingredients(grams, grocery_id) values(35, 19); -- Almonds
insert into meals_ingredients(meal_id, ingredient_id) values(9, 24);
insert into meals_ingredients(meal_id, ingredient_id) values(9, 25);
insert into meals_ingredients(meal_id, ingredient_id) values(9, 26);

-- For Greek Moussaka
insert into ingredients(grams, grocery_id) values(200, 2); -- Beef
insert into ingredients(grams, grocery_id) values(300, 8); -- Potato
insert into meals_ingredients(meal_id, ingredient_id) values(10, 27);
insert into meals_ingredients(meal_id, ingredient_id) values(10, 28);


-- Admin Report
insert into admin_reports("date", eatings) values(1623276000000, 350);

insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Tuna Salad', 1, null, 57, 0);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Caesar Salad', 1, null, 49, 0);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('French Omlette', 1, null, 42, 0);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Fruit Salad', 1, null, 37, 0);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Rice Cake', 1, null, 31, 0);

insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('French Omlette', null, 1, 0, 4.9);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Caesar Salad', null, 1, 0, 4.7);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Avocado Salad', null, 1, 0, 4.3);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Tuna Salad', null, 1, 0, 4.1);
insert into meal_popularity(meal, admin_report_most_popular_id, admin_report_most_rated_id, eaten, rate) values('Chicken with Rice', null, 1, 0, 3.9);


-- Weeks and days
insert into allergen_user(user_id, allergen_id) values(3, 1); -- Chicken
insert into allergen_user(user_id, allergen_id) values(3, 2); -- Beef

insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, true, false, 3, 'GAIN_WEIGHT');
insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, true, false, 3, 'MAINTAIN_WEIGHT');
insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, true, false, 3, 'LOSE_WEIGHT');
insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, true, false, 3, 'LOSE_WEIGHT');
insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, true, false, 3, 'MAINTAIN_WEIGHT');
insert into weeks(goal_calories, goal_carbs, goal_proteins, goal_fats, submitted, cheat, user_id, goal) values(2000, 160, 210, 70, false, false, 3, 'MAINTAIN_WEIGHT');

insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(true, 2000, 160, 210, 70, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(true, 2000, 160, 210, 70, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(true, 2000, 160, 210, 70, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(false, 1895, 77, 130, 58, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(false, 0, 0, 0, 0, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(false, 0, 0, 0, 0, false, 6);
insert into days(submitted, calories, carbs, proteins, fats, exceed, week_id) values(false, 0, 0, 0, 0, false, 6);

insert into concrete_meals(grams, is_custom_meal, meal_id, day_id) values(150, false, 1, 4);
insert into concrete_meals(grams, is_custom_meal, meal_id, day_id) values(200, true, null, 4);
insert into concrete_meals(grams, is_custom_meal, meal_id, day_id) values(350, false, 2, 4);
insert into concrete_meals(grams, is_custom_meal, meal_id, day_id) values(80, false, 3, 4);

insert into ingredients(grams, grocery_id) values(250, 7); -- Milk, id 29
insert into ingredients(grams, grocery_id) values(40, 22); -- Chocolate, id 30
insert into ingredients(grams, grocery_id) values(35, 19); -- Almonds, id 31

insert into concrete_meals_ingredients(concrete_meal_id, ingredient_id) values(2, 29);
insert into concrete_meals_ingredients(concrete_meal_id, ingredient_id) values(2, 30);
insert into concrete_meals_ingredients(concrete_meal_id, ingredient_id) values(2, 31);

insert into alarms(user_id, message) values(3, 'You failed to follow your nutrition plan in the last two days. Be more careful in following days.');