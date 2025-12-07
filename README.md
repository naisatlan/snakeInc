# Questions de cours

## Java Fundamentals et OOO concepts

<ins>Class</ins> : Une classe permet de créer des objets avec des attributs et des méthodes. Elle définit le cadre de ces objets, un nouveau type. Ex : Snake, Apple ou Basket dans le code qui définissent à quoi doivent ressembler les serpents, les pommes et les paniers. 
'''
public class Snake {
'''

<ins>Object</ins> : Un object est une instance concrète d'une classe. Par exemple, dans Game.py, on crée avec new une grille :
'''
grid = new Grid();
''' 

<ins>Primitive types<\ins> : Ce sont les types natifs de Java : int, long, double, float, char, boolean, byte, short. Ils ne sont pas des objets.

<ins>Encapsulation<\ins> : L'encapsulation est un principe fondateur de la POO. Il s'agit de cacher les détails internes d'une classe et contrôler l’accès à son état via des méthodes publiques (getters, setters, etc.). On déclare alors les champs en private et on crée des méthodes pour y accéder.

<ins>Properties<\ins> : Attributs d'un objet, état d'un objet. 

<ins>Getter and setter<\ins> : Les getters et les setters permettent d'accéder et de modifier des objets sans toucher directement aux attributs. Cela permet de respecter le principe d'encapsulation. Par exemple, avec Lombok : 
'''
@Getter
private int x;
'''

<ins>Final<\ins> : Mot clef qui définit une variable ou une fonction qui ne pourra plus être changée par la suite. Exemple dans Game.py : 
'''
private final Grid grid;
'''

<ins>Instantiation of objects<\ins> : L'instanciation des objets en java se fait avec le mot clef new. On crée des instances de la classe, c'est-à-dire des objets.
'''
grid = new Grid();
''' 

<ins>Constructors<\ins> : Les constructeurs permettent d’initialiser un objet lors de sa création.. Selon le constructeur, on doit mettre plus ou moins de paramètres pour créer son objet. Il existe aussi des constructeur par défaut et des constructeurs de recopie. 
'''
    public Basket(Grid grid) {
        apples = new ArrayList<>();
        this.grid = grid;
    }
'''

<ins>Static fields<\ins> : Les variables statiques sont partagées par toutes les instances d'une classe, elles appartiennent à la classe et non aux instances. 
'''
public static Integer TILES_X = 30;
'''

<ins>Static methods<\ins> : Elle peuvent être appelées sans créer d'instance de la classe. Elle peuvent aussi accéder aux champs statiques des données et modifier leur valeur.
'''
    public static Apple createAppleInCell(Cell cell) {
        Apple apple = new Apple();
        cell.addApple(apple);
        return apple;
    }
'''

<ins>Particularity of "static"<\ins> : "Static" signifie qu'il est lié à la classe elle-même et non à un objet particulier.

<ins>Composition<\ins> : Une composition est lorsqu'un objet est une partie d'un autre objet. On va simplement utiliser la classe correspondante dans la définition de l'objet contenant. 
Par exemple, la classe Game est composée d'une grille, d'un panier, etc...
'''
    public Game() {
        grid = new Grid();
        basket = new Basket(grid);
	...
    }
'''

<ins>Inheritance<\ins> : L'héritage est lorsqu'une classe est une spécialisation d'une autre. Elle a les attributs et les méthodes de sa classe mère mais peut les personnaliser et en ajouter de nouvelles. 
Par exemple, la classe Chat hériterait de la classe Animal : 
'''
public class Chat extends Animal{
}
'''

<ins>Interface<\ins> : Une interface décrit un ensemble de méthodes que les classes qui l’implémentent doivent fournir.
'''
public interface AppleEatenListener {

    void onAppleEaten(Apple apple, Cell cell);

}
'''

<ins>Polymorphism<\ins> : Le polymorphisme est un concept clef en java, c'est le fait de pouvoir définir des méthodes avec le même nom mais avec une signature différente. On peut ainsi redéfinir une méthode dansune sous classe ou encore utiliser le type parent.

<ins>Static vs dynamic types<\ins> : Le type statique est le type vu par le compilateur, celui qui est écrit dans le code tandis que le type dynamique est le type réel de l’objet en mémoire pendant l’exécution.
'''
Snake snake = new Anaconda(...);
'''
Ici, le type statique est Snake mais le type dynamique est Anaconda.

<ins>Separations of concerns (SoC)<\ins> (design principle) : Le SoC est principe fondamental du développement qui consiste à diviser un problème complexe en petites parties plus facilement traitable. 

<ins>Collections<\ins> : Les collections sont des structures de données fournies par Java pour gérer des ensembles d’objets : List, Set, Map, etc.
'''
private final List<Cell> body = new ArrayList<>();
'''

<ins>Exceptions<\ins> : Les exceptions sont lancées dans les cas définis par le programme. Elles sont redéfinies pour correspondre aux cas précis où elles sont lancées : 
'''
public class OutOfPlayException extends Exception {

    public OutOfPlayException() {

    }
}
'''

<ins>Functional interfaces / Lambda<\ins> : Une interface fonctionnelle est une interface qui a seulement une méthode abstraite. Elle peut donc utiliser des expressions lambda. Exemple d'expression lambda : 
'''
Add add = (a,b) -> a + b;
'''


