# BuyTown

### Выпускной проект Android Kotlin: Приложение для продавцов на маркетплейсе

## Описание проекта
Приложение **BuyTown** предназначено для продавцов на маркетплейсе и предоставляет функциональность для управления товарами и учетной записью продавца. Приложение включает в себя следующие возможности:
- Авторизация пользователей
- Просмотр информации о выложенных товарах
- Добавление новых товаров для продажи
- Просмотр данных учетной записи продавца

## Экраны

### 1. Главный экран (Авторизация)
На этом экране пользователь вводит свои учетные данные (логин и пароль) для входа в систему. В случае успешной авторизации осуществляется переход на экран списка товаров.

### 2. Список товаров
На этом экране отображаются все товары, которые пользователь выложил на продажу.

### 3. Добавление товара
Этот экран позволяет пользователю добавить новый товар для продажи. Пользователь вводит название товара, его описание, цену, загружает фото, выбирает категорию и т.д.

### 4. Личный кабинет
На этом экране отображается информация учетной записи продавца. Пользователь может просмотреть свои данные, а также изменить их при необходимости (например, изменить пароль, адрес электронной почты и т.д.).

## Технологии

### Архитектура
- **Single Activity Application**: В приложении используется одна активити, и навигация между экранами организована через фрагменты.
- **MVVM (Model-View-ViewModel)**
- **Навигация**: Для навигации используется библиотека Navigation Component.

### Сетевое взаимодействие
- **HTTP клиент**: Retrofit
- **Сериализация/десериализация JSON**: Gson

### Асинхронность
- **Корутины**: Kotlin Coroutines для выполнения асинхронных операций.

### DI (Dependency Injection)
- Hilt
