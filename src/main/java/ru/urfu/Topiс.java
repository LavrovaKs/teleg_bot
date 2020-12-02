package ru.urfu;

enum Topic {

    INFO ("Кодирование информации"),//4 7 8 11
    SYSTEMS ("Системы счисления"),//14
    LOGICS("Основы логики, таблицы истинности"),//2 15
    USER("Пользовательский курс"),//1 3 9 10 13
    ALGO ("Теория алгоритмов"),//5 12 16 18
    GAME("Теория игр"),// 19 20 21
    PROGRAM ("Программирование");// 6 17 22 23

    private String exercise;

    Topic(String ex) {
        this.exercise = ex;
    }

    public String getTopic(){
        return exercise;
    }

    @Override
    public String toString(){
        return exercise;
    }
}
