INSERT INTO public.users(id, account_enabled, email, password, username)
VALUES (9999, 'true', 'email@email.com', '$2a$10$xO.rrYTVaTiFO5gcUofGPupL3bxpJbvTvB.pWwJkisjzFuzwKinmu', 'admin');

-- Recipe 1

INSERT INTO public.recipes(id, description, diet, name, preparation_time, author_id, nutrients_id, rating_id)
VALUES (9999, 'Pyszne bananowe pancakes z dużą ilością białka. Perfekcyjna na śniadanie i kolację', 0,
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

INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (9999, 'Rozgnieć banana w misce');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (9999, 'Dodaj wszystkie składniki i wymieszaj');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (9999, 'Rozgrzej patelnię na średnim ogniu i dodaj preferowany tłuszcz');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (9999, 'Smaż do momentu aż placuszki będą lekko brązowe');

UPDATE public.recipes
SET rating_id = 9999
WHERE id = 9999;

-- Recipe 2

INSERT INTO public.recipes(id, description, diet, name, preparation_time, author_id, nutrients_id, rating_id)
VALUES (10000, 'Ciekawa wariacja standardowego śniadania. Jajecznica z Jajecznica z Pieprzem Cayenne który dodaje lekkiej pikantności oraz Kurkumą dla smaku i koloru.', 2,
        'Jajecznica z Pieprzem Cayenne i Kurkumą', 15, 9999, null, null);

INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10004, 3, 'Jajka kurze', null, 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10005, 1, 'Średnia cebula', null, 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10006, 70, 'Kiełbasy (można wybrać również boczek czy ulubioną wędlinę)', 'gram', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10007, 1, 'Pieprzu Cayenne', 'szczypta', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10008, 1, 'Kurkumy', 'szczypta', 10000);

INSERT INTO public.nutrients(id, calories, proteins, fats, carbs)
VALUES (10000, 386, 36.4, 24.5, 5.8);

UPDATE public.recipes
SET nutrients_id = 10000
WHERE id = 10000;

INSERT INTO public.ratings(id, average_rating)
VALUES (10000, 4);

INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (10000, 4, 1);
INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (10000, 4, 2);

INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Posiekaj cebulę oraz kiełbasę w kostkę');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Rozgrzej patelnię na średnim ogniu i dodaj preferowany tłuszcz');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Lekko podsmaż cebulę a następnie dodaj kiełbasę');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Jak kiełbasa się podsmaży, zmniejsz ogień i dodaj jajka oraz przyprawy');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Smaż jajecznicę do momenctu lekkiego ścięcia');

UPDATE public.recipes
SET rating_id = 10000
WHERE id = 10000;

-- Recipe 3

INSERT INTO public.recipes(id, description, diet, name, preparation_time, author_id, nutrients_id, rating_id, image_name)
VALUES (10001, 'Przepyszna słodka owsianka', 0,
        'Owsianka z serkiem wiejskim i bananem', 5, 9999, null, null, '10001.jpg');

INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10009, 200, 'Serka Wiejskiego', 'gram', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10010, 4, 'Płatków owsianych', 'łyżki', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10011, 0.6, 'Wody', 'szklanki', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10012, 1, 'Banan', null, 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10013, 1, 'Miodu', 'łyżeczka', 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10014, 1, 'Posiekanych orzechów włoskich lub innych', 'łyżeczka', 10000);

INSERT INTO public.nutrients(id, calories, proteins, fats, carbs)
VALUES (10001, 553, 30.4, 17.4, 71.9);

UPDATE public.recipes
SET nutrients_id = 10001
WHERE id = 10001;

INSERT INTO public.ratings(id, average_rating)
VALUES (10001, 5);

INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (10001, 5, 1);
INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (10001, 5, 2);

INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10001, 'Wrzuć do garnka płatki owsiane i zalej wodą a następnie gotuj na lekkim ogniu do momentu aż płatki wchłoną wodę');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10001, 'Przerzuc płatki do miseczki, dodaj werek wiejski i łyżeczkę miodu, a następnie wszystko razem wymieszaj');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10001, 'Na wierzch dania dodaj posiekane orzechy oraz pokrojonego banana');

UPDATE public.recipes
SET rating_id = 10001
WHERE id = 10001;