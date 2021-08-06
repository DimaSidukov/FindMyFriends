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
* Пофиксить функции `getListOfUsersWithCities()` и `getExclusiveIndices()`, последнюю можно вообще реализовать внутри первой, так как в последней происходит слишком много ненужных действий.
* Сделать `UserLocationData` типа Parcelable с помощью аннтоации `@Parcelize`, она не подключена, нужно будет её подключить
* Передавать массив пользователей в качестве аргумента в `startMapActivity()`
* Пофиксить доступ к приватным переменным в презентере

Check: https://ru.stackoverflow.com/questions/1164863/moxy-android-mvp-%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D0%B2-basepresenter