# FindMyFriends
Mark your VK friends' cities location on map

Используемые технологии:
* VK Android SDK
* Retrofit
* Moshi
* ROOM
* Moxy
* Dagger2

Что осталось сделать:
* Функцию для проверки сети, для которой требуется контекст, перенести в даггер, чтобы больше нигде в презентер не нужно было передавать контекст
* OmegaMoxy накладывает определённые ограничения, связанные с конструктором, потому разобраться, как всю эту передачу параметром обеспечить
* Вызывать AppComponent в презентере, так как вся инжекция затем переходит в презентер
* Использовать какую-то фичу из OmegaBase или создать свой coroutineScope, чтобы не пришлось прибегать к использованию GlobalScope.launch
* Абстрагировать удаление базы данных, чтобы о ней не знал ни презентер ни вью
* Все строки перенести в string.xml в ресурсах

[Репозиторий](https://github.com/KostiaLeo/moxy-dagger2-rxjava) с MVP, на который следует опираться

Ещё один [репозиторий](https://github.com/aleesha711/Android-App-with-MVP-and-Room) с MVP 

Check: https://ru.stackoverflow.com/questions/1164863/moxy-android-mvp-%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D0%B2-basepresenter
