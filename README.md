## API для обычных пользователей

`POST`
`/topic`
Создать топик с одним сообщением

`PUT`
`/topic`
Обновить существующий топик

`GET`
`/topic`
Получить все топики

`GET`
`/topic/{topicId}`
Получить все сообщения в топике

`POST`
`/topic/{topicId}/message`
Создать новое сообщение в топике

`PUT`
`/topic/{topicId}/message`
Обновить сообщение (только своё)

`DELETE`
`/message/{messageId}`
Удалить сообщение (только своё)


## API для администраторов

`PUT`
`/topic/{topicId}/message`
Обновить сообщение (любое)

`DELETE`
`/message/{messageId}`
Удалить сообщение (любое)

`DELETE`
`/topic/{topicId}`
Удалить топик


## Регистрация и авторизация

`POST`
`/auth/register`
Зарегистрировать пользователя (username, password)

`POST`
`/auth/login`
Авторизоваться


## Данные

- Автоматически регистрируются администратор (admin, admin) и пользователь (qwerty, 123)
- Добавляются 3 топика и 10 сообщений
- Пагинация
