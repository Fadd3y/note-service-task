## Запуск

1) docker build -t note-service .
  
2) docker run -d --name note-service -p 8080:8080 note-service

## API

#### - Получить все заметки

GET /notes

#### - Получить заметки по тегу

GET /notes?tag={tag}

#### - Получить заметку по id

GET /notes/{id}

#### - Создать заметку

POST /notes
```
{
    "title": "hello",
    "content": "world",
    "tags": [
        "work",
        "rest"
    ]
}
```


#### - Изменить заметку

PUT /notes/{id}

```
{
    "title": "hello",
    "content": "world",
    "tags": [
        "work",
        "rest"
    ]
}
```

#### - Удалить заметку

DELETE /notes/{id}
