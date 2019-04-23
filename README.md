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
      + [Formulario Billetera](#formbilletera)
      + [Solicitud de Token de Pago para BSA en Decidir](#tokendecidir)
      + [Ejecución del Pago para BSA en Decidir](#pagodecidir)
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
#### Diagrama de secuencia (ejemplo integración con Decidir)

![Diagrama de secuencia](https://raw.githubusercontent.com/guillermoluced/docbsadec/master/img/bsa-decidir-secuence.png)

Para acceder al servicio, los vendedores podrán adherirse en el sitio exclusivo de Botón o a través de su ejecutivo comercial. En estos procesos se generará el usuario y clave para este servicio.

<a name="discover"></a>
### Descubrimiento de medios de pago
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
### Transacciones
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

<a name="formbilletera"></a>
### Formulario Billetera

Para abrir el formulario se debe agregar un archivo javascript provisto por TodoPago e instanciar la API Javascript tal cual se muestra en el ejemplo correspondiente.

##### Endpoints:
+ Ambientes de pruebas: https://forms.integration.todopago.com.ar/resources/TPBSAForm.js
+ Ambiente Produccion: https://forms.todopago.com.ar/resources/TPBSAForm.min.js

```html

<html>
    <head>
        <title>Formulario de pago TP</title>
        <meta charset="UTF-8">
        <script src="https://forms.integration.todopago.com.ar/resources/TPBSAForm.js"></script>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <script type="text/javascript">
        </script>
    </head>
	<body>
	    <script>
		var success = function(data) {
		    console.log(data);
		};
		var error = function(data) {
		    console.log(data);
		};
		var validation = function(data) {
		    console.log(data);
		}
		window.TPFORMAPI.hybridForm.initBSA({
		    publicKey: "requestpublickey",
		    merchantAccountId: "merchant",
		    callbackCustomSuccessFunction: "success",
		    callbackCustomErrorFunction: "error",
		    callbackValidationErrorFunction: "validation"
		});
	    </script>
	</body>
</html>

```

![Formulario de pago](https://raw.githubusercontent.com/TodoPago/imagenes/master/bsa/formulario-bsa_medios_pago.png)

##### Respuesta

Si la compra fue aprobada el formulario devolverá un JSON con la siguiente estructura.

```C#

{
"ResultCode":1,
"ResultMessage":"El medio de pago se selecciono correctamente",
"Action":"accion"
"SessionId":"DB37611F-6510-2423-1223-1C4F76F04A0D",
"IdCuenta":"41703",
"Token":"4507991692027787",
"MerchantAccountId": "46523",
"BankId":"17",
"CardNumberBin": 450799,
"FourLastDigitsOfCardNumber":"7783",
"PaymentMethodID":"42",
"SecurityCodeCheck": "false",
"SelectorClaveFlag": "1",
"TokenDate": "20180427",
"TokenizationFlag": "false",
"DatosAdicionales": {
	"tipoDocumento": "DNI",
	"numeroDocumento": "45998745",
	"generoCuentaCompradora": "M",
	"nombre": "Comprador",
	"apellido": "BSA",
  	"permiteObtenerMP": false
},
"VOLATILE_ENCRYPTED_DATA": "YRfrWggICAggsF0nR6ViuAgWsPr5ouR5knIbPtkN+yntd7G6FzN/Xb8zt6+QHnoxmpTraKphZVHvxA=="
"BSA":true
} 

```

**Nota**: Los campos queridos por decidir son el "Token" y "VOLATILE_ENCRYPTED_DATA".

<a name="tokendecidir"></a>
### Solicitud de Token de Pago para BSA en Decidir
Para implementar los servicios de Decidir en Java se deberá utilizar el servicio /tokens. [Documentacion solicitud de token de pago bsa](https://decidirv2.api-docs.io/1.0/transacciones-simples/solicitud-de-token-de-pago-para-bsa). Ademas es necesario tener disponibles las claves publicas y privadas provistas por Decidir para utilizar dicho servicio.

Campo       | Descripción           | Tipo de dato | Ejemplo
------------|-----------------------|--------------|--------
public_token| Campo String que se obtiene en la respuesta del formulario de pago de Todopago ("Token":"4507991692027787")| String     | 4507994025297787
volatile_encrypted_data| Este se obtiene en la respuesta del formulario de pago de Todopago ("VOLATILE_ENCRYPTED_DATA": "YRfrWggICAggsF0nR6ViuAgWsPr5ouR5knIbPtkN+yntd7G6FzN/Xb8zt6+QHnoxmpTraKphZVHvxA==")| String     | YRfrWggICAggsF0nR6ViuAgWsPr5ouR5knIbPtkN+yntd7G6FzN/Xb8zt6+QHnoxmpTraKphZVHvxA==
public_request_key| Este se obtiene a partir del publicRequestKey, en la respuesta del servicio Transaction (publicRequestKey = "0e6d1f45-a85e-480f-a98f-5f18cf881b9b")| String | publicRequestKey
flag_security_code|  | String     | 0
flag_tokenization|  | String     | 0
flag_selector_key|  | String     | 1
flag_pei| Se define si PEI esta habilitado | String | 1
card_holder_name| Nombre del titular de la tarjeta | String | "Pepe"
card_holder_identification.type| tipo de identificacion | String | "dni"
card_holder_identification.number| Numero de identificacion | String | "23968498"
fraud_detection.device_unique_identifier | Numero unico de identificacion | String | "12345"
```java
{
  "public_token": "[public_token]",
  "volatile_encrypted_data": "[volatile_encrypted_data]",
  "public_request_key": "12345678",
  "issue_date": "[issue_date]",
  "flag_security_code": "0",
  "flag_tokenization": "0",
  "flag_selector_key": "1",
  "flag_pei": "1",
  "card_holder_name": "Horacio",
  "card_holder_identification": {
    "type": "dni",
    "number": "23968498"
  },
  "fraud_detection": {
    "device_unique_identifier": "12345"
  }
}
```
Este servicio requiere los siguientes atributos de la respuesta del Formulario de pago Todopago y del servicio Transaction:
+ [Token](#formularioresponse) para el campo "availablePaymentMethods.add('1')"
+ [VOLATILE_ENCRYPTED_DATA](#formularioresponse) para el campo "tokensData.volatile_encrypted_data"
+ [publicRequestKey](#publicRequestKey) para el campo "tokensData.public_request_key"
<a name="tokenresponse"></a>
#### Respuesta:

```java
{
	{
	   "id": "708fe42a-c8f9-4468-8029-6d06dc3fca9a",
	   "status": "active",
	   "card_number_length": 16,
	   "date_created": "2019-01-11T12:12Z",
	   "bin": "450799",
	   "last_four_digits": "4905",
	   "security_code_length": 0,
	   "expiration_month": 8,
	   "expiration_year": 19,
	   "date_due": "2019-01-11T14:42Z",
	   "cardholder": {
	       "identification": {
	           "type": "dni",
	           "number": "33222444"
	       },
	       "name": "Comprador"
	   }
	}
}
```
El servicio [Decidir Payment](#pagodecidir) requiere el token devuelto en el Request en el campo **id** :"708fe42a-c8f9-4468-8029-6d06dc3fca9a".

<a name="pagodecidir"></a>
### Ejecución del Pago para BSA en Decidir
Luego de generar el Token de pago con el servicio anterior se deberá utilizarlo en el servicio /payments como indica la documentación. [Documentacion ejecución del Pago para BSA](https://decidirv2.api-docs.io/1.0/transacciones-simples/ejecucion-del-pago-para-bsa)

|Campo | Descripcion  | Oblig | Restricciones  |Ejemplo   |
| ------------ | ------------ | ------------ | ------------ | ------------ |
|id  | id usuario que esta haciendo uso del sitio, pertenece al campo customer (ver ejemplo)  |Condicional, si no se enviar el Merchant este campo no se envia  |Sin validacion   | user_id: "marcos",  |
|email  | email del usuario que esta haciendo uso del sitio (se utiliza para tokenizacion), pertenece al campo customer(ver ejemplo)  |Condicional   |Sin validacion   | email: "user@mail.com",  |
|ip_address  | IP del comercio | Condicional |Sin validacion   | ip_address: "192.168.100.2",  |
|site_transaction_id   | nro de operacion  |SI   | Alfanumerico de hasta 39 caracteres  | "prueba 1"  |
| site_id  |Site relacionado a otro site, este mismo no requiere del uso de la apikey ya que para el pago se utiliza la apikey del site al que se encuentra asociado.   | NO  | Se debe encontrar configurado en la tabla site_merchant como merchant_id del site_id  | 28464385  |
| token  | token generado en el servicio token de Decidir, se puede obtener desde el campo id de la respuesta. Ejemplo: "id" : "708fe42a-c8f9-4468-8029-6d06dc3fca9a"  |SI   |Alfanumerico de hasta 36 caracteres. No se podra ingresar un token utilizado para un  pago generado anteriormente.   | ""  |
| payment_method_id  | id del medio de pago  |SI  |El id debe coincidir con el medio de pago de tarjeta ingresada.Se valida que sean los primeros 6 digitos de la tarjeta ingresada al generar el token.    | payment_method_id: 1,  |
|bin   |primeros 6 numeros de la tarjeta   |SI |Importe minimo = 1 ($0.01)  |bin: "456578"  |
|amount  |importe del pago   |  SI| Importe Maximo = 9223372036854775807 ($92233720368547758.07) |amount=20000  |
|currency   |moneda   | SI|Valor permitido: ARS   | ARS  |
|installments   |cuotas del pago   | SI|"Valor minimo = 1 Valor maximo = 99"     |  installments: 1 |
|payment_type   |forma de pago   | SI| Valor permitido: single / distributed
|"single"   |
|establishment_name   |nombre de comercio |Condicional   | Alfanumerico de hasta 25 caracteres |  "Nombre establecimiento"  |

#### Ejemplo:

```java
{
  "site_transaction_id": "[ID TRANSACCION]",
  "token":"9ddca765-6abd-49d1-8d1b-d68db23bf348"
  "payment_mode": "bsa",
  "customer": {
    "id": "maxi",
    "email": "maxi@decidir.com",
    "ip_address": "19.168.1.10"
  },
  "payment_method_id": 1,
  "bin": "450799",
  "amount": 200,
  "currency": "ARS",
  "installments": 1,
  "description": "8",
  "payment_type": "single",
  "sub_payments": []
}
```

Este servicio requiere el siguiente atributo de la respuesta del servicio [Token](#tokendecidir) de Decidir:
+ [id](#tokenresponse) para el campo "payment.token"

<a name="pagodecidirresponse"></a>
#### Respuesta:
```java
{
    "id": 1391404,
    "site_transaction_id": "110119_02",
    "payment_method_id": 1,
    "card_brand": "Visa",
    "amount": 2000,
    "currency": "ars",
    "status": "approved",
    "status_details": {
        "ticket": "5746",
        "card_authorization_code": "151936",
        "address_validation_code": "VTE0011",
        "error": null
    },
    "date": "2019-01-11T12:19Z",
    "customer": {
        "id": "user",
        "email": "user@mail.com"
    },
    "bin": "450799",
    "installments": 1,
    "first_installment_expiration_date": null,
    "payment_type": "single",
    "sub_payments": [],
    "site_id": "00030118",
    "fraud_detection": {
        "status": null
    },
    "aggregate_data": null,
    "establishment_name": "prueba desa soft",
    "spv": null,
    "confirmed": null,
    "pan": null,
    "customer_token": "f2931755d7e472d2c553eef9026717a9cb3bb91185c6e44f6c02f8ac46b9659e",
    "card_data": "/tokens/1391404"
}
```
Los datos necesarios para el siguiente servicio [Notification Push](#pushnotification) son **status**, **ticket**, **authorization**.

<a name="notificationPush"></a>
### Notification Push
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
### Obtener credenciales
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
