# teleg_bot
Telegram-бот – помощник в подготовке к ЕГЭ по информатике
=========================================================
Задачи:    
-------
- [X] Создание консольного бота, добавление команд, создание telegram-бота, добавление возможных команд и соответствующих им кнопок     
- [X] Возможность отслеживать время выполнения задания    
- [ ] Считать набранные очки, вести рейтинг всех пользователей, подсчёт ошибок по темам    
- [ ] Организовать возможность брать задачу из интернета          
- [ ] Возможность оценивать сложность задания, оставлять комментарии, регулирования сложности заданий для пользователя        

Возможности бота:
-----------------
1.Основные команды:        
    -/start - начало работы        
    -/help - краткая справка    
    -/user_name - зарегистрировать свое имя    
    -/exercise - выслать задание        
    -/time_ex - выполнение задания на время    
    -/my_point - посмотреть количество набранных баллов    
    -/top - вывод рейтинга пользователей    
    -/mistake - анализ частых ошибок по темам    
    
Задача №2
---------
Возможность отслеживать время выполнения задания    
Например:    
/time_ex(функция выполнения задания на время)    
Введите номер задания    
5    
...Задание...    
154    
Правильный ответ!    
Время выполнения: 80 сек    

Задача №3
---------
Возможность отслеживать время выполнения задания    
Например:    
/my_point    
Ваш текущий балл: 171(количество правильно решенных задач)    
/top    
1.user1 - 200 баллов    
2.user2 - 190 баллов     
3.user3 - 180 баллов    
...    
5. Вы - 171 балл    
/mistake    
Системы счисления - 0 ошибок    
Логические выражения - 5 ошибок    
...    
Вам стоит обратить внимание на тему - Логические выражения, задания 18, 23    
