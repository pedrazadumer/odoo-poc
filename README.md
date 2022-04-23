# Prueba de Concepto | XML-RPC ODOO

## Configuración Inicial

1. Iniciar los contenedores:

```
$ docker-compose up --build
```

2. Ingresar a la siguiente URL: http://localhost:8069/

3. Ingresar los siguientes valores en la pantalla de configuración inicial de ODOO:
    - **Master Password:** odoo
    - **Database:** odoo
    - **Email:** odoo@odoo.com
    - **Password:** odoo
    - **Seleccionar la opción "Demo data"**

4. Instalar el módulo "Sales"

5. Actualizar la contraseña del usuario "demo".

    - Ir a Settings > Users & Companies > Users
    - Seleccionar el usuario "demo"
    - Hacer click en el botón "Action", seleccionar "Change Password" y cambiar la contraseña a "demo" (Usuario: demo, Contraseña: demo).


## Detener y Limpiar

1. Para detener los contenedores basta con presionar Ctrl + C

2. Para limpiar los volúmenes creados por el proyecto se puede utilizar los siguientes comandos.

**ADVERTENCIA:** Los comandos presentados a continuación limpian las imágenes, contenedores y sobre todo volúmenes sin uso, lo que quiere decir que **SI** el usuario tiene algún contenedor, para otros proyectos no relacionados, que utilice volúmenes para la persistencia de estado, como una base de datos, y se quiere conservar el estado de dicha base de datos **NO ES CONVENIENTE** utilizar dichos comandos, es preferible en esos casos buscar específicamente los contenedores y volúmenes relacionados con esta prueba de concepto y removerlos.

*En Windows (Power Shell):*

```
$ docker container prune -f; docker image prune -f; docker volume prune -f
```

*En Mac/Linux (Terminal) o Windows (Git Bash):*
```
$ docker container prune -f && docker image prune -f && docker volume prune -f
```