# Corona Bounce

* Outil de simulation de la propagation de la COVID-19.

## Presentation

Corona Bounce est un outil de simulation et d'analyse du comportement du virus.Son objectif et d'aider à déterminer le meilleur comportement à adopter face à la situation.

Cet outil :

 - dispose de graphiques qui évoluent au cours d'une simulation pour optimiser l'analyse de l'utilisateur et pour lui fournir plus de données.
 - permet d'éditer un environnement.
 - permet à l'utilisateur de configurer plusieurs paramètres avant de commencer la simulation tel que :
    - le nombre d'occupants de l'environnement ou se déroulera le scénario.
    - la probabilité/possibilité de voyage des occupants de l'environnement.
    - la probabilité de transmission du virus lors d'un contact entre deux personnes.
    - la probabilité de tomber malade si une personne voyage.
 - permet à l'utilisateur de régler certains paramètres pendant le déroulement du scénario tel que :
    - Ouverture/fermeture des frontières.
    - Confinement / déconfinement de la population.
 - met à disposition de l'utilisateur un bouton pause/play pour lui laisser le temps d'analyser les données.
 - dispose d'un bouton permettant de mettre fin à la simulation, qui redirigera l'utilisateur vers un espace de notes ou il pourra rédiger et sauvegarder toutes ces notes auxquelles il aura bien évidemment accès plus tard.


 ## Usage ( avec image dans le rapport )

 ### sur un terminal :

 1. cloner le répertoire :
     ```
    git clone https://gaufre.informatique.univ-paris-diderot.fr/verhaegh/Projet-info-s4.git
    ```
2. Enter dans le dossier du projet :
    ```
    cd Projet-info-s4
    ```
4. exécutez le wrapper gradle (il téléchargera toutes les dépendances, y compris gradle lui-même) :
    ```
    ./gradlew build
    ```
5. exécutez le programme :
    ```
    ./gradlew run
    ```


 ### sur eclipse :

 1. File -> Import -> Gradle -> Existing Gradle Project -> Next -> Selectionner le répertoire du projet -> Finish -> Run Configurations -> Gradle Task -> Projet-info-s4 -> application -> run


 ### sur intelliJ :

 1. Open -> Selectionner le répertoire du projet -> Tasks -> application -> run

## Participants au projet :
    ABDELLATIF Kenzi : k.abdellatif03@gmail.com
    HANNA Destiny : destiny.k.h@outlook.com
    SIMONNET William : william.mpsim@gmail.com
    VERHAEGHE Jean-Yves : thevanderer@gmail.com
