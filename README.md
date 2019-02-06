# RickMortyDating
First project with Kotlin / Paging

Au 6/02/2019:
Je m'étais laisser 1 semaines pour voir ce que je pouvais arriver à faire dans ce laps de temps.
C'était ma 1ere vrai application sous Kotlin et "grosse" application (arriver à un produit fini/fonctionnelle) depuis assez lontemps.

Je stop le développement de cette 1ere mouture. Le code est stable même si il reste quelques léger bug d'affichage.

Problème restant:
- Pas d'injection de dépendance / trop de liaison forte entre certaines classes
Solution: implémenter des interfaces et une lib de di (Koin à test)

- Pas de test... J'ai bataillé avec mon architecture et notemment le paging.
Solution: revoir Mockito / robotelectrique / Hamcrest

- Pas de sauvegarde local / repository
Solution: implémenter Room et un repository pour le load des datas local ou web

- Fragment utilisé de manière grossière
Solution: implémenter le composant de Navigation des aar

Next step:
Je vais repartir de 0 en suivant plus les méthode de Clean Architecture et en essayant d'utiliser mieux les spécificitées de Kotlin
Je souhaite aussi intégrer les coroutines de kotlin.
Amélioré l'UI et notamment intégrer les data binding et des animations
Revoir l'architecture de l'appli (schéma à venir)
Les tests, les tests, les tests, mon plus gros point noir (avec les di)


