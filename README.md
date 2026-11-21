# Évaluation Hibernate / JPA

Trois applications Java (Hibernate 6 + MySQL), une par exercice. Les commentaires du code sont en chinois.

| Dossier | Exercice | Entités |
|---|---|---|
| [exercice1](exercice1) | Gestion de stock | `Categorie`, `Produit`, `Commande`, `LigneCommandeProduit` |
| [exercice2](exercice2) | Gestion de projets | `Employe`, `Projet`, `Tache`, `EmployeTache` |
| [exercice3](exercice3) | Gestion de l'état civil | `Personne` (abstraite), `Homme`, `Femme`, `Mariage` |

## Structure de chaque projet

- `ma.projet.classes` (ex. 1 et 2) / `ma.projet.beans` (ex. 3) : entités JPA
- `ma.projet.util.HibernateUtil` : crée la `SessionFactory` à partir de `application.properties`
- `ma.projet.dao.IDao` : interface générique CRUD (+ `AbstractDao`, implémentation commune)
- `ma.projet.service` : services métier
- `ma.projet.test.Test` : programme de test

## Fonctionnalités demandées

**Exercice 1** (`ProduitService`) : produits par catégorie, produits commandés entre deux dates,
produits d'une commande (format de l'énoncé), produits dont le prix > 100 DH (requête nommée `Produit.findPrixSuperieur`).

**Exercice 2** : `EmployeService` (tâches réalisées, projets gérés), `ProjetService` (tâches planifiées,
tâches réalisées avec dates réelles), `TacheService` (prix > 1000 DH via la requête nommée `Tache.findPrixSuperieur`,
tâches réalisées entre deux dates).

**Exercice 3** : `HommeService` (épouses entre deux dates, hommes mariés à 4 femmes via l'API Criteria,
affichage des mariages), `FemmeService` (requête native nommée `Femme.nbrEnfantsEntreDates`,
requête nommée `Femme.marieesAuMoinsDeuxFois`, femme la plus âgée).

## Exécution

1. Démarrer MySQL (port 3306) et adapter l'utilisateur / mot de passe dans
   `src/main/resources/application.properties` (par défaut `root` sans mot de passe).
   La base est créée automatiquement (`createDatabaseIfNotExist=true`, `hbm2ddl.auto=create`).
2. Dans le dossier d'un exercice :

```bash
mvn compile exec:java
```

Prérequis : JDK 17+ et Maven.
