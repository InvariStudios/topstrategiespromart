# Reto Técnico Promart TOPSTRATEGIES

El aplicativo está orientado a demostrar el uso de diferentes tecnologias
arquitecturas, componetes y demas piezas de software que permiten medir
la experiencia de un desarrollador.

## Características

- **XML**: El app usa xml para el diseño de UI
- **Clean Architecture**: Usamos la arquitectura clean (data,domain,,presentatation)
- **Navigation**: Utilizamos navigation para ir de un fragmento a otro
- **Dagger Hilt**: Usamos libreria dagger hilt para la inyección de dependencias
- **Work makager**: Usamos work manager para el guardado de registros de servicio
- **Retrofit**: Usamos esta libreria para hacer los consumos de servicio del APIREST
- **Firebase**: Usamos esto para remote config (access_token) y Autenticación con google
- **Preferences**: Usamos preferencias para guardar el access_token obtenido de remoteconfig 
- **MVVM**: Utilizamos mvvm como arquitectura 
- **Principios SOLID**: Las clases mantienen el uso de solid en sus diferentes implementaciones
- **Testing**: Pruebas a nivel de casos de uso
- **Flow**: Se integro un ejemplo de uso de flow obteniendo el listado de peliculas
- **Livedata**: Se integro un ejemplo de uso de corutinas y livedata para el listado de peliculas
- **Room**: Se integro room para guardar el listado de peliculas
- **Sonar Lint**: Se ha usado un plugin para verificar que las clases sean de calidad

## Consideraciones

- Tengamos en cuenta que se ajunta con el proyecto un apk para pruebas de funcionamiento
- El login está con Google Firebase por ende compilarlo desde otro proyecto hace que sea necesario agregar el SHA1 en Firebase
