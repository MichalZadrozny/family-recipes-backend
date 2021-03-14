INSERT INTO public.users(id, account_enabled, email, password, username)
VALUES (9999, 'true', 'email@email.com', '$2a$10$xO.rrYTVaTiFO5gcUofGPupL3bxpJbvTvB.pWwJkisjzFuzwKinmu', 'admin');

INSERT INTO public.recipes(id, description, diet, name, preparation_time, author_id, nutrients_id, rating_id)
VALUES (9999, 'Pyszne bananowe pancakes z dużą ilością białka. Perfekcyjna na śniadanie i kolację', 1,
        'Bananowe pancakes', 15, 9999, null, null);

INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (9999, 1, 'Banan', null, 9999);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10000, 1, 'Jajko', null, 9999);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10001, 30, 'Odżywki białkowej', 'gram', 9999);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10002, 5, 'Cynamonu', 'gram (jedna łyżeczka)', 9999);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10003, 5, 'Proszku do pieczenia', 'gram (jedna łyżeczka)', 9999);

INSERT INTO public.nutrients(id, calories, proteins, fats, carbs)
VALUES (9999, 338, 31.7, 8.4, 34.6);

UPDATE public.recipes
SET nutrients_id = 9999
WHERE id = 9999;

INSERT INTO public.ratings(id, average_rating)
VALUES (9999, 4.5);

INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (9999, 4, 1);
INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (9999, 5, 2);

INSERT INTO public.recipe_steps(recipe_id, steps, steps_key)
VALUES (9999, 'Wlej mleko do miski', 1);
INSERT INTO public.recipe_steps(recipe_id, steps, steps_key)
VALUES (9999, 'Wbij jajko do miski', 2);
INSERT INTO public.recipe_steps(recipe_id, steps, steps_key)
VALUES (9999, 'Wymieszaj', 3);
INSERT INTO public.recipe_steps(recipe_id, steps, steps_key)
VALUES (9999, 'Upiecz', 4);

UPDATE public.recipes
SET rating_id = 9999
WHERE id = 9999;


