# buytown

Выпускной проект Android Kotlin: Приложение для продавцов на маркетплейсе
Описание проекта
Приложение предназначено для продавцов на маркетплейсе и предоставляет функциональность для управления товарами и учетной записью продавца. Приложение включает в себя следующие возможности:

Авторизация пользователей
Просмотр информации о выложенных товарах
Добавление новых товаров для продажи
Просмотр и редактирование данных учетной записи продавца
Экраны
1. Главный экран (Авторизация)
   На этом экране пользователь вводит свои учетные данные (логин и пароль) для входа в систему. В случае успешной авторизации переходит на экран списка товаров. Если пользователь не зарегистрирован, он может перейти на экран регистрации.

2. Список товаров
   На этом экране отображаются все товары, которые пользователь выложил на продажу. Пользователь может просмотреть детальную информацию о каждом товаре, а также перейти к добавлению нового товара.

3. Добавление товара
   Этот экран позволяет пользователю добавить новый товар для продажи. Пользователь вводит название товара, его описание, цену, загружает фото, выбирает категорию и т.д. После заполнения всех полей, пользователь нажимает кнопку "Добавить", и товар появляется в списке товаров.

4. Личный кабинет
   На этом экране отображается информация учетной записи продавца. Пользователь может просмотреть свои данные, а также изменить их при необходимости (например, изменить пароль, адрес электронной почты и т.д.).

Технологии
Архитектура
Single Activity Application: В приложении используется одна активити, и навигация между экранами организована через фрагменты.
MVVM (Model-View-ViewModel): Архитектура презентационного слоя включает в себя ViewModel и LiveData/StateFlow из Android Architecture Components.
Навигация: Для навигации используется библиотека Navigation Component.
Сетевое взаимодействие
HTTP клиент: Retrofit
Сериализация/десериализация JSON: Gson
Асинхронность
Корутины: Kotlin Coroutines для выполнения асинхронных операций.
DI (Dependency Injection)
Hilt
Тестирование
Unit-тесты: Покрыто тестами два класса.
UI-тесты: Написаны тесты для одного пользовательского сценария.
Функциональность
Авторизация

Ввод логина и пароля
Переход на экран регистрации
Проверка учетных данных и вход в систему в случае успеха
Просмотр выложенных товаров

Список товаров
Детальная информация о товаре (название, описание, цена, фото, категория и т.д.)
Добавление нового товара

Ввод информации о товаре (название, описание, цена, фото и т.д.)
Выбор категории
Загрузка фото
Подтверждение добавления товара
Личный кабинет

Просмотр данных учетной записи
Редактирование данных (пароль, адрес электронной почты и т.д.)