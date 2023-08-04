## Дипломная работа по курсу Java-разработчик

# О проекте 

Spring-boot приложение, которое реализует бэкенд-часть сайта объявлений.

Разработана согласно [техническому заданию](https://skyengpublic.notion.site/64113e0a2641475c9ad9bea93144afff).


Реализован следующий функционал:
* Авторизация и аутентификация пользователей
* Распределение ролей между пользователями: пользователь и администратор
* CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления и комментарии, а пользователи — только свои
* Под каждым объявлением пользователи могут оставлять отзывы
* В заголовке сайта можно осуществлять поиск объявлений по названию.
* Показывать и сохранять картинки объявлений, аватарки пользователей


## Стек:
* Java 11
* Maven
* Spring Boot
* Spring Data
* Spring Security
* REST
* GIT
* Lombok
* PostgresSQL
* Liquibase
* Mockito

## Инструкция по запуску
1. Для запуска фронтенд-части требуется запустить Dockdr и через терминал запкстить docker образ `docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.16`
2. Для запуска бэкенд-части требуется:
   * Клонировать проект в среду разработки
   * В файле `application.properties` изменить значения для `datasource.url`, `datasource.username`, `datasource.password`
   * Запустить main метод в классе `HomeworkApplication`\
\
После выполнения этих действий, откройте браузер и перейдите по этому url-адресу `http://localhost:3000`
