# Kanban
Учебный проект, представляющий из себя бэкенд для программы, которая позволит ставить цели, задачи и сроки по проектам, следить и измерять активность и оценивать результаты.

## Проект был реализован в течение 6 спринтов:
1. Реализация моделей: задачи, эпики и подзадачи, и статусы NEW, IN_PROGRESS, DONE. Создание менеджера с мапами для хранения (ключ - айди, значение - задача) и методами создания, обновления, удаления и т.д.
2. Создание интерфейса TaskManager со всеми методами. Создание: - класса InMemoryTaskManager, который хранит данные в оперативной памяти; - класса InMemoryHistoryManager, который хранит историю просмотров задач; - служебного класса Managers который возвращает нужный менеджер.
3. Создание кастомной коллекции и класса CustomLinkedList, который отвечает за хранение истории бех повторов (удаление за О(1)).
4. Создание класса FileBackedTasksManager, который позволяет после каждой операции автоматически сохранять все задачи и их состояние в специальный файл, и восстанавливать данные менеджера из файла при запуске программы.
5. Покрытие кода тестами. Добавление продолжительности задач и даты старта,  проверки на непересечение с другими задачами по времени, вывода приоритетных задач.
6. Создание API на базе встроенного HttpServer:  
HttpTaskServer - API между frontend и backend - слушает порт 8080, принимает запросы и реализует маппинг запросов на методы интерфейса TaskManager;  
HttpTaskManager сохраняет и восстанавливает данные с KVServer;  
KVServer - регистрация клиентов, сохранение и выгрузка данных (ключ - tasks, subtasks или epics; значение - соответствующая коллекция);  
KVTaskClient -  слой между HttpTaskManager и KVServer(создание http запросов к KVServer).

## Стек: ООП, коллекции, исключения, работа с файлами, датами, HTTP, API, JSON, GSON, Junit5, Insomnia.

