# FindMyFriends
Mark your VK friends' cities location on map

Шаги:
* Авторизация ВК :white_check_mark:
* Информация о городах друзей :white_check_mark:
* Отметка городов на карте :white_check_mark:

Используемые технологии:
* VK Android SDK
* Retrofit
* Moshi
* ROOM
* Moxy
* Dagger2

Что осталось доделать:
* При смене аккаунта происходит смешение данных в базе данных. Очищать базу данных при смене аккаунта
* При первом запуске приложения данные пока данные загружаются в базу данных, попутно вызывается функция по получение данных из БД в адаптер, из-за этого RecyclerView пустой. Нужно как-то наладить корутины.

Требования по заданию:
* Пофиксить корутины с ДБ

[Репозиторий](https://github.com/KostiaLeo/moxy-dagger2-rxjava) с MVP, на который следует опираться

Ещё один [репозиторий](https://github.com/aleesha711/Android-App-with-MVP-and-Room) с MVP 
