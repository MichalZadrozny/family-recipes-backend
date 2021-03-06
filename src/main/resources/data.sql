INSERT INTO public.users(id, account_enabled, email, password, username)
VALUES (9999, 'true', 'email@email.com', '$2a$10$xO.rrYTVaTiFO5gcUofGPupL3bxpJbvTvB.pWwJkisjzFuzwKinmu', 'admin');

INSERT INTO public.users(id, account_enabled, email, password, username)
VALUES (10000, 'true', 'email2@email.com', '$2a$10$xO.rrYTVaTiFO5gcUofGPupL3bxpJbvTvB.pWwJkisjzFuzwKinmu', 'Głodomor');

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
VALUES (10000, 'Ciekawa wariacja standardowego śniadania. Jajecznica z Pieprzem Cayenne który dodaje lekkiej pikantności oraz Kurkumą dla smaku i koloru.', 2,
        'Jajecznica z Pieprzem Cayenne i Kurkumą', 15, 9999, null, null);

INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10004, 3, 'Jajka kurze', null, 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10005, 1, 'Średnia cebula', null, 10000);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10006, 70, 'Wędlina (można wybrać również boczek lub delikatną kiełbasę)', 'gram', 10000);
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
VALUES (10000, 'Lekko podsmaż kiełbasę a następnie dodaj cebulę');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10000, 'Jak cebula się zeszkli, zmniejsz ogień i dodaj jajka oraz przyprawy. Dobrze jest jeszcze dodać odrobinę soli i pieprzu wedle uznania');
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
VALUES (10009, 200, 'Serka Wiejskiego', 'gram', 10001);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10010, 4, 'Płatków owsianych', 'łyżki', 10001);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10011, 0.6, 'Wody', 'szklanki', 10001);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10012, 1, 'Banan', null, 10001);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10013, 1, 'Miodu', 'łyżeczka', 10001);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10014, 1, 'Posiekanych orzechów włoskich lub innych', 'łyżeczka', 10001);

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

-- Recipe 4

INSERT INTO public.recipes(id, description, diet, name, preparation_time, author_id, nutrients_id, rating_id, image_name)
VALUES (10002, 'BURRITO w zdrowszej fit wersji z indykiem zaczerpnięte z kanały Policzona Szama na Youtube. Zaledwie 17 g tłuszczów na jedno burito. Mimo odchudzonej wersji to meksykańskie danie jest smakowym wypasem. Dziś farsz do wrapów robiony na jednej patelni razem z ryżem, który bezpośrednio wchłania smaki. Kilkanaście minut pracy i dostajecie dwie przepyszne, solidne, pełnowartościowe porcje. Dziś cenowo policzone użyte składniki 15,59 / 2 = 7,80 zł (22.04.2021). Link do filmiku autora: https://www.youtube.com/watch?v=I4OHJX2EdT0&list=PL7a3rf01kAEXKoRauTJPfAWqpSbXb-QwB&index=41&ab_channel=PoliczonaSzama',
        0, 'Buritto - Policzona Szama', 30, 10000, null, null, '10002.jpg');

INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10015, 300, 'Piersi z indyka', 'gram', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10016, 0.5, 'Papryki czerwonej', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10017, 0.3, 'Papryki zielonej', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10018, 40, 'Kukurydzy', 'gram', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10019, 100, 'Ryżu parboiled', 'gram', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10020, 350, 'Passaty pomidorowej', 'ml', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10021, 50, 'Mozzarelli', 'gram', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10022, 0.5, 'Cebuli czerwonej', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10023, 3, 'Ząbki czosnku', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10024, 1, 'Natka pietruszki', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10025, 2, 'Wrapy', null, 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10026, 10, 'Oliwy', 'ml', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10027, 1.5, 'Oregano', 'łyżki', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10028, 2, 'Kminu rzymskiego', 'łyżeczki', 10002);
INSERT INTO public.ingredients(id, amount, name, unit, recipe_id)
VALUES (10029, 1, 'Czerwona papryka ostra', 'łyżeczka', 10002);

INSERT INTO public.nutrients(id, calories, proteins, fats, carbs)
VALUES (10002, 1394, 94, 34, 170);

UPDATE public.recipes
SET nutrients_id = 10002
WHERE id = 10002;

INSERT INTO public.ratings(id, average_rating)
VALUES (10002, 5);

INSERT INTO public.rating_ratings_map(rating_id, ratings_map, ratings_map_key)
VALUES (10002, 5, 1);

INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Pierś z indyka, papryki oraz cebulę pokroić w kostkę');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Poszatkować czosnek');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Na patelni podsmarzyć przez około minutę na średnim ogniu czosnek oraz połowę z posiekanej cebuli na 10 ml oliwy (można więcej, lecz tyle jest wliczone w makroskładniki');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Dodać kmin rzymski oraz oregano i podsmażyć przez około 10-20s mieszając');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Dodać indyka i delikatnie podsmażyć');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Lekko doprawić solą i pieprzem');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Dodać pasatę, ryż oraz papryki');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Smażyć na delikatnym ogniu przez około 15-20min dodając co jakiś czas wody aż ryż będzie gotowy');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Po kilku minutach smażenia dodać czerwoną paprykę ostrą');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Posiekać natkę pietruszki');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Wymieszać pietruszkę wraz z kukurydzą i drugą połową posiekanej cebuli');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Połączyć mieszankę z poprzedniego kroku wraz z zawartością patelni');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Zetrzeć ser mozzarella');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Podzielić ser oraz farsz na 2 i zawinąć we wrapy');
INSERT INTO public.recipe_steps(recipe_id, steps)
VALUES (10002, 'Wrzucić buritto do piekarnika rozgrzanego do 220 stopni celcjusza (termoobieg) do momentu aż się zarumienią');

UPDATE public.recipes
SET rating_id = 10002
WHERE id = 10002;