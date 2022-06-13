# testeFilial

 Esse projeto está configurado pra trabalhar em conjunto com o servidor do projeto no link a seguir:
https://github.com/rogerhideo/laravelTesteFilial

Para que funcione a comunicação do módulo android com o servidor, eu não consegui configurar para que ele redirecionasse ao "localhost"
então será necessário que o servidor esteja rodando no IP da máquina que está com os projetos.

Nota: Você pode usar o comando "ifconfig" no windows ou "hostname -I" no Linux para localizar seu IP.
E então rode o servidos com 

    $php artisan serve --host=192.168.100.76:3000
    
Esse mesmo host utilizado no servidor deve ser adicionado ao arquivo desse projeto android:
  app/src/main/java/com/example/testefilial/config/AppConfig.java
  
  Deve ser adicionado na linha 9: Subtituindo o já existente

``` java  private static String serverHost = "http://192.168.100.76:3000";```

> É muito importante que esse endereço IP seja da máquina que está rodando o projeto
<br><br>
Então você pode executar o aplicativo pelo Android Studio em um dispositivo emulado.


Bibliotecas usadas de Terceiros:
    "com.squareup.okhttp3:okhttp-bom:4.9.0"
    "com.squareup.okhttp3:okhttp"
    "com.squareup.okhttp3:logging-interceptor"
