<a name="inicio"></a>
Todo Pago - módulo SDK-JAVA para conexión con Billetera Virtual de Gateways
=======

+ [Instalación](#instalacion)
  + [Versiones de Java soportadas](#Versionesdejavasoportadas)
  + [Generalidades](#general)
+ [Uso](#uso)
    + [Inicializar la clase correspondiente al conector](#initconector)
    + [Ambientes](#test)
    + [Billetera Virtual de Gateways](#BSA)
      + [Diagrama de Secuencia](#bsa-uml)
      + [Descubrimiento de medios de pago](#discover)
      + [Transacciones](#transaction)
      + [NotificationPush](#notificationPush)
      + [Obtener Credenciales](#credenciales)


<a name="instalacion"></a>
## Instalación
Se debe descargar la última versión del SDK desde el botón Download ZIP del branch master.

En caso de utilizar Maven, se puede agregar el jar TodoPago.jar al repositorio local de Maven utilizando la siguiente línea de comando:
```
mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile>
```

Una vez hecho esto se puede agregar la dependencia a este paquete desde el pom.xml
```xml
<dependency>
    <groupId>com.ar.todopago</groupId>
    <artifactId>sdk-java</artifactId>
    <version>1.5.0</version>
</dependency>
```
De ser necesario agregar la siguiente dependencia requerida por TodoPago desde el pom.xml
 ```xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20090211</version>
</dependency>
```

Una vez descargado se deben hacer los siguientes imports.
```java
import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.TodoPagoConector;
import ar.com.todopago.api.model.*;
import ar.com.todopago.api.exceptions.*;
```

El Ejemplo es un proyecto hecho en maven, con un pom.xml que incluye la configuración para importar y exportar las librerías requeridas.

Para agregar el proyecto de ejemplo a Eclipse, una vez descargado, por consola ir hasta su carpeta y ejecutar las siguientes líneas:
```
mvn clean install -Dwtpversion=2.0
mvn eclipse:clean eclipse:eclipse -Dwtpversion=2.0
```
Luego, importar el proyecto normalmente en Eclipse.

<a name="Versionesdejavasoportadas"></a>
#### Versiones de Java soportadas
La versi&oacute;n implementada de la SDK, esta testeada para versiones desde Java 5 en adelante con JAX-WS.

<a name="general"></a>
#### Generalidades
Esta versión soporta únicamente pago en moneda nacional argentina (CURRENCYCODE = 32).

[<sub>Volver a inicio</sub>](#inicio)

<a name="uso"></a>
## Uso
<a name="initconector"></a>
### Inicializar la clase correspondiente al conector.

Si se cuenta con los http header suministrados por Todo Pago

- Crear un Map con dichos http header
```java
Map<String, List<String>> auth = new HashMap<>(String, List<String>);
auth.put(ElementNames.Authorization, Collections.singletonList("PRISMA f3d8b72c94ab4a06be2ef7c95490f7d3"));
```

- Crear una instancia de la clase TodoPago
```java
TodoPagoConector tpc = new TodoPagoConector(TodoPagoConector.developerEndpoint, auth);//End Point developer y http_header provisto por TODO PAGO
```

Si se cuenta el con User y Password del login en TodoPago

- Crear una instancia de la clase TodoPago
```java
TodoPagoConector tpc = new TodoPagoConector(TodoPagoConector.developerEndpoint);//End Point developer
```
- Obtener las credenciales a traves  del m&eacute;todo getCredentials de TodoPago. Ver [Obtener Credenciales](#credenciales)

[<sub>Volver a inicio</sub>](#inicio)

<a name="BSA"></a>
### Billetera Virtual para Gateways

La Billetera Virtual para Gateways es la versión de Todo Pago para los comercios que permite utilizar los servicios de la billetera TodoPago dentro de los e-commerce, respetando y manteniendo sus respectivas promociones con bancos y marcas y números de comercio (métodos de adquirencia). Manteniendo su Gateway de pago actual, y utilizando BVG para la selección del medio de pago y la tokenizacion de la información para mayor seguridad en las transacciones.

<a name="bsa-uml"></a>
#### Diagrama de secuencia

![Diagrama de Secuencia BSA](http://www.plantuml.com/plantuml/png/ZL9BJiCm4Dtd5BDi5roW2oJw0I7ngMWlC3ZJOd0zaUq4XJknuWYz67Q-JY65bUNHlFVcpHiKZWqib2JjACdGE2baXjh1DPj3hj187fGNV20ZJehppTNWVuEEth5C4XHE5lxJAJGlN5nsJ323bP9xWWptQ42mhlXwQAlO0JpOTtZSXfMNT0YFcQzhif1MD0oJfRI22pBJdYYm1jnG-ubinjhZjcXUoQ654kQe1TiafG4srczzpE0-9-iC0f-CSDPgQ3v-wQvtLAVskTB5yHE156ISofG33dEVdFp0ccYoDQXje64z7N4P1iN_cRgZmkU8yH48Gm4JLIA3VJM0UIzrRob2H6s_xl1PAaME38voRqYH28l6DgzJqjxpaegSLE6JvJVIthZNu7BW83BVtAp7hVqTLcVezrr3Eo_jORVD8wTaoERAOHMKgXEErjwI_CpvLk_yS1ZX6pXCrhbzUM0dTsKJRoJznsMUdwOZYMirnpS0)

Para acceder al servicio, los vendedores podrán adherirse en el sitio exclusivo de Botón o a través de su ejecutivo comercial. En estos procesos se generará el usuario y clave para este servicio.

<a name="discover"></a>
#### Descubrimiento de medios de pago
El SDK cuenta con un método para consultar cuales son los medios de pago disponibles.
El método retorna el objeto PaymentMethodsBSA con los medios de pago asignados en el atributo paymentMethodsBSAList.
Se utiliza de la siguiente manera:

```java
PaymentMethodsBSA paymentMethodBSA = new PaymentMethodsBSA();

try {
	paymentMethodBSA = tpc.discoverPaymentMethodBSA();
	List<Map<String, Object>> paymentMethodsBSAList = paymentMethodBSA.getPaymentMethodsBSAList();

} catch (ResponseException e) {
	logger.log(Level.WARNING, e.getMessage());
} catch (ConnectionException e) {
	logger.log(Level.WARNING, e.getMessage());
}
```

<strong>Ejemplo de Respuesta</strong>

```java
List<Map<String, Object>> ()
	Map<string, Object>()
	{  idMedioPago = 1,
	   nombre = AMEX,
	   tipoMedioPago = Crédito,
	   idBanco = 1,
	   nombreBanco = Provincia
	}
```


Campo       | Descripción           | Tipo de dato | Ejemplo
------------|-----------------------|--------------|--------
id          | Id del medio de pago  | numérico     | 42
nombre      | Marca de la tarjeta   | string       | "VISA"
tipo        | Tipo de medio de pago | string       | "Crédito"
idBanco     | Id del banco          | numérico     | 10
nombreBanco | Nombre del banco      | string       | "Banco Ciudad"


[<sub>Volver a inicio</sub>](#inicio)

<a name="transaction"></a>
#### Transacciones
El SDK cuenta con un método que permite registrar una transacción.
El método retorna el objeto TransactionBSA con el resultado de la transacción.
Se utiliza de la siguiente manera:

```java
TransactionBSA transactionBSA = new TransactionBSA();

try {
	transactionBSA = tpc.transaction(trasactionBSA);
	Map<String, Object> response = transactionBSA.getTransactionResponse();

} catch (EmptyFieldPassException e) {
	logger.log(Level.WARNING, e.getMessage());
} catch (ResponseException e) {
	logger.log(Level.WARNING, e.getMessage());
} catch (ConnectionException e) {
	logger.log(Level.WARNING, e.getMessage());
}

```

El parámetro trasactionBSA, debe ser un objeto TransactionBSA con la siguiente estructura:

```java
	TransactionBSA transactionBSA = new TransactionBSA();

	Map<String, Object> generalData = new HashMap<String, Object>();
	generalData.put(ElementNames.BSA_MERCHANT, "1");
	generalData.put(ElementNames.BSA_SECURITY, "PRISMA 86333EFD8AD0C71CEA3BF06D7BDEF90D");
	generalData.put(ElementNames.BSA_OPERATION_DATE_TIME, "201604251556134");
	generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");

	Map<String, Object> operationData = new HashMap<String, Object>();
	operationData.put(ElementNames.BSA_OPERATION_TYPE, "Compra");
	operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
	operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
	operationData.put(ElementNames.BSA_CONCEPT, "compra");
	operationData.put(ElementNames.BSA_AMOUNT, "999,99");

	List<String> list = new ArrayList<String>();
	list.add("1");
	list.add("42");
	operationData.put(ElementNames.BSA_AVAILABLE_PAYMENT_METHODS, list);
    operationData.put(ElementNames.BSA_AVAILABLE_PAYMENT_BANK, list);

	Map<String, Object> buyerPreselection = new HashMap<String, Object>();
	buyerPreselection.put(ElementNames.BSA_PAYMENT_METHODS_ID, "42");
	buyerPreselection.put(ElementNames.BSA_BANK_id, "11");
	operationData.put(ElementNames.BSA_BUYER_PRESELECTION, buyerPreselection);

	Map<String, Object> technicalData = new HashMap<String, Object>();
	technicalData.put(ElementNames.BSA_SDK, "Java");
	technicalData.put(ElementNames.BSA_SDK_VERSION, "2.0");
	technicalData.put(ElementNames.BSA_LANGUAGE_VERSION, "1.8");
	technicalData.put(ElementNames.BSA_PLUGIN_VERSION, "2.1");
	technicalData.put(ElementNames.BSA_ECOMMERCE_NAME, "bla");
	technicalData.put(ElementNames.BSA_ECOMMERCE_VERSION, "3.1");
	technicalData.put(ElementNames.BSA_CM_VERSION, "2.4");

	transactionBSA.setGeneralData(generalData);
	transactionBSA.setOperationData(operationData);
	transactionBSA.setTechnicalData(technicalData);

```

<strong>Ejemplo de Respuesta</strong>

```java
Map<String, Object> response = transactionBSA.getTransactionResponse();
	{  channel = 11,
           publicRequestKey = 411d188b-2c6d-4d39-977c-b6d9de119c80,
           merchantId = 37581
	}
```

#### Datos de referencia

<table>
<tr><th>Nombre del campo</th><th>Required/Optional</th><th>Data Type</th><th>Comentarios</th></tr>
<tr><td>security</td><td>Required</td><td>String</td><td>Campo de autorización que deberá contener el valor del api key de la cuenta del vendedor (Merchant)</td></tr>
<tr><td>operationDatetime</td><td>Required</td><td>String</td><td>Fecha Hora de la invocación en Formato yyyyMMddHHmmssSSS</td></tr>
<tr><td>remoteIpAddress</td><td>Required</td><td>String</td><td>IP desde la cual se envía el requerimiento</td></tr>
<tr><td>merchant</td><td>Required</td><td>String</td><td>ID de cuenta del vendedor</td></tr>
<tr><td>operationType</td><td>Optional</td><td>String</td><td>Valor fijo definido para esta operatoria de integración</td></tr>
<tr><td>operationID</td><td>Required</td><td>String</td><td>ID de la operación en el eCommerce</td></tr>
<tr><td>currencyCode</td><td>Required</td><td>String</td><td>Valor fijo 32</td></tr>
<tr><td>concept</td><td>Optional</td><td>String</td><td>Especifica el concepto de la operación</td></tr>
<tr><td>amount</td><td>Required</td><td>String</td><td>Formato 999999999,99</td></tr>
<tr><td>availablePaymentMethods</td><td>Optional</td><td>Array</td><td>Array de Strings obtenidos desde el servicio de descubrimiento de medios de pago. Lista de ids de Medios de Pago habilitados para la transacción. Si no se envía están habilitados todos los Medios de Pago del usuario.</td></tr>
<tr><td>availableBanks</td><td>Optional</td><td>Array</td><td>Array de Strings obtenidos desde el servicio de descubrimiento de medios de pago. Lista de ids de Bancos habilitados para la transacción. Si no se envía están habilitados todos los bancos del usuario.</td></tr>
<tr><td>buyerPreselection</td><td>Optional</td><td>BuyerPreselection</td><td>Preselección de pago del usuario</td></tr>
<tr><td>sdk</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>sdkversion</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>lenguageversion</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>pluginversion</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>ecommercename</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>ecommerceversion</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
<tr><td>cmsversion</td><td>Optional</td><td>String</td><td>Parámetro de versión de API</td></tr>
</table>
<br>
<strong>BuyerPreselection</strong>
<br>
<table>
<tr><th>Nombre del campo</th><th>Data Type</th><th>Comentarios</th></tr>
<tr><td>paymentMethodId</td><td>String</td><td>Id del medio de pago seleccionado</td></tr>
<tr><td>bankId</td><td>String</td><td>Id del banco seleccionado</td></tr>
</table>

[<sub>Volver a inicio</sub>](#inicio)

<a name="notificationPush"></a>
#### Notification Push
La SDK cuenta con un método que permite registrar la finalización de una transacción.
El método retorna el objeto NotificationPushBSA con el resultado de la notificación.
Se utiliza de la siguiente manera:

```java
NotificationPushBSA notificationPushBSA = new NotificationPushBSA();

try{
	notificationPushBSA = tpc.notificationPush(notificationPushBSA());
	Map<String, Object> response = notificationPushBSA.toMap();

} catch (EmptyFieldPassException e) {
	logger.log(Level.WARNING, e.getMessage());
} catch (ResponseException e) {
	logger.log(Level.WARNING, e.getMessage());
} catch (ConnectionException e) {
	logger.log(Level.WARNING, e.getMessage());
}
```

El parámetro notificationPushBSA, debe ser un objeto NotificationPushBSA con la siguiente estructura:

```java
    Map <String, Object> generalData = new HashMap<String, Object>();
    generalData.put(ElementNames.BSA_MERCHANT, "1");
    generalData.put(ElementNames.BSA_SECURITY, "PRISMA 86333EFD8AD0C71CEA3BF06D7BDEF90D");
    generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");
    generalData.put(ElementNames.BSA_PUBLIC_REQUEST_KEY, "f50208ea-be00-4519-bf85-035e2733d09e");
    generalData.put(ElementNames.BSA_OPERATION_NAME, "Compra");

    Map <String, Object> operationData = new HashMap<String, Object>();
    operationData.put(ElementNames.BSA_RESULT_CODE_MEDIOPAGO, "-1");
    operationData.put(ElementNames.BSA_RESULT_CODE_GATEWAY, "-1");
    operationData.put(ElementNames.BSA_ID_GATEWAY, "8");
    operationData.put(ElementNames.BSA_RESULT_MESSAGE, "Aprobada");
    operationData.put(ElementNames.BSA_OPERATION_DATE_TIME, "201607040857364");
    operationData.put(ElementNames.BSA_TICKET_MUNBER, "7866463542424");
    operationData.put(ElementNames.BSA_CODIGO_AUTORIZATION, "455422446756567");
    operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
    operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
    operationData.put(ElementNames.BSA_AMOUNT, "999,99");
    operationData.put(ElementNames.BSA_FACILITIES_PAYMENT, "03");

    Map <String, Object> tokenizationData = new HashMap<String, Object>();
    tokenizationData.put(ElementNames.BSA_PUBLIC_TOKENIZATION_FIELD, "sydguyt3e862t76ierh76487638rhkh7");
    tokenizationData.put(ElementNames.BSA_CREDENTIAL_MASK, "4510XXXXX00001");

    NotificationPushBSA notificationPushBSA = new NotificationPushBSA(generalData, operationData, tokenizationData);
```

<strong>Ejemplo de Respuesta</strong>

```java
Map<String, Object> response = notificationPushBSA.toMap();
	{  statusCode = -1,
   	   statusMessage = OK
	}
```
#### Datos de referencia

<table>
<tr><th>Nombre del campo</th><th>Required/Optional</th><th>Data Type</th><th>Comentarios</th></tr>
<tr><td>Security</td><td>Required</td><td>String</td><td>Authorization que deberá contener el valor del api key de la cuenta del vendedor (Merchant). Este dato viaja en el Header HTTP</td></tr>
<tr><td>Merchant</td><td>Required</td><td>String</td><td>ID de cuenta del comercio</td></tr>
<tr><td>RemoteIpAddress</td><td>Optional</td><td>String</td><td>IP desde la cual se envía el requerimiento</td></tr>
<tr><td>PublicRequestKey</td><td>Required</td><td>String</td><td>publicRequestKey de la transacción creada. Ejemplo: 710268a7-7688-c8bf-68c9-430107e6b9da</td></tr>
<tr><td>OperationName</td><td>Required</td><td>String</td><td>Valor que describe la operación a realizar, debe ser fijo entre los siguientes valores: “Compra”, “Devolucion” o “Anulacion”</td></tr>
<tr><td>ResultCodeMedioPago</td><td>Optional</td><td>String</td><td>Código de respuesta de la operación propocionado por el medio de pago</td></tr>
<tr><td>ResultCodeGateway</td><td>Optional</td><td>String</td><td>Código de respuesta de la operación propocionado por el gateway</td></tr>
<tr><td>idGateway</td><td>Optional</td><td>String</td><td>Id del Gateway que procesó el pago. Si envían el resultCodeGateway, es obligatorio que envíen este campo</td></tr>
<tr><td>ResultMessage</td><td>Optional</td><td>String</td><td>Detalle de respuesta de la operación.</td></tr>
<tr><td>OperationDatetime</td><td>Required</td><td>String</td><td>Fecha Hora de la operación en el comercio en Formato yyyyMMddHHmmssMMM</td></tr>
<tr><td>TicketNumber</td><td>Optional</td><td>String</td><td>Numero de ticket generado</td></tr>
<tr><td>CodigoAutorizacion</td><td>Optional</td><td>String</td><td>Codigo de autorización de la operación</td></tr>
<tr><td>CurrencyCode</td><td>Required</td><td>String</td><td>Valor fijo 32</td></tr>
<tr><td>OperationID</td><td>Required</td><td>String</td><td>ID de la operación en el eCommerce</td></tr>
<tr><td>Amount</td><td>Required</td><td>String</td><td>Formato 999999999,99</td></tr>
<tr><td>FacilitiesPayment</td><td>Required</td><td>String</td><td>Formato 99</td></tr>
<tr><td>Concept</td><td>Optional</td><td>String</td><td>Especifica el concepto de la operación dentro del ecommerce</td></tr>
<tr><td>PublicTokenizationField</td><td>Required</td><td>String</td><td></td></tr>
<tr><td>CredentialMask</td><td>Optional</td><td>String</td><td></td></tr>
</table>

[<sub>Volver a inicio</sub>](#inicio)
<br>

<a name="caracteristicas"></a>
### Características
<a name="credenciales"></a>
#### Obtener credenciales
El SDK permite obtener las credenciales "Authentification", "MerchandId" y "Security" de la cuenta de Todo Pago, ingresando el usuario y contraseña.<br>
Esta funcionalidad es útil para obtener los parámetros de configuración dentro de la implementación.

- Crear una instancia de la clase User:
```java

public void getCredentials(TodoPagoConector tpc) {

		User user = new User("test@test.com", "test1234");// user y pass de TodoPago

		try {
			user = tpc.getCredentials(user);
			tpc.setAuthorize(getAuthorization(user));// set de la APIKey a TodoPagoConector

		} catch (EmptyFieldException e) {//se debe realizar catch por campos en blanco
			logger.log(Level.WARNING, e.getMessage());
		} catch (MalformedURLException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ResponseException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ConnectionException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		System.out.println(user.toString());
}

	private Map<String, List<String>> getAuthorization(User user) {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		parameters.put(ElementNames.Authorization,Collections.singletonList(user.getApiKey()));

		return parameters;
}
```
