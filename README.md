# Collectify: a collection managing CLI application

[![CodeFactor](https://www.codefactor.io/repository/github/xviistarplatinum/java_v_var_372799/badge)](https://www.codefactor.io/repository/github/xviistarplatinum/java_v_var_372799)
___

## Лабораторная работа `#5`

<p align="center">
    <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExemY5ZW96enc3c3FhMHZhcXl3YzF4ZWRkMGxqMnQ1NGU3MXo4cmJwdSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/nBYUsPFw8TXTW/giphy.gif"  alt="Collection of stands"/>
</p>

### `Вариант 372799`

## Результат: ${\color{green}95\\%}$

## _Внимание! У разных вариантов разный текст задания!_

Реализовать консольное приложение, которое реализует управление коллекцией объектов в интерактивном режиме. В коллекции
необходимо хранить объекты класса ${\color{red}MusicBand}$, описание которого приведено ниже.

**Разработанная программа должна удовлетворять следующим требованиям:**

- Класс, коллекцией экземпляров которого управляет программа, должен реализовывать сортировку по умолчанию.
- Все требования к полям класса (указанные в виде комментариев) должны быть выполнены.
- Для хранения необходимо использовать коллекцию типа `java.util.LinkedList`
- При запуске приложения коллекция должна автоматически заполняться значениями из файла.
- Имя файла должно передаваться программе с помощью: **аргумент командной строки**.
- Данные должны храниться в файле в формате `json`
- Чтение данных из файла необходимо реализовать с помощью класса `java.io.FileReader`
- Запись данных в файл необходимо реализовать с помощью класса `java.io.OutputStreamWriter`
- Все классы в программе должны быть задокументированы в формате `javadoc`.
- Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутствие прав доступа к
  файлу и т.п.).

**В интерактивном режиме программа должна поддерживать выполнение следующих команд:**

- ${\color{red}help}$: вывести справку по доступным командам
- ${\color{red}info}$: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество
  элементов и т.д.)
- ${\color{red}show}$: вывести в стандартный поток вывода все элементы коллекции в строковом представлении
- ${\color{red}add \space \lbrace element \rbrace}$: добавить новый элемент в коллекцию
- ${\color{red}update id \space \lbrace element \rbrace}$: обновить значение элемента коллекции, id которого равен
  заданному
- ${\color{red}remove \textunderscore by \textunderscore id \space id}$: удалить элемент из коллекции по его id
- ${\color{red}clear}$: очистить коллекцию
- ${\color{red}save}$: сохранить коллекцию в файл
- ${\color{red}execute \textunderscore script \space file \textunderscore name}$: считать и исполнить скрипт из
  указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном
  режиме.
- ${\color{red}exit}$: завершить программу (без сохранения в файл)
- ${\color{red}remove \textunderscore at \space index}$: удалить элемент, находящийся в заданной позиции коллекции (
  index)
- ${\color{red}shuffle}$: перемешать элементы коллекции в случайном порядке
- ${\color{red}history}$: вывести последние 10 команд (без их аргументов)
- ${\color{red}group \textunderscore counting \textunderscore by \textunderscore establishment \textunderscore date}$:
  сгруппировать элементы коллекции по значению
  поля
  establishmentDate, вывести
  количество элементов в каждой группе
- ${\color{red}filter \textunderscore less \textunderscore than \textunderscore number \textunderscore of
  \textunderscore participants \space numberOfParticipants}$: вывести элементы, значение поля numberOfParticipants
  которых меньше заданного
- ${\color{red}print \textunderscore field \textunderscore descending \textunderscore establishment \textunderscore
  date}$: вывести значения поля establishmentDate всех элементов в порядке убывания

**Формат ввода команд:**

- Все аргументы команды, являющиеся стандартными типами данных (примитивные типы, классы-оболочки, String, классы для
  хранения дат), должны вводиться в той же строке, что и имя команды.
- Все составные типы данных (объекты классов, хранящиеся в коллекции) должны вводиться по одному полю в строку.
- При вводе составных типов данных пользователю должно показываться приглашение к вводу, содержащее имя поля (
  например, "Введите дату рождения:")
- Если поле является enum'ом, то вводится имя одной из его констант (при этом список констант должен быть предварительно
  выведен).
- При некорректном пользовательском вводе (введена строка, не являющаяся именем константы в enum'е; введена строка
  вместо числа; введённое число не входит в указанные границы и т.п.) должно быть показано сообщение об ошибке и
  предложено повторить ввод поля.
- Для ввода значений null использовать пустую строку.
- Поля с комментарием "Значение этого поля должно генерироваться автоматически" не должны вводиться пользователем
  в ручную при добавлении.

**Описание хранимых в коллекции классов:**

```java
public class MusicBand {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.LocalDateTime establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Studio studio; //Поле не может быть null
}

public class Coordinates {
    private float x; //Значение поля должно быть больше -584
    private float y;
}

public class Studio {
    private String address; //Поле не может быть null
}

public enum MusicGenre {
    PSYCHEDELIC_ROCK,
    PSYCHEDELIC_CLOUD_RAP,
    SOUL,
    POP,
    BRIT_POP;
}
```

**Отчёт по работе должен содержать:**

1. Текст задания.
2. Диаграмма классов разработанной программы.
3. Исходный код программы.
4. Выводы по работе.

**Вопросы к защите лабораторной работы:**

1. Коллекции. Сортировка элементов коллекции. Интерфейсы `java.util.Comparable` и `java.util.Comparator`.
2. Категории коллекций - списки, множества. Интерфейс `java.util.Map` и его реализации.
3. Параметризованные типы. Создание параметризуемых классов. Wildcard-параметры.
4. Классы-оболочки. Назначение, область применения, преимущества и недостатки. Автоупаковка и автораспаковка.
5. Потоки ввода-вывода в Java. Байтовые и символьные потоки. "Цепочки" потоков (Stream Chains).
6. Работа с файлами в Java. Класс `java.io.File`.
7. Пакет `java.nio` - назначение, основные классы и интерфейсы.
8. Утилита `javadoc`. Особенности автоматического документирования кода в Java.

___

### Диаграмма классов: [UML](Lab5-UML.png)
