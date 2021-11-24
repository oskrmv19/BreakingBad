#Enviroment
------------------------------------------------------------
- Gradle 7.0.2
- Build time:   2021-05-14 12:02:31 UTC
- Revision:     1ef1b260d39daacbf9357f9d8594a8a743e2152e

- Kotlin:       1.4.31
- Groovy:       3.0.7
- Ant:          Apache Ant(TM) version 1.10.9 compiled on September 27 2020
- JVM:          11.0.8 (JetBrains s.r.o 11.0.8+10-b944.6916264)
- OS:           Mac OS X 10.16 x86_64

- Android Studio 4.2.2


## Librerias y componentes: ##
  - ViewModel
  - LiveData
  - Lifecycle
  - Flow
  - Retrofit
  - Room Database
  - Hilt ( Dependency Injection)
  - Navigation component
  - Safe Args
  - Coroutines
  - Glide
  - Timber
  
## Tests
  - Mockk
  - Robolectric
  - Junit

## Clean Architecture: ##

 - data # Data layer 
	- db # Room Database, Entities, DataConverters 
	- mapper # Transformar entidades a dto y viceversa
	- datasource # Definición de fuente de datos local o remoto
	- repository #Implementación del repository, consumo de servicios y guardar en bd
	- api #Definición interfaz retrofit para consumo de rest services
	
 - di # Hilt configuration
	- module(s) # definición de modulos para ser usados en los componentes
 
 - presentation # View layer
	- base # Clases y Objetos base con codigo común para ser heredados
	- extensions # Creación de kotlin exts functions de utilidad para facilitar el desarrollo
	- activities, adapters, fragments, listeners and viewmodels )

- Domain # Domain layer
	- dto # data class retornados por webservice
	- failure # definición de excepciones
	- repository # definición de repositorios de datos(Interfaces)
	- request # creación de jsons, mapas a ser enviados al webservice

	- usecase # casos de uso de la app a ser usados por los viewmodels

<img width="460" alt="Captur 1" src="https://user-images.githubusercontent.com/87392655/143149564-bf66860f-a1ee-4e3f-a9f3-cf22ddf3b161.png">
<img width="461" alt="Captura de Pantalla 2021-11-23 a la(s) 6 54 19 p  m" src="https://user-images.githubusercontent.com/87392655/143149602-fa1820d2-6150-4b42-9d92-912e85bbcdff.png">


<img width="461" alt="Captura de Pantalla 2021-11-23 a la(s) 6 54 33 p  m" src="https://user-images.githubusercontent.com/87392655/143149610-0ebec11b-ea8d-4b91-92ad-2cf7f4bae271.png">




